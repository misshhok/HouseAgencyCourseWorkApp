package com.bsu.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.bsu.app.IntegrationTest;
import com.bsu.app.domain.Cities;
import com.bsu.app.repository.CitiesRepository;
import com.bsu.app.service.criteria.CitiesCriteria;
import com.bsu.app.service.dto.CitiesDTO;
import com.bsu.app.service.mapper.CitiesMapper;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link CitiesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CitiesResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/cities";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CitiesRepository citiesRepository;

    @Autowired
    private CitiesMapper citiesMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCitiesMockMvc;

    private Cities cities;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cities createEntity(EntityManager em) {
        Cities cities = new Cities().title(DEFAULT_TITLE);
        return cities;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cities createUpdatedEntity(EntityManager em) {
        Cities cities = new Cities().title(UPDATED_TITLE);
        return cities;
    }

    @BeforeEach
    public void initTest() {
        cities = createEntity(em);
    }

    @Test
    @Transactional
    void createCities() throws Exception {
        int databaseSizeBeforeCreate = citiesRepository.findAll().size();
        // Create the Cities
        CitiesDTO citiesDTO = citiesMapper.toDto(cities);
        restCitiesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(citiesDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Cities in the database
        List<Cities> citiesList = citiesRepository.findAll();
        assertThat(citiesList).hasSize(databaseSizeBeforeCreate + 1);
        Cities testCities = citiesList.get(citiesList.size() - 1);
        assertThat(testCities.getTitle()).isEqualTo(DEFAULT_TITLE);
    }

    @Test
    @Transactional
    void createCitiesWithExistingId() throws Exception {
        // Create the Cities with an existing ID
        cities.setId(1L);
        CitiesDTO citiesDTO = citiesMapper.toDto(cities);

        int databaseSizeBeforeCreate = citiesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCitiesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(citiesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cities in the database
        List<Cities> citiesList = citiesRepository.findAll();
        assertThat(citiesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCities() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        // Get all the citiesList
        restCitiesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cities.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)));
    }

    @Test
    @Transactional
    void getCities() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        // Get the cities
        restCitiesMockMvc
            .perform(get(ENTITY_API_URL_ID, cities.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cities.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE));
    }

    @Test
    @Transactional
    void getCitiesByIdFiltering() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        Long id = cities.getId();

        defaultCitiesShouldBeFound("id.equals=" + id);
        defaultCitiesShouldNotBeFound("id.notEquals=" + id);

        defaultCitiesShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCitiesShouldNotBeFound("id.greaterThan=" + id);

        defaultCitiesShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCitiesShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCitiesByTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        // Get all the citiesList where title equals to DEFAULT_TITLE
        defaultCitiesShouldBeFound("title.equals=" + DEFAULT_TITLE);

        // Get all the citiesList where title equals to UPDATED_TITLE
        defaultCitiesShouldNotBeFound("title.equals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllCitiesByTitleIsInShouldWork() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        // Get all the citiesList where title in DEFAULT_TITLE or UPDATED_TITLE
        defaultCitiesShouldBeFound("title.in=" + DEFAULT_TITLE + "," + UPDATED_TITLE);

        // Get all the citiesList where title equals to UPDATED_TITLE
        defaultCitiesShouldNotBeFound("title.in=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllCitiesByTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        // Get all the citiesList where title is not null
        defaultCitiesShouldBeFound("title.specified=true");

        // Get all the citiesList where title is null
        defaultCitiesShouldNotBeFound("title.specified=false");
    }

    @Test
    @Transactional
    void getAllCitiesByTitleContainsSomething() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        // Get all the citiesList where title contains DEFAULT_TITLE
        defaultCitiesShouldBeFound("title.contains=" + DEFAULT_TITLE);

        // Get all the citiesList where title contains UPDATED_TITLE
        defaultCitiesShouldNotBeFound("title.contains=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllCitiesByTitleNotContainsSomething() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        // Get all the citiesList where title does not contain DEFAULT_TITLE
        defaultCitiesShouldNotBeFound("title.doesNotContain=" + DEFAULT_TITLE);

        // Get all the citiesList where title does not contain UPDATED_TITLE
        defaultCitiesShouldBeFound("title.doesNotContain=" + UPDATED_TITLE);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCitiesShouldBeFound(String filter) throws Exception {
        restCitiesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cities.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)));

        // Check, that the count call also returns 1
        restCitiesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCitiesShouldNotBeFound(String filter) throws Exception {
        restCitiesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCitiesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCities() throws Exception {
        // Get the cities
        restCitiesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCities() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        int databaseSizeBeforeUpdate = citiesRepository.findAll().size();

        // Update the cities
        Cities updatedCities = citiesRepository.findById(cities.getId()).get();
        // Disconnect from session so that the updates on updatedCities are not directly saved in db
        em.detach(updatedCities);
        updatedCities.title(UPDATED_TITLE);
        CitiesDTO citiesDTO = citiesMapper.toDto(updatedCities);

        restCitiesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, citiesDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(citiesDTO))
            )
            .andExpect(status().isOk());

        // Validate the Cities in the database
        List<Cities> citiesList = citiesRepository.findAll();
        assertThat(citiesList).hasSize(databaseSizeBeforeUpdate);
        Cities testCities = citiesList.get(citiesList.size() - 1);
        assertThat(testCities.getTitle()).isEqualTo(UPDATED_TITLE);
    }

    @Test
    @Transactional
    void putNonExistingCities() throws Exception {
        int databaseSizeBeforeUpdate = citiesRepository.findAll().size();
        cities.setId(count.incrementAndGet());

        // Create the Cities
        CitiesDTO citiesDTO = citiesMapper.toDto(cities);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCitiesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, citiesDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(citiesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cities in the database
        List<Cities> citiesList = citiesRepository.findAll();
        assertThat(citiesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCities() throws Exception {
        int databaseSizeBeforeUpdate = citiesRepository.findAll().size();
        cities.setId(count.incrementAndGet());

        // Create the Cities
        CitiesDTO citiesDTO = citiesMapper.toDto(cities);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCitiesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(citiesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cities in the database
        List<Cities> citiesList = citiesRepository.findAll();
        assertThat(citiesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCities() throws Exception {
        int databaseSizeBeforeUpdate = citiesRepository.findAll().size();
        cities.setId(count.incrementAndGet());

        // Create the Cities
        CitiesDTO citiesDTO = citiesMapper.toDto(cities);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCitiesMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(citiesDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Cities in the database
        List<Cities> citiesList = citiesRepository.findAll();
        assertThat(citiesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCitiesWithPatch() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        int databaseSizeBeforeUpdate = citiesRepository.findAll().size();

        // Update the cities using partial update
        Cities partialUpdatedCities = new Cities();
        partialUpdatedCities.setId(cities.getId());

        restCitiesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCities.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCities))
            )
            .andExpect(status().isOk());

        // Validate the Cities in the database
        List<Cities> citiesList = citiesRepository.findAll();
        assertThat(citiesList).hasSize(databaseSizeBeforeUpdate);
        Cities testCities = citiesList.get(citiesList.size() - 1);
        assertThat(testCities.getTitle()).isEqualTo(DEFAULT_TITLE);
    }

    @Test
    @Transactional
    void fullUpdateCitiesWithPatch() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        int databaseSizeBeforeUpdate = citiesRepository.findAll().size();

        // Update the cities using partial update
        Cities partialUpdatedCities = new Cities();
        partialUpdatedCities.setId(cities.getId());

        partialUpdatedCities.title(UPDATED_TITLE);

        restCitiesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCities.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCities))
            )
            .andExpect(status().isOk());

        // Validate the Cities in the database
        List<Cities> citiesList = citiesRepository.findAll();
        assertThat(citiesList).hasSize(databaseSizeBeforeUpdate);
        Cities testCities = citiesList.get(citiesList.size() - 1);
        assertThat(testCities.getTitle()).isEqualTo(UPDATED_TITLE);
    }

    @Test
    @Transactional
    void patchNonExistingCities() throws Exception {
        int databaseSizeBeforeUpdate = citiesRepository.findAll().size();
        cities.setId(count.incrementAndGet());

        // Create the Cities
        CitiesDTO citiesDTO = citiesMapper.toDto(cities);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCitiesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, citiesDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(citiesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cities in the database
        List<Cities> citiesList = citiesRepository.findAll();
        assertThat(citiesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCities() throws Exception {
        int databaseSizeBeforeUpdate = citiesRepository.findAll().size();
        cities.setId(count.incrementAndGet());

        // Create the Cities
        CitiesDTO citiesDTO = citiesMapper.toDto(cities);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCitiesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(citiesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cities in the database
        List<Cities> citiesList = citiesRepository.findAll();
        assertThat(citiesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCities() throws Exception {
        int databaseSizeBeforeUpdate = citiesRepository.findAll().size();
        cities.setId(count.incrementAndGet());

        // Create the Cities
        CitiesDTO citiesDTO = citiesMapper.toDto(cities);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCitiesMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(citiesDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Cities in the database
        List<Cities> citiesList = citiesRepository.findAll();
        assertThat(citiesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCities() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        int databaseSizeBeforeDelete = citiesRepository.findAll().size();

        // Delete the cities
        restCitiesMockMvc
            .perform(delete(ENTITY_API_URL_ID, cities.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Cities> citiesList = citiesRepository.findAll();
        assertThat(citiesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
