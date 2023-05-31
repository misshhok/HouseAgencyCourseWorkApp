package com.bsu.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.bsu.app.IntegrationTest;
import com.bsu.app.domain.Clients;
import com.bsu.app.domain.ComercialEventsOfNonResidentalEstate;
import com.bsu.app.domain.NonResidentalEstates;
import com.bsu.app.domain.TypesOfCommercialEvents;
import com.bsu.app.repository.ComercialEventsOfNonResidentalEstateRepository;
import com.bsu.app.service.criteria.ComercialEventsOfNonResidentalEstateCriteria;
import com.bsu.app.service.dto.ComercialEventsOfNonResidentalEstateDTO;
import com.bsu.app.service.mapper.ComercialEventsOfNonResidentalEstateMapper;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link ComercialEventsOfNonResidentalEstateResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ComercialEventsOfNonResidentalEstateResourceIT {

    private static final String DEFAULT_AGENT_NOTES = "AAAAAAAAAA";
    private static final String UPDATED_AGENT_NOTES = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_OF_EVENT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_OF_EVENT = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATE_OF_EVENT = LocalDate.ofEpochDay(-1L);

    private static final String ENTITY_API_URL = "/api/comercial-events-of-non-residental-estates";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ComercialEventsOfNonResidentalEstateRepository comercialEventsOfNonResidentalEstateRepository;

    @Autowired
    private ComercialEventsOfNonResidentalEstateMapper comercialEventsOfNonResidentalEstateMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restComercialEventsOfNonResidentalEstateMockMvc;

    private ComercialEventsOfNonResidentalEstate comercialEventsOfNonResidentalEstate;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ComercialEventsOfNonResidentalEstate createEntity(EntityManager em) {
        ComercialEventsOfNonResidentalEstate comercialEventsOfNonResidentalEstate = new ComercialEventsOfNonResidentalEstate()
            .agentNotes(DEFAULT_AGENT_NOTES)
            .dateOfEvent(DEFAULT_DATE_OF_EVENT);
        return comercialEventsOfNonResidentalEstate;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ComercialEventsOfNonResidentalEstate createUpdatedEntity(EntityManager em) {
        ComercialEventsOfNonResidentalEstate comercialEventsOfNonResidentalEstate = new ComercialEventsOfNonResidentalEstate()
            .agentNotes(UPDATED_AGENT_NOTES)
            .dateOfEvent(UPDATED_DATE_OF_EVENT);
        return comercialEventsOfNonResidentalEstate;
    }

    @BeforeEach
    public void initTest() {
        comercialEventsOfNonResidentalEstate = createEntity(em);
    }

    @Test
    @Transactional
    void createComercialEventsOfNonResidentalEstate() throws Exception {
        int databaseSizeBeforeCreate = comercialEventsOfNonResidentalEstateRepository.findAll().size();
        // Create the ComercialEventsOfNonResidentalEstate
        ComercialEventsOfNonResidentalEstateDTO comercialEventsOfNonResidentalEstateDTO = comercialEventsOfNonResidentalEstateMapper.toDto(
            comercialEventsOfNonResidentalEstate
        );
        restComercialEventsOfNonResidentalEstateMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(comercialEventsOfNonResidentalEstateDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ComercialEventsOfNonResidentalEstate in the database
        List<ComercialEventsOfNonResidentalEstate> comercialEventsOfNonResidentalEstateList = comercialEventsOfNonResidentalEstateRepository.findAll();
        assertThat(comercialEventsOfNonResidentalEstateList).hasSize(databaseSizeBeforeCreate + 1);
        ComercialEventsOfNonResidentalEstate testComercialEventsOfNonResidentalEstate = comercialEventsOfNonResidentalEstateList.get(
            comercialEventsOfNonResidentalEstateList.size() - 1
        );
        assertThat(testComercialEventsOfNonResidentalEstate.getAgentNotes()).isEqualTo(DEFAULT_AGENT_NOTES);
        assertThat(testComercialEventsOfNonResidentalEstate.getDateOfEvent()).isEqualTo(DEFAULT_DATE_OF_EVENT);
    }

    @Test
    @Transactional
    void createComercialEventsOfNonResidentalEstateWithExistingId() throws Exception {
        // Create the ComercialEventsOfNonResidentalEstate with an existing ID
        comercialEventsOfNonResidentalEstate.setId(1L);
        ComercialEventsOfNonResidentalEstateDTO comercialEventsOfNonResidentalEstateDTO = comercialEventsOfNonResidentalEstateMapper.toDto(
            comercialEventsOfNonResidentalEstate
        );

        int databaseSizeBeforeCreate = comercialEventsOfNonResidentalEstateRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restComercialEventsOfNonResidentalEstateMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(comercialEventsOfNonResidentalEstateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ComercialEventsOfNonResidentalEstate in the database
        List<ComercialEventsOfNonResidentalEstate> comercialEventsOfNonResidentalEstateList = comercialEventsOfNonResidentalEstateRepository.findAll();
        assertThat(comercialEventsOfNonResidentalEstateList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllComercialEventsOfNonResidentalEstates() throws Exception {
        // Initialize the database
        comercialEventsOfNonResidentalEstateRepository.saveAndFlush(comercialEventsOfNonResidentalEstate);

        // Get all the comercialEventsOfNonResidentalEstateList
        restComercialEventsOfNonResidentalEstateMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(comercialEventsOfNonResidentalEstate.getId().intValue())))
            .andExpect(jsonPath("$.[*].agentNotes").value(hasItem(DEFAULT_AGENT_NOTES)))
            .andExpect(jsonPath("$.[*].dateOfEvent").value(hasItem(DEFAULT_DATE_OF_EVENT.toString())));
    }

    @Test
    @Transactional
    void getComercialEventsOfNonResidentalEstate() throws Exception {
        // Initialize the database
        comercialEventsOfNonResidentalEstateRepository.saveAndFlush(comercialEventsOfNonResidentalEstate);

        // Get the comercialEventsOfNonResidentalEstate
        restComercialEventsOfNonResidentalEstateMockMvc
            .perform(get(ENTITY_API_URL_ID, comercialEventsOfNonResidentalEstate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(comercialEventsOfNonResidentalEstate.getId().intValue()))
            .andExpect(jsonPath("$.agentNotes").value(DEFAULT_AGENT_NOTES))
            .andExpect(jsonPath("$.dateOfEvent").value(DEFAULT_DATE_OF_EVENT.toString()));
    }

    @Test
    @Transactional
    void getComercialEventsOfNonResidentalEstatesByIdFiltering() throws Exception {
        // Initialize the database
        comercialEventsOfNonResidentalEstateRepository.saveAndFlush(comercialEventsOfNonResidentalEstate);

        Long id = comercialEventsOfNonResidentalEstate.getId();

        defaultComercialEventsOfNonResidentalEstateShouldBeFound("id.equals=" + id);
        defaultComercialEventsOfNonResidentalEstateShouldNotBeFound("id.notEquals=" + id);

        defaultComercialEventsOfNonResidentalEstateShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultComercialEventsOfNonResidentalEstateShouldNotBeFound("id.greaterThan=" + id);

        defaultComercialEventsOfNonResidentalEstateShouldBeFound("id.lessThanOrEqual=" + id);
        defaultComercialEventsOfNonResidentalEstateShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllComercialEventsOfNonResidentalEstatesByAgentNotesIsEqualToSomething() throws Exception {
        // Initialize the database
        comercialEventsOfNonResidentalEstateRepository.saveAndFlush(comercialEventsOfNonResidentalEstate);

        // Get all the comercialEventsOfNonResidentalEstateList where agentNotes equals to DEFAULT_AGENT_NOTES
        defaultComercialEventsOfNonResidentalEstateShouldBeFound("agentNotes.equals=" + DEFAULT_AGENT_NOTES);

        // Get all the comercialEventsOfNonResidentalEstateList where agentNotes equals to UPDATED_AGENT_NOTES
        defaultComercialEventsOfNonResidentalEstateShouldNotBeFound("agentNotes.equals=" + UPDATED_AGENT_NOTES);
    }

    @Test
    @Transactional
    void getAllComercialEventsOfNonResidentalEstatesByAgentNotesIsInShouldWork() throws Exception {
        // Initialize the database
        comercialEventsOfNonResidentalEstateRepository.saveAndFlush(comercialEventsOfNonResidentalEstate);

        // Get all the comercialEventsOfNonResidentalEstateList where agentNotes in DEFAULT_AGENT_NOTES or UPDATED_AGENT_NOTES
        defaultComercialEventsOfNonResidentalEstateShouldBeFound("agentNotes.in=" + DEFAULT_AGENT_NOTES + "," + UPDATED_AGENT_NOTES);

        // Get all the comercialEventsOfNonResidentalEstateList where agentNotes equals to UPDATED_AGENT_NOTES
        defaultComercialEventsOfNonResidentalEstateShouldNotBeFound("agentNotes.in=" + UPDATED_AGENT_NOTES);
    }

    @Test
    @Transactional
    void getAllComercialEventsOfNonResidentalEstatesByAgentNotesIsNullOrNotNull() throws Exception {
        // Initialize the database
        comercialEventsOfNonResidentalEstateRepository.saveAndFlush(comercialEventsOfNonResidentalEstate);

        // Get all the comercialEventsOfNonResidentalEstateList where agentNotes is not null
        defaultComercialEventsOfNonResidentalEstateShouldBeFound("agentNotes.specified=true");

        // Get all the comercialEventsOfNonResidentalEstateList where agentNotes is null
        defaultComercialEventsOfNonResidentalEstateShouldNotBeFound("agentNotes.specified=false");
    }

    @Test
    @Transactional
    void getAllComercialEventsOfNonResidentalEstatesByAgentNotesContainsSomething() throws Exception {
        // Initialize the database
        comercialEventsOfNonResidentalEstateRepository.saveAndFlush(comercialEventsOfNonResidentalEstate);

        // Get all the comercialEventsOfNonResidentalEstateList where agentNotes contains DEFAULT_AGENT_NOTES
        defaultComercialEventsOfNonResidentalEstateShouldBeFound("agentNotes.contains=" + DEFAULT_AGENT_NOTES);

        // Get all the comercialEventsOfNonResidentalEstateList where agentNotes contains UPDATED_AGENT_NOTES
        defaultComercialEventsOfNonResidentalEstateShouldNotBeFound("agentNotes.contains=" + UPDATED_AGENT_NOTES);
    }

    @Test
    @Transactional
    void getAllComercialEventsOfNonResidentalEstatesByAgentNotesNotContainsSomething() throws Exception {
        // Initialize the database
        comercialEventsOfNonResidentalEstateRepository.saveAndFlush(comercialEventsOfNonResidentalEstate);

        // Get all the comercialEventsOfNonResidentalEstateList where agentNotes does not contain DEFAULT_AGENT_NOTES
        defaultComercialEventsOfNonResidentalEstateShouldNotBeFound("agentNotes.doesNotContain=" + DEFAULT_AGENT_NOTES);

        // Get all the comercialEventsOfNonResidentalEstateList where agentNotes does not contain UPDATED_AGENT_NOTES
        defaultComercialEventsOfNonResidentalEstateShouldBeFound("agentNotes.doesNotContain=" + UPDATED_AGENT_NOTES);
    }

    @Test
    @Transactional
    void getAllComercialEventsOfNonResidentalEstatesByDateOfEventIsEqualToSomething() throws Exception {
        // Initialize the database
        comercialEventsOfNonResidentalEstateRepository.saveAndFlush(comercialEventsOfNonResidentalEstate);

        // Get all the comercialEventsOfNonResidentalEstateList where dateOfEvent equals to DEFAULT_DATE_OF_EVENT
        defaultComercialEventsOfNonResidentalEstateShouldBeFound("dateOfEvent.equals=" + DEFAULT_DATE_OF_EVENT);

        // Get all the comercialEventsOfNonResidentalEstateList where dateOfEvent equals to UPDATED_DATE_OF_EVENT
        defaultComercialEventsOfNonResidentalEstateShouldNotBeFound("dateOfEvent.equals=" + UPDATED_DATE_OF_EVENT);
    }

    @Test
    @Transactional
    void getAllComercialEventsOfNonResidentalEstatesByDateOfEventIsInShouldWork() throws Exception {
        // Initialize the database
        comercialEventsOfNonResidentalEstateRepository.saveAndFlush(comercialEventsOfNonResidentalEstate);

        // Get all the comercialEventsOfNonResidentalEstateList where dateOfEvent in DEFAULT_DATE_OF_EVENT or UPDATED_DATE_OF_EVENT
        defaultComercialEventsOfNonResidentalEstateShouldBeFound("dateOfEvent.in=" + DEFAULT_DATE_OF_EVENT + "," + UPDATED_DATE_OF_EVENT);

        // Get all the comercialEventsOfNonResidentalEstateList where dateOfEvent equals to UPDATED_DATE_OF_EVENT
        defaultComercialEventsOfNonResidentalEstateShouldNotBeFound("dateOfEvent.in=" + UPDATED_DATE_OF_EVENT);
    }

    @Test
    @Transactional
    void getAllComercialEventsOfNonResidentalEstatesByDateOfEventIsNullOrNotNull() throws Exception {
        // Initialize the database
        comercialEventsOfNonResidentalEstateRepository.saveAndFlush(comercialEventsOfNonResidentalEstate);

        // Get all the comercialEventsOfNonResidentalEstateList where dateOfEvent is not null
        defaultComercialEventsOfNonResidentalEstateShouldBeFound("dateOfEvent.specified=true");

        // Get all the comercialEventsOfNonResidentalEstateList where dateOfEvent is null
        defaultComercialEventsOfNonResidentalEstateShouldNotBeFound("dateOfEvent.specified=false");
    }

    @Test
    @Transactional
    void getAllComercialEventsOfNonResidentalEstatesByDateOfEventIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        comercialEventsOfNonResidentalEstateRepository.saveAndFlush(comercialEventsOfNonResidentalEstate);

        // Get all the comercialEventsOfNonResidentalEstateList where dateOfEvent is greater than or equal to DEFAULT_DATE_OF_EVENT
        defaultComercialEventsOfNonResidentalEstateShouldBeFound("dateOfEvent.greaterThanOrEqual=" + DEFAULT_DATE_OF_EVENT);

        // Get all the comercialEventsOfNonResidentalEstateList where dateOfEvent is greater than or equal to UPDATED_DATE_OF_EVENT
        defaultComercialEventsOfNonResidentalEstateShouldNotBeFound("dateOfEvent.greaterThanOrEqual=" + UPDATED_DATE_OF_EVENT);
    }

    @Test
    @Transactional
    void getAllComercialEventsOfNonResidentalEstatesByDateOfEventIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        comercialEventsOfNonResidentalEstateRepository.saveAndFlush(comercialEventsOfNonResidentalEstate);

        // Get all the comercialEventsOfNonResidentalEstateList where dateOfEvent is less than or equal to DEFAULT_DATE_OF_EVENT
        defaultComercialEventsOfNonResidentalEstateShouldBeFound("dateOfEvent.lessThanOrEqual=" + DEFAULT_DATE_OF_EVENT);

        // Get all the comercialEventsOfNonResidentalEstateList where dateOfEvent is less than or equal to SMALLER_DATE_OF_EVENT
        defaultComercialEventsOfNonResidentalEstateShouldNotBeFound("dateOfEvent.lessThanOrEqual=" + SMALLER_DATE_OF_EVENT);
    }

    @Test
    @Transactional
    void getAllComercialEventsOfNonResidentalEstatesByDateOfEventIsLessThanSomething() throws Exception {
        // Initialize the database
        comercialEventsOfNonResidentalEstateRepository.saveAndFlush(comercialEventsOfNonResidentalEstate);

        // Get all the comercialEventsOfNonResidentalEstateList where dateOfEvent is less than DEFAULT_DATE_OF_EVENT
        defaultComercialEventsOfNonResidentalEstateShouldNotBeFound("dateOfEvent.lessThan=" + DEFAULT_DATE_OF_EVENT);

        // Get all the comercialEventsOfNonResidentalEstateList where dateOfEvent is less than UPDATED_DATE_OF_EVENT
        defaultComercialEventsOfNonResidentalEstateShouldBeFound("dateOfEvent.lessThan=" + UPDATED_DATE_OF_EVENT);
    }

    @Test
    @Transactional
    void getAllComercialEventsOfNonResidentalEstatesByDateOfEventIsGreaterThanSomething() throws Exception {
        // Initialize the database
        comercialEventsOfNonResidentalEstateRepository.saveAndFlush(comercialEventsOfNonResidentalEstate);

        // Get all the comercialEventsOfNonResidentalEstateList where dateOfEvent is greater than DEFAULT_DATE_OF_EVENT
        defaultComercialEventsOfNonResidentalEstateShouldNotBeFound("dateOfEvent.greaterThan=" + DEFAULT_DATE_OF_EVENT);

        // Get all the comercialEventsOfNonResidentalEstateList where dateOfEvent is greater than SMALLER_DATE_OF_EVENT
        defaultComercialEventsOfNonResidentalEstateShouldBeFound("dateOfEvent.greaterThan=" + SMALLER_DATE_OF_EVENT);
    }

    @Test
    @Transactional
    void getAllComercialEventsOfNonResidentalEstatesByTypeOfCommercialEventIdIsEqualToSomething() throws Exception {
        TypesOfCommercialEvents typeOfCommercialEventId;
        if (TestUtil.findAll(em, TypesOfCommercialEvents.class).isEmpty()) {
            comercialEventsOfNonResidentalEstateRepository.saveAndFlush(comercialEventsOfNonResidentalEstate);
            typeOfCommercialEventId = TypesOfCommercialEventsResourceIT.createEntity(em);
        } else {
            typeOfCommercialEventId = TestUtil.findAll(em, TypesOfCommercialEvents.class).get(0);
        }
        em.persist(typeOfCommercialEventId);
        em.flush();
        comercialEventsOfNonResidentalEstate.setTypeOfCommercialEventId(typeOfCommercialEventId);
        comercialEventsOfNonResidentalEstateRepository.saveAndFlush(comercialEventsOfNonResidentalEstate);
        Long typeOfCommercialEventIdId = typeOfCommercialEventId.getId();

        // Get all the comercialEventsOfNonResidentalEstateList where typeOfCommercialEventId equals to typeOfCommercialEventIdId
        defaultComercialEventsOfNonResidentalEstateShouldBeFound("typeOfCommercialEventIdId.equals=" + typeOfCommercialEventIdId);

        // Get all the comercialEventsOfNonResidentalEstateList where typeOfCommercialEventId equals to (typeOfCommercialEventIdId + 1)
        defaultComercialEventsOfNonResidentalEstateShouldNotBeFound("typeOfCommercialEventIdId.equals=" + (typeOfCommercialEventIdId + 1));
    }

    @Test
    @Transactional
    void getAllComercialEventsOfNonResidentalEstatesByNonResidentalEstateIdIsEqualToSomething() throws Exception {
        NonResidentalEstates nonResidentalEstateId;
        if (TestUtil.findAll(em, NonResidentalEstates.class).isEmpty()) {
            comercialEventsOfNonResidentalEstateRepository.saveAndFlush(comercialEventsOfNonResidentalEstate);
            nonResidentalEstateId = NonResidentalEstatesResourceIT.createEntity(em);
        } else {
            nonResidentalEstateId = TestUtil.findAll(em, NonResidentalEstates.class).get(0);
        }
        em.persist(nonResidentalEstateId);
        em.flush();
        comercialEventsOfNonResidentalEstate.setNonResidentalEstateId(nonResidentalEstateId);
        comercialEventsOfNonResidentalEstateRepository.saveAndFlush(comercialEventsOfNonResidentalEstate);
        Long nonResidentalEstateIdId = nonResidentalEstateId.getId();

        // Get all the comercialEventsOfNonResidentalEstateList where nonResidentalEstateId equals to nonResidentalEstateIdId
        defaultComercialEventsOfNonResidentalEstateShouldBeFound("nonResidentalEstateIdId.equals=" + nonResidentalEstateIdId);

        // Get all the comercialEventsOfNonResidentalEstateList where nonResidentalEstateId equals to (nonResidentalEstateIdId + 1)
        defaultComercialEventsOfNonResidentalEstateShouldNotBeFound("nonResidentalEstateIdId.equals=" + (nonResidentalEstateIdId + 1));
    }

    @Test
    @Transactional
    void getAllComercialEventsOfNonResidentalEstatesByClientIdIsEqualToSomething() throws Exception {
        Clients clientId;
        if (TestUtil.findAll(em, Clients.class).isEmpty()) {
            comercialEventsOfNonResidentalEstateRepository.saveAndFlush(comercialEventsOfNonResidentalEstate);
            clientId = ClientsResourceIT.createEntity(em);
        } else {
            clientId = TestUtil.findAll(em, Clients.class).get(0);
        }
        em.persist(clientId);
        em.flush();
        comercialEventsOfNonResidentalEstate.setClientId(clientId);
        comercialEventsOfNonResidentalEstateRepository.saveAndFlush(comercialEventsOfNonResidentalEstate);
        Long clientIdId = clientId.getId();

        // Get all the comercialEventsOfNonResidentalEstateList where clientId equals to clientIdId
        defaultComercialEventsOfNonResidentalEstateShouldBeFound("clientIdId.equals=" + clientIdId);

        // Get all the comercialEventsOfNonResidentalEstateList where clientId equals to (clientIdId + 1)
        defaultComercialEventsOfNonResidentalEstateShouldNotBeFound("clientIdId.equals=" + (clientIdId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultComercialEventsOfNonResidentalEstateShouldBeFound(String filter) throws Exception {
        restComercialEventsOfNonResidentalEstateMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(comercialEventsOfNonResidentalEstate.getId().intValue())))
            .andExpect(jsonPath("$.[*].agentNotes").value(hasItem(DEFAULT_AGENT_NOTES)))
            .andExpect(jsonPath("$.[*].dateOfEvent").value(hasItem(DEFAULT_DATE_OF_EVENT.toString())));

        // Check, that the count call also returns 1
        restComercialEventsOfNonResidentalEstateMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultComercialEventsOfNonResidentalEstateShouldNotBeFound(String filter) throws Exception {
        restComercialEventsOfNonResidentalEstateMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restComercialEventsOfNonResidentalEstateMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingComercialEventsOfNonResidentalEstate() throws Exception {
        // Get the comercialEventsOfNonResidentalEstate
        restComercialEventsOfNonResidentalEstateMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingComercialEventsOfNonResidentalEstate() throws Exception {
        // Initialize the database
        comercialEventsOfNonResidentalEstateRepository.saveAndFlush(comercialEventsOfNonResidentalEstate);

        int databaseSizeBeforeUpdate = comercialEventsOfNonResidentalEstateRepository.findAll().size();

        // Update the comercialEventsOfNonResidentalEstate
        ComercialEventsOfNonResidentalEstate updatedComercialEventsOfNonResidentalEstate = comercialEventsOfNonResidentalEstateRepository
            .findById(comercialEventsOfNonResidentalEstate.getId())
            .get();
        // Disconnect from session so that the updates on updatedComercialEventsOfNonResidentalEstate are not directly saved in db
        em.detach(updatedComercialEventsOfNonResidentalEstate);
        updatedComercialEventsOfNonResidentalEstate.agentNotes(UPDATED_AGENT_NOTES).dateOfEvent(UPDATED_DATE_OF_EVENT);
        ComercialEventsOfNonResidentalEstateDTO comercialEventsOfNonResidentalEstateDTO = comercialEventsOfNonResidentalEstateMapper.toDto(
            updatedComercialEventsOfNonResidentalEstate
        );

        restComercialEventsOfNonResidentalEstateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, comercialEventsOfNonResidentalEstateDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(comercialEventsOfNonResidentalEstateDTO))
            )
            .andExpect(status().isOk());

        // Validate the ComercialEventsOfNonResidentalEstate in the database
        List<ComercialEventsOfNonResidentalEstate> comercialEventsOfNonResidentalEstateList = comercialEventsOfNonResidentalEstateRepository.findAll();
        assertThat(comercialEventsOfNonResidentalEstateList).hasSize(databaseSizeBeforeUpdate);
        ComercialEventsOfNonResidentalEstate testComercialEventsOfNonResidentalEstate = comercialEventsOfNonResidentalEstateList.get(
            comercialEventsOfNonResidentalEstateList.size() - 1
        );
        assertThat(testComercialEventsOfNonResidentalEstate.getAgentNotes()).isEqualTo(UPDATED_AGENT_NOTES);
        assertThat(testComercialEventsOfNonResidentalEstate.getDateOfEvent()).isEqualTo(UPDATED_DATE_OF_EVENT);
    }

    @Test
    @Transactional
    void putNonExistingComercialEventsOfNonResidentalEstate() throws Exception {
        int databaseSizeBeforeUpdate = comercialEventsOfNonResidentalEstateRepository.findAll().size();
        comercialEventsOfNonResidentalEstate.setId(count.incrementAndGet());

        // Create the ComercialEventsOfNonResidentalEstate
        ComercialEventsOfNonResidentalEstateDTO comercialEventsOfNonResidentalEstateDTO = comercialEventsOfNonResidentalEstateMapper.toDto(
            comercialEventsOfNonResidentalEstate
        );

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restComercialEventsOfNonResidentalEstateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, comercialEventsOfNonResidentalEstateDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(comercialEventsOfNonResidentalEstateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ComercialEventsOfNonResidentalEstate in the database
        List<ComercialEventsOfNonResidentalEstate> comercialEventsOfNonResidentalEstateList = comercialEventsOfNonResidentalEstateRepository.findAll();
        assertThat(comercialEventsOfNonResidentalEstateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchComercialEventsOfNonResidentalEstate() throws Exception {
        int databaseSizeBeforeUpdate = comercialEventsOfNonResidentalEstateRepository.findAll().size();
        comercialEventsOfNonResidentalEstate.setId(count.incrementAndGet());

        // Create the ComercialEventsOfNonResidentalEstate
        ComercialEventsOfNonResidentalEstateDTO comercialEventsOfNonResidentalEstateDTO = comercialEventsOfNonResidentalEstateMapper.toDto(
            comercialEventsOfNonResidentalEstate
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restComercialEventsOfNonResidentalEstateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(comercialEventsOfNonResidentalEstateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ComercialEventsOfNonResidentalEstate in the database
        List<ComercialEventsOfNonResidentalEstate> comercialEventsOfNonResidentalEstateList = comercialEventsOfNonResidentalEstateRepository.findAll();
        assertThat(comercialEventsOfNonResidentalEstateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamComercialEventsOfNonResidentalEstate() throws Exception {
        int databaseSizeBeforeUpdate = comercialEventsOfNonResidentalEstateRepository.findAll().size();
        comercialEventsOfNonResidentalEstate.setId(count.incrementAndGet());

        // Create the ComercialEventsOfNonResidentalEstate
        ComercialEventsOfNonResidentalEstateDTO comercialEventsOfNonResidentalEstateDTO = comercialEventsOfNonResidentalEstateMapper.toDto(
            comercialEventsOfNonResidentalEstate
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restComercialEventsOfNonResidentalEstateMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(comercialEventsOfNonResidentalEstateDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ComercialEventsOfNonResidentalEstate in the database
        List<ComercialEventsOfNonResidentalEstate> comercialEventsOfNonResidentalEstateList = comercialEventsOfNonResidentalEstateRepository.findAll();
        assertThat(comercialEventsOfNonResidentalEstateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateComercialEventsOfNonResidentalEstateWithPatch() throws Exception {
        // Initialize the database
        comercialEventsOfNonResidentalEstateRepository.saveAndFlush(comercialEventsOfNonResidentalEstate);

        int databaseSizeBeforeUpdate = comercialEventsOfNonResidentalEstateRepository.findAll().size();

        // Update the comercialEventsOfNonResidentalEstate using partial update
        ComercialEventsOfNonResidentalEstate partialUpdatedComercialEventsOfNonResidentalEstate = new ComercialEventsOfNonResidentalEstate();
        partialUpdatedComercialEventsOfNonResidentalEstate.setId(comercialEventsOfNonResidentalEstate.getId());

        partialUpdatedComercialEventsOfNonResidentalEstate.dateOfEvent(UPDATED_DATE_OF_EVENT);

        restComercialEventsOfNonResidentalEstateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedComercialEventsOfNonResidentalEstate.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedComercialEventsOfNonResidentalEstate))
            )
            .andExpect(status().isOk());

        // Validate the ComercialEventsOfNonResidentalEstate in the database
        List<ComercialEventsOfNonResidentalEstate> comercialEventsOfNonResidentalEstateList = comercialEventsOfNonResidentalEstateRepository.findAll();
        assertThat(comercialEventsOfNonResidentalEstateList).hasSize(databaseSizeBeforeUpdate);
        ComercialEventsOfNonResidentalEstate testComercialEventsOfNonResidentalEstate = comercialEventsOfNonResidentalEstateList.get(
            comercialEventsOfNonResidentalEstateList.size() - 1
        );
        assertThat(testComercialEventsOfNonResidentalEstate.getAgentNotes()).isEqualTo(DEFAULT_AGENT_NOTES);
        assertThat(testComercialEventsOfNonResidentalEstate.getDateOfEvent()).isEqualTo(UPDATED_DATE_OF_EVENT);
    }

    @Test
    @Transactional
    void fullUpdateComercialEventsOfNonResidentalEstateWithPatch() throws Exception {
        // Initialize the database
        comercialEventsOfNonResidentalEstateRepository.saveAndFlush(comercialEventsOfNonResidentalEstate);

        int databaseSizeBeforeUpdate = comercialEventsOfNonResidentalEstateRepository.findAll().size();

        // Update the comercialEventsOfNonResidentalEstate using partial update
        ComercialEventsOfNonResidentalEstate partialUpdatedComercialEventsOfNonResidentalEstate = new ComercialEventsOfNonResidentalEstate();
        partialUpdatedComercialEventsOfNonResidentalEstate.setId(comercialEventsOfNonResidentalEstate.getId());

        partialUpdatedComercialEventsOfNonResidentalEstate.agentNotes(UPDATED_AGENT_NOTES).dateOfEvent(UPDATED_DATE_OF_EVENT);

        restComercialEventsOfNonResidentalEstateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedComercialEventsOfNonResidentalEstate.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedComercialEventsOfNonResidentalEstate))
            )
            .andExpect(status().isOk());

        // Validate the ComercialEventsOfNonResidentalEstate in the database
        List<ComercialEventsOfNonResidentalEstate> comercialEventsOfNonResidentalEstateList = comercialEventsOfNonResidentalEstateRepository.findAll();
        assertThat(comercialEventsOfNonResidentalEstateList).hasSize(databaseSizeBeforeUpdate);
        ComercialEventsOfNonResidentalEstate testComercialEventsOfNonResidentalEstate = comercialEventsOfNonResidentalEstateList.get(
            comercialEventsOfNonResidentalEstateList.size() - 1
        );
        assertThat(testComercialEventsOfNonResidentalEstate.getAgentNotes()).isEqualTo(UPDATED_AGENT_NOTES);
        assertThat(testComercialEventsOfNonResidentalEstate.getDateOfEvent()).isEqualTo(UPDATED_DATE_OF_EVENT);
    }

    @Test
    @Transactional
    void patchNonExistingComercialEventsOfNonResidentalEstate() throws Exception {
        int databaseSizeBeforeUpdate = comercialEventsOfNonResidentalEstateRepository.findAll().size();
        comercialEventsOfNonResidentalEstate.setId(count.incrementAndGet());

        // Create the ComercialEventsOfNonResidentalEstate
        ComercialEventsOfNonResidentalEstateDTO comercialEventsOfNonResidentalEstateDTO = comercialEventsOfNonResidentalEstateMapper.toDto(
            comercialEventsOfNonResidentalEstate
        );

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restComercialEventsOfNonResidentalEstateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, comercialEventsOfNonResidentalEstateDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(comercialEventsOfNonResidentalEstateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ComercialEventsOfNonResidentalEstate in the database
        List<ComercialEventsOfNonResidentalEstate> comercialEventsOfNonResidentalEstateList = comercialEventsOfNonResidentalEstateRepository.findAll();
        assertThat(comercialEventsOfNonResidentalEstateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchComercialEventsOfNonResidentalEstate() throws Exception {
        int databaseSizeBeforeUpdate = comercialEventsOfNonResidentalEstateRepository.findAll().size();
        comercialEventsOfNonResidentalEstate.setId(count.incrementAndGet());

        // Create the ComercialEventsOfNonResidentalEstate
        ComercialEventsOfNonResidentalEstateDTO comercialEventsOfNonResidentalEstateDTO = comercialEventsOfNonResidentalEstateMapper.toDto(
            comercialEventsOfNonResidentalEstate
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restComercialEventsOfNonResidentalEstateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(comercialEventsOfNonResidentalEstateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ComercialEventsOfNonResidentalEstate in the database
        List<ComercialEventsOfNonResidentalEstate> comercialEventsOfNonResidentalEstateList = comercialEventsOfNonResidentalEstateRepository.findAll();
        assertThat(comercialEventsOfNonResidentalEstateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamComercialEventsOfNonResidentalEstate() throws Exception {
        int databaseSizeBeforeUpdate = comercialEventsOfNonResidentalEstateRepository.findAll().size();
        comercialEventsOfNonResidentalEstate.setId(count.incrementAndGet());

        // Create the ComercialEventsOfNonResidentalEstate
        ComercialEventsOfNonResidentalEstateDTO comercialEventsOfNonResidentalEstateDTO = comercialEventsOfNonResidentalEstateMapper.toDto(
            comercialEventsOfNonResidentalEstate
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restComercialEventsOfNonResidentalEstateMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(comercialEventsOfNonResidentalEstateDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ComercialEventsOfNonResidentalEstate in the database
        List<ComercialEventsOfNonResidentalEstate> comercialEventsOfNonResidentalEstateList = comercialEventsOfNonResidentalEstateRepository.findAll();
        assertThat(comercialEventsOfNonResidentalEstateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteComercialEventsOfNonResidentalEstate() throws Exception {
        // Initialize the database
        comercialEventsOfNonResidentalEstateRepository.saveAndFlush(comercialEventsOfNonResidentalEstate);

        int databaseSizeBeforeDelete = comercialEventsOfNonResidentalEstateRepository.findAll().size();

        // Delete the comercialEventsOfNonResidentalEstate
        restComercialEventsOfNonResidentalEstateMockMvc
            .perform(
                delete(ENTITY_API_URL_ID, comercialEventsOfNonResidentalEstate.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ComercialEventsOfNonResidentalEstate> comercialEventsOfNonResidentalEstateList = comercialEventsOfNonResidentalEstateRepository.findAll();
        assertThat(comercialEventsOfNonResidentalEstateList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
