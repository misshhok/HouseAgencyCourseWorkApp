package com.bsu.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.bsu.app.IntegrationTest;
import com.bsu.app.domain.Addresses;
import com.bsu.app.domain.StreetsOfCities;
import com.bsu.app.repository.AddressesRepository;
import com.bsu.app.service.criteria.AddressesCriteria;
import com.bsu.app.service.dto.AddressesDTO;
import com.bsu.app.service.mapper.AddressesMapper;
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
 * Integration tests for the {@link AddressesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AddressesResourceIT {

    private static final Integer DEFAULT_HOUSE_NUMBER = 1;
    private static final Integer UPDATED_HOUSE_NUMBER = 2;
    private static final Integer SMALLER_HOUSE_NUMBER = 1 - 1;

    private static final String ENTITY_API_URL = "/api/addresses";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AddressesRepository addressesRepository;

    @Autowired
    private AddressesMapper addressesMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAddressesMockMvc;

    private Addresses addresses;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Addresses createEntity(EntityManager em) {
        Addresses addresses = new Addresses().houseNumber(DEFAULT_HOUSE_NUMBER);
        return addresses;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Addresses createUpdatedEntity(EntityManager em) {
        Addresses addresses = new Addresses().houseNumber(UPDATED_HOUSE_NUMBER);
        return addresses;
    }

    @BeforeEach
    public void initTest() {
        addresses = createEntity(em);
    }

    @Test
    @Transactional
    void createAddresses() throws Exception {
        int databaseSizeBeforeCreate = addressesRepository.findAll().size();
        // Create the Addresses
        AddressesDTO addressesDTO = addressesMapper.toDto(addresses);
        restAddressesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(addressesDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Addresses in the database
        List<Addresses> addressesList = addressesRepository.findAll();
        assertThat(addressesList).hasSize(databaseSizeBeforeCreate + 1);
        Addresses testAddresses = addressesList.get(addressesList.size() - 1);
        assertThat(testAddresses.getHouseNumber()).isEqualTo(DEFAULT_HOUSE_NUMBER);
    }

    @Test
    @Transactional
    void createAddressesWithExistingId() throws Exception {
        // Create the Addresses with an existing ID
        addresses.setId(1L);
        AddressesDTO addressesDTO = addressesMapper.toDto(addresses);

        int databaseSizeBeforeCreate = addressesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAddressesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(addressesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Addresses in the database
        List<Addresses> addressesList = addressesRepository.findAll();
        assertThat(addressesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAddresses() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList
        restAddressesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(addresses.getId().intValue())))
            .andExpect(jsonPath("$.[*].houseNumber").value(hasItem(DEFAULT_HOUSE_NUMBER)));
    }

    @Test
    @Transactional
    void getAddresses() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get the addresses
        restAddressesMockMvc
            .perform(get(ENTITY_API_URL_ID, addresses.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(addresses.getId().intValue()))
            .andExpect(jsonPath("$.houseNumber").value(DEFAULT_HOUSE_NUMBER));
    }

    @Test
    @Transactional
    void getAddressesByIdFiltering() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        Long id = addresses.getId();

        defaultAddressesShouldBeFound("id.equals=" + id);
        defaultAddressesShouldNotBeFound("id.notEquals=" + id);

        defaultAddressesShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAddressesShouldNotBeFound("id.greaterThan=" + id);

        defaultAddressesShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAddressesShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllAddressesByHouseNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where houseNumber equals to DEFAULT_HOUSE_NUMBER
        defaultAddressesShouldBeFound("houseNumber.equals=" + DEFAULT_HOUSE_NUMBER);

        // Get all the addressesList where houseNumber equals to UPDATED_HOUSE_NUMBER
        defaultAddressesShouldNotBeFound("houseNumber.equals=" + UPDATED_HOUSE_NUMBER);
    }

    @Test
    @Transactional
    void getAllAddressesByHouseNumberIsInShouldWork() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where houseNumber in DEFAULT_HOUSE_NUMBER or UPDATED_HOUSE_NUMBER
        defaultAddressesShouldBeFound("houseNumber.in=" + DEFAULT_HOUSE_NUMBER + "," + UPDATED_HOUSE_NUMBER);

        // Get all the addressesList where houseNumber equals to UPDATED_HOUSE_NUMBER
        defaultAddressesShouldNotBeFound("houseNumber.in=" + UPDATED_HOUSE_NUMBER);
    }

    @Test
    @Transactional
    void getAllAddressesByHouseNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where houseNumber is not null
        defaultAddressesShouldBeFound("houseNumber.specified=true");

        // Get all the addressesList where houseNumber is null
        defaultAddressesShouldNotBeFound("houseNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllAddressesByHouseNumberIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where houseNumber is greater than or equal to DEFAULT_HOUSE_NUMBER
        defaultAddressesShouldBeFound("houseNumber.greaterThanOrEqual=" + DEFAULT_HOUSE_NUMBER);

        // Get all the addressesList where houseNumber is greater than or equal to UPDATED_HOUSE_NUMBER
        defaultAddressesShouldNotBeFound("houseNumber.greaterThanOrEqual=" + UPDATED_HOUSE_NUMBER);
    }

    @Test
    @Transactional
    void getAllAddressesByHouseNumberIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where houseNumber is less than or equal to DEFAULT_HOUSE_NUMBER
        defaultAddressesShouldBeFound("houseNumber.lessThanOrEqual=" + DEFAULT_HOUSE_NUMBER);

        // Get all the addressesList where houseNumber is less than or equal to SMALLER_HOUSE_NUMBER
        defaultAddressesShouldNotBeFound("houseNumber.lessThanOrEqual=" + SMALLER_HOUSE_NUMBER);
    }

    @Test
    @Transactional
    void getAllAddressesByHouseNumberIsLessThanSomething() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where houseNumber is less than DEFAULT_HOUSE_NUMBER
        defaultAddressesShouldNotBeFound("houseNumber.lessThan=" + DEFAULT_HOUSE_NUMBER);

        // Get all the addressesList where houseNumber is less than UPDATED_HOUSE_NUMBER
        defaultAddressesShouldBeFound("houseNumber.lessThan=" + UPDATED_HOUSE_NUMBER);
    }

    @Test
    @Transactional
    void getAllAddressesByHouseNumberIsGreaterThanSomething() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        // Get all the addressesList where houseNumber is greater than DEFAULT_HOUSE_NUMBER
        defaultAddressesShouldNotBeFound("houseNumber.greaterThan=" + DEFAULT_HOUSE_NUMBER);

        // Get all the addressesList where houseNumber is greater than SMALLER_HOUSE_NUMBER
        defaultAddressesShouldBeFound("houseNumber.greaterThan=" + SMALLER_HOUSE_NUMBER);
    }

    @Test
    @Transactional
    void getAllAddressesByStreetOfCityIdIsEqualToSomething() throws Exception {
        StreetsOfCities streetOfCityId;
        if (TestUtil.findAll(em, StreetsOfCities.class).isEmpty()) {
            addressesRepository.saveAndFlush(addresses);
            streetOfCityId = StreetsOfCitiesResourceIT.createEntity(em);
        } else {
            streetOfCityId = TestUtil.findAll(em, StreetsOfCities.class).get(0);
        }
        em.persist(streetOfCityId);
        em.flush();
        addresses.setStreetOfCityId(streetOfCityId);
        addressesRepository.saveAndFlush(addresses);
        Long streetOfCityIdId = streetOfCityId.getId();

        // Get all the addressesList where streetOfCityId equals to streetOfCityIdId
        defaultAddressesShouldBeFound("streetOfCityIdId.equals=" + streetOfCityIdId);

        // Get all the addressesList where streetOfCityId equals to (streetOfCityIdId + 1)
        defaultAddressesShouldNotBeFound("streetOfCityIdId.equals=" + (streetOfCityIdId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAddressesShouldBeFound(String filter) throws Exception {
        restAddressesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(addresses.getId().intValue())))
            .andExpect(jsonPath("$.[*].houseNumber").value(hasItem(DEFAULT_HOUSE_NUMBER)));

        // Check, that the count call also returns 1
        restAddressesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAddressesShouldNotBeFound(String filter) throws Exception {
        restAddressesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAddressesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingAddresses() throws Exception {
        // Get the addresses
        restAddressesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAddresses() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        int databaseSizeBeforeUpdate = addressesRepository.findAll().size();

        // Update the addresses
        Addresses updatedAddresses = addressesRepository.findById(addresses.getId()).get();
        // Disconnect from session so that the updates on updatedAddresses are not directly saved in db
        em.detach(updatedAddresses);
        updatedAddresses.houseNumber(UPDATED_HOUSE_NUMBER);
        AddressesDTO addressesDTO = addressesMapper.toDto(updatedAddresses);

        restAddressesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, addressesDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(addressesDTO))
            )
            .andExpect(status().isOk());

        // Validate the Addresses in the database
        List<Addresses> addressesList = addressesRepository.findAll();
        assertThat(addressesList).hasSize(databaseSizeBeforeUpdate);
        Addresses testAddresses = addressesList.get(addressesList.size() - 1);
        assertThat(testAddresses.getHouseNumber()).isEqualTo(UPDATED_HOUSE_NUMBER);
    }

    @Test
    @Transactional
    void putNonExistingAddresses() throws Exception {
        int databaseSizeBeforeUpdate = addressesRepository.findAll().size();
        addresses.setId(count.incrementAndGet());

        // Create the Addresses
        AddressesDTO addressesDTO = addressesMapper.toDto(addresses);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAddressesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, addressesDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(addressesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Addresses in the database
        List<Addresses> addressesList = addressesRepository.findAll();
        assertThat(addressesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAddresses() throws Exception {
        int databaseSizeBeforeUpdate = addressesRepository.findAll().size();
        addresses.setId(count.incrementAndGet());

        // Create the Addresses
        AddressesDTO addressesDTO = addressesMapper.toDto(addresses);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAddressesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(addressesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Addresses in the database
        List<Addresses> addressesList = addressesRepository.findAll();
        assertThat(addressesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAddresses() throws Exception {
        int databaseSizeBeforeUpdate = addressesRepository.findAll().size();
        addresses.setId(count.incrementAndGet());

        // Create the Addresses
        AddressesDTO addressesDTO = addressesMapper.toDto(addresses);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAddressesMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(addressesDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Addresses in the database
        List<Addresses> addressesList = addressesRepository.findAll();
        assertThat(addressesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAddressesWithPatch() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        int databaseSizeBeforeUpdate = addressesRepository.findAll().size();

        // Update the addresses using partial update
        Addresses partialUpdatedAddresses = new Addresses();
        partialUpdatedAddresses.setId(addresses.getId());

        partialUpdatedAddresses.houseNumber(UPDATED_HOUSE_NUMBER);

        restAddressesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAddresses.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAddresses))
            )
            .andExpect(status().isOk());

        // Validate the Addresses in the database
        List<Addresses> addressesList = addressesRepository.findAll();
        assertThat(addressesList).hasSize(databaseSizeBeforeUpdate);
        Addresses testAddresses = addressesList.get(addressesList.size() - 1);
        assertThat(testAddresses.getHouseNumber()).isEqualTo(UPDATED_HOUSE_NUMBER);
    }

    @Test
    @Transactional
    void fullUpdateAddressesWithPatch() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        int databaseSizeBeforeUpdate = addressesRepository.findAll().size();

        // Update the addresses using partial update
        Addresses partialUpdatedAddresses = new Addresses();
        partialUpdatedAddresses.setId(addresses.getId());

        partialUpdatedAddresses.houseNumber(UPDATED_HOUSE_NUMBER);

        restAddressesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAddresses.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAddresses))
            )
            .andExpect(status().isOk());

        // Validate the Addresses in the database
        List<Addresses> addressesList = addressesRepository.findAll();
        assertThat(addressesList).hasSize(databaseSizeBeforeUpdate);
        Addresses testAddresses = addressesList.get(addressesList.size() - 1);
        assertThat(testAddresses.getHouseNumber()).isEqualTo(UPDATED_HOUSE_NUMBER);
    }

    @Test
    @Transactional
    void patchNonExistingAddresses() throws Exception {
        int databaseSizeBeforeUpdate = addressesRepository.findAll().size();
        addresses.setId(count.incrementAndGet());

        // Create the Addresses
        AddressesDTO addressesDTO = addressesMapper.toDto(addresses);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAddressesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, addressesDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(addressesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Addresses in the database
        List<Addresses> addressesList = addressesRepository.findAll();
        assertThat(addressesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAddresses() throws Exception {
        int databaseSizeBeforeUpdate = addressesRepository.findAll().size();
        addresses.setId(count.incrementAndGet());

        // Create the Addresses
        AddressesDTO addressesDTO = addressesMapper.toDto(addresses);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAddressesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(addressesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Addresses in the database
        List<Addresses> addressesList = addressesRepository.findAll();
        assertThat(addressesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAddresses() throws Exception {
        int databaseSizeBeforeUpdate = addressesRepository.findAll().size();
        addresses.setId(count.incrementAndGet());

        // Create the Addresses
        AddressesDTO addressesDTO = addressesMapper.toDto(addresses);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAddressesMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(addressesDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Addresses in the database
        List<Addresses> addressesList = addressesRepository.findAll();
        assertThat(addressesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAddresses() throws Exception {
        // Initialize the database
        addressesRepository.saveAndFlush(addresses);

        int databaseSizeBeforeDelete = addressesRepository.findAll().size();

        // Delete the addresses
        restAddressesMockMvc
            .perform(delete(ENTITY_API_URL_ID, addresses.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Addresses> addressesList = addressesRepository.findAll();
        assertThat(addressesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
