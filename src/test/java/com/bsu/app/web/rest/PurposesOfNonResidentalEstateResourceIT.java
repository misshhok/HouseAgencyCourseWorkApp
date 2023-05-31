package com.bsu.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.bsu.app.IntegrationTest;
import com.bsu.app.domain.PurposesOfNonResidentalEstate;
import com.bsu.app.repository.PurposesOfNonResidentalEstateRepository;
import com.bsu.app.service.criteria.PurposesOfNonResidentalEstateCriteria;
import com.bsu.app.service.dto.PurposesOfNonResidentalEstateDTO;
import com.bsu.app.service.mapper.PurposesOfNonResidentalEstateMapper;
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
 * Integration tests for the {@link PurposesOfNonResidentalEstateResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PurposesOfNonResidentalEstateResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/purposes-of-non-residental-estates";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PurposesOfNonResidentalEstateRepository purposesOfNonResidentalEstateRepository;

    @Autowired
    private PurposesOfNonResidentalEstateMapper purposesOfNonResidentalEstateMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPurposesOfNonResidentalEstateMockMvc;

    private PurposesOfNonResidentalEstate purposesOfNonResidentalEstate;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PurposesOfNonResidentalEstate createEntity(EntityManager em) {
        PurposesOfNonResidentalEstate purposesOfNonResidentalEstate = new PurposesOfNonResidentalEstate().title(DEFAULT_TITLE);
        return purposesOfNonResidentalEstate;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PurposesOfNonResidentalEstate createUpdatedEntity(EntityManager em) {
        PurposesOfNonResidentalEstate purposesOfNonResidentalEstate = new PurposesOfNonResidentalEstate().title(UPDATED_TITLE);
        return purposesOfNonResidentalEstate;
    }

    @BeforeEach
    public void initTest() {
        purposesOfNonResidentalEstate = createEntity(em);
    }

    @Test
    @Transactional
    void createPurposesOfNonResidentalEstate() throws Exception {
        int databaseSizeBeforeCreate = purposesOfNonResidentalEstateRepository.findAll().size();
        // Create the PurposesOfNonResidentalEstate
        PurposesOfNonResidentalEstateDTO purposesOfNonResidentalEstateDTO = purposesOfNonResidentalEstateMapper.toDto(
            purposesOfNonResidentalEstate
        );
        restPurposesOfNonResidentalEstateMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(purposesOfNonResidentalEstateDTO))
            )
            .andExpect(status().isCreated());

        // Validate the PurposesOfNonResidentalEstate in the database
        List<PurposesOfNonResidentalEstate> purposesOfNonResidentalEstateList = purposesOfNonResidentalEstateRepository.findAll();
        assertThat(purposesOfNonResidentalEstateList).hasSize(databaseSizeBeforeCreate + 1);
        PurposesOfNonResidentalEstate testPurposesOfNonResidentalEstate = purposesOfNonResidentalEstateList.get(
            purposesOfNonResidentalEstateList.size() - 1
        );
        assertThat(testPurposesOfNonResidentalEstate.getTitle()).isEqualTo(DEFAULT_TITLE);
    }

    @Test
    @Transactional
    void createPurposesOfNonResidentalEstateWithExistingId() throws Exception {
        // Create the PurposesOfNonResidentalEstate with an existing ID
        purposesOfNonResidentalEstate.setId(1L);
        PurposesOfNonResidentalEstateDTO purposesOfNonResidentalEstateDTO = purposesOfNonResidentalEstateMapper.toDto(
            purposesOfNonResidentalEstate
        );

        int databaseSizeBeforeCreate = purposesOfNonResidentalEstateRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPurposesOfNonResidentalEstateMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(purposesOfNonResidentalEstateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PurposesOfNonResidentalEstate in the database
        List<PurposesOfNonResidentalEstate> purposesOfNonResidentalEstateList = purposesOfNonResidentalEstateRepository.findAll();
        assertThat(purposesOfNonResidentalEstateList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPurposesOfNonResidentalEstates() throws Exception {
        // Initialize the database
        purposesOfNonResidentalEstateRepository.saveAndFlush(purposesOfNonResidentalEstate);

        // Get all the purposesOfNonResidentalEstateList
        restPurposesOfNonResidentalEstateMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(purposesOfNonResidentalEstate.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)));
    }

    @Test
    @Transactional
    void getPurposesOfNonResidentalEstate() throws Exception {
        // Initialize the database
        purposesOfNonResidentalEstateRepository.saveAndFlush(purposesOfNonResidentalEstate);

        // Get the purposesOfNonResidentalEstate
        restPurposesOfNonResidentalEstateMockMvc
            .perform(get(ENTITY_API_URL_ID, purposesOfNonResidentalEstate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(purposesOfNonResidentalEstate.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE));
    }

    @Test
    @Transactional
    void getPurposesOfNonResidentalEstatesByIdFiltering() throws Exception {
        // Initialize the database
        purposesOfNonResidentalEstateRepository.saveAndFlush(purposesOfNonResidentalEstate);

        Long id = purposesOfNonResidentalEstate.getId();

        defaultPurposesOfNonResidentalEstateShouldBeFound("id.equals=" + id);
        defaultPurposesOfNonResidentalEstateShouldNotBeFound("id.notEquals=" + id);

        defaultPurposesOfNonResidentalEstateShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPurposesOfNonResidentalEstateShouldNotBeFound("id.greaterThan=" + id);

        defaultPurposesOfNonResidentalEstateShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPurposesOfNonResidentalEstateShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllPurposesOfNonResidentalEstatesByTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        purposesOfNonResidentalEstateRepository.saveAndFlush(purposesOfNonResidentalEstate);

        // Get all the purposesOfNonResidentalEstateList where title equals to DEFAULT_TITLE
        defaultPurposesOfNonResidentalEstateShouldBeFound("title.equals=" + DEFAULT_TITLE);

        // Get all the purposesOfNonResidentalEstateList where title equals to UPDATED_TITLE
        defaultPurposesOfNonResidentalEstateShouldNotBeFound("title.equals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllPurposesOfNonResidentalEstatesByTitleIsInShouldWork() throws Exception {
        // Initialize the database
        purposesOfNonResidentalEstateRepository.saveAndFlush(purposesOfNonResidentalEstate);

        // Get all the purposesOfNonResidentalEstateList where title in DEFAULT_TITLE or UPDATED_TITLE
        defaultPurposesOfNonResidentalEstateShouldBeFound("title.in=" + DEFAULT_TITLE + "," + UPDATED_TITLE);

        // Get all the purposesOfNonResidentalEstateList where title equals to UPDATED_TITLE
        defaultPurposesOfNonResidentalEstateShouldNotBeFound("title.in=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllPurposesOfNonResidentalEstatesByTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        purposesOfNonResidentalEstateRepository.saveAndFlush(purposesOfNonResidentalEstate);

        // Get all the purposesOfNonResidentalEstateList where title is not null
        defaultPurposesOfNonResidentalEstateShouldBeFound("title.specified=true");

        // Get all the purposesOfNonResidentalEstateList where title is null
        defaultPurposesOfNonResidentalEstateShouldNotBeFound("title.specified=false");
    }

    @Test
    @Transactional
    void getAllPurposesOfNonResidentalEstatesByTitleContainsSomething() throws Exception {
        // Initialize the database
        purposesOfNonResidentalEstateRepository.saveAndFlush(purposesOfNonResidentalEstate);

        // Get all the purposesOfNonResidentalEstateList where title contains DEFAULT_TITLE
        defaultPurposesOfNonResidentalEstateShouldBeFound("title.contains=" + DEFAULT_TITLE);

        // Get all the purposesOfNonResidentalEstateList where title contains UPDATED_TITLE
        defaultPurposesOfNonResidentalEstateShouldNotBeFound("title.contains=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllPurposesOfNonResidentalEstatesByTitleNotContainsSomething() throws Exception {
        // Initialize the database
        purposesOfNonResidentalEstateRepository.saveAndFlush(purposesOfNonResidentalEstate);

        // Get all the purposesOfNonResidentalEstateList where title does not contain DEFAULT_TITLE
        defaultPurposesOfNonResidentalEstateShouldNotBeFound("title.doesNotContain=" + DEFAULT_TITLE);

        // Get all the purposesOfNonResidentalEstateList where title does not contain UPDATED_TITLE
        defaultPurposesOfNonResidentalEstateShouldBeFound("title.doesNotContain=" + UPDATED_TITLE);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPurposesOfNonResidentalEstateShouldBeFound(String filter) throws Exception {
        restPurposesOfNonResidentalEstateMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(purposesOfNonResidentalEstate.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)));

        // Check, that the count call also returns 1
        restPurposesOfNonResidentalEstateMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPurposesOfNonResidentalEstateShouldNotBeFound(String filter) throws Exception {
        restPurposesOfNonResidentalEstateMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPurposesOfNonResidentalEstateMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingPurposesOfNonResidentalEstate() throws Exception {
        // Get the purposesOfNonResidentalEstate
        restPurposesOfNonResidentalEstateMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPurposesOfNonResidentalEstate() throws Exception {
        // Initialize the database
        purposesOfNonResidentalEstateRepository.saveAndFlush(purposesOfNonResidentalEstate);

        int databaseSizeBeforeUpdate = purposesOfNonResidentalEstateRepository.findAll().size();

        // Update the purposesOfNonResidentalEstate
        PurposesOfNonResidentalEstate updatedPurposesOfNonResidentalEstate = purposesOfNonResidentalEstateRepository
            .findById(purposesOfNonResidentalEstate.getId())
            .get();
        // Disconnect from session so that the updates on updatedPurposesOfNonResidentalEstate are not directly saved in db
        em.detach(updatedPurposesOfNonResidentalEstate);
        updatedPurposesOfNonResidentalEstate.title(UPDATED_TITLE);
        PurposesOfNonResidentalEstateDTO purposesOfNonResidentalEstateDTO = purposesOfNonResidentalEstateMapper.toDto(
            updatedPurposesOfNonResidentalEstate
        );

        restPurposesOfNonResidentalEstateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, purposesOfNonResidentalEstateDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(purposesOfNonResidentalEstateDTO))
            )
            .andExpect(status().isOk());

        // Validate the PurposesOfNonResidentalEstate in the database
        List<PurposesOfNonResidentalEstate> purposesOfNonResidentalEstateList = purposesOfNonResidentalEstateRepository.findAll();
        assertThat(purposesOfNonResidentalEstateList).hasSize(databaseSizeBeforeUpdate);
        PurposesOfNonResidentalEstate testPurposesOfNonResidentalEstate = purposesOfNonResidentalEstateList.get(
            purposesOfNonResidentalEstateList.size() - 1
        );
        assertThat(testPurposesOfNonResidentalEstate.getTitle()).isEqualTo(UPDATED_TITLE);
    }

    @Test
    @Transactional
    void putNonExistingPurposesOfNonResidentalEstate() throws Exception {
        int databaseSizeBeforeUpdate = purposesOfNonResidentalEstateRepository.findAll().size();
        purposesOfNonResidentalEstate.setId(count.incrementAndGet());

        // Create the PurposesOfNonResidentalEstate
        PurposesOfNonResidentalEstateDTO purposesOfNonResidentalEstateDTO = purposesOfNonResidentalEstateMapper.toDto(
            purposesOfNonResidentalEstate
        );

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPurposesOfNonResidentalEstateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, purposesOfNonResidentalEstateDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(purposesOfNonResidentalEstateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PurposesOfNonResidentalEstate in the database
        List<PurposesOfNonResidentalEstate> purposesOfNonResidentalEstateList = purposesOfNonResidentalEstateRepository.findAll();
        assertThat(purposesOfNonResidentalEstateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPurposesOfNonResidentalEstate() throws Exception {
        int databaseSizeBeforeUpdate = purposesOfNonResidentalEstateRepository.findAll().size();
        purposesOfNonResidentalEstate.setId(count.incrementAndGet());

        // Create the PurposesOfNonResidentalEstate
        PurposesOfNonResidentalEstateDTO purposesOfNonResidentalEstateDTO = purposesOfNonResidentalEstateMapper.toDto(
            purposesOfNonResidentalEstate
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPurposesOfNonResidentalEstateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(purposesOfNonResidentalEstateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PurposesOfNonResidentalEstate in the database
        List<PurposesOfNonResidentalEstate> purposesOfNonResidentalEstateList = purposesOfNonResidentalEstateRepository.findAll();
        assertThat(purposesOfNonResidentalEstateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPurposesOfNonResidentalEstate() throws Exception {
        int databaseSizeBeforeUpdate = purposesOfNonResidentalEstateRepository.findAll().size();
        purposesOfNonResidentalEstate.setId(count.incrementAndGet());

        // Create the PurposesOfNonResidentalEstate
        PurposesOfNonResidentalEstateDTO purposesOfNonResidentalEstateDTO = purposesOfNonResidentalEstateMapper.toDto(
            purposesOfNonResidentalEstate
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPurposesOfNonResidentalEstateMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(purposesOfNonResidentalEstateDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PurposesOfNonResidentalEstate in the database
        List<PurposesOfNonResidentalEstate> purposesOfNonResidentalEstateList = purposesOfNonResidentalEstateRepository.findAll();
        assertThat(purposesOfNonResidentalEstateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePurposesOfNonResidentalEstateWithPatch() throws Exception {
        // Initialize the database
        purposesOfNonResidentalEstateRepository.saveAndFlush(purposesOfNonResidentalEstate);

        int databaseSizeBeforeUpdate = purposesOfNonResidentalEstateRepository.findAll().size();

        // Update the purposesOfNonResidentalEstate using partial update
        PurposesOfNonResidentalEstate partialUpdatedPurposesOfNonResidentalEstate = new PurposesOfNonResidentalEstate();
        partialUpdatedPurposesOfNonResidentalEstate.setId(purposesOfNonResidentalEstate.getId());

        restPurposesOfNonResidentalEstateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPurposesOfNonResidentalEstate.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPurposesOfNonResidentalEstate))
            )
            .andExpect(status().isOk());

        // Validate the PurposesOfNonResidentalEstate in the database
        List<PurposesOfNonResidentalEstate> purposesOfNonResidentalEstateList = purposesOfNonResidentalEstateRepository.findAll();
        assertThat(purposesOfNonResidentalEstateList).hasSize(databaseSizeBeforeUpdate);
        PurposesOfNonResidentalEstate testPurposesOfNonResidentalEstate = purposesOfNonResidentalEstateList.get(
            purposesOfNonResidentalEstateList.size() - 1
        );
        assertThat(testPurposesOfNonResidentalEstate.getTitle()).isEqualTo(DEFAULT_TITLE);
    }

    @Test
    @Transactional
    void fullUpdatePurposesOfNonResidentalEstateWithPatch() throws Exception {
        // Initialize the database
        purposesOfNonResidentalEstateRepository.saveAndFlush(purposesOfNonResidentalEstate);

        int databaseSizeBeforeUpdate = purposesOfNonResidentalEstateRepository.findAll().size();

        // Update the purposesOfNonResidentalEstate using partial update
        PurposesOfNonResidentalEstate partialUpdatedPurposesOfNonResidentalEstate = new PurposesOfNonResidentalEstate();
        partialUpdatedPurposesOfNonResidentalEstate.setId(purposesOfNonResidentalEstate.getId());

        partialUpdatedPurposesOfNonResidentalEstate.title(UPDATED_TITLE);

        restPurposesOfNonResidentalEstateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPurposesOfNonResidentalEstate.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPurposesOfNonResidentalEstate))
            )
            .andExpect(status().isOk());

        // Validate the PurposesOfNonResidentalEstate in the database
        List<PurposesOfNonResidentalEstate> purposesOfNonResidentalEstateList = purposesOfNonResidentalEstateRepository.findAll();
        assertThat(purposesOfNonResidentalEstateList).hasSize(databaseSizeBeforeUpdate);
        PurposesOfNonResidentalEstate testPurposesOfNonResidentalEstate = purposesOfNonResidentalEstateList.get(
            purposesOfNonResidentalEstateList.size() - 1
        );
        assertThat(testPurposesOfNonResidentalEstate.getTitle()).isEqualTo(UPDATED_TITLE);
    }

    @Test
    @Transactional
    void patchNonExistingPurposesOfNonResidentalEstate() throws Exception {
        int databaseSizeBeforeUpdate = purposesOfNonResidentalEstateRepository.findAll().size();
        purposesOfNonResidentalEstate.setId(count.incrementAndGet());

        // Create the PurposesOfNonResidentalEstate
        PurposesOfNonResidentalEstateDTO purposesOfNonResidentalEstateDTO = purposesOfNonResidentalEstateMapper.toDto(
            purposesOfNonResidentalEstate
        );

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPurposesOfNonResidentalEstateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, purposesOfNonResidentalEstateDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(purposesOfNonResidentalEstateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PurposesOfNonResidentalEstate in the database
        List<PurposesOfNonResidentalEstate> purposesOfNonResidentalEstateList = purposesOfNonResidentalEstateRepository.findAll();
        assertThat(purposesOfNonResidentalEstateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPurposesOfNonResidentalEstate() throws Exception {
        int databaseSizeBeforeUpdate = purposesOfNonResidentalEstateRepository.findAll().size();
        purposesOfNonResidentalEstate.setId(count.incrementAndGet());

        // Create the PurposesOfNonResidentalEstate
        PurposesOfNonResidentalEstateDTO purposesOfNonResidentalEstateDTO = purposesOfNonResidentalEstateMapper.toDto(
            purposesOfNonResidentalEstate
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPurposesOfNonResidentalEstateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(purposesOfNonResidentalEstateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PurposesOfNonResidentalEstate in the database
        List<PurposesOfNonResidentalEstate> purposesOfNonResidentalEstateList = purposesOfNonResidentalEstateRepository.findAll();
        assertThat(purposesOfNonResidentalEstateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPurposesOfNonResidentalEstate() throws Exception {
        int databaseSizeBeforeUpdate = purposesOfNonResidentalEstateRepository.findAll().size();
        purposesOfNonResidentalEstate.setId(count.incrementAndGet());

        // Create the PurposesOfNonResidentalEstate
        PurposesOfNonResidentalEstateDTO purposesOfNonResidentalEstateDTO = purposesOfNonResidentalEstateMapper.toDto(
            purposesOfNonResidentalEstate
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPurposesOfNonResidentalEstateMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(purposesOfNonResidentalEstateDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PurposesOfNonResidentalEstate in the database
        List<PurposesOfNonResidentalEstate> purposesOfNonResidentalEstateList = purposesOfNonResidentalEstateRepository.findAll();
        assertThat(purposesOfNonResidentalEstateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePurposesOfNonResidentalEstate() throws Exception {
        // Initialize the database
        purposesOfNonResidentalEstateRepository.saveAndFlush(purposesOfNonResidentalEstate);

        int databaseSizeBeforeDelete = purposesOfNonResidentalEstateRepository.findAll().size();

        // Delete the purposesOfNonResidentalEstate
        restPurposesOfNonResidentalEstateMockMvc
            .perform(delete(ENTITY_API_URL_ID, purposesOfNonResidentalEstate.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PurposesOfNonResidentalEstate> purposesOfNonResidentalEstateList = purposesOfNonResidentalEstateRepository.findAll();
        assertThat(purposesOfNonResidentalEstateList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
