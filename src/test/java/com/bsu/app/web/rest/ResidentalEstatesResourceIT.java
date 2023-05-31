package com.bsu.app.web.rest;

import static com.bsu.app.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.bsu.app.IntegrationTest;
import com.bsu.app.domain.Addresses;
import com.bsu.app.domain.ResidentalEstates;
import com.bsu.app.domain.StatusesOfResidentalEstate;
import com.bsu.app.domain.TypesOfResidentalEstate;
import com.bsu.app.repository.ResidentalEstatesRepository;
import com.bsu.app.service.criteria.ResidentalEstatesCriteria;
import com.bsu.app.service.dto.ResidentalEstatesDTO;
import com.bsu.app.service.mapper.ResidentalEstatesMapper;
import java.math.BigDecimal;
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
 * Integration tests for the {@link ResidentalEstatesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ResidentalEstatesResourceIT {

    private static final Double DEFAULT_LIVING_SPACE = 1D;
    private static final Double UPDATED_LIVING_SPACE = 2D;
    private static final Double SMALLER_LIVING_SPACE = 1D - 1D;

    private static final Long DEFAULT_CADASTRAL_NUMBER = 1L;
    private static final Long UPDATED_CADASTRAL_NUMBER = 2L;
    private static final Long SMALLER_CADASTRAL_NUMBER = 1L - 1L;

    private static final LocalDate DEFAULT_COMMISSIONING_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_COMMISSIONING_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_COMMISSIONING_DATE = LocalDate.ofEpochDay(-1L);

    private static final Integer DEFAULT_NUMBER_OF_ROOMS = 1;
    private static final Integer UPDATED_NUMBER_OF_ROOMS = 2;
    private static final Integer SMALLER_NUMBER_OF_ROOMS = 1 - 1;

    private static final String DEFAULT_FURNISH_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_FURNISH_TYPE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_HAS_BALCONY = false;
    private static final Boolean UPDATED_HAS_BALCONY = true;

    private static final String DEFAULT_BATHROOM_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_BATHROOM_TYPE = "BBBBBBBBBB";

    private static final Double DEFAULT_CEILING_HEIGHT = 1D;
    private static final Double UPDATED_CEILING_HEIGHT = 2D;
    private static final Double SMALLER_CEILING_HEIGHT = 1D - 1D;

    private static final BigDecimal DEFAULT_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRICE = new BigDecimal(2);
    private static final BigDecimal SMALLER_PRICE = new BigDecimal(1 - 1);

    private static final String ENTITY_API_URL = "/api/residental-estates";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ResidentalEstatesRepository residentalEstatesRepository;

    @Autowired
    private ResidentalEstatesMapper residentalEstatesMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restResidentalEstatesMockMvc;

    private ResidentalEstates residentalEstates;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ResidentalEstates createEntity(EntityManager em) {
        ResidentalEstates residentalEstates = new ResidentalEstates()
            .livingSpace(DEFAULT_LIVING_SPACE)
            .cadastralNumber(DEFAULT_CADASTRAL_NUMBER)
            .commissioningDate(DEFAULT_COMMISSIONING_DATE)
            .numberOfRooms(DEFAULT_NUMBER_OF_ROOMS)
            .furnishType(DEFAULT_FURNISH_TYPE)
            .hasBalcony(DEFAULT_HAS_BALCONY)
            .bathroomType(DEFAULT_BATHROOM_TYPE)
            .ceilingHeight(DEFAULT_CEILING_HEIGHT)
            .price(DEFAULT_PRICE);
        return residentalEstates;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ResidentalEstates createUpdatedEntity(EntityManager em) {
        ResidentalEstates residentalEstates = new ResidentalEstates()
            .livingSpace(UPDATED_LIVING_SPACE)
            .cadastralNumber(UPDATED_CADASTRAL_NUMBER)
            .commissioningDate(UPDATED_COMMISSIONING_DATE)
            .numberOfRooms(UPDATED_NUMBER_OF_ROOMS)
            .furnishType(UPDATED_FURNISH_TYPE)
            .hasBalcony(UPDATED_HAS_BALCONY)
            .bathroomType(UPDATED_BATHROOM_TYPE)
            .ceilingHeight(UPDATED_CEILING_HEIGHT)
            .price(UPDATED_PRICE);
        return residentalEstates;
    }

    @BeforeEach
    public void initTest() {
        residentalEstates = createEntity(em);
    }

    @Test
    @Transactional
    void createResidentalEstates() throws Exception {
        int databaseSizeBeforeCreate = residentalEstatesRepository.findAll().size();
        // Create the ResidentalEstates
        ResidentalEstatesDTO residentalEstatesDTO = residentalEstatesMapper.toDto(residentalEstates);
        restResidentalEstatesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(residentalEstatesDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ResidentalEstates in the database
        List<ResidentalEstates> residentalEstatesList = residentalEstatesRepository.findAll();
        assertThat(residentalEstatesList).hasSize(databaseSizeBeforeCreate + 1);
        ResidentalEstates testResidentalEstates = residentalEstatesList.get(residentalEstatesList.size() - 1);
        assertThat(testResidentalEstates.getLivingSpace()).isEqualTo(DEFAULT_LIVING_SPACE);
        assertThat(testResidentalEstates.getCadastralNumber()).isEqualTo(DEFAULT_CADASTRAL_NUMBER);
        assertThat(testResidentalEstates.getCommissioningDate()).isEqualTo(DEFAULT_COMMISSIONING_DATE);
        assertThat(testResidentalEstates.getNumberOfRooms()).isEqualTo(DEFAULT_NUMBER_OF_ROOMS);
        assertThat(testResidentalEstates.getFurnishType()).isEqualTo(DEFAULT_FURNISH_TYPE);
        assertThat(testResidentalEstates.getHasBalcony()).isEqualTo(DEFAULT_HAS_BALCONY);
        assertThat(testResidentalEstates.getBathroomType()).isEqualTo(DEFAULT_BATHROOM_TYPE);
        assertThat(testResidentalEstates.getCeilingHeight()).isEqualTo(DEFAULT_CEILING_HEIGHT);
        assertThat(testResidentalEstates.getPrice()).isEqualByComparingTo(DEFAULT_PRICE);
    }

    @Test
    @Transactional
    void createResidentalEstatesWithExistingId() throws Exception {
        // Create the ResidentalEstates with an existing ID
        residentalEstates.setId(1L);
        ResidentalEstatesDTO residentalEstatesDTO = residentalEstatesMapper.toDto(residentalEstates);

        int databaseSizeBeforeCreate = residentalEstatesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restResidentalEstatesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(residentalEstatesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ResidentalEstates in the database
        List<ResidentalEstates> residentalEstatesList = residentalEstatesRepository.findAll();
        assertThat(residentalEstatesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllResidentalEstates() throws Exception {
        // Initialize the database
        residentalEstatesRepository.saveAndFlush(residentalEstates);

        // Get all the residentalEstatesList
        restResidentalEstatesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(residentalEstates.getId().intValue())))
            .andExpect(jsonPath("$.[*].livingSpace").value(hasItem(DEFAULT_LIVING_SPACE.doubleValue())))
            .andExpect(jsonPath("$.[*].cadastralNumber").value(hasItem(DEFAULT_CADASTRAL_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].commissioningDate").value(hasItem(DEFAULT_COMMISSIONING_DATE.toString())))
            .andExpect(jsonPath("$.[*].numberOfRooms").value(hasItem(DEFAULT_NUMBER_OF_ROOMS)))
            .andExpect(jsonPath("$.[*].furnishType").value(hasItem(DEFAULT_FURNISH_TYPE)))
            .andExpect(jsonPath("$.[*].hasBalcony").value(hasItem(DEFAULT_HAS_BALCONY.booleanValue())))
            .andExpect(jsonPath("$.[*].bathroomType").value(hasItem(DEFAULT_BATHROOM_TYPE)))
            .andExpect(jsonPath("$.[*].ceilingHeight").value(hasItem(DEFAULT_CEILING_HEIGHT.doubleValue())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(sameNumber(DEFAULT_PRICE))));
    }

    @Test
    @Transactional
    void getResidentalEstates() throws Exception {
        // Initialize the database
        residentalEstatesRepository.saveAndFlush(residentalEstates);

        // Get the residentalEstates
        restResidentalEstatesMockMvc
            .perform(get(ENTITY_API_URL_ID, residentalEstates.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(residentalEstates.getId().intValue()))
            .andExpect(jsonPath("$.livingSpace").value(DEFAULT_LIVING_SPACE.doubleValue()))
            .andExpect(jsonPath("$.cadastralNumber").value(DEFAULT_CADASTRAL_NUMBER.intValue()))
            .andExpect(jsonPath("$.commissioningDate").value(DEFAULT_COMMISSIONING_DATE.toString()))
            .andExpect(jsonPath("$.numberOfRooms").value(DEFAULT_NUMBER_OF_ROOMS))
            .andExpect(jsonPath("$.furnishType").value(DEFAULT_FURNISH_TYPE))
            .andExpect(jsonPath("$.hasBalcony").value(DEFAULT_HAS_BALCONY.booleanValue()))
            .andExpect(jsonPath("$.bathroomType").value(DEFAULT_BATHROOM_TYPE))
            .andExpect(jsonPath("$.ceilingHeight").value(DEFAULT_CEILING_HEIGHT.doubleValue()))
            .andExpect(jsonPath("$.price").value(sameNumber(DEFAULT_PRICE)));
    }

    @Test
    @Transactional
    void getResidentalEstatesByIdFiltering() throws Exception {
        // Initialize the database
        residentalEstatesRepository.saveAndFlush(residentalEstates);

        Long id = residentalEstates.getId();

        defaultResidentalEstatesShouldBeFound("id.equals=" + id);
        defaultResidentalEstatesShouldNotBeFound("id.notEquals=" + id);

        defaultResidentalEstatesShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultResidentalEstatesShouldNotBeFound("id.greaterThan=" + id);

        defaultResidentalEstatesShouldBeFound("id.lessThanOrEqual=" + id);
        defaultResidentalEstatesShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllResidentalEstatesByLivingSpaceIsEqualToSomething() throws Exception {
        // Initialize the database
        residentalEstatesRepository.saveAndFlush(residentalEstates);

        // Get all the residentalEstatesList where livingSpace equals to DEFAULT_LIVING_SPACE
        defaultResidentalEstatesShouldBeFound("livingSpace.equals=" + DEFAULT_LIVING_SPACE);

        // Get all the residentalEstatesList where livingSpace equals to UPDATED_LIVING_SPACE
        defaultResidentalEstatesShouldNotBeFound("livingSpace.equals=" + UPDATED_LIVING_SPACE);
    }

    @Test
    @Transactional
    void getAllResidentalEstatesByLivingSpaceIsInShouldWork() throws Exception {
        // Initialize the database
        residentalEstatesRepository.saveAndFlush(residentalEstates);

        // Get all the residentalEstatesList where livingSpace in DEFAULT_LIVING_SPACE or UPDATED_LIVING_SPACE
        defaultResidentalEstatesShouldBeFound("livingSpace.in=" + DEFAULT_LIVING_SPACE + "," + UPDATED_LIVING_SPACE);

        // Get all the residentalEstatesList where livingSpace equals to UPDATED_LIVING_SPACE
        defaultResidentalEstatesShouldNotBeFound("livingSpace.in=" + UPDATED_LIVING_SPACE);
    }

    @Test
    @Transactional
    void getAllResidentalEstatesByLivingSpaceIsNullOrNotNull() throws Exception {
        // Initialize the database
        residentalEstatesRepository.saveAndFlush(residentalEstates);

        // Get all the residentalEstatesList where livingSpace is not null
        defaultResidentalEstatesShouldBeFound("livingSpace.specified=true");

        // Get all the residentalEstatesList where livingSpace is null
        defaultResidentalEstatesShouldNotBeFound("livingSpace.specified=false");
    }

    @Test
    @Transactional
    void getAllResidentalEstatesByLivingSpaceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        residentalEstatesRepository.saveAndFlush(residentalEstates);

        // Get all the residentalEstatesList where livingSpace is greater than or equal to DEFAULT_LIVING_SPACE
        defaultResidentalEstatesShouldBeFound("livingSpace.greaterThanOrEqual=" + DEFAULT_LIVING_SPACE);

        // Get all the residentalEstatesList where livingSpace is greater than or equal to UPDATED_LIVING_SPACE
        defaultResidentalEstatesShouldNotBeFound("livingSpace.greaterThanOrEqual=" + UPDATED_LIVING_SPACE);
    }

    @Test
    @Transactional
    void getAllResidentalEstatesByLivingSpaceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        residentalEstatesRepository.saveAndFlush(residentalEstates);

        // Get all the residentalEstatesList where livingSpace is less than or equal to DEFAULT_LIVING_SPACE
        defaultResidentalEstatesShouldBeFound("livingSpace.lessThanOrEqual=" + DEFAULT_LIVING_SPACE);

        // Get all the residentalEstatesList where livingSpace is less than or equal to SMALLER_LIVING_SPACE
        defaultResidentalEstatesShouldNotBeFound("livingSpace.lessThanOrEqual=" + SMALLER_LIVING_SPACE);
    }

    @Test
    @Transactional
    void getAllResidentalEstatesByLivingSpaceIsLessThanSomething() throws Exception {
        // Initialize the database
        residentalEstatesRepository.saveAndFlush(residentalEstates);

        // Get all the residentalEstatesList where livingSpace is less than DEFAULT_LIVING_SPACE
        defaultResidentalEstatesShouldNotBeFound("livingSpace.lessThan=" + DEFAULT_LIVING_SPACE);

        // Get all the residentalEstatesList where livingSpace is less than UPDATED_LIVING_SPACE
        defaultResidentalEstatesShouldBeFound("livingSpace.lessThan=" + UPDATED_LIVING_SPACE);
    }

    @Test
    @Transactional
    void getAllResidentalEstatesByLivingSpaceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        residentalEstatesRepository.saveAndFlush(residentalEstates);

        // Get all the residentalEstatesList where livingSpace is greater than DEFAULT_LIVING_SPACE
        defaultResidentalEstatesShouldNotBeFound("livingSpace.greaterThan=" + DEFAULT_LIVING_SPACE);

        // Get all the residentalEstatesList where livingSpace is greater than SMALLER_LIVING_SPACE
        defaultResidentalEstatesShouldBeFound("livingSpace.greaterThan=" + SMALLER_LIVING_SPACE);
    }

    @Test
    @Transactional
    void getAllResidentalEstatesByCadastralNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        residentalEstatesRepository.saveAndFlush(residentalEstates);

        // Get all the residentalEstatesList where cadastralNumber equals to DEFAULT_CADASTRAL_NUMBER
        defaultResidentalEstatesShouldBeFound("cadastralNumber.equals=" + DEFAULT_CADASTRAL_NUMBER);

        // Get all the residentalEstatesList where cadastralNumber equals to UPDATED_CADASTRAL_NUMBER
        defaultResidentalEstatesShouldNotBeFound("cadastralNumber.equals=" + UPDATED_CADASTRAL_NUMBER);
    }

    @Test
    @Transactional
    void getAllResidentalEstatesByCadastralNumberIsInShouldWork() throws Exception {
        // Initialize the database
        residentalEstatesRepository.saveAndFlush(residentalEstates);

        // Get all the residentalEstatesList where cadastralNumber in DEFAULT_CADASTRAL_NUMBER or UPDATED_CADASTRAL_NUMBER
        defaultResidentalEstatesShouldBeFound("cadastralNumber.in=" + DEFAULT_CADASTRAL_NUMBER + "," + UPDATED_CADASTRAL_NUMBER);

        // Get all the residentalEstatesList where cadastralNumber equals to UPDATED_CADASTRAL_NUMBER
        defaultResidentalEstatesShouldNotBeFound("cadastralNumber.in=" + UPDATED_CADASTRAL_NUMBER);
    }

    @Test
    @Transactional
    void getAllResidentalEstatesByCadastralNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        residentalEstatesRepository.saveAndFlush(residentalEstates);

        // Get all the residentalEstatesList where cadastralNumber is not null
        defaultResidentalEstatesShouldBeFound("cadastralNumber.specified=true");

        // Get all the residentalEstatesList where cadastralNumber is null
        defaultResidentalEstatesShouldNotBeFound("cadastralNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllResidentalEstatesByCadastralNumberIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        residentalEstatesRepository.saveAndFlush(residentalEstates);

        // Get all the residentalEstatesList where cadastralNumber is greater than or equal to DEFAULT_CADASTRAL_NUMBER
        defaultResidentalEstatesShouldBeFound("cadastralNumber.greaterThanOrEqual=" + DEFAULT_CADASTRAL_NUMBER);

        // Get all the residentalEstatesList where cadastralNumber is greater than or equal to UPDATED_CADASTRAL_NUMBER
        defaultResidentalEstatesShouldNotBeFound("cadastralNumber.greaterThanOrEqual=" + UPDATED_CADASTRAL_NUMBER);
    }

    @Test
    @Transactional
    void getAllResidentalEstatesByCadastralNumberIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        residentalEstatesRepository.saveAndFlush(residentalEstates);

        // Get all the residentalEstatesList where cadastralNumber is less than or equal to DEFAULT_CADASTRAL_NUMBER
        defaultResidentalEstatesShouldBeFound("cadastralNumber.lessThanOrEqual=" + DEFAULT_CADASTRAL_NUMBER);

        // Get all the residentalEstatesList where cadastralNumber is less than or equal to SMALLER_CADASTRAL_NUMBER
        defaultResidentalEstatesShouldNotBeFound("cadastralNumber.lessThanOrEqual=" + SMALLER_CADASTRAL_NUMBER);
    }

    @Test
    @Transactional
    void getAllResidentalEstatesByCadastralNumberIsLessThanSomething() throws Exception {
        // Initialize the database
        residentalEstatesRepository.saveAndFlush(residentalEstates);

        // Get all the residentalEstatesList where cadastralNumber is less than DEFAULT_CADASTRAL_NUMBER
        defaultResidentalEstatesShouldNotBeFound("cadastralNumber.lessThan=" + DEFAULT_CADASTRAL_NUMBER);

        // Get all the residentalEstatesList where cadastralNumber is less than UPDATED_CADASTRAL_NUMBER
        defaultResidentalEstatesShouldBeFound("cadastralNumber.lessThan=" + UPDATED_CADASTRAL_NUMBER);
    }

    @Test
    @Transactional
    void getAllResidentalEstatesByCadastralNumberIsGreaterThanSomething() throws Exception {
        // Initialize the database
        residentalEstatesRepository.saveAndFlush(residentalEstates);

        // Get all the residentalEstatesList where cadastralNumber is greater than DEFAULT_CADASTRAL_NUMBER
        defaultResidentalEstatesShouldNotBeFound("cadastralNumber.greaterThan=" + DEFAULT_CADASTRAL_NUMBER);

        // Get all the residentalEstatesList where cadastralNumber is greater than SMALLER_CADASTRAL_NUMBER
        defaultResidentalEstatesShouldBeFound("cadastralNumber.greaterThan=" + SMALLER_CADASTRAL_NUMBER);
    }

    @Test
    @Transactional
    void getAllResidentalEstatesByCommissioningDateIsEqualToSomething() throws Exception {
        // Initialize the database
        residentalEstatesRepository.saveAndFlush(residentalEstates);

        // Get all the residentalEstatesList where commissioningDate equals to DEFAULT_COMMISSIONING_DATE
        defaultResidentalEstatesShouldBeFound("commissioningDate.equals=" + DEFAULT_COMMISSIONING_DATE);

        // Get all the residentalEstatesList where commissioningDate equals to UPDATED_COMMISSIONING_DATE
        defaultResidentalEstatesShouldNotBeFound("commissioningDate.equals=" + UPDATED_COMMISSIONING_DATE);
    }

    @Test
    @Transactional
    void getAllResidentalEstatesByCommissioningDateIsInShouldWork() throws Exception {
        // Initialize the database
        residentalEstatesRepository.saveAndFlush(residentalEstates);

        // Get all the residentalEstatesList where commissioningDate in DEFAULT_COMMISSIONING_DATE or UPDATED_COMMISSIONING_DATE
        defaultResidentalEstatesShouldBeFound("commissioningDate.in=" + DEFAULT_COMMISSIONING_DATE + "," + UPDATED_COMMISSIONING_DATE);

        // Get all the residentalEstatesList where commissioningDate equals to UPDATED_COMMISSIONING_DATE
        defaultResidentalEstatesShouldNotBeFound("commissioningDate.in=" + UPDATED_COMMISSIONING_DATE);
    }

    @Test
    @Transactional
    void getAllResidentalEstatesByCommissioningDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        residentalEstatesRepository.saveAndFlush(residentalEstates);

        // Get all the residentalEstatesList where commissioningDate is not null
        defaultResidentalEstatesShouldBeFound("commissioningDate.specified=true");

        // Get all the residentalEstatesList where commissioningDate is null
        defaultResidentalEstatesShouldNotBeFound("commissioningDate.specified=false");
    }

    @Test
    @Transactional
    void getAllResidentalEstatesByCommissioningDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        residentalEstatesRepository.saveAndFlush(residentalEstates);

        // Get all the residentalEstatesList where commissioningDate is greater than or equal to DEFAULT_COMMISSIONING_DATE
        defaultResidentalEstatesShouldBeFound("commissioningDate.greaterThanOrEqual=" + DEFAULT_COMMISSIONING_DATE);

        // Get all the residentalEstatesList where commissioningDate is greater than or equal to UPDATED_COMMISSIONING_DATE
        defaultResidentalEstatesShouldNotBeFound("commissioningDate.greaterThanOrEqual=" + UPDATED_COMMISSIONING_DATE);
    }

    @Test
    @Transactional
    void getAllResidentalEstatesByCommissioningDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        residentalEstatesRepository.saveAndFlush(residentalEstates);

        // Get all the residentalEstatesList where commissioningDate is less than or equal to DEFAULT_COMMISSIONING_DATE
        defaultResidentalEstatesShouldBeFound("commissioningDate.lessThanOrEqual=" + DEFAULT_COMMISSIONING_DATE);

        // Get all the residentalEstatesList where commissioningDate is less than or equal to SMALLER_COMMISSIONING_DATE
        defaultResidentalEstatesShouldNotBeFound("commissioningDate.lessThanOrEqual=" + SMALLER_COMMISSIONING_DATE);
    }

    @Test
    @Transactional
    void getAllResidentalEstatesByCommissioningDateIsLessThanSomething() throws Exception {
        // Initialize the database
        residentalEstatesRepository.saveAndFlush(residentalEstates);

        // Get all the residentalEstatesList where commissioningDate is less than DEFAULT_COMMISSIONING_DATE
        defaultResidentalEstatesShouldNotBeFound("commissioningDate.lessThan=" + DEFAULT_COMMISSIONING_DATE);

        // Get all the residentalEstatesList where commissioningDate is less than UPDATED_COMMISSIONING_DATE
        defaultResidentalEstatesShouldBeFound("commissioningDate.lessThan=" + UPDATED_COMMISSIONING_DATE);
    }

    @Test
    @Transactional
    void getAllResidentalEstatesByCommissioningDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        residentalEstatesRepository.saveAndFlush(residentalEstates);

        // Get all the residentalEstatesList where commissioningDate is greater than DEFAULT_COMMISSIONING_DATE
        defaultResidentalEstatesShouldNotBeFound("commissioningDate.greaterThan=" + DEFAULT_COMMISSIONING_DATE);

        // Get all the residentalEstatesList where commissioningDate is greater than SMALLER_COMMISSIONING_DATE
        defaultResidentalEstatesShouldBeFound("commissioningDate.greaterThan=" + SMALLER_COMMISSIONING_DATE);
    }

    @Test
    @Transactional
    void getAllResidentalEstatesByNumberOfRoomsIsEqualToSomething() throws Exception {
        // Initialize the database
        residentalEstatesRepository.saveAndFlush(residentalEstates);

        // Get all the residentalEstatesList where numberOfRooms equals to DEFAULT_NUMBER_OF_ROOMS
        defaultResidentalEstatesShouldBeFound("numberOfRooms.equals=" + DEFAULT_NUMBER_OF_ROOMS);

        // Get all the residentalEstatesList where numberOfRooms equals to UPDATED_NUMBER_OF_ROOMS
        defaultResidentalEstatesShouldNotBeFound("numberOfRooms.equals=" + UPDATED_NUMBER_OF_ROOMS);
    }

    @Test
    @Transactional
    void getAllResidentalEstatesByNumberOfRoomsIsInShouldWork() throws Exception {
        // Initialize the database
        residentalEstatesRepository.saveAndFlush(residentalEstates);

        // Get all the residentalEstatesList where numberOfRooms in DEFAULT_NUMBER_OF_ROOMS or UPDATED_NUMBER_OF_ROOMS
        defaultResidentalEstatesShouldBeFound("numberOfRooms.in=" + DEFAULT_NUMBER_OF_ROOMS + "," + UPDATED_NUMBER_OF_ROOMS);

        // Get all the residentalEstatesList where numberOfRooms equals to UPDATED_NUMBER_OF_ROOMS
        defaultResidentalEstatesShouldNotBeFound("numberOfRooms.in=" + UPDATED_NUMBER_OF_ROOMS);
    }

    @Test
    @Transactional
    void getAllResidentalEstatesByNumberOfRoomsIsNullOrNotNull() throws Exception {
        // Initialize the database
        residentalEstatesRepository.saveAndFlush(residentalEstates);

        // Get all the residentalEstatesList where numberOfRooms is not null
        defaultResidentalEstatesShouldBeFound("numberOfRooms.specified=true");

        // Get all the residentalEstatesList where numberOfRooms is null
        defaultResidentalEstatesShouldNotBeFound("numberOfRooms.specified=false");
    }

    @Test
    @Transactional
    void getAllResidentalEstatesByNumberOfRoomsIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        residentalEstatesRepository.saveAndFlush(residentalEstates);

        // Get all the residentalEstatesList where numberOfRooms is greater than or equal to DEFAULT_NUMBER_OF_ROOMS
        defaultResidentalEstatesShouldBeFound("numberOfRooms.greaterThanOrEqual=" + DEFAULT_NUMBER_OF_ROOMS);

        // Get all the residentalEstatesList where numberOfRooms is greater than or equal to UPDATED_NUMBER_OF_ROOMS
        defaultResidentalEstatesShouldNotBeFound("numberOfRooms.greaterThanOrEqual=" + UPDATED_NUMBER_OF_ROOMS);
    }

    @Test
    @Transactional
    void getAllResidentalEstatesByNumberOfRoomsIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        residentalEstatesRepository.saveAndFlush(residentalEstates);

        // Get all the residentalEstatesList where numberOfRooms is less than or equal to DEFAULT_NUMBER_OF_ROOMS
        defaultResidentalEstatesShouldBeFound("numberOfRooms.lessThanOrEqual=" + DEFAULT_NUMBER_OF_ROOMS);

        // Get all the residentalEstatesList where numberOfRooms is less than or equal to SMALLER_NUMBER_OF_ROOMS
        defaultResidentalEstatesShouldNotBeFound("numberOfRooms.lessThanOrEqual=" + SMALLER_NUMBER_OF_ROOMS);
    }

    @Test
    @Transactional
    void getAllResidentalEstatesByNumberOfRoomsIsLessThanSomething() throws Exception {
        // Initialize the database
        residentalEstatesRepository.saveAndFlush(residentalEstates);

        // Get all the residentalEstatesList where numberOfRooms is less than DEFAULT_NUMBER_OF_ROOMS
        defaultResidentalEstatesShouldNotBeFound("numberOfRooms.lessThan=" + DEFAULT_NUMBER_OF_ROOMS);

        // Get all the residentalEstatesList where numberOfRooms is less than UPDATED_NUMBER_OF_ROOMS
        defaultResidentalEstatesShouldBeFound("numberOfRooms.lessThan=" + UPDATED_NUMBER_OF_ROOMS);
    }

    @Test
    @Transactional
    void getAllResidentalEstatesByNumberOfRoomsIsGreaterThanSomething() throws Exception {
        // Initialize the database
        residentalEstatesRepository.saveAndFlush(residentalEstates);

        // Get all the residentalEstatesList where numberOfRooms is greater than DEFAULT_NUMBER_OF_ROOMS
        defaultResidentalEstatesShouldNotBeFound("numberOfRooms.greaterThan=" + DEFAULT_NUMBER_OF_ROOMS);

        // Get all the residentalEstatesList where numberOfRooms is greater than SMALLER_NUMBER_OF_ROOMS
        defaultResidentalEstatesShouldBeFound("numberOfRooms.greaterThan=" + SMALLER_NUMBER_OF_ROOMS);
    }

    @Test
    @Transactional
    void getAllResidentalEstatesByFurnishTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        residentalEstatesRepository.saveAndFlush(residentalEstates);

        // Get all the residentalEstatesList where furnishType equals to DEFAULT_FURNISH_TYPE
        defaultResidentalEstatesShouldBeFound("furnishType.equals=" + DEFAULT_FURNISH_TYPE);

        // Get all the residentalEstatesList where furnishType equals to UPDATED_FURNISH_TYPE
        defaultResidentalEstatesShouldNotBeFound("furnishType.equals=" + UPDATED_FURNISH_TYPE);
    }

    @Test
    @Transactional
    void getAllResidentalEstatesByFurnishTypeIsInShouldWork() throws Exception {
        // Initialize the database
        residentalEstatesRepository.saveAndFlush(residentalEstates);

        // Get all the residentalEstatesList where furnishType in DEFAULT_FURNISH_TYPE or UPDATED_FURNISH_TYPE
        defaultResidentalEstatesShouldBeFound("furnishType.in=" + DEFAULT_FURNISH_TYPE + "," + UPDATED_FURNISH_TYPE);

        // Get all the residentalEstatesList where furnishType equals to UPDATED_FURNISH_TYPE
        defaultResidentalEstatesShouldNotBeFound("furnishType.in=" + UPDATED_FURNISH_TYPE);
    }

    @Test
    @Transactional
    void getAllResidentalEstatesByFurnishTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        residentalEstatesRepository.saveAndFlush(residentalEstates);

        // Get all the residentalEstatesList where furnishType is not null
        defaultResidentalEstatesShouldBeFound("furnishType.specified=true");

        // Get all the residentalEstatesList where furnishType is null
        defaultResidentalEstatesShouldNotBeFound("furnishType.specified=false");
    }

    @Test
    @Transactional
    void getAllResidentalEstatesByFurnishTypeContainsSomething() throws Exception {
        // Initialize the database
        residentalEstatesRepository.saveAndFlush(residentalEstates);

        // Get all the residentalEstatesList where furnishType contains DEFAULT_FURNISH_TYPE
        defaultResidentalEstatesShouldBeFound("furnishType.contains=" + DEFAULT_FURNISH_TYPE);

        // Get all the residentalEstatesList where furnishType contains UPDATED_FURNISH_TYPE
        defaultResidentalEstatesShouldNotBeFound("furnishType.contains=" + UPDATED_FURNISH_TYPE);
    }

    @Test
    @Transactional
    void getAllResidentalEstatesByFurnishTypeNotContainsSomething() throws Exception {
        // Initialize the database
        residentalEstatesRepository.saveAndFlush(residentalEstates);

        // Get all the residentalEstatesList where furnishType does not contain DEFAULT_FURNISH_TYPE
        defaultResidentalEstatesShouldNotBeFound("furnishType.doesNotContain=" + DEFAULT_FURNISH_TYPE);

        // Get all the residentalEstatesList where furnishType does not contain UPDATED_FURNISH_TYPE
        defaultResidentalEstatesShouldBeFound("furnishType.doesNotContain=" + UPDATED_FURNISH_TYPE);
    }

    @Test
    @Transactional
    void getAllResidentalEstatesByHasBalconyIsEqualToSomething() throws Exception {
        // Initialize the database
        residentalEstatesRepository.saveAndFlush(residentalEstates);

        // Get all the residentalEstatesList where hasBalcony equals to DEFAULT_HAS_BALCONY
        defaultResidentalEstatesShouldBeFound("hasBalcony.equals=" + DEFAULT_HAS_BALCONY);

        // Get all the residentalEstatesList where hasBalcony equals to UPDATED_HAS_BALCONY
        defaultResidentalEstatesShouldNotBeFound("hasBalcony.equals=" + UPDATED_HAS_BALCONY);
    }

    @Test
    @Transactional
    void getAllResidentalEstatesByHasBalconyIsInShouldWork() throws Exception {
        // Initialize the database
        residentalEstatesRepository.saveAndFlush(residentalEstates);

        // Get all the residentalEstatesList where hasBalcony in DEFAULT_HAS_BALCONY or UPDATED_HAS_BALCONY
        defaultResidentalEstatesShouldBeFound("hasBalcony.in=" + DEFAULT_HAS_BALCONY + "," + UPDATED_HAS_BALCONY);

        // Get all the residentalEstatesList where hasBalcony equals to UPDATED_HAS_BALCONY
        defaultResidentalEstatesShouldNotBeFound("hasBalcony.in=" + UPDATED_HAS_BALCONY);
    }

    @Test
    @Transactional
    void getAllResidentalEstatesByHasBalconyIsNullOrNotNull() throws Exception {
        // Initialize the database
        residentalEstatesRepository.saveAndFlush(residentalEstates);

        // Get all the residentalEstatesList where hasBalcony is not null
        defaultResidentalEstatesShouldBeFound("hasBalcony.specified=true");

        // Get all the residentalEstatesList where hasBalcony is null
        defaultResidentalEstatesShouldNotBeFound("hasBalcony.specified=false");
    }

    @Test
    @Transactional
    void getAllResidentalEstatesByBathroomTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        residentalEstatesRepository.saveAndFlush(residentalEstates);

        // Get all the residentalEstatesList where bathroomType equals to DEFAULT_BATHROOM_TYPE
        defaultResidentalEstatesShouldBeFound("bathroomType.equals=" + DEFAULT_BATHROOM_TYPE);

        // Get all the residentalEstatesList where bathroomType equals to UPDATED_BATHROOM_TYPE
        defaultResidentalEstatesShouldNotBeFound("bathroomType.equals=" + UPDATED_BATHROOM_TYPE);
    }

    @Test
    @Transactional
    void getAllResidentalEstatesByBathroomTypeIsInShouldWork() throws Exception {
        // Initialize the database
        residentalEstatesRepository.saveAndFlush(residentalEstates);

        // Get all the residentalEstatesList where bathroomType in DEFAULT_BATHROOM_TYPE or UPDATED_BATHROOM_TYPE
        defaultResidentalEstatesShouldBeFound("bathroomType.in=" + DEFAULT_BATHROOM_TYPE + "," + UPDATED_BATHROOM_TYPE);

        // Get all the residentalEstatesList where bathroomType equals to UPDATED_BATHROOM_TYPE
        defaultResidentalEstatesShouldNotBeFound("bathroomType.in=" + UPDATED_BATHROOM_TYPE);
    }

    @Test
    @Transactional
    void getAllResidentalEstatesByBathroomTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        residentalEstatesRepository.saveAndFlush(residentalEstates);

        // Get all the residentalEstatesList where bathroomType is not null
        defaultResidentalEstatesShouldBeFound("bathroomType.specified=true");

        // Get all the residentalEstatesList where bathroomType is null
        defaultResidentalEstatesShouldNotBeFound("bathroomType.specified=false");
    }

    @Test
    @Transactional
    void getAllResidentalEstatesByBathroomTypeContainsSomething() throws Exception {
        // Initialize the database
        residentalEstatesRepository.saveAndFlush(residentalEstates);

        // Get all the residentalEstatesList where bathroomType contains DEFAULT_BATHROOM_TYPE
        defaultResidentalEstatesShouldBeFound("bathroomType.contains=" + DEFAULT_BATHROOM_TYPE);

        // Get all the residentalEstatesList where bathroomType contains UPDATED_BATHROOM_TYPE
        defaultResidentalEstatesShouldNotBeFound("bathroomType.contains=" + UPDATED_BATHROOM_TYPE);
    }

    @Test
    @Transactional
    void getAllResidentalEstatesByBathroomTypeNotContainsSomething() throws Exception {
        // Initialize the database
        residentalEstatesRepository.saveAndFlush(residentalEstates);

        // Get all the residentalEstatesList where bathroomType does not contain DEFAULT_BATHROOM_TYPE
        defaultResidentalEstatesShouldNotBeFound("bathroomType.doesNotContain=" + DEFAULT_BATHROOM_TYPE);

        // Get all the residentalEstatesList where bathroomType does not contain UPDATED_BATHROOM_TYPE
        defaultResidentalEstatesShouldBeFound("bathroomType.doesNotContain=" + UPDATED_BATHROOM_TYPE);
    }

    @Test
    @Transactional
    void getAllResidentalEstatesByCeilingHeightIsEqualToSomething() throws Exception {
        // Initialize the database
        residentalEstatesRepository.saveAndFlush(residentalEstates);

        // Get all the residentalEstatesList where ceilingHeight equals to DEFAULT_CEILING_HEIGHT
        defaultResidentalEstatesShouldBeFound("ceilingHeight.equals=" + DEFAULT_CEILING_HEIGHT);

        // Get all the residentalEstatesList where ceilingHeight equals to UPDATED_CEILING_HEIGHT
        defaultResidentalEstatesShouldNotBeFound("ceilingHeight.equals=" + UPDATED_CEILING_HEIGHT);
    }

    @Test
    @Transactional
    void getAllResidentalEstatesByCeilingHeightIsInShouldWork() throws Exception {
        // Initialize the database
        residentalEstatesRepository.saveAndFlush(residentalEstates);

        // Get all the residentalEstatesList where ceilingHeight in DEFAULT_CEILING_HEIGHT or UPDATED_CEILING_HEIGHT
        defaultResidentalEstatesShouldBeFound("ceilingHeight.in=" + DEFAULT_CEILING_HEIGHT + "," + UPDATED_CEILING_HEIGHT);

        // Get all the residentalEstatesList where ceilingHeight equals to UPDATED_CEILING_HEIGHT
        defaultResidentalEstatesShouldNotBeFound("ceilingHeight.in=" + UPDATED_CEILING_HEIGHT);
    }

    @Test
    @Transactional
    void getAllResidentalEstatesByCeilingHeightIsNullOrNotNull() throws Exception {
        // Initialize the database
        residentalEstatesRepository.saveAndFlush(residentalEstates);

        // Get all the residentalEstatesList where ceilingHeight is not null
        defaultResidentalEstatesShouldBeFound("ceilingHeight.specified=true");

        // Get all the residentalEstatesList where ceilingHeight is null
        defaultResidentalEstatesShouldNotBeFound("ceilingHeight.specified=false");
    }

    @Test
    @Transactional
    void getAllResidentalEstatesByCeilingHeightIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        residentalEstatesRepository.saveAndFlush(residentalEstates);

        // Get all the residentalEstatesList where ceilingHeight is greater than or equal to DEFAULT_CEILING_HEIGHT
        defaultResidentalEstatesShouldBeFound("ceilingHeight.greaterThanOrEqual=" + DEFAULT_CEILING_HEIGHT);

        // Get all the residentalEstatesList where ceilingHeight is greater than or equal to UPDATED_CEILING_HEIGHT
        defaultResidentalEstatesShouldNotBeFound("ceilingHeight.greaterThanOrEqual=" + UPDATED_CEILING_HEIGHT);
    }

    @Test
    @Transactional
    void getAllResidentalEstatesByCeilingHeightIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        residentalEstatesRepository.saveAndFlush(residentalEstates);

        // Get all the residentalEstatesList where ceilingHeight is less than or equal to DEFAULT_CEILING_HEIGHT
        defaultResidentalEstatesShouldBeFound("ceilingHeight.lessThanOrEqual=" + DEFAULT_CEILING_HEIGHT);

        // Get all the residentalEstatesList where ceilingHeight is less than or equal to SMALLER_CEILING_HEIGHT
        defaultResidentalEstatesShouldNotBeFound("ceilingHeight.lessThanOrEqual=" + SMALLER_CEILING_HEIGHT);
    }

    @Test
    @Transactional
    void getAllResidentalEstatesByCeilingHeightIsLessThanSomething() throws Exception {
        // Initialize the database
        residentalEstatesRepository.saveAndFlush(residentalEstates);

        // Get all the residentalEstatesList where ceilingHeight is less than DEFAULT_CEILING_HEIGHT
        defaultResidentalEstatesShouldNotBeFound("ceilingHeight.lessThan=" + DEFAULT_CEILING_HEIGHT);

        // Get all the residentalEstatesList where ceilingHeight is less than UPDATED_CEILING_HEIGHT
        defaultResidentalEstatesShouldBeFound("ceilingHeight.lessThan=" + UPDATED_CEILING_HEIGHT);
    }

    @Test
    @Transactional
    void getAllResidentalEstatesByCeilingHeightIsGreaterThanSomething() throws Exception {
        // Initialize the database
        residentalEstatesRepository.saveAndFlush(residentalEstates);

        // Get all the residentalEstatesList where ceilingHeight is greater than DEFAULT_CEILING_HEIGHT
        defaultResidentalEstatesShouldNotBeFound("ceilingHeight.greaterThan=" + DEFAULT_CEILING_HEIGHT);

        // Get all the residentalEstatesList where ceilingHeight is greater than SMALLER_CEILING_HEIGHT
        defaultResidentalEstatesShouldBeFound("ceilingHeight.greaterThan=" + SMALLER_CEILING_HEIGHT);
    }

    @Test
    @Transactional
    void getAllResidentalEstatesByPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        residentalEstatesRepository.saveAndFlush(residentalEstates);

        // Get all the residentalEstatesList where price equals to DEFAULT_PRICE
        defaultResidentalEstatesShouldBeFound("price.equals=" + DEFAULT_PRICE);

        // Get all the residentalEstatesList where price equals to UPDATED_PRICE
        defaultResidentalEstatesShouldNotBeFound("price.equals=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    void getAllResidentalEstatesByPriceIsInShouldWork() throws Exception {
        // Initialize the database
        residentalEstatesRepository.saveAndFlush(residentalEstates);

        // Get all the residentalEstatesList where price in DEFAULT_PRICE or UPDATED_PRICE
        defaultResidentalEstatesShouldBeFound("price.in=" + DEFAULT_PRICE + "," + UPDATED_PRICE);

        // Get all the residentalEstatesList where price equals to UPDATED_PRICE
        defaultResidentalEstatesShouldNotBeFound("price.in=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    void getAllResidentalEstatesByPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        residentalEstatesRepository.saveAndFlush(residentalEstates);

        // Get all the residentalEstatesList where price is not null
        defaultResidentalEstatesShouldBeFound("price.specified=true");

        // Get all the residentalEstatesList where price is null
        defaultResidentalEstatesShouldNotBeFound("price.specified=false");
    }

    @Test
    @Transactional
    void getAllResidentalEstatesByPriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        residentalEstatesRepository.saveAndFlush(residentalEstates);

        // Get all the residentalEstatesList where price is greater than or equal to DEFAULT_PRICE
        defaultResidentalEstatesShouldBeFound("price.greaterThanOrEqual=" + DEFAULT_PRICE);

        // Get all the residentalEstatesList where price is greater than or equal to UPDATED_PRICE
        defaultResidentalEstatesShouldNotBeFound("price.greaterThanOrEqual=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    void getAllResidentalEstatesByPriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        residentalEstatesRepository.saveAndFlush(residentalEstates);

        // Get all the residentalEstatesList where price is less than or equal to DEFAULT_PRICE
        defaultResidentalEstatesShouldBeFound("price.lessThanOrEqual=" + DEFAULT_PRICE);

        // Get all the residentalEstatesList where price is less than or equal to SMALLER_PRICE
        defaultResidentalEstatesShouldNotBeFound("price.lessThanOrEqual=" + SMALLER_PRICE);
    }

    @Test
    @Transactional
    void getAllResidentalEstatesByPriceIsLessThanSomething() throws Exception {
        // Initialize the database
        residentalEstatesRepository.saveAndFlush(residentalEstates);

        // Get all the residentalEstatesList where price is less than DEFAULT_PRICE
        defaultResidentalEstatesShouldNotBeFound("price.lessThan=" + DEFAULT_PRICE);

        // Get all the residentalEstatesList where price is less than UPDATED_PRICE
        defaultResidentalEstatesShouldBeFound("price.lessThan=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    void getAllResidentalEstatesByPriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        residentalEstatesRepository.saveAndFlush(residentalEstates);

        // Get all the residentalEstatesList where price is greater than DEFAULT_PRICE
        defaultResidentalEstatesShouldNotBeFound("price.greaterThan=" + DEFAULT_PRICE);

        // Get all the residentalEstatesList where price is greater than SMALLER_PRICE
        defaultResidentalEstatesShouldBeFound("price.greaterThan=" + SMALLER_PRICE);
    }

    @Test
    @Transactional
    void getAllResidentalEstatesByAddressIdIsEqualToSomething() throws Exception {
        Addresses addressId;
        if (TestUtil.findAll(em, Addresses.class).isEmpty()) {
            residentalEstatesRepository.saveAndFlush(residentalEstates);
            addressId = AddressesResourceIT.createEntity(em);
        } else {
            addressId = TestUtil.findAll(em, Addresses.class).get(0);
        }
        em.persist(addressId);
        em.flush();
        residentalEstates.setAddressId(addressId);
        residentalEstatesRepository.saveAndFlush(residentalEstates);
        Long addressIdId = addressId.getId();

        // Get all the residentalEstatesList where addressId equals to addressIdId
        defaultResidentalEstatesShouldBeFound("addressIdId.equals=" + addressIdId);

        // Get all the residentalEstatesList where addressId equals to (addressIdId + 1)
        defaultResidentalEstatesShouldNotBeFound("addressIdId.equals=" + (addressIdId + 1));
    }

    @Test
    @Transactional
    void getAllResidentalEstatesByTypeOfResidentalEstateIdIsEqualToSomething() throws Exception {
        TypesOfResidentalEstate typeOfResidentalEstateId;
        if (TestUtil.findAll(em, TypesOfResidentalEstate.class).isEmpty()) {
            residentalEstatesRepository.saveAndFlush(residentalEstates);
            typeOfResidentalEstateId = TypesOfResidentalEstateResourceIT.createEntity(em);
        } else {
            typeOfResidentalEstateId = TestUtil.findAll(em, TypesOfResidentalEstate.class).get(0);
        }
        em.persist(typeOfResidentalEstateId);
        em.flush();
        residentalEstates.setTypeOfResidentalEstateId(typeOfResidentalEstateId);
        residentalEstatesRepository.saveAndFlush(residentalEstates);
        Long typeOfResidentalEstateIdId = typeOfResidentalEstateId.getId();

        // Get all the residentalEstatesList where typeOfResidentalEstateId equals to typeOfResidentalEstateIdId
        defaultResidentalEstatesShouldBeFound("typeOfResidentalEstateIdId.equals=" + typeOfResidentalEstateIdId);

        // Get all the residentalEstatesList where typeOfResidentalEstateId equals to (typeOfResidentalEstateIdId + 1)
        defaultResidentalEstatesShouldNotBeFound("typeOfResidentalEstateIdId.equals=" + (typeOfResidentalEstateIdId + 1));
    }

    @Test
    @Transactional
    void getAllResidentalEstatesByStatusOfResidentalEstateIdIsEqualToSomething() throws Exception {
        StatusesOfResidentalEstate statusOfResidentalEstateId;
        if (TestUtil.findAll(em, StatusesOfResidentalEstate.class).isEmpty()) {
            residentalEstatesRepository.saveAndFlush(residentalEstates);
            statusOfResidentalEstateId = StatusesOfResidentalEstateResourceIT.createEntity(em);
        } else {
            statusOfResidentalEstateId = TestUtil.findAll(em, StatusesOfResidentalEstate.class).get(0);
        }
        em.persist(statusOfResidentalEstateId);
        em.flush();
        residentalEstates.setStatusOfResidentalEstateId(statusOfResidentalEstateId);
        residentalEstatesRepository.saveAndFlush(residentalEstates);
        Long statusOfResidentalEstateIdId = statusOfResidentalEstateId.getId();

        // Get all the residentalEstatesList where statusOfResidentalEstateId equals to statusOfResidentalEstateIdId
        defaultResidentalEstatesShouldBeFound("statusOfResidentalEstateIdId.equals=" + statusOfResidentalEstateIdId);

        // Get all the residentalEstatesList where statusOfResidentalEstateId equals to (statusOfResidentalEstateIdId + 1)
        defaultResidentalEstatesShouldNotBeFound("statusOfResidentalEstateIdId.equals=" + (statusOfResidentalEstateIdId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultResidentalEstatesShouldBeFound(String filter) throws Exception {
        restResidentalEstatesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(residentalEstates.getId().intValue())))
            .andExpect(jsonPath("$.[*].livingSpace").value(hasItem(DEFAULT_LIVING_SPACE.doubleValue())))
            .andExpect(jsonPath("$.[*].cadastralNumber").value(hasItem(DEFAULT_CADASTRAL_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].commissioningDate").value(hasItem(DEFAULT_COMMISSIONING_DATE.toString())))
            .andExpect(jsonPath("$.[*].numberOfRooms").value(hasItem(DEFAULT_NUMBER_OF_ROOMS)))
            .andExpect(jsonPath("$.[*].furnishType").value(hasItem(DEFAULT_FURNISH_TYPE)))
            .andExpect(jsonPath("$.[*].hasBalcony").value(hasItem(DEFAULT_HAS_BALCONY.booleanValue())))
            .andExpect(jsonPath("$.[*].bathroomType").value(hasItem(DEFAULT_BATHROOM_TYPE)))
            .andExpect(jsonPath("$.[*].ceilingHeight").value(hasItem(DEFAULT_CEILING_HEIGHT.doubleValue())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(sameNumber(DEFAULT_PRICE))));

        // Check, that the count call also returns 1
        restResidentalEstatesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultResidentalEstatesShouldNotBeFound(String filter) throws Exception {
        restResidentalEstatesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restResidentalEstatesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingResidentalEstates() throws Exception {
        // Get the residentalEstates
        restResidentalEstatesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingResidentalEstates() throws Exception {
        // Initialize the database
        residentalEstatesRepository.saveAndFlush(residentalEstates);

        int databaseSizeBeforeUpdate = residentalEstatesRepository.findAll().size();

        // Update the residentalEstates
        ResidentalEstates updatedResidentalEstates = residentalEstatesRepository.findById(residentalEstates.getId()).get();
        // Disconnect from session so that the updates on updatedResidentalEstates are not directly saved in db
        em.detach(updatedResidentalEstates);
        updatedResidentalEstates
            .livingSpace(UPDATED_LIVING_SPACE)
            .cadastralNumber(UPDATED_CADASTRAL_NUMBER)
            .commissioningDate(UPDATED_COMMISSIONING_DATE)
            .numberOfRooms(UPDATED_NUMBER_OF_ROOMS)
            .furnishType(UPDATED_FURNISH_TYPE)
            .hasBalcony(UPDATED_HAS_BALCONY)
            .bathroomType(UPDATED_BATHROOM_TYPE)
            .ceilingHeight(UPDATED_CEILING_HEIGHT)
            .price(UPDATED_PRICE);
        ResidentalEstatesDTO residentalEstatesDTO = residentalEstatesMapper.toDto(updatedResidentalEstates);

        restResidentalEstatesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, residentalEstatesDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(residentalEstatesDTO))
            )
            .andExpect(status().isOk());

        // Validate the ResidentalEstates in the database
        List<ResidentalEstates> residentalEstatesList = residentalEstatesRepository.findAll();
        assertThat(residentalEstatesList).hasSize(databaseSizeBeforeUpdate);
        ResidentalEstates testResidentalEstates = residentalEstatesList.get(residentalEstatesList.size() - 1);
        assertThat(testResidentalEstates.getLivingSpace()).isEqualTo(UPDATED_LIVING_SPACE);
        assertThat(testResidentalEstates.getCadastralNumber()).isEqualTo(UPDATED_CADASTRAL_NUMBER);
        assertThat(testResidentalEstates.getCommissioningDate()).isEqualTo(UPDATED_COMMISSIONING_DATE);
        assertThat(testResidentalEstates.getNumberOfRooms()).isEqualTo(UPDATED_NUMBER_OF_ROOMS);
        assertThat(testResidentalEstates.getFurnishType()).isEqualTo(UPDATED_FURNISH_TYPE);
        assertThat(testResidentalEstates.getHasBalcony()).isEqualTo(UPDATED_HAS_BALCONY);
        assertThat(testResidentalEstates.getBathroomType()).isEqualTo(UPDATED_BATHROOM_TYPE);
        assertThat(testResidentalEstates.getCeilingHeight()).isEqualTo(UPDATED_CEILING_HEIGHT);
        assertThat(testResidentalEstates.getPrice()).isEqualByComparingTo(UPDATED_PRICE);
    }

    @Test
    @Transactional
    void putNonExistingResidentalEstates() throws Exception {
        int databaseSizeBeforeUpdate = residentalEstatesRepository.findAll().size();
        residentalEstates.setId(count.incrementAndGet());

        // Create the ResidentalEstates
        ResidentalEstatesDTO residentalEstatesDTO = residentalEstatesMapper.toDto(residentalEstates);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restResidentalEstatesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, residentalEstatesDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(residentalEstatesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ResidentalEstates in the database
        List<ResidentalEstates> residentalEstatesList = residentalEstatesRepository.findAll();
        assertThat(residentalEstatesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchResidentalEstates() throws Exception {
        int databaseSizeBeforeUpdate = residentalEstatesRepository.findAll().size();
        residentalEstates.setId(count.incrementAndGet());

        // Create the ResidentalEstates
        ResidentalEstatesDTO residentalEstatesDTO = residentalEstatesMapper.toDto(residentalEstates);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResidentalEstatesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(residentalEstatesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ResidentalEstates in the database
        List<ResidentalEstates> residentalEstatesList = residentalEstatesRepository.findAll();
        assertThat(residentalEstatesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamResidentalEstates() throws Exception {
        int databaseSizeBeforeUpdate = residentalEstatesRepository.findAll().size();
        residentalEstates.setId(count.incrementAndGet());

        // Create the ResidentalEstates
        ResidentalEstatesDTO residentalEstatesDTO = residentalEstatesMapper.toDto(residentalEstates);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResidentalEstatesMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(residentalEstatesDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ResidentalEstates in the database
        List<ResidentalEstates> residentalEstatesList = residentalEstatesRepository.findAll();
        assertThat(residentalEstatesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateResidentalEstatesWithPatch() throws Exception {
        // Initialize the database
        residentalEstatesRepository.saveAndFlush(residentalEstates);

        int databaseSizeBeforeUpdate = residentalEstatesRepository.findAll().size();

        // Update the residentalEstates using partial update
        ResidentalEstates partialUpdatedResidentalEstates = new ResidentalEstates();
        partialUpdatedResidentalEstates.setId(residentalEstates.getId());

        partialUpdatedResidentalEstates
            .livingSpace(UPDATED_LIVING_SPACE)
            .cadastralNumber(UPDATED_CADASTRAL_NUMBER)
            .numberOfRooms(UPDATED_NUMBER_OF_ROOMS)
            .bathroomType(UPDATED_BATHROOM_TYPE)
            .price(UPDATED_PRICE);

        restResidentalEstatesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedResidentalEstates.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedResidentalEstates))
            )
            .andExpect(status().isOk());

        // Validate the ResidentalEstates in the database
        List<ResidentalEstates> residentalEstatesList = residentalEstatesRepository.findAll();
        assertThat(residentalEstatesList).hasSize(databaseSizeBeforeUpdate);
        ResidentalEstates testResidentalEstates = residentalEstatesList.get(residentalEstatesList.size() - 1);
        assertThat(testResidentalEstates.getLivingSpace()).isEqualTo(UPDATED_LIVING_SPACE);
        assertThat(testResidentalEstates.getCadastralNumber()).isEqualTo(UPDATED_CADASTRAL_NUMBER);
        assertThat(testResidentalEstates.getCommissioningDate()).isEqualTo(DEFAULT_COMMISSIONING_DATE);
        assertThat(testResidentalEstates.getNumberOfRooms()).isEqualTo(UPDATED_NUMBER_OF_ROOMS);
        assertThat(testResidentalEstates.getFurnishType()).isEqualTo(DEFAULT_FURNISH_TYPE);
        assertThat(testResidentalEstates.getHasBalcony()).isEqualTo(DEFAULT_HAS_BALCONY);
        assertThat(testResidentalEstates.getBathroomType()).isEqualTo(UPDATED_BATHROOM_TYPE);
        assertThat(testResidentalEstates.getCeilingHeight()).isEqualTo(DEFAULT_CEILING_HEIGHT);
        assertThat(testResidentalEstates.getPrice()).isEqualByComparingTo(UPDATED_PRICE);
    }

    @Test
    @Transactional
    void fullUpdateResidentalEstatesWithPatch() throws Exception {
        // Initialize the database
        residentalEstatesRepository.saveAndFlush(residentalEstates);

        int databaseSizeBeforeUpdate = residentalEstatesRepository.findAll().size();

        // Update the residentalEstates using partial update
        ResidentalEstates partialUpdatedResidentalEstates = new ResidentalEstates();
        partialUpdatedResidentalEstates.setId(residentalEstates.getId());

        partialUpdatedResidentalEstates
            .livingSpace(UPDATED_LIVING_SPACE)
            .cadastralNumber(UPDATED_CADASTRAL_NUMBER)
            .commissioningDate(UPDATED_COMMISSIONING_DATE)
            .numberOfRooms(UPDATED_NUMBER_OF_ROOMS)
            .furnishType(UPDATED_FURNISH_TYPE)
            .hasBalcony(UPDATED_HAS_BALCONY)
            .bathroomType(UPDATED_BATHROOM_TYPE)
            .ceilingHeight(UPDATED_CEILING_HEIGHT)
            .price(UPDATED_PRICE);

        restResidentalEstatesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedResidentalEstates.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedResidentalEstates))
            )
            .andExpect(status().isOk());

        // Validate the ResidentalEstates in the database
        List<ResidentalEstates> residentalEstatesList = residentalEstatesRepository.findAll();
        assertThat(residentalEstatesList).hasSize(databaseSizeBeforeUpdate);
        ResidentalEstates testResidentalEstates = residentalEstatesList.get(residentalEstatesList.size() - 1);
        assertThat(testResidentalEstates.getLivingSpace()).isEqualTo(UPDATED_LIVING_SPACE);
        assertThat(testResidentalEstates.getCadastralNumber()).isEqualTo(UPDATED_CADASTRAL_NUMBER);
        assertThat(testResidentalEstates.getCommissioningDate()).isEqualTo(UPDATED_COMMISSIONING_DATE);
        assertThat(testResidentalEstates.getNumberOfRooms()).isEqualTo(UPDATED_NUMBER_OF_ROOMS);
        assertThat(testResidentalEstates.getFurnishType()).isEqualTo(UPDATED_FURNISH_TYPE);
        assertThat(testResidentalEstates.getHasBalcony()).isEqualTo(UPDATED_HAS_BALCONY);
        assertThat(testResidentalEstates.getBathroomType()).isEqualTo(UPDATED_BATHROOM_TYPE);
        assertThat(testResidentalEstates.getCeilingHeight()).isEqualTo(UPDATED_CEILING_HEIGHT);
        assertThat(testResidentalEstates.getPrice()).isEqualByComparingTo(UPDATED_PRICE);
    }

    @Test
    @Transactional
    void patchNonExistingResidentalEstates() throws Exception {
        int databaseSizeBeforeUpdate = residentalEstatesRepository.findAll().size();
        residentalEstates.setId(count.incrementAndGet());

        // Create the ResidentalEstates
        ResidentalEstatesDTO residentalEstatesDTO = residentalEstatesMapper.toDto(residentalEstates);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restResidentalEstatesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, residentalEstatesDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(residentalEstatesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ResidentalEstates in the database
        List<ResidentalEstates> residentalEstatesList = residentalEstatesRepository.findAll();
        assertThat(residentalEstatesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchResidentalEstates() throws Exception {
        int databaseSizeBeforeUpdate = residentalEstatesRepository.findAll().size();
        residentalEstates.setId(count.incrementAndGet());

        // Create the ResidentalEstates
        ResidentalEstatesDTO residentalEstatesDTO = residentalEstatesMapper.toDto(residentalEstates);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResidentalEstatesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(residentalEstatesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ResidentalEstates in the database
        List<ResidentalEstates> residentalEstatesList = residentalEstatesRepository.findAll();
        assertThat(residentalEstatesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamResidentalEstates() throws Exception {
        int databaseSizeBeforeUpdate = residentalEstatesRepository.findAll().size();
        residentalEstates.setId(count.incrementAndGet());

        // Create the ResidentalEstates
        ResidentalEstatesDTO residentalEstatesDTO = residentalEstatesMapper.toDto(residentalEstates);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResidentalEstatesMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(residentalEstatesDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ResidentalEstates in the database
        List<ResidentalEstates> residentalEstatesList = residentalEstatesRepository.findAll();
        assertThat(residentalEstatesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteResidentalEstates() throws Exception {
        // Initialize the database
        residentalEstatesRepository.saveAndFlush(residentalEstates);

        int databaseSizeBeforeDelete = residentalEstatesRepository.findAll().size();

        // Delete the residentalEstates
        restResidentalEstatesMockMvc
            .perform(delete(ENTITY_API_URL_ID, residentalEstates.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ResidentalEstates> residentalEstatesList = residentalEstatesRepository.findAll();
        assertThat(residentalEstatesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
