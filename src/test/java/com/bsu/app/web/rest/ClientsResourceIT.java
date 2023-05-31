package com.bsu.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.bsu.app.IntegrationTest;
import com.bsu.app.domain.Clients;
import com.bsu.app.repository.ClientsRepository;
import com.bsu.app.service.criteria.ClientsCriteria;
import com.bsu.app.service.dto.ClientsDTO;
import com.bsu.app.service.mapper.ClientsMapper;
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
 * Integration tests for the {@link ClientsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ClientsResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SURENAME = "AAAAAAAAAA";
    private static final String UPDATED_SURENAME = "BBBBBBBBBB";

    private static final String DEFAULT_PATRONYMIC = "AAAAAAAAAA";
    private static final String UPDATED_PATRONYMIC = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NUMBER = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/clients";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ClientsRepository clientsRepository;

    @Autowired
    private ClientsMapper clientsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restClientsMockMvc;

    private Clients clients;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Clients createEntity(EntityManager em) {
        Clients clients = new Clients()
            .name(DEFAULT_NAME)
            .surename(DEFAULT_SURENAME)
            .patronymic(DEFAULT_PATRONYMIC)
            .phoneNumber(DEFAULT_PHONE_NUMBER);
        return clients;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Clients createUpdatedEntity(EntityManager em) {
        Clients clients = new Clients()
            .name(UPDATED_NAME)
            .surename(UPDATED_SURENAME)
            .patronymic(UPDATED_PATRONYMIC)
            .phoneNumber(UPDATED_PHONE_NUMBER);
        return clients;
    }

    @BeforeEach
    public void initTest() {
        clients = createEntity(em);
    }

    @Test
    @Transactional
    void createClients() throws Exception {
        int databaseSizeBeforeCreate = clientsRepository.findAll().size();
        // Create the Clients
        ClientsDTO clientsDTO = clientsMapper.toDto(clients);
        restClientsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(clientsDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Clients in the database
        List<Clients> clientsList = clientsRepository.findAll();
        assertThat(clientsList).hasSize(databaseSizeBeforeCreate + 1);
        Clients testClients = clientsList.get(clientsList.size() - 1);
        assertThat(testClients.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testClients.getSurename()).isEqualTo(DEFAULT_SURENAME);
        assertThat(testClients.getPatronymic()).isEqualTo(DEFAULT_PATRONYMIC);
        assertThat(testClients.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
    }

    @Test
    @Transactional
    void createClientsWithExistingId() throws Exception {
        // Create the Clients with an existing ID
        clients.setId(1L);
        ClientsDTO clientsDTO = clientsMapper.toDto(clients);

        int databaseSizeBeforeCreate = clientsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restClientsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(clientsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Clients in the database
        List<Clients> clientsList = clientsRepository.findAll();
        assertThat(clientsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllClients() throws Exception {
        // Initialize the database
        clientsRepository.saveAndFlush(clients);

        // Get all the clientsList
        restClientsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(clients.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].surename").value(hasItem(DEFAULT_SURENAME)))
            .andExpect(jsonPath("$.[*].patronymic").value(hasItem(DEFAULT_PATRONYMIC)))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)));
    }

    @Test
    @Transactional
    void getClients() throws Exception {
        // Initialize the database
        clientsRepository.saveAndFlush(clients);

        // Get the clients
        restClientsMockMvc
            .perform(get(ENTITY_API_URL_ID, clients.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(clients.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.surename").value(DEFAULT_SURENAME))
            .andExpect(jsonPath("$.patronymic").value(DEFAULT_PATRONYMIC))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER));
    }

    @Test
    @Transactional
    void getClientsByIdFiltering() throws Exception {
        // Initialize the database
        clientsRepository.saveAndFlush(clients);

        Long id = clients.getId();

        defaultClientsShouldBeFound("id.equals=" + id);
        defaultClientsShouldNotBeFound("id.notEquals=" + id);

        defaultClientsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultClientsShouldNotBeFound("id.greaterThan=" + id);

        defaultClientsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultClientsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllClientsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        clientsRepository.saveAndFlush(clients);

        // Get all the clientsList where name equals to DEFAULT_NAME
        defaultClientsShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the clientsList where name equals to UPDATED_NAME
        defaultClientsShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllClientsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        clientsRepository.saveAndFlush(clients);

        // Get all the clientsList where name in DEFAULT_NAME or UPDATED_NAME
        defaultClientsShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the clientsList where name equals to UPDATED_NAME
        defaultClientsShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllClientsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        clientsRepository.saveAndFlush(clients);

        // Get all the clientsList where name is not null
        defaultClientsShouldBeFound("name.specified=true");

        // Get all the clientsList where name is null
        defaultClientsShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllClientsByNameContainsSomething() throws Exception {
        // Initialize the database
        clientsRepository.saveAndFlush(clients);

        // Get all the clientsList where name contains DEFAULT_NAME
        defaultClientsShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the clientsList where name contains UPDATED_NAME
        defaultClientsShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllClientsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        clientsRepository.saveAndFlush(clients);

        // Get all the clientsList where name does not contain DEFAULT_NAME
        defaultClientsShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the clientsList where name does not contain UPDATED_NAME
        defaultClientsShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllClientsBySurenameIsEqualToSomething() throws Exception {
        // Initialize the database
        clientsRepository.saveAndFlush(clients);

        // Get all the clientsList where surename equals to DEFAULT_SURENAME
        defaultClientsShouldBeFound("surename.equals=" + DEFAULT_SURENAME);

        // Get all the clientsList where surename equals to UPDATED_SURENAME
        defaultClientsShouldNotBeFound("surename.equals=" + UPDATED_SURENAME);
    }

    @Test
    @Transactional
    void getAllClientsBySurenameIsInShouldWork() throws Exception {
        // Initialize the database
        clientsRepository.saveAndFlush(clients);

        // Get all the clientsList where surename in DEFAULT_SURENAME or UPDATED_SURENAME
        defaultClientsShouldBeFound("surename.in=" + DEFAULT_SURENAME + "," + UPDATED_SURENAME);

        // Get all the clientsList where surename equals to UPDATED_SURENAME
        defaultClientsShouldNotBeFound("surename.in=" + UPDATED_SURENAME);
    }

    @Test
    @Transactional
    void getAllClientsBySurenameIsNullOrNotNull() throws Exception {
        // Initialize the database
        clientsRepository.saveAndFlush(clients);

        // Get all the clientsList where surename is not null
        defaultClientsShouldBeFound("surename.specified=true");

        // Get all the clientsList where surename is null
        defaultClientsShouldNotBeFound("surename.specified=false");
    }

    @Test
    @Transactional
    void getAllClientsBySurenameContainsSomething() throws Exception {
        // Initialize the database
        clientsRepository.saveAndFlush(clients);

        // Get all the clientsList where surename contains DEFAULT_SURENAME
        defaultClientsShouldBeFound("surename.contains=" + DEFAULT_SURENAME);

        // Get all the clientsList where surename contains UPDATED_SURENAME
        defaultClientsShouldNotBeFound("surename.contains=" + UPDATED_SURENAME);
    }

    @Test
    @Transactional
    void getAllClientsBySurenameNotContainsSomething() throws Exception {
        // Initialize the database
        clientsRepository.saveAndFlush(clients);

        // Get all the clientsList where surename does not contain DEFAULT_SURENAME
        defaultClientsShouldNotBeFound("surename.doesNotContain=" + DEFAULT_SURENAME);

        // Get all the clientsList where surename does not contain UPDATED_SURENAME
        defaultClientsShouldBeFound("surename.doesNotContain=" + UPDATED_SURENAME);
    }

    @Test
    @Transactional
    void getAllClientsByPatronymicIsEqualToSomething() throws Exception {
        // Initialize the database
        clientsRepository.saveAndFlush(clients);

        // Get all the clientsList where patronymic equals to DEFAULT_PATRONYMIC
        defaultClientsShouldBeFound("patronymic.equals=" + DEFAULT_PATRONYMIC);

        // Get all the clientsList where patronymic equals to UPDATED_PATRONYMIC
        defaultClientsShouldNotBeFound("patronymic.equals=" + UPDATED_PATRONYMIC);
    }

    @Test
    @Transactional
    void getAllClientsByPatronymicIsInShouldWork() throws Exception {
        // Initialize the database
        clientsRepository.saveAndFlush(clients);

        // Get all the clientsList where patronymic in DEFAULT_PATRONYMIC or UPDATED_PATRONYMIC
        defaultClientsShouldBeFound("patronymic.in=" + DEFAULT_PATRONYMIC + "," + UPDATED_PATRONYMIC);

        // Get all the clientsList where patronymic equals to UPDATED_PATRONYMIC
        defaultClientsShouldNotBeFound("patronymic.in=" + UPDATED_PATRONYMIC);
    }

    @Test
    @Transactional
    void getAllClientsByPatronymicIsNullOrNotNull() throws Exception {
        // Initialize the database
        clientsRepository.saveAndFlush(clients);

        // Get all the clientsList where patronymic is not null
        defaultClientsShouldBeFound("patronymic.specified=true");

        // Get all the clientsList where patronymic is null
        defaultClientsShouldNotBeFound("patronymic.specified=false");
    }

    @Test
    @Transactional
    void getAllClientsByPatronymicContainsSomething() throws Exception {
        // Initialize the database
        clientsRepository.saveAndFlush(clients);

        // Get all the clientsList where patronymic contains DEFAULT_PATRONYMIC
        defaultClientsShouldBeFound("patronymic.contains=" + DEFAULT_PATRONYMIC);

        // Get all the clientsList where patronymic contains UPDATED_PATRONYMIC
        defaultClientsShouldNotBeFound("patronymic.contains=" + UPDATED_PATRONYMIC);
    }

    @Test
    @Transactional
    void getAllClientsByPatronymicNotContainsSomething() throws Exception {
        // Initialize the database
        clientsRepository.saveAndFlush(clients);

        // Get all the clientsList where patronymic does not contain DEFAULT_PATRONYMIC
        defaultClientsShouldNotBeFound("patronymic.doesNotContain=" + DEFAULT_PATRONYMIC);

        // Get all the clientsList where patronymic does not contain UPDATED_PATRONYMIC
        defaultClientsShouldBeFound("patronymic.doesNotContain=" + UPDATED_PATRONYMIC);
    }

    @Test
    @Transactional
    void getAllClientsByPhoneNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        clientsRepository.saveAndFlush(clients);

        // Get all the clientsList where phoneNumber equals to DEFAULT_PHONE_NUMBER
        defaultClientsShouldBeFound("phoneNumber.equals=" + DEFAULT_PHONE_NUMBER);

        // Get all the clientsList where phoneNumber equals to UPDATED_PHONE_NUMBER
        defaultClientsShouldNotBeFound("phoneNumber.equals=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    void getAllClientsByPhoneNumberIsInShouldWork() throws Exception {
        // Initialize the database
        clientsRepository.saveAndFlush(clients);

        // Get all the clientsList where phoneNumber in DEFAULT_PHONE_NUMBER or UPDATED_PHONE_NUMBER
        defaultClientsShouldBeFound("phoneNumber.in=" + DEFAULT_PHONE_NUMBER + "," + UPDATED_PHONE_NUMBER);

        // Get all the clientsList where phoneNumber equals to UPDATED_PHONE_NUMBER
        defaultClientsShouldNotBeFound("phoneNumber.in=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    void getAllClientsByPhoneNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        clientsRepository.saveAndFlush(clients);

        // Get all the clientsList where phoneNumber is not null
        defaultClientsShouldBeFound("phoneNumber.specified=true");

        // Get all the clientsList where phoneNumber is null
        defaultClientsShouldNotBeFound("phoneNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllClientsByPhoneNumberContainsSomething() throws Exception {
        // Initialize the database
        clientsRepository.saveAndFlush(clients);

        // Get all the clientsList where phoneNumber contains DEFAULT_PHONE_NUMBER
        defaultClientsShouldBeFound("phoneNumber.contains=" + DEFAULT_PHONE_NUMBER);

        // Get all the clientsList where phoneNumber contains UPDATED_PHONE_NUMBER
        defaultClientsShouldNotBeFound("phoneNumber.contains=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    void getAllClientsByPhoneNumberNotContainsSomething() throws Exception {
        // Initialize the database
        clientsRepository.saveAndFlush(clients);

        // Get all the clientsList where phoneNumber does not contain DEFAULT_PHONE_NUMBER
        defaultClientsShouldNotBeFound("phoneNumber.doesNotContain=" + DEFAULT_PHONE_NUMBER);

        // Get all the clientsList where phoneNumber does not contain UPDATED_PHONE_NUMBER
        defaultClientsShouldBeFound("phoneNumber.doesNotContain=" + UPDATED_PHONE_NUMBER);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultClientsShouldBeFound(String filter) throws Exception {
        restClientsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(clients.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].surename").value(hasItem(DEFAULT_SURENAME)))
            .andExpect(jsonPath("$.[*].patronymic").value(hasItem(DEFAULT_PATRONYMIC)))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)));

        // Check, that the count call also returns 1
        restClientsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultClientsShouldNotBeFound(String filter) throws Exception {
        restClientsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restClientsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingClients() throws Exception {
        // Get the clients
        restClientsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingClients() throws Exception {
        // Initialize the database
        clientsRepository.saveAndFlush(clients);

        int databaseSizeBeforeUpdate = clientsRepository.findAll().size();

        // Update the clients
        Clients updatedClients = clientsRepository.findById(clients.getId()).get();
        // Disconnect from session so that the updates on updatedClients are not directly saved in db
        em.detach(updatedClients);
        updatedClients.name(UPDATED_NAME).surename(UPDATED_SURENAME).patronymic(UPDATED_PATRONYMIC).phoneNumber(UPDATED_PHONE_NUMBER);
        ClientsDTO clientsDTO = clientsMapper.toDto(updatedClients);

        restClientsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, clientsDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(clientsDTO))
            )
            .andExpect(status().isOk());

        // Validate the Clients in the database
        List<Clients> clientsList = clientsRepository.findAll();
        assertThat(clientsList).hasSize(databaseSizeBeforeUpdate);
        Clients testClients = clientsList.get(clientsList.size() - 1);
        assertThat(testClients.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testClients.getSurename()).isEqualTo(UPDATED_SURENAME);
        assertThat(testClients.getPatronymic()).isEqualTo(UPDATED_PATRONYMIC);
        assertThat(testClients.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    void putNonExistingClients() throws Exception {
        int databaseSizeBeforeUpdate = clientsRepository.findAll().size();
        clients.setId(count.incrementAndGet());

        // Create the Clients
        ClientsDTO clientsDTO = clientsMapper.toDto(clients);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClientsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, clientsDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(clientsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Clients in the database
        List<Clients> clientsList = clientsRepository.findAll();
        assertThat(clientsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchClients() throws Exception {
        int databaseSizeBeforeUpdate = clientsRepository.findAll().size();
        clients.setId(count.incrementAndGet());

        // Create the Clients
        ClientsDTO clientsDTO = clientsMapper.toDto(clients);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClientsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(clientsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Clients in the database
        List<Clients> clientsList = clientsRepository.findAll();
        assertThat(clientsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamClients() throws Exception {
        int databaseSizeBeforeUpdate = clientsRepository.findAll().size();
        clients.setId(count.incrementAndGet());

        // Create the Clients
        ClientsDTO clientsDTO = clientsMapper.toDto(clients);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClientsMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(clientsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Clients in the database
        List<Clients> clientsList = clientsRepository.findAll();
        assertThat(clientsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateClientsWithPatch() throws Exception {
        // Initialize the database
        clientsRepository.saveAndFlush(clients);

        int databaseSizeBeforeUpdate = clientsRepository.findAll().size();

        // Update the clients using partial update
        Clients partialUpdatedClients = new Clients();
        partialUpdatedClients.setId(clients.getId());

        partialUpdatedClients.name(UPDATED_NAME).patronymic(UPDATED_PATRONYMIC).phoneNumber(UPDATED_PHONE_NUMBER);

        restClientsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedClients.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedClients))
            )
            .andExpect(status().isOk());

        // Validate the Clients in the database
        List<Clients> clientsList = clientsRepository.findAll();
        assertThat(clientsList).hasSize(databaseSizeBeforeUpdate);
        Clients testClients = clientsList.get(clientsList.size() - 1);
        assertThat(testClients.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testClients.getSurename()).isEqualTo(DEFAULT_SURENAME);
        assertThat(testClients.getPatronymic()).isEqualTo(UPDATED_PATRONYMIC);
        assertThat(testClients.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    void fullUpdateClientsWithPatch() throws Exception {
        // Initialize the database
        clientsRepository.saveAndFlush(clients);

        int databaseSizeBeforeUpdate = clientsRepository.findAll().size();

        // Update the clients using partial update
        Clients partialUpdatedClients = new Clients();
        partialUpdatedClients.setId(clients.getId());

        partialUpdatedClients
            .name(UPDATED_NAME)
            .surename(UPDATED_SURENAME)
            .patronymic(UPDATED_PATRONYMIC)
            .phoneNumber(UPDATED_PHONE_NUMBER);

        restClientsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedClients.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedClients))
            )
            .andExpect(status().isOk());

        // Validate the Clients in the database
        List<Clients> clientsList = clientsRepository.findAll();
        assertThat(clientsList).hasSize(databaseSizeBeforeUpdate);
        Clients testClients = clientsList.get(clientsList.size() - 1);
        assertThat(testClients.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testClients.getSurename()).isEqualTo(UPDATED_SURENAME);
        assertThat(testClients.getPatronymic()).isEqualTo(UPDATED_PATRONYMIC);
        assertThat(testClients.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    void patchNonExistingClients() throws Exception {
        int databaseSizeBeforeUpdate = clientsRepository.findAll().size();
        clients.setId(count.incrementAndGet());

        // Create the Clients
        ClientsDTO clientsDTO = clientsMapper.toDto(clients);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClientsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, clientsDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(clientsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Clients in the database
        List<Clients> clientsList = clientsRepository.findAll();
        assertThat(clientsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchClients() throws Exception {
        int databaseSizeBeforeUpdate = clientsRepository.findAll().size();
        clients.setId(count.incrementAndGet());

        // Create the Clients
        ClientsDTO clientsDTO = clientsMapper.toDto(clients);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClientsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(clientsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Clients in the database
        List<Clients> clientsList = clientsRepository.findAll();
        assertThat(clientsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamClients() throws Exception {
        int databaseSizeBeforeUpdate = clientsRepository.findAll().size();
        clients.setId(count.incrementAndGet());

        // Create the Clients
        ClientsDTO clientsDTO = clientsMapper.toDto(clients);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClientsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(clientsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Clients in the database
        List<Clients> clientsList = clientsRepository.findAll();
        assertThat(clientsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteClients() throws Exception {
        // Initialize the database
        clientsRepository.saveAndFlush(clients);

        int databaseSizeBeforeDelete = clientsRepository.findAll().size();

        // Delete the clients
        restClientsMockMvc
            .perform(delete(ENTITY_API_URL_ID, clients.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Clients> clientsList = clientsRepository.findAll();
        assertThat(clientsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
