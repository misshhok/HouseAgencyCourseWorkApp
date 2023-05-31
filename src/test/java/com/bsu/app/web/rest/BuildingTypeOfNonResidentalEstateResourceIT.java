package com.bsu.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.bsu.app.IntegrationTest;
import com.bsu.app.domain.BuildingTypeOfNonResidentalEstate;
import com.bsu.app.repository.BuildingTypeOfNonResidentalEstateRepository;
import com.bsu.app.service.criteria.BuildingTypeOfNonResidentalEstateCriteria;
import com.bsu.app.service.dto.BuildingTypeOfNonResidentalEstateDTO;
import com.bsu.app.service.mapper.BuildingTypeOfNonResidentalEstateMapper;
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
 * Integration tests for the {@link BuildingTypeOfNonResidentalEstateResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BuildingTypeOfNonResidentalEstateResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/building-type-of-non-residental-estates";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BuildingTypeOfNonResidentalEstateRepository buildingTypeOfNonResidentalEstateRepository;

    @Autowired
    private BuildingTypeOfNonResidentalEstateMapper buildingTypeOfNonResidentalEstateMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBuildingTypeOfNonResidentalEstateMockMvc;

    private BuildingTypeOfNonResidentalEstate buildingTypeOfNonResidentalEstate;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BuildingTypeOfNonResidentalEstate createEntity(EntityManager em) {
        BuildingTypeOfNonResidentalEstate buildingTypeOfNonResidentalEstate = new BuildingTypeOfNonResidentalEstate().title(DEFAULT_TITLE);
        return buildingTypeOfNonResidentalEstate;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BuildingTypeOfNonResidentalEstate createUpdatedEntity(EntityManager em) {
        BuildingTypeOfNonResidentalEstate buildingTypeOfNonResidentalEstate = new BuildingTypeOfNonResidentalEstate().title(UPDATED_TITLE);
        return buildingTypeOfNonResidentalEstate;
    }

    @BeforeEach
    public void initTest() {
        buildingTypeOfNonResidentalEstate = createEntity(em);
    }

    @Test
    @Transactional
    void createBuildingTypeOfNonResidentalEstate() throws Exception {
        int databaseSizeBeforeCreate = buildingTypeOfNonResidentalEstateRepository.findAll().size();
        // Create the BuildingTypeOfNonResidentalEstate
        BuildingTypeOfNonResidentalEstateDTO buildingTypeOfNonResidentalEstateDTO = buildingTypeOfNonResidentalEstateMapper.toDto(
            buildingTypeOfNonResidentalEstate
        );
        restBuildingTypeOfNonResidentalEstateMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(buildingTypeOfNonResidentalEstateDTO))
            )
            .andExpect(status().isCreated());

        // Validate the BuildingTypeOfNonResidentalEstate in the database
        List<BuildingTypeOfNonResidentalEstate> buildingTypeOfNonResidentalEstateList = buildingTypeOfNonResidentalEstateRepository.findAll();
        assertThat(buildingTypeOfNonResidentalEstateList).hasSize(databaseSizeBeforeCreate + 1);
        BuildingTypeOfNonResidentalEstate testBuildingTypeOfNonResidentalEstate = buildingTypeOfNonResidentalEstateList.get(
            buildingTypeOfNonResidentalEstateList.size() - 1
        );
        assertThat(testBuildingTypeOfNonResidentalEstate.getTitle()).isEqualTo(DEFAULT_TITLE);
    }

    @Test
    @Transactional
    void createBuildingTypeOfNonResidentalEstateWithExistingId() throws Exception {
        // Create the BuildingTypeOfNonResidentalEstate with an existing ID
        buildingTypeOfNonResidentalEstate.setId(1L);
        BuildingTypeOfNonResidentalEstateDTO buildingTypeOfNonResidentalEstateDTO = buildingTypeOfNonResidentalEstateMapper.toDto(
            buildingTypeOfNonResidentalEstate
        );

        int databaseSizeBeforeCreate = buildingTypeOfNonResidentalEstateRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBuildingTypeOfNonResidentalEstateMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(buildingTypeOfNonResidentalEstateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BuildingTypeOfNonResidentalEstate in the database
        List<BuildingTypeOfNonResidentalEstate> buildingTypeOfNonResidentalEstateList = buildingTypeOfNonResidentalEstateRepository.findAll();
        assertThat(buildingTypeOfNonResidentalEstateList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBuildingTypeOfNonResidentalEstates() throws Exception {
        // Initialize the database
        buildingTypeOfNonResidentalEstateRepository.saveAndFlush(buildingTypeOfNonResidentalEstate);

        // Get all the buildingTypeOfNonResidentalEstateList
        restBuildingTypeOfNonResidentalEstateMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(buildingTypeOfNonResidentalEstate.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)));
    }

    @Test
    @Transactional
    void getBuildingTypeOfNonResidentalEstate() throws Exception {
        // Initialize the database
        buildingTypeOfNonResidentalEstateRepository.saveAndFlush(buildingTypeOfNonResidentalEstate);

        // Get the buildingTypeOfNonResidentalEstate
        restBuildingTypeOfNonResidentalEstateMockMvc
            .perform(get(ENTITY_API_URL_ID, buildingTypeOfNonResidentalEstate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(buildingTypeOfNonResidentalEstate.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE));
    }

    @Test
    @Transactional
    void getBuildingTypeOfNonResidentalEstatesByIdFiltering() throws Exception {
        // Initialize the database
        buildingTypeOfNonResidentalEstateRepository.saveAndFlush(buildingTypeOfNonResidentalEstate);

        Long id = buildingTypeOfNonResidentalEstate.getId();

        defaultBuildingTypeOfNonResidentalEstateShouldBeFound("id.equals=" + id);
        defaultBuildingTypeOfNonResidentalEstateShouldNotBeFound("id.notEquals=" + id);

        defaultBuildingTypeOfNonResidentalEstateShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultBuildingTypeOfNonResidentalEstateShouldNotBeFound("id.greaterThan=" + id);

        defaultBuildingTypeOfNonResidentalEstateShouldBeFound("id.lessThanOrEqual=" + id);
        defaultBuildingTypeOfNonResidentalEstateShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllBuildingTypeOfNonResidentalEstatesByTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        buildingTypeOfNonResidentalEstateRepository.saveAndFlush(buildingTypeOfNonResidentalEstate);

        // Get all the buildingTypeOfNonResidentalEstateList where title equals to DEFAULT_TITLE
        defaultBuildingTypeOfNonResidentalEstateShouldBeFound("title.equals=" + DEFAULT_TITLE);

        // Get all the buildingTypeOfNonResidentalEstateList where title equals to UPDATED_TITLE
        defaultBuildingTypeOfNonResidentalEstateShouldNotBeFound("title.equals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllBuildingTypeOfNonResidentalEstatesByTitleIsInShouldWork() throws Exception {
        // Initialize the database
        buildingTypeOfNonResidentalEstateRepository.saveAndFlush(buildingTypeOfNonResidentalEstate);

        // Get all the buildingTypeOfNonResidentalEstateList where title in DEFAULT_TITLE or UPDATED_TITLE
        defaultBuildingTypeOfNonResidentalEstateShouldBeFound("title.in=" + DEFAULT_TITLE + "," + UPDATED_TITLE);

        // Get all the buildingTypeOfNonResidentalEstateList where title equals to UPDATED_TITLE
        defaultBuildingTypeOfNonResidentalEstateShouldNotBeFound("title.in=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllBuildingTypeOfNonResidentalEstatesByTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        buildingTypeOfNonResidentalEstateRepository.saveAndFlush(buildingTypeOfNonResidentalEstate);

        // Get all the buildingTypeOfNonResidentalEstateList where title is not null
        defaultBuildingTypeOfNonResidentalEstateShouldBeFound("title.specified=true");

        // Get all the buildingTypeOfNonResidentalEstateList where title is null
        defaultBuildingTypeOfNonResidentalEstateShouldNotBeFound("title.specified=false");
    }

    @Test
    @Transactional
    void getAllBuildingTypeOfNonResidentalEstatesByTitleContainsSomething() throws Exception {
        // Initialize the database
        buildingTypeOfNonResidentalEstateRepository.saveAndFlush(buildingTypeOfNonResidentalEstate);

        // Get all the buildingTypeOfNonResidentalEstateList where title contains DEFAULT_TITLE
        defaultBuildingTypeOfNonResidentalEstateShouldBeFound("title.contains=" + DEFAULT_TITLE);

        // Get all the buildingTypeOfNonResidentalEstateList where title contains UPDATED_TITLE
        defaultBuildingTypeOfNonResidentalEstateShouldNotBeFound("title.contains=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllBuildingTypeOfNonResidentalEstatesByTitleNotContainsSomething() throws Exception {
        // Initialize the database
        buildingTypeOfNonResidentalEstateRepository.saveAndFlush(buildingTypeOfNonResidentalEstate);

        // Get all the buildingTypeOfNonResidentalEstateList where title does not contain DEFAULT_TITLE
        defaultBuildingTypeOfNonResidentalEstateShouldNotBeFound("title.doesNotContain=" + DEFAULT_TITLE);

        // Get all the buildingTypeOfNonResidentalEstateList where title does not contain UPDATED_TITLE
        defaultBuildingTypeOfNonResidentalEstateShouldBeFound("title.doesNotContain=" + UPDATED_TITLE);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultBuildingTypeOfNonResidentalEstateShouldBeFound(String filter) throws Exception {
        restBuildingTypeOfNonResidentalEstateMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(buildingTypeOfNonResidentalEstate.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)));

        // Check, that the count call also returns 1
        restBuildingTypeOfNonResidentalEstateMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultBuildingTypeOfNonResidentalEstateShouldNotBeFound(String filter) throws Exception {
        restBuildingTypeOfNonResidentalEstateMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restBuildingTypeOfNonResidentalEstateMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingBuildingTypeOfNonResidentalEstate() throws Exception {
        // Get the buildingTypeOfNonResidentalEstate
        restBuildingTypeOfNonResidentalEstateMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBuildingTypeOfNonResidentalEstate() throws Exception {
        // Initialize the database
        buildingTypeOfNonResidentalEstateRepository.saveAndFlush(buildingTypeOfNonResidentalEstate);

        int databaseSizeBeforeUpdate = buildingTypeOfNonResidentalEstateRepository.findAll().size();

        // Update the buildingTypeOfNonResidentalEstate
        BuildingTypeOfNonResidentalEstate updatedBuildingTypeOfNonResidentalEstate = buildingTypeOfNonResidentalEstateRepository
            .findById(buildingTypeOfNonResidentalEstate.getId())
            .get();
        // Disconnect from session so that the updates on updatedBuildingTypeOfNonResidentalEstate are not directly saved in db
        em.detach(updatedBuildingTypeOfNonResidentalEstate);
        updatedBuildingTypeOfNonResidentalEstate.title(UPDATED_TITLE);
        BuildingTypeOfNonResidentalEstateDTO buildingTypeOfNonResidentalEstateDTO = buildingTypeOfNonResidentalEstateMapper.toDto(
            updatedBuildingTypeOfNonResidentalEstate
        );

        restBuildingTypeOfNonResidentalEstateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, buildingTypeOfNonResidentalEstateDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(buildingTypeOfNonResidentalEstateDTO))
            )
            .andExpect(status().isOk());

        // Validate the BuildingTypeOfNonResidentalEstate in the database
        List<BuildingTypeOfNonResidentalEstate> buildingTypeOfNonResidentalEstateList = buildingTypeOfNonResidentalEstateRepository.findAll();
        assertThat(buildingTypeOfNonResidentalEstateList).hasSize(databaseSizeBeforeUpdate);
        BuildingTypeOfNonResidentalEstate testBuildingTypeOfNonResidentalEstate = buildingTypeOfNonResidentalEstateList.get(
            buildingTypeOfNonResidentalEstateList.size() - 1
        );
        assertThat(testBuildingTypeOfNonResidentalEstate.getTitle()).isEqualTo(UPDATED_TITLE);
    }

    @Test
    @Transactional
    void putNonExistingBuildingTypeOfNonResidentalEstate() throws Exception {
        int databaseSizeBeforeUpdate = buildingTypeOfNonResidentalEstateRepository.findAll().size();
        buildingTypeOfNonResidentalEstate.setId(count.incrementAndGet());

        // Create the BuildingTypeOfNonResidentalEstate
        BuildingTypeOfNonResidentalEstateDTO buildingTypeOfNonResidentalEstateDTO = buildingTypeOfNonResidentalEstateMapper.toDto(
            buildingTypeOfNonResidentalEstate
        );

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBuildingTypeOfNonResidentalEstateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, buildingTypeOfNonResidentalEstateDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(buildingTypeOfNonResidentalEstateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BuildingTypeOfNonResidentalEstate in the database
        List<BuildingTypeOfNonResidentalEstate> buildingTypeOfNonResidentalEstateList = buildingTypeOfNonResidentalEstateRepository.findAll();
        assertThat(buildingTypeOfNonResidentalEstateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBuildingTypeOfNonResidentalEstate() throws Exception {
        int databaseSizeBeforeUpdate = buildingTypeOfNonResidentalEstateRepository.findAll().size();
        buildingTypeOfNonResidentalEstate.setId(count.incrementAndGet());

        // Create the BuildingTypeOfNonResidentalEstate
        BuildingTypeOfNonResidentalEstateDTO buildingTypeOfNonResidentalEstateDTO = buildingTypeOfNonResidentalEstateMapper.toDto(
            buildingTypeOfNonResidentalEstate
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBuildingTypeOfNonResidentalEstateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(buildingTypeOfNonResidentalEstateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BuildingTypeOfNonResidentalEstate in the database
        List<BuildingTypeOfNonResidentalEstate> buildingTypeOfNonResidentalEstateList = buildingTypeOfNonResidentalEstateRepository.findAll();
        assertThat(buildingTypeOfNonResidentalEstateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBuildingTypeOfNonResidentalEstate() throws Exception {
        int databaseSizeBeforeUpdate = buildingTypeOfNonResidentalEstateRepository.findAll().size();
        buildingTypeOfNonResidentalEstate.setId(count.incrementAndGet());

        // Create the BuildingTypeOfNonResidentalEstate
        BuildingTypeOfNonResidentalEstateDTO buildingTypeOfNonResidentalEstateDTO = buildingTypeOfNonResidentalEstateMapper.toDto(
            buildingTypeOfNonResidentalEstate
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBuildingTypeOfNonResidentalEstateMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(buildingTypeOfNonResidentalEstateDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BuildingTypeOfNonResidentalEstate in the database
        List<BuildingTypeOfNonResidentalEstate> buildingTypeOfNonResidentalEstateList = buildingTypeOfNonResidentalEstateRepository.findAll();
        assertThat(buildingTypeOfNonResidentalEstateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBuildingTypeOfNonResidentalEstateWithPatch() throws Exception {
        // Initialize the database
        buildingTypeOfNonResidentalEstateRepository.saveAndFlush(buildingTypeOfNonResidentalEstate);

        int databaseSizeBeforeUpdate = buildingTypeOfNonResidentalEstateRepository.findAll().size();

        // Update the buildingTypeOfNonResidentalEstate using partial update
        BuildingTypeOfNonResidentalEstate partialUpdatedBuildingTypeOfNonResidentalEstate = new BuildingTypeOfNonResidentalEstate();
        partialUpdatedBuildingTypeOfNonResidentalEstate.setId(buildingTypeOfNonResidentalEstate.getId());

        restBuildingTypeOfNonResidentalEstateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBuildingTypeOfNonResidentalEstate.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBuildingTypeOfNonResidentalEstate))
            )
            .andExpect(status().isOk());

        // Validate the BuildingTypeOfNonResidentalEstate in the database
        List<BuildingTypeOfNonResidentalEstate> buildingTypeOfNonResidentalEstateList = buildingTypeOfNonResidentalEstateRepository.findAll();
        assertThat(buildingTypeOfNonResidentalEstateList).hasSize(databaseSizeBeforeUpdate);
        BuildingTypeOfNonResidentalEstate testBuildingTypeOfNonResidentalEstate = buildingTypeOfNonResidentalEstateList.get(
            buildingTypeOfNonResidentalEstateList.size() - 1
        );
        assertThat(testBuildingTypeOfNonResidentalEstate.getTitle()).isEqualTo(DEFAULT_TITLE);
    }

    @Test
    @Transactional
    void fullUpdateBuildingTypeOfNonResidentalEstateWithPatch() throws Exception {
        // Initialize the database
        buildingTypeOfNonResidentalEstateRepository.saveAndFlush(buildingTypeOfNonResidentalEstate);

        int databaseSizeBeforeUpdate = buildingTypeOfNonResidentalEstateRepository.findAll().size();

        // Update the buildingTypeOfNonResidentalEstate using partial update
        BuildingTypeOfNonResidentalEstate partialUpdatedBuildingTypeOfNonResidentalEstate = new BuildingTypeOfNonResidentalEstate();
        partialUpdatedBuildingTypeOfNonResidentalEstate.setId(buildingTypeOfNonResidentalEstate.getId());

        partialUpdatedBuildingTypeOfNonResidentalEstate.title(UPDATED_TITLE);

        restBuildingTypeOfNonResidentalEstateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBuildingTypeOfNonResidentalEstate.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBuildingTypeOfNonResidentalEstate))
            )
            .andExpect(status().isOk());

        // Validate the BuildingTypeOfNonResidentalEstate in the database
        List<BuildingTypeOfNonResidentalEstate> buildingTypeOfNonResidentalEstateList = buildingTypeOfNonResidentalEstateRepository.findAll();
        assertThat(buildingTypeOfNonResidentalEstateList).hasSize(databaseSizeBeforeUpdate);
        BuildingTypeOfNonResidentalEstate testBuildingTypeOfNonResidentalEstate = buildingTypeOfNonResidentalEstateList.get(
            buildingTypeOfNonResidentalEstateList.size() - 1
        );
        assertThat(testBuildingTypeOfNonResidentalEstate.getTitle()).isEqualTo(UPDATED_TITLE);
    }

    @Test
    @Transactional
    void patchNonExistingBuildingTypeOfNonResidentalEstate() throws Exception {
        int databaseSizeBeforeUpdate = buildingTypeOfNonResidentalEstateRepository.findAll().size();
        buildingTypeOfNonResidentalEstate.setId(count.incrementAndGet());

        // Create the BuildingTypeOfNonResidentalEstate
        BuildingTypeOfNonResidentalEstateDTO buildingTypeOfNonResidentalEstateDTO = buildingTypeOfNonResidentalEstateMapper.toDto(
            buildingTypeOfNonResidentalEstate
        );

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBuildingTypeOfNonResidentalEstateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, buildingTypeOfNonResidentalEstateDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(buildingTypeOfNonResidentalEstateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BuildingTypeOfNonResidentalEstate in the database
        List<BuildingTypeOfNonResidentalEstate> buildingTypeOfNonResidentalEstateList = buildingTypeOfNonResidentalEstateRepository.findAll();
        assertThat(buildingTypeOfNonResidentalEstateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBuildingTypeOfNonResidentalEstate() throws Exception {
        int databaseSizeBeforeUpdate = buildingTypeOfNonResidentalEstateRepository.findAll().size();
        buildingTypeOfNonResidentalEstate.setId(count.incrementAndGet());

        // Create the BuildingTypeOfNonResidentalEstate
        BuildingTypeOfNonResidentalEstateDTO buildingTypeOfNonResidentalEstateDTO = buildingTypeOfNonResidentalEstateMapper.toDto(
            buildingTypeOfNonResidentalEstate
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBuildingTypeOfNonResidentalEstateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(buildingTypeOfNonResidentalEstateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BuildingTypeOfNonResidentalEstate in the database
        List<BuildingTypeOfNonResidentalEstate> buildingTypeOfNonResidentalEstateList = buildingTypeOfNonResidentalEstateRepository.findAll();
        assertThat(buildingTypeOfNonResidentalEstateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBuildingTypeOfNonResidentalEstate() throws Exception {
        int databaseSizeBeforeUpdate = buildingTypeOfNonResidentalEstateRepository.findAll().size();
        buildingTypeOfNonResidentalEstate.setId(count.incrementAndGet());

        // Create the BuildingTypeOfNonResidentalEstate
        BuildingTypeOfNonResidentalEstateDTO buildingTypeOfNonResidentalEstateDTO = buildingTypeOfNonResidentalEstateMapper.toDto(
            buildingTypeOfNonResidentalEstate
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBuildingTypeOfNonResidentalEstateMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(buildingTypeOfNonResidentalEstateDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BuildingTypeOfNonResidentalEstate in the database
        List<BuildingTypeOfNonResidentalEstate> buildingTypeOfNonResidentalEstateList = buildingTypeOfNonResidentalEstateRepository.findAll();
        assertThat(buildingTypeOfNonResidentalEstateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBuildingTypeOfNonResidentalEstate() throws Exception {
        // Initialize the database
        buildingTypeOfNonResidentalEstateRepository.saveAndFlush(buildingTypeOfNonResidentalEstate);

        int databaseSizeBeforeDelete = buildingTypeOfNonResidentalEstateRepository.findAll().size();

        // Delete the buildingTypeOfNonResidentalEstate
        restBuildingTypeOfNonResidentalEstateMockMvc
            .perform(delete(ENTITY_API_URL_ID, buildingTypeOfNonResidentalEstate.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BuildingTypeOfNonResidentalEstate> buildingTypeOfNonResidentalEstateList = buildingTypeOfNonResidentalEstateRepository.findAll();
        assertThat(buildingTypeOfNonResidentalEstateList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
