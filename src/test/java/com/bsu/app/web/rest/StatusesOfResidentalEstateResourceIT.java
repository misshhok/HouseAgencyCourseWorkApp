package com.bsu.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.bsu.app.IntegrationTest;
import com.bsu.app.domain.StatusesOfResidentalEstate;
import com.bsu.app.repository.StatusesOfResidentalEstateRepository;
import com.bsu.app.service.criteria.StatusesOfResidentalEstateCriteria;
import com.bsu.app.service.dto.StatusesOfResidentalEstateDTO;
import com.bsu.app.service.mapper.StatusesOfResidentalEstateMapper;
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
 * Integration tests for the {@link StatusesOfResidentalEstateResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class StatusesOfResidentalEstateResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/statuses-of-residental-estates";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private StatusesOfResidentalEstateRepository statusesOfResidentalEstateRepository;

    @Autowired
    private StatusesOfResidentalEstateMapper statusesOfResidentalEstateMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStatusesOfResidentalEstateMockMvc;

    private StatusesOfResidentalEstate statusesOfResidentalEstate;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StatusesOfResidentalEstate createEntity(EntityManager em) {
        StatusesOfResidentalEstate statusesOfResidentalEstate = new StatusesOfResidentalEstate().title(DEFAULT_TITLE);
        return statusesOfResidentalEstate;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StatusesOfResidentalEstate createUpdatedEntity(EntityManager em) {
        StatusesOfResidentalEstate statusesOfResidentalEstate = new StatusesOfResidentalEstate().title(UPDATED_TITLE);
        return statusesOfResidentalEstate;
    }

    @BeforeEach
    public void initTest() {
        statusesOfResidentalEstate = createEntity(em);
    }

    @Test
    @Transactional
    void createStatusesOfResidentalEstate() throws Exception {
        int databaseSizeBeforeCreate = statusesOfResidentalEstateRepository.findAll().size();
        // Create the StatusesOfResidentalEstate
        StatusesOfResidentalEstateDTO statusesOfResidentalEstateDTO = statusesOfResidentalEstateMapper.toDto(statusesOfResidentalEstate);
        restStatusesOfResidentalEstateMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(statusesOfResidentalEstateDTO))
            )
            .andExpect(status().isCreated());

        // Validate the StatusesOfResidentalEstate in the database
        List<StatusesOfResidentalEstate> statusesOfResidentalEstateList = statusesOfResidentalEstateRepository.findAll();
        assertThat(statusesOfResidentalEstateList).hasSize(databaseSizeBeforeCreate + 1);
        StatusesOfResidentalEstate testStatusesOfResidentalEstate = statusesOfResidentalEstateList.get(
            statusesOfResidentalEstateList.size() - 1
        );
        assertThat(testStatusesOfResidentalEstate.getTitle()).isEqualTo(DEFAULT_TITLE);
    }

    @Test
    @Transactional
    void createStatusesOfResidentalEstateWithExistingId() throws Exception {
        // Create the StatusesOfResidentalEstate with an existing ID
        statusesOfResidentalEstate.setId(1L);
        StatusesOfResidentalEstateDTO statusesOfResidentalEstateDTO = statusesOfResidentalEstateMapper.toDto(statusesOfResidentalEstate);

        int databaseSizeBeforeCreate = statusesOfResidentalEstateRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restStatusesOfResidentalEstateMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(statusesOfResidentalEstateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the StatusesOfResidentalEstate in the database
        List<StatusesOfResidentalEstate> statusesOfResidentalEstateList = statusesOfResidentalEstateRepository.findAll();
        assertThat(statusesOfResidentalEstateList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllStatusesOfResidentalEstates() throws Exception {
        // Initialize the database
        statusesOfResidentalEstateRepository.saveAndFlush(statusesOfResidentalEstate);

        // Get all the statusesOfResidentalEstateList
        restStatusesOfResidentalEstateMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(statusesOfResidentalEstate.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)));
    }

    @Test
    @Transactional
    void getStatusesOfResidentalEstate() throws Exception {
        // Initialize the database
        statusesOfResidentalEstateRepository.saveAndFlush(statusesOfResidentalEstate);

        // Get the statusesOfResidentalEstate
        restStatusesOfResidentalEstateMockMvc
            .perform(get(ENTITY_API_URL_ID, statusesOfResidentalEstate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(statusesOfResidentalEstate.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE));
    }

    @Test
    @Transactional
    void getStatusesOfResidentalEstatesByIdFiltering() throws Exception {
        // Initialize the database
        statusesOfResidentalEstateRepository.saveAndFlush(statusesOfResidentalEstate);

        Long id = statusesOfResidentalEstate.getId();

        defaultStatusesOfResidentalEstateShouldBeFound("id.equals=" + id);
        defaultStatusesOfResidentalEstateShouldNotBeFound("id.notEquals=" + id);

        defaultStatusesOfResidentalEstateShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultStatusesOfResidentalEstateShouldNotBeFound("id.greaterThan=" + id);

        defaultStatusesOfResidentalEstateShouldBeFound("id.lessThanOrEqual=" + id);
        defaultStatusesOfResidentalEstateShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllStatusesOfResidentalEstatesByTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        statusesOfResidentalEstateRepository.saveAndFlush(statusesOfResidentalEstate);

        // Get all the statusesOfResidentalEstateList where title equals to DEFAULT_TITLE
        defaultStatusesOfResidentalEstateShouldBeFound("title.equals=" + DEFAULT_TITLE);

        // Get all the statusesOfResidentalEstateList where title equals to UPDATED_TITLE
        defaultStatusesOfResidentalEstateShouldNotBeFound("title.equals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllStatusesOfResidentalEstatesByTitleIsInShouldWork() throws Exception {
        // Initialize the database
        statusesOfResidentalEstateRepository.saveAndFlush(statusesOfResidentalEstate);

        // Get all the statusesOfResidentalEstateList where title in DEFAULT_TITLE or UPDATED_TITLE
        defaultStatusesOfResidentalEstateShouldBeFound("title.in=" + DEFAULT_TITLE + "," + UPDATED_TITLE);

        // Get all the statusesOfResidentalEstateList where title equals to UPDATED_TITLE
        defaultStatusesOfResidentalEstateShouldNotBeFound("title.in=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllStatusesOfResidentalEstatesByTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        statusesOfResidentalEstateRepository.saveAndFlush(statusesOfResidentalEstate);

        // Get all the statusesOfResidentalEstateList where title is not null
        defaultStatusesOfResidentalEstateShouldBeFound("title.specified=true");

        // Get all the statusesOfResidentalEstateList where title is null
        defaultStatusesOfResidentalEstateShouldNotBeFound("title.specified=false");
    }

    @Test
    @Transactional
    void getAllStatusesOfResidentalEstatesByTitleContainsSomething() throws Exception {
        // Initialize the database
        statusesOfResidentalEstateRepository.saveAndFlush(statusesOfResidentalEstate);

        // Get all the statusesOfResidentalEstateList where title contains DEFAULT_TITLE
        defaultStatusesOfResidentalEstateShouldBeFound("title.contains=" + DEFAULT_TITLE);

        // Get all the statusesOfResidentalEstateList where title contains UPDATED_TITLE
        defaultStatusesOfResidentalEstateShouldNotBeFound("title.contains=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllStatusesOfResidentalEstatesByTitleNotContainsSomething() throws Exception {
        // Initialize the database
        statusesOfResidentalEstateRepository.saveAndFlush(statusesOfResidentalEstate);

        // Get all the statusesOfResidentalEstateList where title does not contain DEFAULT_TITLE
        defaultStatusesOfResidentalEstateShouldNotBeFound("title.doesNotContain=" + DEFAULT_TITLE);

        // Get all the statusesOfResidentalEstateList where title does not contain UPDATED_TITLE
        defaultStatusesOfResidentalEstateShouldBeFound("title.doesNotContain=" + UPDATED_TITLE);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultStatusesOfResidentalEstateShouldBeFound(String filter) throws Exception {
        restStatusesOfResidentalEstateMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(statusesOfResidentalEstate.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)));

        // Check, that the count call also returns 1
        restStatusesOfResidentalEstateMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultStatusesOfResidentalEstateShouldNotBeFound(String filter) throws Exception {
        restStatusesOfResidentalEstateMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restStatusesOfResidentalEstateMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingStatusesOfResidentalEstate() throws Exception {
        // Get the statusesOfResidentalEstate
        restStatusesOfResidentalEstateMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingStatusesOfResidentalEstate() throws Exception {
        // Initialize the database
        statusesOfResidentalEstateRepository.saveAndFlush(statusesOfResidentalEstate);

        int databaseSizeBeforeUpdate = statusesOfResidentalEstateRepository.findAll().size();

        // Update the statusesOfResidentalEstate
        StatusesOfResidentalEstate updatedStatusesOfResidentalEstate = statusesOfResidentalEstateRepository
            .findById(statusesOfResidentalEstate.getId())
            .get();
        // Disconnect from session so that the updates on updatedStatusesOfResidentalEstate are not directly saved in db
        em.detach(updatedStatusesOfResidentalEstate);
        updatedStatusesOfResidentalEstate.title(UPDATED_TITLE);
        StatusesOfResidentalEstateDTO statusesOfResidentalEstateDTO = statusesOfResidentalEstateMapper.toDto(
            updatedStatusesOfResidentalEstate
        );

        restStatusesOfResidentalEstateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, statusesOfResidentalEstateDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(statusesOfResidentalEstateDTO))
            )
            .andExpect(status().isOk());

        // Validate the StatusesOfResidentalEstate in the database
        List<StatusesOfResidentalEstate> statusesOfResidentalEstateList = statusesOfResidentalEstateRepository.findAll();
        assertThat(statusesOfResidentalEstateList).hasSize(databaseSizeBeforeUpdate);
        StatusesOfResidentalEstate testStatusesOfResidentalEstate = statusesOfResidentalEstateList.get(
            statusesOfResidentalEstateList.size() - 1
        );
        assertThat(testStatusesOfResidentalEstate.getTitle()).isEqualTo(UPDATED_TITLE);
    }

    @Test
    @Transactional
    void putNonExistingStatusesOfResidentalEstate() throws Exception {
        int databaseSizeBeforeUpdate = statusesOfResidentalEstateRepository.findAll().size();
        statusesOfResidentalEstate.setId(count.incrementAndGet());

        // Create the StatusesOfResidentalEstate
        StatusesOfResidentalEstateDTO statusesOfResidentalEstateDTO = statusesOfResidentalEstateMapper.toDto(statusesOfResidentalEstate);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStatusesOfResidentalEstateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, statusesOfResidentalEstateDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(statusesOfResidentalEstateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the StatusesOfResidentalEstate in the database
        List<StatusesOfResidentalEstate> statusesOfResidentalEstateList = statusesOfResidentalEstateRepository.findAll();
        assertThat(statusesOfResidentalEstateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchStatusesOfResidentalEstate() throws Exception {
        int databaseSizeBeforeUpdate = statusesOfResidentalEstateRepository.findAll().size();
        statusesOfResidentalEstate.setId(count.incrementAndGet());

        // Create the StatusesOfResidentalEstate
        StatusesOfResidentalEstateDTO statusesOfResidentalEstateDTO = statusesOfResidentalEstateMapper.toDto(statusesOfResidentalEstate);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStatusesOfResidentalEstateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(statusesOfResidentalEstateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the StatusesOfResidentalEstate in the database
        List<StatusesOfResidentalEstate> statusesOfResidentalEstateList = statusesOfResidentalEstateRepository.findAll();
        assertThat(statusesOfResidentalEstateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamStatusesOfResidentalEstate() throws Exception {
        int databaseSizeBeforeUpdate = statusesOfResidentalEstateRepository.findAll().size();
        statusesOfResidentalEstate.setId(count.incrementAndGet());

        // Create the StatusesOfResidentalEstate
        StatusesOfResidentalEstateDTO statusesOfResidentalEstateDTO = statusesOfResidentalEstateMapper.toDto(statusesOfResidentalEstate);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStatusesOfResidentalEstateMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(statusesOfResidentalEstateDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the StatusesOfResidentalEstate in the database
        List<StatusesOfResidentalEstate> statusesOfResidentalEstateList = statusesOfResidentalEstateRepository.findAll();
        assertThat(statusesOfResidentalEstateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateStatusesOfResidentalEstateWithPatch() throws Exception {
        // Initialize the database
        statusesOfResidentalEstateRepository.saveAndFlush(statusesOfResidentalEstate);

        int databaseSizeBeforeUpdate = statusesOfResidentalEstateRepository.findAll().size();

        // Update the statusesOfResidentalEstate using partial update
        StatusesOfResidentalEstate partialUpdatedStatusesOfResidentalEstate = new StatusesOfResidentalEstate();
        partialUpdatedStatusesOfResidentalEstate.setId(statusesOfResidentalEstate.getId());

        restStatusesOfResidentalEstateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStatusesOfResidentalEstate.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStatusesOfResidentalEstate))
            )
            .andExpect(status().isOk());

        // Validate the StatusesOfResidentalEstate in the database
        List<StatusesOfResidentalEstate> statusesOfResidentalEstateList = statusesOfResidentalEstateRepository.findAll();
        assertThat(statusesOfResidentalEstateList).hasSize(databaseSizeBeforeUpdate);
        StatusesOfResidentalEstate testStatusesOfResidentalEstate = statusesOfResidentalEstateList.get(
            statusesOfResidentalEstateList.size() - 1
        );
        assertThat(testStatusesOfResidentalEstate.getTitle()).isEqualTo(DEFAULT_TITLE);
    }

    @Test
    @Transactional
    void fullUpdateStatusesOfResidentalEstateWithPatch() throws Exception {
        // Initialize the database
        statusesOfResidentalEstateRepository.saveAndFlush(statusesOfResidentalEstate);

        int databaseSizeBeforeUpdate = statusesOfResidentalEstateRepository.findAll().size();

        // Update the statusesOfResidentalEstate using partial update
        StatusesOfResidentalEstate partialUpdatedStatusesOfResidentalEstate = new StatusesOfResidentalEstate();
        partialUpdatedStatusesOfResidentalEstate.setId(statusesOfResidentalEstate.getId());

        partialUpdatedStatusesOfResidentalEstate.title(UPDATED_TITLE);

        restStatusesOfResidentalEstateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStatusesOfResidentalEstate.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStatusesOfResidentalEstate))
            )
            .andExpect(status().isOk());

        // Validate the StatusesOfResidentalEstate in the database
        List<StatusesOfResidentalEstate> statusesOfResidentalEstateList = statusesOfResidentalEstateRepository.findAll();
        assertThat(statusesOfResidentalEstateList).hasSize(databaseSizeBeforeUpdate);
        StatusesOfResidentalEstate testStatusesOfResidentalEstate = statusesOfResidentalEstateList.get(
            statusesOfResidentalEstateList.size() - 1
        );
        assertThat(testStatusesOfResidentalEstate.getTitle()).isEqualTo(UPDATED_TITLE);
    }

    @Test
    @Transactional
    void patchNonExistingStatusesOfResidentalEstate() throws Exception {
        int databaseSizeBeforeUpdate = statusesOfResidentalEstateRepository.findAll().size();
        statusesOfResidentalEstate.setId(count.incrementAndGet());

        // Create the StatusesOfResidentalEstate
        StatusesOfResidentalEstateDTO statusesOfResidentalEstateDTO = statusesOfResidentalEstateMapper.toDto(statusesOfResidentalEstate);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStatusesOfResidentalEstateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, statusesOfResidentalEstateDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(statusesOfResidentalEstateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the StatusesOfResidentalEstate in the database
        List<StatusesOfResidentalEstate> statusesOfResidentalEstateList = statusesOfResidentalEstateRepository.findAll();
        assertThat(statusesOfResidentalEstateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchStatusesOfResidentalEstate() throws Exception {
        int databaseSizeBeforeUpdate = statusesOfResidentalEstateRepository.findAll().size();
        statusesOfResidentalEstate.setId(count.incrementAndGet());

        // Create the StatusesOfResidentalEstate
        StatusesOfResidentalEstateDTO statusesOfResidentalEstateDTO = statusesOfResidentalEstateMapper.toDto(statusesOfResidentalEstate);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStatusesOfResidentalEstateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(statusesOfResidentalEstateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the StatusesOfResidentalEstate in the database
        List<StatusesOfResidentalEstate> statusesOfResidentalEstateList = statusesOfResidentalEstateRepository.findAll();
        assertThat(statusesOfResidentalEstateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamStatusesOfResidentalEstate() throws Exception {
        int databaseSizeBeforeUpdate = statusesOfResidentalEstateRepository.findAll().size();
        statusesOfResidentalEstate.setId(count.incrementAndGet());

        // Create the StatusesOfResidentalEstate
        StatusesOfResidentalEstateDTO statusesOfResidentalEstateDTO = statusesOfResidentalEstateMapper.toDto(statusesOfResidentalEstate);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStatusesOfResidentalEstateMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(statusesOfResidentalEstateDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the StatusesOfResidentalEstate in the database
        List<StatusesOfResidentalEstate> statusesOfResidentalEstateList = statusesOfResidentalEstateRepository.findAll();
        assertThat(statusesOfResidentalEstateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteStatusesOfResidentalEstate() throws Exception {
        // Initialize the database
        statusesOfResidentalEstateRepository.saveAndFlush(statusesOfResidentalEstate);

        int databaseSizeBeforeDelete = statusesOfResidentalEstateRepository.findAll().size();

        // Delete the statusesOfResidentalEstate
        restStatusesOfResidentalEstateMockMvc
            .perform(delete(ENTITY_API_URL_ID, statusesOfResidentalEstate.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<StatusesOfResidentalEstate> statusesOfResidentalEstateList = statusesOfResidentalEstateRepository.findAll();
        assertThat(statusesOfResidentalEstateList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
