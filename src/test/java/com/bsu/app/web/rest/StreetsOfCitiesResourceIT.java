package com.bsu.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.bsu.app.IntegrationTest;
import com.bsu.app.domain.Cities;
import com.bsu.app.domain.StreetsOfCities;
import com.bsu.app.repository.StreetsOfCitiesRepository;
import com.bsu.app.service.criteria.StreetsOfCitiesCriteria;
import com.bsu.app.service.dto.StreetsOfCitiesDTO;
import com.bsu.app.service.mapper.StreetsOfCitiesMapper;
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
 * Integration tests for the {@link StreetsOfCitiesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class StreetsOfCitiesResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/streets-of-cities";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private StreetsOfCitiesRepository streetsOfCitiesRepository;

    @Autowired
    private StreetsOfCitiesMapper streetsOfCitiesMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStreetsOfCitiesMockMvc;

    private StreetsOfCities streetsOfCities;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StreetsOfCities createEntity(EntityManager em) {
        StreetsOfCities streetsOfCities = new StreetsOfCities().title(DEFAULT_TITLE);
        return streetsOfCities;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StreetsOfCities createUpdatedEntity(EntityManager em) {
        StreetsOfCities streetsOfCities = new StreetsOfCities().title(UPDATED_TITLE);
        return streetsOfCities;
    }

    @BeforeEach
    public void initTest() {
        streetsOfCities = createEntity(em);
    }

    @Test
    @Transactional
    void createStreetsOfCities() throws Exception {
        int databaseSizeBeforeCreate = streetsOfCitiesRepository.findAll().size();
        // Create the StreetsOfCities
        StreetsOfCitiesDTO streetsOfCitiesDTO = streetsOfCitiesMapper.toDto(streetsOfCities);
        restStreetsOfCitiesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(streetsOfCitiesDTO))
            )
            .andExpect(status().isCreated());

        // Validate the StreetsOfCities in the database
        List<StreetsOfCities> streetsOfCitiesList = streetsOfCitiesRepository.findAll();
        assertThat(streetsOfCitiesList).hasSize(databaseSizeBeforeCreate + 1);
        StreetsOfCities testStreetsOfCities = streetsOfCitiesList.get(streetsOfCitiesList.size() - 1);
        assertThat(testStreetsOfCities.getTitle()).isEqualTo(DEFAULT_TITLE);
    }

    @Test
    @Transactional
    void createStreetsOfCitiesWithExistingId() throws Exception {
        // Create the StreetsOfCities with an existing ID
        streetsOfCities.setId(1L);
        StreetsOfCitiesDTO streetsOfCitiesDTO = streetsOfCitiesMapper.toDto(streetsOfCities);

        int databaseSizeBeforeCreate = streetsOfCitiesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restStreetsOfCitiesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(streetsOfCitiesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the StreetsOfCities in the database
        List<StreetsOfCities> streetsOfCitiesList = streetsOfCitiesRepository.findAll();
        assertThat(streetsOfCitiesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllStreetsOfCities() throws Exception {
        // Initialize the database
        streetsOfCitiesRepository.saveAndFlush(streetsOfCities);

        // Get all the streetsOfCitiesList
        restStreetsOfCitiesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(streetsOfCities.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)));
    }

    @Test
    @Transactional
    void getStreetsOfCities() throws Exception {
        // Initialize the database
        streetsOfCitiesRepository.saveAndFlush(streetsOfCities);

        // Get the streetsOfCities
        restStreetsOfCitiesMockMvc
            .perform(get(ENTITY_API_URL_ID, streetsOfCities.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(streetsOfCities.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE));
    }

    @Test
    @Transactional
    void getStreetsOfCitiesByIdFiltering() throws Exception {
        // Initialize the database
        streetsOfCitiesRepository.saveAndFlush(streetsOfCities);

        Long id = streetsOfCities.getId();

        defaultStreetsOfCitiesShouldBeFound("id.equals=" + id);
        defaultStreetsOfCitiesShouldNotBeFound("id.notEquals=" + id);

        defaultStreetsOfCitiesShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultStreetsOfCitiesShouldNotBeFound("id.greaterThan=" + id);

        defaultStreetsOfCitiesShouldBeFound("id.lessThanOrEqual=" + id);
        defaultStreetsOfCitiesShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllStreetsOfCitiesByTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        streetsOfCitiesRepository.saveAndFlush(streetsOfCities);

        // Get all the streetsOfCitiesList where title equals to DEFAULT_TITLE
        defaultStreetsOfCitiesShouldBeFound("title.equals=" + DEFAULT_TITLE);

        // Get all the streetsOfCitiesList where title equals to UPDATED_TITLE
        defaultStreetsOfCitiesShouldNotBeFound("title.equals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllStreetsOfCitiesByTitleIsInShouldWork() throws Exception {
        // Initialize the database
        streetsOfCitiesRepository.saveAndFlush(streetsOfCities);

        // Get all the streetsOfCitiesList where title in DEFAULT_TITLE or UPDATED_TITLE
        defaultStreetsOfCitiesShouldBeFound("title.in=" + DEFAULT_TITLE + "," + UPDATED_TITLE);

        // Get all the streetsOfCitiesList where title equals to UPDATED_TITLE
        defaultStreetsOfCitiesShouldNotBeFound("title.in=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllStreetsOfCitiesByTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        streetsOfCitiesRepository.saveAndFlush(streetsOfCities);

        // Get all the streetsOfCitiesList where title is not null
        defaultStreetsOfCitiesShouldBeFound("title.specified=true");

        // Get all the streetsOfCitiesList where title is null
        defaultStreetsOfCitiesShouldNotBeFound("title.specified=false");
    }

    @Test
    @Transactional
    void getAllStreetsOfCitiesByTitleContainsSomething() throws Exception {
        // Initialize the database
        streetsOfCitiesRepository.saveAndFlush(streetsOfCities);

        // Get all the streetsOfCitiesList where title contains DEFAULT_TITLE
        defaultStreetsOfCitiesShouldBeFound("title.contains=" + DEFAULT_TITLE);

        // Get all the streetsOfCitiesList where title contains UPDATED_TITLE
        defaultStreetsOfCitiesShouldNotBeFound("title.contains=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllStreetsOfCitiesByTitleNotContainsSomething() throws Exception {
        // Initialize the database
        streetsOfCitiesRepository.saveAndFlush(streetsOfCities);

        // Get all the streetsOfCitiesList where title does not contain DEFAULT_TITLE
        defaultStreetsOfCitiesShouldNotBeFound("title.doesNotContain=" + DEFAULT_TITLE);

        // Get all the streetsOfCitiesList where title does not contain UPDATED_TITLE
        defaultStreetsOfCitiesShouldBeFound("title.doesNotContain=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllStreetsOfCitiesByCityIdIsEqualToSomething() throws Exception {
        Cities cityId;
        if (TestUtil.findAll(em, Cities.class).isEmpty()) {
            streetsOfCitiesRepository.saveAndFlush(streetsOfCities);
            cityId = CitiesResourceIT.createEntity(em);
        } else {
            cityId = TestUtil.findAll(em, Cities.class).get(0);
        }
        em.persist(cityId);
        em.flush();
        streetsOfCities.setCityId(cityId);
        streetsOfCitiesRepository.saveAndFlush(streetsOfCities);
        Long cityIdId = cityId.getId();

        // Get all the streetsOfCitiesList where cityId equals to cityIdId
        defaultStreetsOfCitiesShouldBeFound("cityIdId.equals=" + cityIdId);

        // Get all the streetsOfCitiesList where cityId equals to (cityIdId + 1)
        defaultStreetsOfCitiesShouldNotBeFound("cityIdId.equals=" + (cityIdId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultStreetsOfCitiesShouldBeFound(String filter) throws Exception {
        restStreetsOfCitiesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(streetsOfCities.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)));

        // Check, that the count call also returns 1
        restStreetsOfCitiesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultStreetsOfCitiesShouldNotBeFound(String filter) throws Exception {
        restStreetsOfCitiesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restStreetsOfCitiesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingStreetsOfCities() throws Exception {
        // Get the streetsOfCities
        restStreetsOfCitiesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingStreetsOfCities() throws Exception {
        // Initialize the database
        streetsOfCitiesRepository.saveAndFlush(streetsOfCities);

        int databaseSizeBeforeUpdate = streetsOfCitiesRepository.findAll().size();

        // Update the streetsOfCities
        StreetsOfCities updatedStreetsOfCities = streetsOfCitiesRepository.findById(streetsOfCities.getId()).get();
        // Disconnect from session so that the updates on updatedStreetsOfCities are not directly saved in db
        em.detach(updatedStreetsOfCities);
        updatedStreetsOfCities.title(UPDATED_TITLE);
        StreetsOfCitiesDTO streetsOfCitiesDTO = streetsOfCitiesMapper.toDto(updatedStreetsOfCities);

        restStreetsOfCitiesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, streetsOfCitiesDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(streetsOfCitiesDTO))
            )
            .andExpect(status().isOk());

        // Validate the StreetsOfCities in the database
        List<StreetsOfCities> streetsOfCitiesList = streetsOfCitiesRepository.findAll();
        assertThat(streetsOfCitiesList).hasSize(databaseSizeBeforeUpdate);
        StreetsOfCities testStreetsOfCities = streetsOfCitiesList.get(streetsOfCitiesList.size() - 1);
        assertThat(testStreetsOfCities.getTitle()).isEqualTo(UPDATED_TITLE);
    }

    @Test
    @Transactional
    void putNonExistingStreetsOfCities() throws Exception {
        int databaseSizeBeforeUpdate = streetsOfCitiesRepository.findAll().size();
        streetsOfCities.setId(count.incrementAndGet());

        // Create the StreetsOfCities
        StreetsOfCitiesDTO streetsOfCitiesDTO = streetsOfCitiesMapper.toDto(streetsOfCities);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStreetsOfCitiesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, streetsOfCitiesDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(streetsOfCitiesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the StreetsOfCities in the database
        List<StreetsOfCities> streetsOfCitiesList = streetsOfCitiesRepository.findAll();
        assertThat(streetsOfCitiesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchStreetsOfCities() throws Exception {
        int databaseSizeBeforeUpdate = streetsOfCitiesRepository.findAll().size();
        streetsOfCities.setId(count.incrementAndGet());

        // Create the StreetsOfCities
        StreetsOfCitiesDTO streetsOfCitiesDTO = streetsOfCitiesMapper.toDto(streetsOfCities);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStreetsOfCitiesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(streetsOfCitiesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the StreetsOfCities in the database
        List<StreetsOfCities> streetsOfCitiesList = streetsOfCitiesRepository.findAll();
        assertThat(streetsOfCitiesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamStreetsOfCities() throws Exception {
        int databaseSizeBeforeUpdate = streetsOfCitiesRepository.findAll().size();
        streetsOfCities.setId(count.incrementAndGet());

        // Create the StreetsOfCities
        StreetsOfCitiesDTO streetsOfCitiesDTO = streetsOfCitiesMapper.toDto(streetsOfCities);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStreetsOfCitiesMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(streetsOfCitiesDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the StreetsOfCities in the database
        List<StreetsOfCities> streetsOfCitiesList = streetsOfCitiesRepository.findAll();
        assertThat(streetsOfCitiesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateStreetsOfCitiesWithPatch() throws Exception {
        // Initialize the database
        streetsOfCitiesRepository.saveAndFlush(streetsOfCities);

        int databaseSizeBeforeUpdate = streetsOfCitiesRepository.findAll().size();

        // Update the streetsOfCities using partial update
        StreetsOfCities partialUpdatedStreetsOfCities = new StreetsOfCities();
        partialUpdatedStreetsOfCities.setId(streetsOfCities.getId());

        restStreetsOfCitiesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStreetsOfCities.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStreetsOfCities))
            )
            .andExpect(status().isOk());

        // Validate the StreetsOfCities in the database
        List<StreetsOfCities> streetsOfCitiesList = streetsOfCitiesRepository.findAll();
        assertThat(streetsOfCitiesList).hasSize(databaseSizeBeforeUpdate);
        StreetsOfCities testStreetsOfCities = streetsOfCitiesList.get(streetsOfCitiesList.size() - 1);
        assertThat(testStreetsOfCities.getTitle()).isEqualTo(DEFAULT_TITLE);
    }

    @Test
    @Transactional
    void fullUpdateStreetsOfCitiesWithPatch() throws Exception {
        // Initialize the database
        streetsOfCitiesRepository.saveAndFlush(streetsOfCities);

        int databaseSizeBeforeUpdate = streetsOfCitiesRepository.findAll().size();

        // Update the streetsOfCities using partial update
        StreetsOfCities partialUpdatedStreetsOfCities = new StreetsOfCities();
        partialUpdatedStreetsOfCities.setId(streetsOfCities.getId());

        partialUpdatedStreetsOfCities.title(UPDATED_TITLE);

        restStreetsOfCitiesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStreetsOfCities.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStreetsOfCities))
            )
            .andExpect(status().isOk());

        // Validate the StreetsOfCities in the database
        List<StreetsOfCities> streetsOfCitiesList = streetsOfCitiesRepository.findAll();
        assertThat(streetsOfCitiesList).hasSize(databaseSizeBeforeUpdate);
        StreetsOfCities testStreetsOfCities = streetsOfCitiesList.get(streetsOfCitiesList.size() - 1);
        assertThat(testStreetsOfCities.getTitle()).isEqualTo(UPDATED_TITLE);
    }

    @Test
    @Transactional
    void patchNonExistingStreetsOfCities() throws Exception {
        int databaseSizeBeforeUpdate = streetsOfCitiesRepository.findAll().size();
        streetsOfCities.setId(count.incrementAndGet());

        // Create the StreetsOfCities
        StreetsOfCitiesDTO streetsOfCitiesDTO = streetsOfCitiesMapper.toDto(streetsOfCities);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStreetsOfCitiesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, streetsOfCitiesDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(streetsOfCitiesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the StreetsOfCities in the database
        List<StreetsOfCities> streetsOfCitiesList = streetsOfCitiesRepository.findAll();
        assertThat(streetsOfCitiesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchStreetsOfCities() throws Exception {
        int databaseSizeBeforeUpdate = streetsOfCitiesRepository.findAll().size();
        streetsOfCities.setId(count.incrementAndGet());

        // Create the StreetsOfCities
        StreetsOfCitiesDTO streetsOfCitiesDTO = streetsOfCitiesMapper.toDto(streetsOfCities);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStreetsOfCitiesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(streetsOfCitiesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the StreetsOfCities in the database
        List<StreetsOfCities> streetsOfCitiesList = streetsOfCitiesRepository.findAll();
        assertThat(streetsOfCitiesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamStreetsOfCities() throws Exception {
        int databaseSizeBeforeUpdate = streetsOfCitiesRepository.findAll().size();
        streetsOfCities.setId(count.incrementAndGet());

        // Create the StreetsOfCities
        StreetsOfCitiesDTO streetsOfCitiesDTO = streetsOfCitiesMapper.toDto(streetsOfCities);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStreetsOfCitiesMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(streetsOfCitiesDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the StreetsOfCities in the database
        List<StreetsOfCities> streetsOfCitiesList = streetsOfCitiesRepository.findAll();
        assertThat(streetsOfCitiesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteStreetsOfCities() throws Exception {
        // Initialize the database
        streetsOfCitiesRepository.saveAndFlush(streetsOfCities);

        int databaseSizeBeforeDelete = streetsOfCitiesRepository.findAll().size();

        // Delete the streetsOfCities
        restStreetsOfCitiesMockMvc
            .perform(delete(ENTITY_API_URL_ID, streetsOfCities.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<StreetsOfCities> streetsOfCitiesList = streetsOfCitiesRepository.findAll();
        assertThat(streetsOfCitiesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
