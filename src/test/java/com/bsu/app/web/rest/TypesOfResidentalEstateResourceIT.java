package com.bsu.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.bsu.app.IntegrationTest;
import com.bsu.app.domain.TypesOfResidentalEstate;
import com.bsu.app.repository.TypesOfResidentalEstateRepository;
import com.bsu.app.service.criteria.TypesOfResidentalEstateCriteria;
import com.bsu.app.service.dto.TypesOfResidentalEstateDTO;
import com.bsu.app.service.mapper.TypesOfResidentalEstateMapper;
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
 * Integration tests for the {@link TypesOfResidentalEstateResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TypesOfResidentalEstateResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/types-of-residental-estates";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TypesOfResidentalEstateRepository typesOfResidentalEstateRepository;

    @Autowired
    private TypesOfResidentalEstateMapper typesOfResidentalEstateMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTypesOfResidentalEstateMockMvc;

    private TypesOfResidentalEstate typesOfResidentalEstate;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TypesOfResidentalEstate createEntity(EntityManager em) {
        TypesOfResidentalEstate typesOfResidentalEstate = new TypesOfResidentalEstate().title(DEFAULT_TITLE);
        return typesOfResidentalEstate;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TypesOfResidentalEstate createUpdatedEntity(EntityManager em) {
        TypesOfResidentalEstate typesOfResidentalEstate = new TypesOfResidentalEstate().title(UPDATED_TITLE);
        return typesOfResidentalEstate;
    }

    @BeforeEach
    public void initTest() {
        typesOfResidentalEstate = createEntity(em);
    }

    @Test
    @Transactional
    void createTypesOfResidentalEstate() throws Exception {
        int databaseSizeBeforeCreate = typesOfResidentalEstateRepository.findAll().size();
        // Create the TypesOfResidentalEstate
        TypesOfResidentalEstateDTO typesOfResidentalEstateDTO = typesOfResidentalEstateMapper.toDto(typesOfResidentalEstate);
        restTypesOfResidentalEstateMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(typesOfResidentalEstateDTO))
            )
            .andExpect(status().isCreated());

        // Validate the TypesOfResidentalEstate in the database
        List<TypesOfResidentalEstate> typesOfResidentalEstateList = typesOfResidentalEstateRepository.findAll();
        assertThat(typesOfResidentalEstateList).hasSize(databaseSizeBeforeCreate + 1);
        TypesOfResidentalEstate testTypesOfResidentalEstate = typesOfResidentalEstateList.get(typesOfResidentalEstateList.size() - 1);
        assertThat(testTypesOfResidentalEstate.getTitle()).isEqualTo(DEFAULT_TITLE);
    }

    @Test
    @Transactional
    void createTypesOfResidentalEstateWithExistingId() throws Exception {
        // Create the TypesOfResidentalEstate with an existing ID
        typesOfResidentalEstate.setId(1L);
        TypesOfResidentalEstateDTO typesOfResidentalEstateDTO = typesOfResidentalEstateMapper.toDto(typesOfResidentalEstate);

        int databaseSizeBeforeCreate = typesOfResidentalEstateRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTypesOfResidentalEstateMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(typesOfResidentalEstateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TypesOfResidentalEstate in the database
        List<TypesOfResidentalEstate> typesOfResidentalEstateList = typesOfResidentalEstateRepository.findAll();
        assertThat(typesOfResidentalEstateList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTypesOfResidentalEstates() throws Exception {
        // Initialize the database
        typesOfResidentalEstateRepository.saveAndFlush(typesOfResidentalEstate);

        // Get all the typesOfResidentalEstateList
        restTypesOfResidentalEstateMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typesOfResidentalEstate.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)));
    }

    @Test
    @Transactional
    void getTypesOfResidentalEstate() throws Exception {
        // Initialize the database
        typesOfResidentalEstateRepository.saveAndFlush(typesOfResidentalEstate);

        // Get the typesOfResidentalEstate
        restTypesOfResidentalEstateMockMvc
            .perform(get(ENTITY_API_URL_ID, typesOfResidentalEstate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(typesOfResidentalEstate.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE));
    }

    @Test
    @Transactional
    void getTypesOfResidentalEstatesByIdFiltering() throws Exception {
        // Initialize the database
        typesOfResidentalEstateRepository.saveAndFlush(typesOfResidentalEstate);

        Long id = typesOfResidentalEstate.getId();

        defaultTypesOfResidentalEstateShouldBeFound("id.equals=" + id);
        defaultTypesOfResidentalEstateShouldNotBeFound("id.notEquals=" + id);

        defaultTypesOfResidentalEstateShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTypesOfResidentalEstateShouldNotBeFound("id.greaterThan=" + id);

        defaultTypesOfResidentalEstateShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTypesOfResidentalEstateShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllTypesOfResidentalEstatesByTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        typesOfResidentalEstateRepository.saveAndFlush(typesOfResidentalEstate);

        // Get all the typesOfResidentalEstateList where title equals to DEFAULT_TITLE
        defaultTypesOfResidentalEstateShouldBeFound("title.equals=" + DEFAULT_TITLE);

        // Get all the typesOfResidentalEstateList where title equals to UPDATED_TITLE
        defaultTypesOfResidentalEstateShouldNotBeFound("title.equals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllTypesOfResidentalEstatesByTitleIsInShouldWork() throws Exception {
        // Initialize the database
        typesOfResidentalEstateRepository.saveAndFlush(typesOfResidentalEstate);

        // Get all the typesOfResidentalEstateList where title in DEFAULT_TITLE or UPDATED_TITLE
        defaultTypesOfResidentalEstateShouldBeFound("title.in=" + DEFAULT_TITLE + "," + UPDATED_TITLE);

        // Get all the typesOfResidentalEstateList where title equals to UPDATED_TITLE
        defaultTypesOfResidentalEstateShouldNotBeFound("title.in=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllTypesOfResidentalEstatesByTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        typesOfResidentalEstateRepository.saveAndFlush(typesOfResidentalEstate);

        // Get all the typesOfResidentalEstateList where title is not null
        defaultTypesOfResidentalEstateShouldBeFound("title.specified=true");

        // Get all the typesOfResidentalEstateList where title is null
        defaultTypesOfResidentalEstateShouldNotBeFound("title.specified=false");
    }

    @Test
    @Transactional
    void getAllTypesOfResidentalEstatesByTitleContainsSomething() throws Exception {
        // Initialize the database
        typesOfResidentalEstateRepository.saveAndFlush(typesOfResidentalEstate);

        // Get all the typesOfResidentalEstateList where title contains DEFAULT_TITLE
        defaultTypesOfResidentalEstateShouldBeFound("title.contains=" + DEFAULT_TITLE);

        // Get all the typesOfResidentalEstateList where title contains UPDATED_TITLE
        defaultTypesOfResidentalEstateShouldNotBeFound("title.contains=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllTypesOfResidentalEstatesByTitleNotContainsSomething() throws Exception {
        // Initialize the database
        typesOfResidentalEstateRepository.saveAndFlush(typesOfResidentalEstate);

        // Get all the typesOfResidentalEstateList where title does not contain DEFAULT_TITLE
        defaultTypesOfResidentalEstateShouldNotBeFound("title.doesNotContain=" + DEFAULT_TITLE);

        // Get all the typesOfResidentalEstateList where title does not contain UPDATED_TITLE
        defaultTypesOfResidentalEstateShouldBeFound("title.doesNotContain=" + UPDATED_TITLE);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTypesOfResidentalEstateShouldBeFound(String filter) throws Exception {
        restTypesOfResidentalEstateMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typesOfResidentalEstate.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)));

        // Check, that the count call also returns 1
        restTypesOfResidentalEstateMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTypesOfResidentalEstateShouldNotBeFound(String filter) throws Exception {
        restTypesOfResidentalEstateMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTypesOfResidentalEstateMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingTypesOfResidentalEstate() throws Exception {
        // Get the typesOfResidentalEstate
        restTypesOfResidentalEstateMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTypesOfResidentalEstate() throws Exception {
        // Initialize the database
        typesOfResidentalEstateRepository.saveAndFlush(typesOfResidentalEstate);

        int databaseSizeBeforeUpdate = typesOfResidentalEstateRepository.findAll().size();

        // Update the typesOfResidentalEstate
        TypesOfResidentalEstate updatedTypesOfResidentalEstate = typesOfResidentalEstateRepository
            .findById(typesOfResidentalEstate.getId())
            .get();
        // Disconnect from session so that the updates on updatedTypesOfResidentalEstate are not directly saved in db
        em.detach(updatedTypesOfResidentalEstate);
        updatedTypesOfResidentalEstate.title(UPDATED_TITLE);
        TypesOfResidentalEstateDTO typesOfResidentalEstateDTO = typesOfResidentalEstateMapper.toDto(updatedTypesOfResidentalEstate);

        restTypesOfResidentalEstateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, typesOfResidentalEstateDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(typesOfResidentalEstateDTO))
            )
            .andExpect(status().isOk());

        // Validate the TypesOfResidentalEstate in the database
        List<TypesOfResidentalEstate> typesOfResidentalEstateList = typesOfResidentalEstateRepository.findAll();
        assertThat(typesOfResidentalEstateList).hasSize(databaseSizeBeforeUpdate);
        TypesOfResidentalEstate testTypesOfResidentalEstate = typesOfResidentalEstateList.get(typesOfResidentalEstateList.size() - 1);
        assertThat(testTypesOfResidentalEstate.getTitle()).isEqualTo(UPDATED_TITLE);
    }

    @Test
    @Transactional
    void putNonExistingTypesOfResidentalEstate() throws Exception {
        int databaseSizeBeforeUpdate = typesOfResidentalEstateRepository.findAll().size();
        typesOfResidentalEstate.setId(count.incrementAndGet());

        // Create the TypesOfResidentalEstate
        TypesOfResidentalEstateDTO typesOfResidentalEstateDTO = typesOfResidentalEstateMapper.toDto(typesOfResidentalEstate);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTypesOfResidentalEstateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, typesOfResidentalEstateDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(typesOfResidentalEstateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TypesOfResidentalEstate in the database
        List<TypesOfResidentalEstate> typesOfResidentalEstateList = typesOfResidentalEstateRepository.findAll();
        assertThat(typesOfResidentalEstateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTypesOfResidentalEstate() throws Exception {
        int databaseSizeBeforeUpdate = typesOfResidentalEstateRepository.findAll().size();
        typesOfResidentalEstate.setId(count.incrementAndGet());

        // Create the TypesOfResidentalEstate
        TypesOfResidentalEstateDTO typesOfResidentalEstateDTO = typesOfResidentalEstateMapper.toDto(typesOfResidentalEstate);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTypesOfResidentalEstateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(typesOfResidentalEstateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TypesOfResidentalEstate in the database
        List<TypesOfResidentalEstate> typesOfResidentalEstateList = typesOfResidentalEstateRepository.findAll();
        assertThat(typesOfResidentalEstateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTypesOfResidentalEstate() throws Exception {
        int databaseSizeBeforeUpdate = typesOfResidentalEstateRepository.findAll().size();
        typesOfResidentalEstate.setId(count.incrementAndGet());

        // Create the TypesOfResidentalEstate
        TypesOfResidentalEstateDTO typesOfResidentalEstateDTO = typesOfResidentalEstateMapper.toDto(typesOfResidentalEstate);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTypesOfResidentalEstateMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(typesOfResidentalEstateDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TypesOfResidentalEstate in the database
        List<TypesOfResidentalEstate> typesOfResidentalEstateList = typesOfResidentalEstateRepository.findAll();
        assertThat(typesOfResidentalEstateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTypesOfResidentalEstateWithPatch() throws Exception {
        // Initialize the database
        typesOfResidentalEstateRepository.saveAndFlush(typesOfResidentalEstate);

        int databaseSizeBeforeUpdate = typesOfResidentalEstateRepository.findAll().size();

        // Update the typesOfResidentalEstate using partial update
        TypesOfResidentalEstate partialUpdatedTypesOfResidentalEstate = new TypesOfResidentalEstate();
        partialUpdatedTypesOfResidentalEstate.setId(typesOfResidentalEstate.getId());

        restTypesOfResidentalEstateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTypesOfResidentalEstate.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTypesOfResidentalEstate))
            )
            .andExpect(status().isOk());

        // Validate the TypesOfResidentalEstate in the database
        List<TypesOfResidentalEstate> typesOfResidentalEstateList = typesOfResidentalEstateRepository.findAll();
        assertThat(typesOfResidentalEstateList).hasSize(databaseSizeBeforeUpdate);
        TypesOfResidentalEstate testTypesOfResidentalEstate = typesOfResidentalEstateList.get(typesOfResidentalEstateList.size() - 1);
        assertThat(testTypesOfResidentalEstate.getTitle()).isEqualTo(DEFAULT_TITLE);
    }

    @Test
    @Transactional
    void fullUpdateTypesOfResidentalEstateWithPatch() throws Exception {
        // Initialize the database
        typesOfResidentalEstateRepository.saveAndFlush(typesOfResidentalEstate);

        int databaseSizeBeforeUpdate = typesOfResidentalEstateRepository.findAll().size();

        // Update the typesOfResidentalEstate using partial update
        TypesOfResidentalEstate partialUpdatedTypesOfResidentalEstate = new TypesOfResidentalEstate();
        partialUpdatedTypesOfResidentalEstate.setId(typesOfResidentalEstate.getId());

        partialUpdatedTypesOfResidentalEstate.title(UPDATED_TITLE);

        restTypesOfResidentalEstateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTypesOfResidentalEstate.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTypesOfResidentalEstate))
            )
            .andExpect(status().isOk());

        // Validate the TypesOfResidentalEstate in the database
        List<TypesOfResidentalEstate> typesOfResidentalEstateList = typesOfResidentalEstateRepository.findAll();
        assertThat(typesOfResidentalEstateList).hasSize(databaseSizeBeforeUpdate);
        TypesOfResidentalEstate testTypesOfResidentalEstate = typesOfResidentalEstateList.get(typesOfResidentalEstateList.size() - 1);
        assertThat(testTypesOfResidentalEstate.getTitle()).isEqualTo(UPDATED_TITLE);
    }

    @Test
    @Transactional
    void patchNonExistingTypesOfResidentalEstate() throws Exception {
        int databaseSizeBeforeUpdate = typesOfResidentalEstateRepository.findAll().size();
        typesOfResidentalEstate.setId(count.incrementAndGet());

        // Create the TypesOfResidentalEstate
        TypesOfResidentalEstateDTO typesOfResidentalEstateDTO = typesOfResidentalEstateMapper.toDto(typesOfResidentalEstate);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTypesOfResidentalEstateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, typesOfResidentalEstateDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(typesOfResidentalEstateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TypesOfResidentalEstate in the database
        List<TypesOfResidentalEstate> typesOfResidentalEstateList = typesOfResidentalEstateRepository.findAll();
        assertThat(typesOfResidentalEstateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTypesOfResidentalEstate() throws Exception {
        int databaseSizeBeforeUpdate = typesOfResidentalEstateRepository.findAll().size();
        typesOfResidentalEstate.setId(count.incrementAndGet());

        // Create the TypesOfResidentalEstate
        TypesOfResidentalEstateDTO typesOfResidentalEstateDTO = typesOfResidentalEstateMapper.toDto(typesOfResidentalEstate);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTypesOfResidentalEstateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(typesOfResidentalEstateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TypesOfResidentalEstate in the database
        List<TypesOfResidentalEstate> typesOfResidentalEstateList = typesOfResidentalEstateRepository.findAll();
        assertThat(typesOfResidentalEstateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTypesOfResidentalEstate() throws Exception {
        int databaseSizeBeforeUpdate = typesOfResidentalEstateRepository.findAll().size();
        typesOfResidentalEstate.setId(count.incrementAndGet());

        // Create the TypesOfResidentalEstate
        TypesOfResidentalEstateDTO typesOfResidentalEstateDTO = typesOfResidentalEstateMapper.toDto(typesOfResidentalEstate);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTypesOfResidentalEstateMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(typesOfResidentalEstateDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TypesOfResidentalEstate in the database
        List<TypesOfResidentalEstate> typesOfResidentalEstateList = typesOfResidentalEstateRepository.findAll();
        assertThat(typesOfResidentalEstateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTypesOfResidentalEstate() throws Exception {
        // Initialize the database
        typesOfResidentalEstateRepository.saveAndFlush(typesOfResidentalEstate);

        int databaseSizeBeforeDelete = typesOfResidentalEstateRepository.findAll().size();

        // Delete the typesOfResidentalEstate
        restTypesOfResidentalEstateMockMvc
            .perform(delete(ENTITY_API_URL_ID, typesOfResidentalEstate.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TypesOfResidentalEstate> typesOfResidentalEstateList = typesOfResidentalEstateRepository.findAll();
        assertThat(typesOfResidentalEstateList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
