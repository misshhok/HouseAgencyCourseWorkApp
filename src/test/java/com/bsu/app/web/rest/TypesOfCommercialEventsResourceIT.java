package com.bsu.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.bsu.app.IntegrationTest;
import com.bsu.app.domain.TypesOfCommercialEvents;
import com.bsu.app.repository.TypesOfCommercialEventsRepository;
import com.bsu.app.service.criteria.TypesOfCommercialEventsCriteria;
import com.bsu.app.service.dto.TypesOfCommercialEventsDTO;
import com.bsu.app.service.mapper.TypesOfCommercialEventsMapper;
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
 * Integration tests for the {@link TypesOfCommercialEventsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TypesOfCommercialEventsResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_ESTATE_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_ESTATE_TYPE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/types-of-commercial-events";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TypesOfCommercialEventsRepository typesOfCommercialEventsRepository;

    @Autowired
    private TypesOfCommercialEventsMapper typesOfCommercialEventsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTypesOfCommercialEventsMockMvc;

    private TypesOfCommercialEvents typesOfCommercialEvents;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TypesOfCommercialEvents createEntity(EntityManager em) {
        TypesOfCommercialEvents typesOfCommercialEvents = new TypesOfCommercialEvents()
            .title(DEFAULT_TITLE)
            .description(DEFAULT_DESCRIPTION)
            .estateType(DEFAULT_ESTATE_TYPE);
        return typesOfCommercialEvents;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TypesOfCommercialEvents createUpdatedEntity(EntityManager em) {
        TypesOfCommercialEvents typesOfCommercialEvents = new TypesOfCommercialEvents()
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .estateType(UPDATED_ESTATE_TYPE);
        return typesOfCommercialEvents;
    }

    @BeforeEach
    public void initTest() {
        typesOfCommercialEvents = createEntity(em);
    }

    @Test
    @Transactional
    void createTypesOfCommercialEvents() throws Exception {
        int databaseSizeBeforeCreate = typesOfCommercialEventsRepository.findAll().size();
        // Create the TypesOfCommercialEvents
        TypesOfCommercialEventsDTO typesOfCommercialEventsDTO = typesOfCommercialEventsMapper.toDto(typesOfCommercialEvents);
        restTypesOfCommercialEventsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(typesOfCommercialEventsDTO))
            )
            .andExpect(status().isCreated());

        // Validate the TypesOfCommercialEvents in the database
        List<TypesOfCommercialEvents> typesOfCommercialEventsList = typesOfCommercialEventsRepository.findAll();
        assertThat(typesOfCommercialEventsList).hasSize(databaseSizeBeforeCreate + 1);
        TypesOfCommercialEvents testTypesOfCommercialEvents = typesOfCommercialEventsList.get(typesOfCommercialEventsList.size() - 1);
        assertThat(testTypesOfCommercialEvents.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testTypesOfCommercialEvents.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testTypesOfCommercialEvents.getEstateType()).isEqualTo(DEFAULT_ESTATE_TYPE);
    }

    @Test
    @Transactional
    void createTypesOfCommercialEventsWithExistingId() throws Exception {
        // Create the TypesOfCommercialEvents with an existing ID
        typesOfCommercialEvents.setId(1L);
        TypesOfCommercialEventsDTO typesOfCommercialEventsDTO = typesOfCommercialEventsMapper.toDto(typesOfCommercialEvents);

        int databaseSizeBeforeCreate = typesOfCommercialEventsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTypesOfCommercialEventsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(typesOfCommercialEventsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TypesOfCommercialEvents in the database
        List<TypesOfCommercialEvents> typesOfCommercialEventsList = typesOfCommercialEventsRepository.findAll();
        assertThat(typesOfCommercialEventsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTypesOfCommercialEvents() throws Exception {
        // Initialize the database
        typesOfCommercialEventsRepository.saveAndFlush(typesOfCommercialEvents);

        // Get all the typesOfCommercialEventsList
        restTypesOfCommercialEventsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typesOfCommercialEvents.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].estateType").value(hasItem(DEFAULT_ESTATE_TYPE)));
    }

    @Test
    @Transactional
    void getTypesOfCommercialEvents() throws Exception {
        // Initialize the database
        typesOfCommercialEventsRepository.saveAndFlush(typesOfCommercialEvents);

        // Get the typesOfCommercialEvents
        restTypesOfCommercialEventsMockMvc
            .perform(get(ENTITY_API_URL_ID, typesOfCommercialEvents.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(typesOfCommercialEvents.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.estateType").value(DEFAULT_ESTATE_TYPE));
    }

    @Test
    @Transactional
    void getTypesOfCommercialEventsByIdFiltering() throws Exception {
        // Initialize the database
        typesOfCommercialEventsRepository.saveAndFlush(typesOfCommercialEvents);

        Long id = typesOfCommercialEvents.getId();

        defaultTypesOfCommercialEventsShouldBeFound("id.equals=" + id);
        defaultTypesOfCommercialEventsShouldNotBeFound("id.notEquals=" + id);

        defaultTypesOfCommercialEventsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTypesOfCommercialEventsShouldNotBeFound("id.greaterThan=" + id);

        defaultTypesOfCommercialEventsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTypesOfCommercialEventsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllTypesOfCommercialEventsByTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        typesOfCommercialEventsRepository.saveAndFlush(typesOfCommercialEvents);

        // Get all the typesOfCommercialEventsList where title equals to DEFAULT_TITLE
        defaultTypesOfCommercialEventsShouldBeFound("title.equals=" + DEFAULT_TITLE);

        // Get all the typesOfCommercialEventsList where title equals to UPDATED_TITLE
        defaultTypesOfCommercialEventsShouldNotBeFound("title.equals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllTypesOfCommercialEventsByTitleIsInShouldWork() throws Exception {
        // Initialize the database
        typesOfCommercialEventsRepository.saveAndFlush(typesOfCommercialEvents);

        // Get all the typesOfCommercialEventsList where title in DEFAULT_TITLE or UPDATED_TITLE
        defaultTypesOfCommercialEventsShouldBeFound("title.in=" + DEFAULT_TITLE + "," + UPDATED_TITLE);

        // Get all the typesOfCommercialEventsList where title equals to UPDATED_TITLE
        defaultTypesOfCommercialEventsShouldNotBeFound("title.in=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllTypesOfCommercialEventsByTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        typesOfCommercialEventsRepository.saveAndFlush(typesOfCommercialEvents);

        // Get all the typesOfCommercialEventsList where title is not null
        defaultTypesOfCommercialEventsShouldBeFound("title.specified=true");

        // Get all the typesOfCommercialEventsList where title is null
        defaultTypesOfCommercialEventsShouldNotBeFound("title.specified=false");
    }

    @Test
    @Transactional
    void getAllTypesOfCommercialEventsByTitleContainsSomething() throws Exception {
        // Initialize the database
        typesOfCommercialEventsRepository.saveAndFlush(typesOfCommercialEvents);

        // Get all the typesOfCommercialEventsList where title contains DEFAULT_TITLE
        defaultTypesOfCommercialEventsShouldBeFound("title.contains=" + DEFAULT_TITLE);

        // Get all the typesOfCommercialEventsList where title contains UPDATED_TITLE
        defaultTypesOfCommercialEventsShouldNotBeFound("title.contains=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllTypesOfCommercialEventsByTitleNotContainsSomething() throws Exception {
        // Initialize the database
        typesOfCommercialEventsRepository.saveAndFlush(typesOfCommercialEvents);

        // Get all the typesOfCommercialEventsList where title does not contain DEFAULT_TITLE
        defaultTypesOfCommercialEventsShouldNotBeFound("title.doesNotContain=" + DEFAULT_TITLE);

        // Get all the typesOfCommercialEventsList where title does not contain UPDATED_TITLE
        defaultTypesOfCommercialEventsShouldBeFound("title.doesNotContain=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllTypesOfCommercialEventsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        typesOfCommercialEventsRepository.saveAndFlush(typesOfCommercialEvents);

        // Get all the typesOfCommercialEventsList where description equals to DEFAULT_DESCRIPTION
        defaultTypesOfCommercialEventsShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the typesOfCommercialEventsList where description equals to UPDATED_DESCRIPTION
        defaultTypesOfCommercialEventsShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllTypesOfCommercialEventsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        typesOfCommercialEventsRepository.saveAndFlush(typesOfCommercialEvents);

        // Get all the typesOfCommercialEventsList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultTypesOfCommercialEventsShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the typesOfCommercialEventsList where description equals to UPDATED_DESCRIPTION
        defaultTypesOfCommercialEventsShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllTypesOfCommercialEventsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        typesOfCommercialEventsRepository.saveAndFlush(typesOfCommercialEvents);

        // Get all the typesOfCommercialEventsList where description is not null
        defaultTypesOfCommercialEventsShouldBeFound("description.specified=true");

        // Get all the typesOfCommercialEventsList where description is null
        defaultTypesOfCommercialEventsShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    void getAllTypesOfCommercialEventsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        typesOfCommercialEventsRepository.saveAndFlush(typesOfCommercialEvents);

        // Get all the typesOfCommercialEventsList where description contains DEFAULT_DESCRIPTION
        defaultTypesOfCommercialEventsShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the typesOfCommercialEventsList where description contains UPDATED_DESCRIPTION
        defaultTypesOfCommercialEventsShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllTypesOfCommercialEventsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        typesOfCommercialEventsRepository.saveAndFlush(typesOfCommercialEvents);

        // Get all the typesOfCommercialEventsList where description does not contain DEFAULT_DESCRIPTION
        defaultTypesOfCommercialEventsShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the typesOfCommercialEventsList where description does not contain UPDATED_DESCRIPTION
        defaultTypesOfCommercialEventsShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllTypesOfCommercialEventsByEstateTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        typesOfCommercialEventsRepository.saveAndFlush(typesOfCommercialEvents);

        // Get all the typesOfCommercialEventsList where estateType equals to DEFAULT_ESTATE_TYPE
        defaultTypesOfCommercialEventsShouldBeFound("estateType.equals=" + DEFAULT_ESTATE_TYPE);

        // Get all the typesOfCommercialEventsList where estateType equals to UPDATED_ESTATE_TYPE
        defaultTypesOfCommercialEventsShouldNotBeFound("estateType.equals=" + UPDATED_ESTATE_TYPE);
    }

    @Test
    @Transactional
    void getAllTypesOfCommercialEventsByEstateTypeIsInShouldWork() throws Exception {
        // Initialize the database
        typesOfCommercialEventsRepository.saveAndFlush(typesOfCommercialEvents);

        // Get all the typesOfCommercialEventsList where estateType in DEFAULT_ESTATE_TYPE or UPDATED_ESTATE_TYPE
        defaultTypesOfCommercialEventsShouldBeFound("estateType.in=" + DEFAULT_ESTATE_TYPE + "," + UPDATED_ESTATE_TYPE);

        // Get all the typesOfCommercialEventsList where estateType equals to UPDATED_ESTATE_TYPE
        defaultTypesOfCommercialEventsShouldNotBeFound("estateType.in=" + UPDATED_ESTATE_TYPE);
    }

    @Test
    @Transactional
    void getAllTypesOfCommercialEventsByEstateTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        typesOfCommercialEventsRepository.saveAndFlush(typesOfCommercialEvents);

        // Get all the typesOfCommercialEventsList where estateType is not null
        defaultTypesOfCommercialEventsShouldBeFound("estateType.specified=true");

        // Get all the typesOfCommercialEventsList where estateType is null
        defaultTypesOfCommercialEventsShouldNotBeFound("estateType.specified=false");
    }

    @Test
    @Transactional
    void getAllTypesOfCommercialEventsByEstateTypeContainsSomething() throws Exception {
        // Initialize the database
        typesOfCommercialEventsRepository.saveAndFlush(typesOfCommercialEvents);

        // Get all the typesOfCommercialEventsList where estateType contains DEFAULT_ESTATE_TYPE
        defaultTypesOfCommercialEventsShouldBeFound("estateType.contains=" + DEFAULT_ESTATE_TYPE);

        // Get all the typesOfCommercialEventsList where estateType contains UPDATED_ESTATE_TYPE
        defaultTypesOfCommercialEventsShouldNotBeFound("estateType.contains=" + UPDATED_ESTATE_TYPE);
    }

    @Test
    @Transactional
    void getAllTypesOfCommercialEventsByEstateTypeNotContainsSomething() throws Exception {
        // Initialize the database
        typesOfCommercialEventsRepository.saveAndFlush(typesOfCommercialEvents);

        // Get all the typesOfCommercialEventsList where estateType does not contain DEFAULT_ESTATE_TYPE
        defaultTypesOfCommercialEventsShouldNotBeFound("estateType.doesNotContain=" + DEFAULT_ESTATE_TYPE);

        // Get all the typesOfCommercialEventsList where estateType does not contain UPDATED_ESTATE_TYPE
        defaultTypesOfCommercialEventsShouldBeFound("estateType.doesNotContain=" + UPDATED_ESTATE_TYPE);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTypesOfCommercialEventsShouldBeFound(String filter) throws Exception {
        restTypesOfCommercialEventsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typesOfCommercialEvents.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].estateType").value(hasItem(DEFAULT_ESTATE_TYPE)));

        // Check, that the count call also returns 1
        restTypesOfCommercialEventsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTypesOfCommercialEventsShouldNotBeFound(String filter) throws Exception {
        restTypesOfCommercialEventsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTypesOfCommercialEventsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingTypesOfCommercialEvents() throws Exception {
        // Get the typesOfCommercialEvents
        restTypesOfCommercialEventsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTypesOfCommercialEvents() throws Exception {
        // Initialize the database
        typesOfCommercialEventsRepository.saveAndFlush(typesOfCommercialEvents);

        int databaseSizeBeforeUpdate = typesOfCommercialEventsRepository.findAll().size();

        // Update the typesOfCommercialEvents
        TypesOfCommercialEvents updatedTypesOfCommercialEvents = typesOfCommercialEventsRepository
            .findById(typesOfCommercialEvents.getId())
            .get();
        // Disconnect from session so that the updates on updatedTypesOfCommercialEvents are not directly saved in db
        em.detach(updatedTypesOfCommercialEvents);
        updatedTypesOfCommercialEvents.title(UPDATED_TITLE).description(UPDATED_DESCRIPTION).estateType(UPDATED_ESTATE_TYPE);
        TypesOfCommercialEventsDTO typesOfCommercialEventsDTO = typesOfCommercialEventsMapper.toDto(updatedTypesOfCommercialEvents);

        restTypesOfCommercialEventsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, typesOfCommercialEventsDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(typesOfCommercialEventsDTO))
            )
            .andExpect(status().isOk());

        // Validate the TypesOfCommercialEvents in the database
        List<TypesOfCommercialEvents> typesOfCommercialEventsList = typesOfCommercialEventsRepository.findAll();
        assertThat(typesOfCommercialEventsList).hasSize(databaseSizeBeforeUpdate);
        TypesOfCommercialEvents testTypesOfCommercialEvents = typesOfCommercialEventsList.get(typesOfCommercialEventsList.size() - 1);
        assertThat(testTypesOfCommercialEvents.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testTypesOfCommercialEvents.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testTypesOfCommercialEvents.getEstateType()).isEqualTo(UPDATED_ESTATE_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingTypesOfCommercialEvents() throws Exception {
        int databaseSizeBeforeUpdate = typesOfCommercialEventsRepository.findAll().size();
        typesOfCommercialEvents.setId(count.incrementAndGet());

        // Create the TypesOfCommercialEvents
        TypesOfCommercialEventsDTO typesOfCommercialEventsDTO = typesOfCommercialEventsMapper.toDto(typesOfCommercialEvents);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTypesOfCommercialEventsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, typesOfCommercialEventsDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(typesOfCommercialEventsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TypesOfCommercialEvents in the database
        List<TypesOfCommercialEvents> typesOfCommercialEventsList = typesOfCommercialEventsRepository.findAll();
        assertThat(typesOfCommercialEventsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTypesOfCommercialEvents() throws Exception {
        int databaseSizeBeforeUpdate = typesOfCommercialEventsRepository.findAll().size();
        typesOfCommercialEvents.setId(count.incrementAndGet());

        // Create the TypesOfCommercialEvents
        TypesOfCommercialEventsDTO typesOfCommercialEventsDTO = typesOfCommercialEventsMapper.toDto(typesOfCommercialEvents);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTypesOfCommercialEventsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(typesOfCommercialEventsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TypesOfCommercialEvents in the database
        List<TypesOfCommercialEvents> typesOfCommercialEventsList = typesOfCommercialEventsRepository.findAll();
        assertThat(typesOfCommercialEventsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTypesOfCommercialEvents() throws Exception {
        int databaseSizeBeforeUpdate = typesOfCommercialEventsRepository.findAll().size();
        typesOfCommercialEvents.setId(count.incrementAndGet());

        // Create the TypesOfCommercialEvents
        TypesOfCommercialEventsDTO typesOfCommercialEventsDTO = typesOfCommercialEventsMapper.toDto(typesOfCommercialEvents);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTypesOfCommercialEventsMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(typesOfCommercialEventsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TypesOfCommercialEvents in the database
        List<TypesOfCommercialEvents> typesOfCommercialEventsList = typesOfCommercialEventsRepository.findAll();
        assertThat(typesOfCommercialEventsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTypesOfCommercialEventsWithPatch() throws Exception {
        // Initialize the database
        typesOfCommercialEventsRepository.saveAndFlush(typesOfCommercialEvents);

        int databaseSizeBeforeUpdate = typesOfCommercialEventsRepository.findAll().size();

        // Update the typesOfCommercialEvents using partial update
        TypesOfCommercialEvents partialUpdatedTypesOfCommercialEvents = new TypesOfCommercialEvents();
        partialUpdatedTypesOfCommercialEvents.setId(typesOfCommercialEvents.getId());

        restTypesOfCommercialEventsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTypesOfCommercialEvents.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTypesOfCommercialEvents))
            )
            .andExpect(status().isOk());

        // Validate the TypesOfCommercialEvents in the database
        List<TypesOfCommercialEvents> typesOfCommercialEventsList = typesOfCommercialEventsRepository.findAll();
        assertThat(typesOfCommercialEventsList).hasSize(databaseSizeBeforeUpdate);
        TypesOfCommercialEvents testTypesOfCommercialEvents = typesOfCommercialEventsList.get(typesOfCommercialEventsList.size() - 1);
        assertThat(testTypesOfCommercialEvents.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testTypesOfCommercialEvents.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testTypesOfCommercialEvents.getEstateType()).isEqualTo(DEFAULT_ESTATE_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateTypesOfCommercialEventsWithPatch() throws Exception {
        // Initialize the database
        typesOfCommercialEventsRepository.saveAndFlush(typesOfCommercialEvents);

        int databaseSizeBeforeUpdate = typesOfCommercialEventsRepository.findAll().size();

        // Update the typesOfCommercialEvents using partial update
        TypesOfCommercialEvents partialUpdatedTypesOfCommercialEvents = new TypesOfCommercialEvents();
        partialUpdatedTypesOfCommercialEvents.setId(typesOfCommercialEvents.getId());

        partialUpdatedTypesOfCommercialEvents.title(UPDATED_TITLE).description(UPDATED_DESCRIPTION).estateType(UPDATED_ESTATE_TYPE);

        restTypesOfCommercialEventsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTypesOfCommercialEvents.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTypesOfCommercialEvents))
            )
            .andExpect(status().isOk());

        // Validate the TypesOfCommercialEvents in the database
        List<TypesOfCommercialEvents> typesOfCommercialEventsList = typesOfCommercialEventsRepository.findAll();
        assertThat(typesOfCommercialEventsList).hasSize(databaseSizeBeforeUpdate);
        TypesOfCommercialEvents testTypesOfCommercialEvents = typesOfCommercialEventsList.get(typesOfCommercialEventsList.size() - 1);
        assertThat(testTypesOfCommercialEvents.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testTypesOfCommercialEvents.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testTypesOfCommercialEvents.getEstateType()).isEqualTo(UPDATED_ESTATE_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingTypesOfCommercialEvents() throws Exception {
        int databaseSizeBeforeUpdate = typesOfCommercialEventsRepository.findAll().size();
        typesOfCommercialEvents.setId(count.incrementAndGet());

        // Create the TypesOfCommercialEvents
        TypesOfCommercialEventsDTO typesOfCommercialEventsDTO = typesOfCommercialEventsMapper.toDto(typesOfCommercialEvents);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTypesOfCommercialEventsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, typesOfCommercialEventsDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(typesOfCommercialEventsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TypesOfCommercialEvents in the database
        List<TypesOfCommercialEvents> typesOfCommercialEventsList = typesOfCommercialEventsRepository.findAll();
        assertThat(typesOfCommercialEventsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTypesOfCommercialEvents() throws Exception {
        int databaseSizeBeforeUpdate = typesOfCommercialEventsRepository.findAll().size();
        typesOfCommercialEvents.setId(count.incrementAndGet());

        // Create the TypesOfCommercialEvents
        TypesOfCommercialEventsDTO typesOfCommercialEventsDTO = typesOfCommercialEventsMapper.toDto(typesOfCommercialEvents);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTypesOfCommercialEventsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(typesOfCommercialEventsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TypesOfCommercialEvents in the database
        List<TypesOfCommercialEvents> typesOfCommercialEventsList = typesOfCommercialEventsRepository.findAll();
        assertThat(typesOfCommercialEventsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTypesOfCommercialEvents() throws Exception {
        int databaseSizeBeforeUpdate = typesOfCommercialEventsRepository.findAll().size();
        typesOfCommercialEvents.setId(count.incrementAndGet());

        // Create the TypesOfCommercialEvents
        TypesOfCommercialEventsDTO typesOfCommercialEventsDTO = typesOfCommercialEventsMapper.toDto(typesOfCommercialEvents);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTypesOfCommercialEventsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(typesOfCommercialEventsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TypesOfCommercialEvents in the database
        List<TypesOfCommercialEvents> typesOfCommercialEventsList = typesOfCommercialEventsRepository.findAll();
        assertThat(typesOfCommercialEventsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTypesOfCommercialEvents() throws Exception {
        // Initialize the database
        typesOfCommercialEventsRepository.saveAndFlush(typesOfCommercialEvents);

        int databaseSizeBeforeDelete = typesOfCommercialEventsRepository.findAll().size();

        // Delete the typesOfCommercialEvents
        restTypesOfCommercialEventsMockMvc
            .perform(delete(ENTITY_API_URL_ID, typesOfCommercialEvents.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TypesOfCommercialEvents> typesOfCommercialEventsList = typesOfCommercialEventsRepository.findAll();
        assertThat(typesOfCommercialEventsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
