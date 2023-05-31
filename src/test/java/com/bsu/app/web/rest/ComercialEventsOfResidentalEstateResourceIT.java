package com.bsu.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.bsu.app.IntegrationTest;
import com.bsu.app.domain.Clients;
import com.bsu.app.domain.ComercialEventsOfResidentalEstate;
import com.bsu.app.domain.ResidentalEstates;
import com.bsu.app.domain.TypesOfCommercialEvents;
import com.bsu.app.repository.ComercialEventsOfResidentalEstateRepository;
import com.bsu.app.service.criteria.ComercialEventsOfResidentalEstateCriteria;
import com.bsu.app.service.dto.ComercialEventsOfResidentalEstateDTO;
import com.bsu.app.service.mapper.ComercialEventsOfResidentalEstateMapper;
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
 * Integration tests for the {@link ComercialEventsOfResidentalEstateResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ComercialEventsOfResidentalEstateResourceIT {

    private static final String DEFAULT_AGENT_NOTES = "AAAAAAAAAA";
    private static final String UPDATED_AGENT_NOTES = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_OF_EVENT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_OF_EVENT = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATE_OF_EVENT = LocalDate.ofEpochDay(-1L);

    private static final String ENTITY_API_URL = "/api/comercial-events-of-residental-estates";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ComercialEventsOfResidentalEstateRepository comercialEventsOfResidentalEstateRepository;

    @Autowired
    private ComercialEventsOfResidentalEstateMapper comercialEventsOfResidentalEstateMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restComercialEventsOfResidentalEstateMockMvc;

    private ComercialEventsOfResidentalEstate comercialEventsOfResidentalEstate;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ComercialEventsOfResidentalEstate createEntity(EntityManager em) {
        ComercialEventsOfResidentalEstate comercialEventsOfResidentalEstate = new ComercialEventsOfResidentalEstate()
            .agentNotes(DEFAULT_AGENT_NOTES)
            .dateOfEvent(DEFAULT_DATE_OF_EVENT);
        return comercialEventsOfResidentalEstate;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ComercialEventsOfResidentalEstate createUpdatedEntity(EntityManager em) {
        ComercialEventsOfResidentalEstate comercialEventsOfResidentalEstate = new ComercialEventsOfResidentalEstate()
            .agentNotes(UPDATED_AGENT_NOTES)
            .dateOfEvent(UPDATED_DATE_OF_EVENT);
        return comercialEventsOfResidentalEstate;
    }

    @BeforeEach
    public void initTest() {
        comercialEventsOfResidentalEstate = createEntity(em);
    }

    @Test
    @Transactional
    void createComercialEventsOfResidentalEstate() throws Exception {
        int databaseSizeBeforeCreate = comercialEventsOfResidentalEstateRepository.findAll().size();
        // Create the ComercialEventsOfResidentalEstate
        ComercialEventsOfResidentalEstateDTO comercialEventsOfResidentalEstateDTO = comercialEventsOfResidentalEstateMapper.toDto(
            comercialEventsOfResidentalEstate
        );
        restComercialEventsOfResidentalEstateMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(comercialEventsOfResidentalEstateDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ComercialEventsOfResidentalEstate in the database
        List<ComercialEventsOfResidentalEstate> comercialEventsOfResidentalEstateList = comercialEventsOfResidentalEstateRepository.findAll();
        assertThat(comercialEventsOfResidentalEstateList).hasSize(databaseSizeBeforeCreate + 1);
        ComercialEventsOfResidentalEstate testComercialEventsOfResidentalEstate = comercialEventsOfResidentalEstateList.get(
            comercialEventsOfResidentalEstateList.size() - 1
        );
        assertThat(testComercialEventsOfResidentalEstate.getAgentNotes()).isEqualTo(DEFAULT_AGENT_NOTES);
        assertThat(testComercialEventsOfResidentalEstate.getDateOfEvent()).isEqualTo(DEFAULT_DATE_OF_EVENT);
    }

    @Test
    @Transactional
    void createComercialEventsOfResidentalEstateWithExistingId() throws Exception {
        // Create the ComercialEventsOfResidentalEstate with an existing ID
        comercialEventsOfResidentalEstate.setId(1L);
        ComercialEventsOfResidentalEstateDTO comercialEventsOfResidentalEstateDTO = comercialEventsOfResidentalEstateMapper.toDto(
            comercialEventsOfResidentalEstate
        );

        int databaseSizeBeforeCreate = comercialEventsOfResidentalEstateRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restComercialEventsOfResidentalEstateMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(comercialEventsOfResidentalEstateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ComercialEventsOfResidentalEstate in the database
        List<ComercialEventsOfResidentalEstate> comercialEventsOfResidentalEstateList = comercialEventsOfResidentalEstateRepository.findAll();
        assertThat(comercialEventsOfResidentalEstateList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllComercialEventsOfResidentalEstates() throws Exception {
        // Initialize the database
        comercialEventsOfResidentalEstateRepository.saveAndFlush(comercialEventsOfResidentalEstate);

        // Get all the comercialEventsOfResidentalEstateList
        restComercialEventsOfResidentalEstateMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(comercialEventsOfResidentalEstate.getId().intValue())))
            .andExpect(jsonPath("$.[*].agentNotes").value(hasItem(DEFAULT_AGENT_NOTES)))
            .andExpect(jsonPath("$.[*].dateOfEvent").value(hasItem(DEFAULT_DATE_OF_EVENT.toString())));
    }

    @Test
    @Transactional
    void getComercialEventsOfResidentalEstate() throws Exception {
        // Initialize the database
        comercialEventsOfResidentalEstateRepository.saveAndFlush(comercialEventsOfResidentalEstate);

        // Get the comercialEventsOfResidentalEstate
        restComercialEventsOfResidentalEstateMockMvc
            .perform(get(ENTITY_API_URL_ID, comercialEventsOfResidentalEstate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(comercialEventsOfResidentalEstate.getId().intValue()))
            .andExpect(jsonPath("$.agentNotes").value(DEFAULT_AGENT_NOTES))
            .andExpect(jsonPath("$.dateOfEvent").value(DEFAULT_DATE_OF_EVENT.toString()));
    }

    @Test
    @Transactional
    void getComercialEventsOfResidentalEstatesByIdFiltering() throws Exception {
        // Initialize the database
        comercialEventsOfResidentalEstateRepository.saveAndFlush(comercialEventsOfResidentalEstate);

        Long id = comercialEventsOfResidentalEstate.getId();

        defaultComercialEventsOfResidentalEstateShouldBeFound("id.equals=" + id);
        defaultComercialEventsOfResidentalEstateShouldNotBeFound("id.notEquals=" + id);

        defaultComercialEventsOfResidentalEstateShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultComercialEventsOfResidentalEstateShouldNotBeFound("id.greaterThan=" + id);

        defaultComercialEventsOfResidentalEstateShouldBeFound("id.lessThanOrEqual=" + id);
        defaultComercialEventsOfResidentalEstateShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllComercialEventsOfResidentalEstatesByAgentNotesIsEqualToSomething() throws Exception {
        // Initialize the database
        comercialEventsOfResidentalEstateRepository.saveAndFlush(comercialEventsOfResidentalEstate);

        // Get all the comercialEventsOfResidentalEstateList where agentNotes equals to DEFAULT_AGENT_NOTES
        defaultComercialEventsOfResidentalEstateShouldBeFound("agentNotes.equals=" + DEFAULT_AGENT_NOTES);

        // Get all the comercialEventsOfResidentalEstateList where agentNotes equals to UPDATED_AGENT_NOTES
        defaultComercialEventsOfResidentalEstateShouldNotBeFound("agentNotes.equals=" + UPDATED_AGENT_NOTES);
    }

    @Test
    @Transactional
    void getAllComercialEventsOfResidentalEstatesByAgentNotesIsInShouldWork() throws Exception {
        // Initialize the database
        comercialEventsOfResidentalEstateRepository.saveAndFlush(comercialEventsOfResidentalEstate);

        // Get all the comercialEventsOfResidentalEstateList where agentNotes in DEFAULT_AGENT_NOTES or UPDATED_AGENT_NOTES
        defaultComercialEventsOfResidentalEstateShouldBeFound("agentNotes.in=" + DEFAULT_AGENT_NOTES + "," + UPDATED_AGENT_NOTES);

        // Get all the comercialEventsOfResidentalEstateList where agentNotes equals to UPDATED_AGENT_NOTES
        defaultComercialEventsOfResidentalEstateShouldNotBeFound("agentNotes.in=" + UPDATED_AGENT_NOTES);
    }

    @Test
    @Transactional
    void getAllComercialEventsOfResidentalEstatesByAgentNotesIsNullOrNotNull() throws Exception {
        // Initialize the database
        comercialEventsOfResidentalEstateRepository.saveAndFlush(comercialEventsOfResidentalEstate);

        // Get all the comercialEventsOfResidentalEstateList where agentNotes is not null
        defaultComercialEventsOfResidentalEstateShouldBeFound("agentNotes.specified=true");

        // Get all the comercialEventsOfResidentalEstateList where agentNotes is null
        defaultComercialEventsOfResidentalEstateShouldNotBeFound("agentNotes.specified=false");
    }

    @Test
    @Transactional
    void getAllComercialEventsOfResidentalEstatesByAgentNotesContainsSomething() throws Exception {
        // Initialize the database
        comercialEventsOfResidentalEstateRepository.saveAndFlush(comercialEventsOfResidentalEstate);

        // Get all the comercialEventsOfResidentalEstateList where agentNotes contains DEFAULT_AGENT_NOTES
        defaultComercialEventsOfResidentalEstateShouldBeFound("agentNotes.contains=" + DEFAULT_AGENT_NOTES);

        // Get all the comercialEventsOfResidentalEstateList where agentNotes contains UPDATED_AGENT_NOTES
        defaultComercialEventsOfResidentalEstateShouldNotBeFound("agentNotes.contains=" + UPDATED_AGENT_NOTES);
    }

    @Test
    @Transactional
    void getAllComercialEventsOfResidentalEstatesByAgentNotesNotContainsSomething() throws Exception {
        // Initialize the database
        comercialEventsOfResidentalEstateRepository.saveAndFlush(comercialEventsOfResidentalEstate);

        // Get all the comercialEventsOfResidentalEstateList where agentNotes does not contain DEFAULT_AGENT_NOTES
        defaultComercialEventsOfResidentalEstateShouldNotBeFound("agentNotes.doesNotContain=" + DEFAULT_AGENT_NOTES);

        // Get all the comercialEventsOfResidentalEstateList where agentNotes does not contain UPDATED_AGENT_NOTES
        defaultComercialEventsOfResidentalEstateShouldBeFound("agentNotes.doesNotContain=" + UPDATED_AGENT_NOTES);
    }

    @Test
    @Transactional
    void getAllComercialEventsOfResidentalEstatesByDateOfEventIsEqualToSomething() throws Exception {
        // Initialize the database
        comercialEventsOfResidentalEstateRepository.saveAndFlush(comercialEventsOfResidentalEstate);

        // Get all the comercialEventsOfResidentalEstateList where dateOfEvent equals to DEFAULT_DATE_OF_EVENT
        defaultComercialEventsOfResidentalEstateShouldBeFound("dateOfEvent.equals=" + DEFAULT_DATE_OF_EVENT);

        // Get all the comercialEventsOfResidentalEstateList where dateOfEvent equals to UPDATED_DATE_OF_EVENT
        defaultComercialEventsOfResidentalEstateShouldNotBeFound("dateOfEvent.equals=" + UPDATED_DATE_OF_EVENT);
    }

    @Test
    @Transactional
    void getAllComercialEventsOfResidentalEstatesByDateOfEventIsInShouldWork() throws Exception {
        // Initialize the database
        comercialEventsOfResidentalEstateRepository.saveAndFlush(comercialEventsOfResidentalEstate);

        // Get all the comercialEventsOfResidentalEstateList where dateOfEvent in DEFAULT_DATE_OF_EVENT or UPDATED_DATE_OF_EVENT
        defaultComercialEventsOfResidentalEstateShouldBeFound("dateOfEvent.in=" + DEFAULT_DATE_OF_EVENT + "," + UPDATED_DATE_OF_EVENT);

        // Get all the comercialEventsOfResidentalEstateList where dateOfEvent equals to UPDATED_DATE_OF_EVENT
        defaultComercialEventsOfResidentalEstateShouldNotBeFound("dateOfEvent.in=" + UPDATED_DATE_OF_EVENT);
    }

    @Test
    @Transactional
    void getAllComercialEventsOfResidentalEstatesByDateOfEventIsNullOrNotNull() throws Exception {
        // Initialize the database
        comercialEventsOfResidentalEstateRepository.saveAndFlush(comercialEventsOfResidentalEstate);

        // Get all the comercialEventsOfResidentalEstateList where dateOfEvent is not null
        defaultComercialEventsOfResidentalEstateShouldBeFound("dateOfEvent.specified=true");

        // Get all the comercialEventsOfResidentalEstateList where dateOfEvent is null
        defaultComercialEventsOfResidentalEstateShouldNotBeFound("dateOfEvent.specified=false");
    }

    @Test
    @Transactional
    void getAllComercialEventsOfResidentalEstatesByDateOfEventIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        comercialEventsOfResidentalEstateRepository.saveAndFlush(comercialEventsOfResidentalEstate);

        // Get all the comercialEventsOfResidentalEstateList where dateOfEvent is greater than or equal to DEFAULT_DATE_OF_EVENT
        defaultComercialEventsOfResidentalEstateShouldBeFound("dateOfEvent.greaterThanOrEqual=" + DEFAULT_DATE_OF_EVENT);

        // Get all the comercialEventsOfResidentalEstateList where dateOfEvent is greater than or equal to UPDATED_DATE_OF_EVENT
        defaultComercialEventsOfResidentalEstateShouldNotBeFound("dateOfEvent.greaterThanOrEqual=" + UPDATED_DATE_OF_EVENT);
    }

    @Test
    @Transactional
    void getAllComercialEventsOfResidentalEstatesByDateOfEventIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        comercialEventsOfResidentalEstateRepository.saveAndFlush(comercialEventsOfResidentalEstate);

        // Get all the comercialEventsOfResidentalEstateList where dateOfEvent is less than or equal to DEFAULT_DATE_OF_EVENT
        defaultComercialEventsOfResidentalEstateShouldBeFound("dateOfEvent.lessThanOrEqual=" + DEFAULT_DATE_OF_EVENT);

        // Get all the comercialEventsOfResidentalEstateList where dateOfEvent is less than or equal to SMALLER_DATE_OF_EVENT
        defaultComercialEventsOfResidentalEstateShouldNotBeFound("dateOfEvent.lessThanOrEqual=" + SMALLER_DATE_OF_EVENT);
    }

    @Test
    @Transactional
    void getAllComercialEventsOfResidentalEstatesByDateOfEventIsLessThanSomething() throws Exception {
        // Initialize the database
        comercialEventsOfResidentalEstateRepository.saveAndFlush(comercialEventsOfResidentalEstate);

        // Get all the comercialEventsOfResidentalEstateList where dateOfEvent is less than DEFAULT_DATE_OF_EVENT
        defaultComercialEventsOfResidentalEstateShouldNotBeFound("dateOfEvent.lessThan=" + DEFAULT_DATE_OF_EVENT);

        // Get all the comercialEventsOfResidentalEstateList where dateOfEvent is less than UPDATED_DATE_OF_EVENT
        defaultComercialEventsOfResidentalEstateShouldBeFound("dateOfEvent.lessThan=" + UPDATED_DATE_OF_EVENT);
    }

    @Test
    @Transactional
    void getAllComercialEventsOfResidentalEstatesByDateOfEventIsGreaterThanSomething() throws Exception {
        // Initialize the database
        comercialEventsOfResidentalEstateRepository.saveAndFlush(comercialEventsOfResidentalEstate);

        // Get all the comercialEventsOfResidentalEstateList where dateOfEvent is greater than DEFAULT_DATE_OF_EVENT
        defaultComercialEventsOfResidentalEstateShouldNotBeFound("dateOfEvent.greaterThan=" + DEFAULT_DATE_OF_EVENT);

        // Get all the comercialEventsOfResidentalEstateList where dateOfEvent is greater than SMALLER_DATE_OF_EVENT
        defaultComercialEventsOfResidentalEstateShouldBeFound("dateOfEvent.greaterThan=" + SMALLER_DATE_OF_EVENT);
    }

    @Test
    @Transactional
    void getAllComercialEventsOfResidentalEstatesByTypeOfCommercialEventIdIsEqualToSomething() throws Exception {
        TypesOfCommercialEvents typeOfCommercialEventId;
        if (TestUtil.findAll(em, TypesOfCommercialEvents.class).isEmpty()) {
            comercialEventsOfResidentalEstateRepository.saveAndFlush(comercialEventsOfResidentalEstate);
            typeOfCommercialEventId = TypesOfCommercialEventsResourceIT.createEntity(em);
        } else {
            typeOfCommercialEventId = TestUtil.findAll(em, TypesOfCommercialEvents.class).get(0);
        }
        em.persist(typeOfCommercialEventId);
        em.flush();
        comercialEventsOfResidentalEstate.setTypeOfCommercialEventId(typeOfCommercialEventId);
        comercialEventsOfResidentalEstateRepository.saveAndFlush(comercialEventsOfResidentalEstate);
        Long typeOfCommercialEventIdId = typeOfCommercialEventId.getId();

        // Get all the comercialEventsOfResidentalEstateList where typeOfCommercialEventId equals to typeOfCommercialEventIdId
        defaultComercialEventsOfResidentalEstateShouldBeFound("typeOfCommercialEventIdId.equals=" + typeOfCommercialEventIdId);

        // Get all the comercialEventsOfResidentalEstateList where typeOfCommercialEventId equals to (typeOfCommercialEventIdId + 1)
        defaultComercialEventsOfResidentalEstateShouldNotBeFound("typeOfCommercialEventIdId.equals=" + (typeOfCommercialEventIdId + 1));
    }

    @Test
    @Transactional
    void getAllComercialEventsOfResidentalEstatesByClientIdIsEqualToSomething() throws Exception {
        Clients clientId;
        if (TestUtil.findAll(em, Clients.class).isEmpty()) {
            comercialEventsOfResidentalEstateRepository.saveAndFlush(comercialEventsOfResidentalEstate);
            clientId = ClientsResourceIT.createEntity(em);
        } else {
            clientId = TestUtil.findAll(em, Clients.class).get(0);
        }
        em.persist(clientId);
        em.flush();
        comercialEventsOfResidentalEstate.setClientId(clientId);
        comercialEventsOfResidentalEstateRepository.saveAndFlush(comercialEventsOfResidentalEstate);
        Long clientIdId = clientId.getId();

        // Get all the comercialEventsOfResidentalEstateList where clientId equals to clientIdId
        defaultComercialEventsOfResidentalEstateShouldBeFound("clientIdId.equals=" + clientIdId);

        // Get all the comercialEventsOfResidentalEstateList where clientId equals to (clientIdId + 1)
        defaultComercialEventsOfResidentalEstateShouldNotBeFound("clientIdId.equals=" + (clientIdId + 1));
    }

    @Test
    @Transactional
    void getAllComercialEventsOfResidentalEstatesByResidentalEstateIdIsEqualToSomething() throws Exception {
        ResidentalEstates residentalEstateId;
        if (TestUtil.findAll(em, ResidentalEstates.class).isEmpty()) {
            comercialEventsOfResidentalEstateRepository.saveAndFlush(comercialEventsOfResidentalEstate);
            residentalEstateId = ResidentalEstatesResourceIT.createEntity(em);
        } else {
            residentalEstateId = TestUtil.findAll(em, ResidentalEstates.class).get(0);
        }
        em.persist(residentalEstateId);
        em.flush();
        comercialEventsOfResidentalEstate.setResidentalEstateId(residentalEstateId);
        comercialEventsOfResidentalEstateRepository.saveAndFlush(comercialEventsOfResidentalEstate);
        Long residentalEstateIdId = residentalEstateId.getId();

        // Get all the comercialEventsOfResidentalEstateList where residentalEstateId equals to residentalEstateIdId
        defaultComercialEventsOfResidentalEstateShouldBeFound("residentalEstateIdId.equals=" + residentalEstateIdId);

        // Get all the comercialEventsOfResidentalEstateList where residentalEstateId equals to (residentalEstateIdId + 1)
        defaultComercialEventsOfResidentalEstateShouldNotBeFound("residentalEstateIdId.equals=" + (residentalEstateIdId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultComercialEventsOfResidentalEstateShouldBeFound(String filter) throws Exception {
        restComercialEventsOfResidentalEstateMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(comercialEventsOfResidentalEstate.getId().intValue())))
            .andExpect(jsonPath("$.[*].agentNotes").value(hasItem(DEFAULT_AGENT_NOTES)))
            .andExpect(jsonPath("$.[*].dateOfEvent").value(hasItem(DEFAULT_DATE_OF_EVENT.toString())));

        // Check, that the count call also returns 1
        restComercialEventsOfResidentalEstateMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultComercialEventsOfResidentalEstateShouldNotBeFound(String filter) throws Exception {
        restComercialEventsOfResidentalEstateMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restComercialEventsOfResidentalEstateMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingComercialEventsOfResidentalEstate() throws Exception {
        // Get the comercialEventsOfResidentalEstate
        restComercialEventsOfResidentalEstateMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingComercialEventsOfResidentalEstate() throws Exception {
        // Initialize the database
        comercialEventsOfResidentalEstateRepository.saveAndFlush(comercialEventsOfResidentalEstate);

        int databaseSizeBeforeUpdate = comercialEventsOfResidentalEstateRepository.findAll().size();

        // Update the comercialEventsOfResidentalEstate
        ComercialEventsOfResidentalEstate updatedComercialEventsOfResidentalEstate = comercialEventsOfResidentalEstateRepository
            .findById(comercialEventsOfResidentalEstate.getId())
            .get();
        // Disconnect from session so that the updates on updatedComercialEventsOfResidentalEstate are not directly saved in db
        em.detach(updatedComercialEventsOfResidentalEstate);
        updatedComercialEventsOfResidentalEstate.agentNotes(UPDATED_AGENT_NOTES).dateOfEvent(UPDATED_DATE_OF_EVENT);
        ComercialEventsOfResidentalEstateDTO comercialEventsOfResidentalEstateDTO = comercialEventsOfResidentalEstateMapper.toDto(
            updatedComercialEventsOfResidentalEstate
        );

        restComercialEventsOfResidentalEstateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, comercialEventsOfResidentalEstateDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(comercialEventsOfResidentalEstateDTO))
            )
            .andExpect(status().isOk());

        // Validate the ComercialEventsOfResidentalEstate in the database
        List<ComercialEventsOfResidentalEstate> comercialEventsOfResidentalEstateList = comercialEventsOfResidentalEstateRepository.findAll();
        assertThat(comercialEventsOfResidentalEstateList).hasSize(databaseSizeBeforeUpdate);
        ComercialEventsOfResidentalEstate testComercialEventsOfResidentalEstate = comercialEventsOfResidentalEstateList.get(
            comercialEventsOfResidentalEstateList.size() - 1
        );
        assertThat(testComercialEventsOfResidentalEstate.getAgentNotes()).isEqualTo(UPDATED_AGENT_NOTES);
        assertThat(testComercialEventsOfResidentalEstate.getDateOfEvent()).isEqualTo(UPDATED_DATE_OF_EVENT);
    }

    @Test
    @Transactional
    void putNonExistingComercialEventsOfResidentalEstate() throws Exception {
        int databaseSizeBeforeUpdate = comercialEventsOfResidentalEstateRepository.findAll().size();
        comercialEventsOfResidentalEstate.setId(count.incrementAndGet());

        // Create the ComercialEventsOfResidentalEstate
        ComercialEventsOfResidentalEstateDTO comercialEventsOfResidentalEstateDTO = comercialEventsOfResidentalEstateMapper.toDto(
            comercialEventsOfResidentalEstate
        );

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restComercialEventsOfResidentalEstateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, comercialEventsOfResidentalEstateDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(comercialEventsOfResidentalEstateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ComercialEventsOfResidentalEstate in the database
        List<ComercialEventsOfResidentalEstate> comercialEventsOfResidentalEstateList = comercialEventsOfResidentalEstateRepository.findAll();
        assertThat(comercialEventsOfResidentalEstateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchComercialEventsOfResidentalEstate() throws Exception {
        int databaseSizeBeforeUpdate = comercialEventsOfResidentalEstateRepository.findAll().size();
        comercialEventsOfResidentalEstate.setId(count.incrementAndGet());

        // Create the ComercialEventsOfResidentalEstate
        ComercialEventsOfResidentalEstateDTO comercialEventsOfResidentalEstateDTO = comercialEventsOfResidentalEstateMapper.toDto(
            comercialEventsOfResidentalEstate
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restComercialEventsOfResidentalEstateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(comercialEventsOfResidentalEstateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ComercialEventsOfResidentalEstate in the database
        List<ComercialEventsOfResidentalEstate> comercialEventsOfResidentalEstateList = comercialEventsOfResidentalEstateRepository.findAll();
        assertThat(comercialEventsOfResidentalEstateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamComercialEventsOfResidentalEstate() throws Exception {
        int databaseSizeBeforeUpdate = comercialEventsOfResidentalEstateRepository.findAll().size();
        comercialEventsOfResidentalEstate.setId(count.incrementAndGet());

        // Create the ComercialEventsOfResidentalEstate
        ComercialEventsOfResidentalEstateDTO comercialEventsOfResidentalEstateDTO = comercialEventsOfResidentalEstateMapper.toDto(
            comercialEventsOfResidentalEstate
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restComercialEventsOfResidentalEstateMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(comercialEventsOfResidentalEstateDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ComercialEventsOfResidentalEstate in the database
        List<ComercialEventsOfResidentalEstate> comercialEventsOfResidentalEstateList = comercialEventsOfResidentalEstateRepository.findAll();
        assertThat(comercialEventsOfResidentalEstateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateComercialEventsOfResidentalEstateWithPatch() throws Exception {
        // Initialize the database
        comercialEventsOfResidentalEstateRepository.saveAndFlush(comercialEventsOfResidentalEstate);

        int databaseSizeBeforeUpdate = comercialEventsOfResidentalEstateRepository.findAll().size();

        // Update the comercialEventsOfResidentalEstate using partial update
        ComercialEventsOfResidentalEstate partialUpdatedComercialEventsOfResidentalEstate = new ComercialEventsOfResidentalEstate();
        partialUpdatedComercialEventsOfResidentalEstate.setId(comercialEventsOfResidentalEstate.getId());

        partialUpdatedComercialEventsOfResidentalEstate.dateOfEvent(UPDATED_DATE_OF_EVENT);

        restComercialEventsOfResidentalEstateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedComercialEventsOfResidentalEstate.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedComercialEventsOfResidentalEstate))
            )
            .andExpect(status().isOk());

        // Validate the ComercialEventsOfResidentalEstate in the database
        List<ComercialEventsOfResidentalEstate> comercialEventsOfResidentalEstateList = comercialEventsOfResidentalEstateRepository.findAll();
        assertThat(comercialEventsOfResidentalEstateList).hasSize(databaseSizeBeforeUpdate);
        ComercialEventsOfResidentalEstate testComercialEventsOfResidentalEstate = comercialEventsOfResidentalEstateList.get(
            comercialEventsOfResidentalEstateList.size() - 1
        );
        assertThat(testComercialEventsOfResidentalEstate.getAgentNotes()).isEqualTo(DEFAULT_AGENT_NOTES);
        assertThat(testComercialEventsOfResidentalEstate.getDateOfEvent()).isEqualTo(UPDATED_DATE_OF_EVENT);
    }

    @Test
    @Transactional
    void fullUpdateComercialEventsOfResidentalEstateWithPatch() throws Exception {
        // Initialize the database
        comercialEventsOfResidentalEstateRepository.saveAndFlush(comercialEventsOfResidentalEstate);

        int databaseSizeBeforeUpdate = comercialEventsOfResidentalEstateRepository.findAll().size();

        // Update the comercialEventsOfResidentalEstate using partial update
        ComercialEventsOfResidentalEstate partialUpdatedComercialEventsOfResidentalEstate = new ComercialEventsOfResidentalEstate();
        partialUpdatedComercialEventsOfResidentalEstate.setId(comercialEventsOfResidentalEstate.getId());

        partialUpdatedComercialEventsOfResidentalEstate.agentNotes(UPDATED_AGENT_NOTES).dateOfEvent(UPDATED_DATE_OF_EVENT);

        restComercialEventsOfResidentalEstateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedComercialEventsOfResidentalEstate.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedComercialEventsOfResidentalEstate))
            )
            .andExpect(status().isOk());

        // Validate the ComercialEventsOfResidentalEstate in the database
        List<ComercialEventsOfResidentalEstate> comercialEventsOfResidentalEstateList = comercialEventsOfResidentalEstateRepository.findAll();
        assertThat(comercialEventsOfResidentalEstateList).hasSize(databaseSizeBeforeUpdate);
        ComercialEventsOfResidentalEstate testComercialEventsOfResidentalEstate = comercialEventsOfResidentalEstateList.get(
            comercialEventsOfResidentalEstateList.size() - 1
        );
        assertThat(testComercialEventsOfResidentalEstate.getAgentNotes()).isEqualTo(UPDATED_AGENT_NOTES);
        assertThat(testComercialEventsOfResidentalEstate.getDateOfEvent()).isEqualTo(UPDATED_DATE_OF_EVENT);
    }

    @Test
    @Transactional
    void patchNonExistingComercialEventsOfResidentalEstate() throws Exception {
        int databaseSizeBeforeUpdate = comercialEventsOfResidentalEstateRepository.findAll().size();
        comercialEventsOfResidentalEstate.setId(count.incrementAndGet());

        // Create the ComercialEventsOfResidentalEstate
        ComercialEventsOfResidentalEstateDTO comercialEventsOfResidentalEstateDTO = comercialEventsOfResidentalEstateMapper.toDto(
            comercialEventsOfResidentalEstate
        );

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restComercialEventsOfResidentalEstateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, comercialEventsOfResidentalEstateDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(comercialEventsOfResidentalEstateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ComercialEventsOfResidentalEstate in the database
        List<ComercialEventsOfResidentalEstate> comercialEventsOfResidentalEstateList = comercialEventsOfResidentalEstateRepository.findAll();
        assertThat(comercialEventsOfResidentalEstateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchComercialEventsOfResidentalEstate() throws Exception {
        int databaseSizeBeforeUpdate = comercialEventsOfResidentalEstateRepository.findAll().size();
        comercialEventsOfResidentalEstate.setId(count.incrementAndGet());

        // Create the ComercialEventsOfResidentalEstate
        ComercialEventsOfResidentalEstateDTO comercialEventsOfResidentalEstateDTO = comercialEventsOfResidentalEstateMapper.toDto(
            comercialEventsOfResidentalEstate
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restComercialEventsOfResidentalEstateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(comercialEventsOfResidentalEstateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ComercialEventsOfResidentalEstate in the database
        List<ComercialEventsOfResidentalEstate> comercialEventsOfResidentalEstateList = comercialEventsOfResidentalEstateRepository.findAll();
        assertThat(comercialEventsOfResidentalEstateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamComercialEventsOfResidentalEstate() throws Exception {
        int databaseSizeBeforeUpdate = comercialEventsOfResidentalEstateRepository.findAll().size();
        comercialEventsOfResidentalEstate.setId(count.incrementAndGet());

        // Create the ComercialEventsOfResidentalEstate
        ComercialEventsOfResidentalEstateDTO comercialEventsOfResidentalEstateDTO = comercialEventsOfResidentalEstateMapper.toDto(
            comercialEventsOfResidentalEstate
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restComercialEventsOfResidentalEstateMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(comercialEventsOfResidentalEstateDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ComercialEventsOfResidentalEstate in the database
        List<ComercialEventsOfResidentalEstate> comercialEventsOfResidentalEstateList = comercialEventsOfResidentalEstateRepository.findAll();
        assertThat(comercialEventsOfResidentalEstateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteComercialEventsOfResidentalEstate() throws Exception {
        // Initialize the database
        comercialEventsOfResidentalEstateRepository.saveAndFlush(comercialEventsOfResidentalEstate);

        int databaseSizeBeforeDelete = comercialEventsOfResidentalEstateRepository.findAll().size();

        // Delete the comercialEventsOfResidentalEstate
        restComercialEventsOfResidentalEstateMockMvc
            .perform(delete(ENTITY_API_URL_ID, comercialEventsOfResidentalEstate.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ComercialEventsOfResidentalEstate> comercialEventsOfResidentalEstateList = comercialEventsOfResidentalEstateRepository.findAll();
        assertThat(comercialEventsOfResidentalEstateList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
