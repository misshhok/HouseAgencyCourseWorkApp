package com.bsu.app.web.rest;

import static com.bsu.app.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.bsu.app.IntegrationTest;
import com.bsu.app.domain.Addresses;
import com.bsu.app.domain.BuildingTypeOfNonResidentalEstate;
import com.bsu.app.domain.NonResidentalEstates;
import com.bsu.app.domain.PurposesOfNonResidentalEstate;
import com.bsu.app.repository.NonResidentalEstatesRepository;
import com.bsu.app.service.criteria.NonResidentalEstatesCriteria;
import com.bsu.app.service.dto.NonResidentalEstatesDTO;
import com.bsu.app.service.mapper.NonResidentalEstatesMapper;
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
 * Integration tests for the {@link NonResidentalEstatesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class NonResidentalEstatesResourceIT {

    private static final BigDecimal DEFAULT_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRICE = new BigDecimal(2);
    private static final BigDecimal SMALLER_PRICE = new BigDecimal(1 - 1);

    private static final LocalDate DEFAULT_COMMISSIONING_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_COMMISSIONING_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_COMMISSIONING_DATE = LocalDate.ofEpochDay(-1L);

    private static final Long DEFAULT_CADASTRAL_NUMBER = 1L;
    private static final Long UPDATED_CADASTRAL_NUMBER = 2L;
    private static final Long SMALLER_CADASTRAL_NUMBER = 1L - 1L;

    private static final Double DEFAULT_TOTAL_SPACE = 1D;
    private static final Double UPDATED_TOTAL_SPACE = 2D;
    private static final Double SMALLER_TOTAL_SPACE = 1D - 1D;

    private static final String ENTITY_API_URL = "/api/non-residental-estates";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private NonResidentalEstatesRepository nonResidentalEstatesRepository;

    @Autowired
    private NonResidentalEstatesMapper nonResidentalEstatesMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNonResidentalEstatesMockMvc;

    private NonResidentalEstates nonResidentalEstates;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NonResidentalEstates createEntity(EntityManager em) {
        NonResidentalEstates nonResidentalEstates = new NonResidentalEstates()
            .price(DEFAULT_PRICE)
            .commissioningDate(DEFAULT_COMMISSIONING_DATE)
            .cadastralNumber(DEFAULT_CADASTRAL_NUMBER)
            .totalSpace(DEFAULT_TOTAL_SPACE);
        return nonResidentalEstates;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NonResidentalEstates createUpdatedEntity(EntityManager em) {
        NonResidentalEstates nonResidentalEstates = new NonResidentalEstates()
            .price(UPDATED_PRICE)
            .commissioningDate(UPDATED_COMMISSIONING_DATE)
            .cadastralNumber(UPDATED_CADASTRAL_NUMBER)
            .totalSpace(UPDATED_TOTAL_SPACE);
        return nonResidentalEstates;
    }

    @BeforeEach
    public void initTest() {
        nonResidentalEstates = createEntity(em);
    }

    @Test
    @Transactional
    void createNonResidentalEstates() throws Exception {
        int databaseSizeBeforeCreate = nonResidentalEstatesRepository.findAll().size();
        // Create the NonResidentalEstates
        NonResidentalEstatesDTO nonResidentalEstatesDTO = nonResidentalEstatesMapper.toDto(nonResidentalEstates);
        restNonResidentalEstatesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(nonResidentalEstatesDTO))
            )
            .andExpect(status().isCreated());

        // Validate the NonResidentalEstates in the database
        List<NonResidentalEstates> nonResidentalEstatesList = nonResidentalEstatesRepository.findAll();
        assertThat(nonResidentalEstatesList).hasSize(databaseSizeBeforeCreate + 1);
        NonResidentalEstates testNonResidentalEstates = nonResidentalEstatesList.get(nonResidentalEstatesList.size() - 1);
        assertThat(testNonResidentalEstates.getPrice()).isEqualByComparingTo(DEFAULT_PRICE);
        assertThat(testNonResidentalEstates.getCommissioningDate()).isEqualTo(DEFAULT_COMMISSIONING_DATE);
        assertThat(testNonResidentalEstates.getCadastralNumber()).isEqualTo(DEFAULT_CADASTRAL_NUMBER);
        assertThat(testNonResidentalEstates.getTotalSpace()).isEqualTo(DEFAULT_TOTAL_SPACE);
    }

    @Test
    @Transactional
    void createNonResidentalEstatesWithExistingId() throws Exception {
        // Create the NonResidentalEstates with an existing ID
        nonResidentalEstates.setId(1L);
        NonResidentalEstatesDTO nonResidentalEstatesDTO = nonResidentalEstatesMapper.toDto(nonResidentalEstates);

        int databaseSizeBeforeCreate = nonResidentalEstatesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restNonResidentalEstatesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(nonResidentalEstatesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NonResidentalEstates in the database
        List<NonResidentalEstates> nonResidentalEstatesList = nonResidentalEstatesRepository.findAll();
        assertThat(nonResidentalEstatesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllNonResidentalEstates() throws Exception {
        // Initialize the database
        nonResidentalEstatesRepository.saveAndFlush(nonResidentalEstates);

        // Get all the nonResidentalEstatesList
        restNonResidentalEstatesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nonResidentalEstates.getId().intValue())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(sameNumber(DEFAULT_PRICE))))
            .andExpect(jsonPath("$.[*].commissioningDate").value(hasItem(DEFAULT_COMMISSIONING_DATE.toString())))
            .andExpect(jsonPath("$.[*].cadastralNumber").value(hasItem(DEFAULT_CADASTRAL_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].totalSpace").value(hasItem(DEFAULT_TOTAL_SPACE.doubleValue())));
    }

    @Test
    @Transactional
    void getNonResidentalEstates() throws Exception {
        // Initialize the database
        nonResidentalEstatesRepository.saveAndFlush(nonResidentalEstates);

        // Get the nonResidentalEstates
        restNonResidentalEstatesMockMvc
            .perform(get(ENTITY_API_URL_ID, nonResidentalEstates.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(nonResidentalEstates.getId().intValue()))
            .andExpect(jsonPath("$.price").value(sameNumber(DEFAULT_PRICE)))
            .andExpect(jsonPath("$.commissioningDate").value(DEFAULT_COMMISSIONING_DATE.toString()))
            .andExpect(jsonPath("$.cadastralNumber").value(DEFAULT_CADASTRAL_NUMBER.intValue()))
            .andExpect(jsonPath("$.totalSpace").value(DEFAULT_TOTAL_SPACE.doubleValue()));
    }

    @Test
    @Transactional
    void getNonResidentalEstatesByIdFiltering() throws Exception {
        // Initialize the database
        nonResidentalEstatesRepository.saveAndFlush(nonResidentalEstates);

        Long id = nonResidentalEstates.getId();

        defaultNonResidentalEstatesShouldBeFound("id.equals=" + id);
        defaultNonResidentalEstatesShouldNotBeFound("id.notEquals=" + id);

        defaultNonResidentalEstatesShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultNonResidentalEstatesShouldNotBeFound("id.greaterThan=" + id);

        defaultNonResidentalEstatesShouldBeFound("id.lessThanOrEqual=" + id);
        defaultNonResidentalEstatesShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllNonResidentalEstatesByPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        nonResidentalEstatesRepository.saveAndFlush(nonResidentalEstates);

        // Get all the nonResidentalEstatesList where price equals to DEFAULT_PRICE
        defaultNonResidentalEstatesShouldBeFound("price.equals=" + DEFAULT_PRICE);

        // Get all the nonResidentalEstatesList where price equals to UPDATED_PRICE
        defaultNonResidentalEstatesShouldNotBeFound("price.equals=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    void getAllNonResidentalEstatesByPriceIsInShouldWork() throws Exception {
        // Initialize the database
        nonResidentalEstatesRepository.saveAndFlush(nonResidentalEstates);

        // Get all the nonResidentalEstatesList where price in DEFAULT_PRICE or UPDATED_PRICE
        defaultNonResidentalEstatesShouldBeFound("price.in=" + DEFAULT_PRICE + "," + UPDATED_PRICE);

        // Get all the nonResidentalEstatesList where price equals to UPDATED_PRICE
        defaultNonResidentalEstatesShouldNotBeFound("price.in=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    void getAllNonResidentalEstatesByPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        nonResidentalEstatesRepository.saveAndFlush(nonResidentalEstates);

        // Get all the nonResidentalEstatesList where price is not null
        defaultNonResidentalEstatesShouldBeFound("price.specified=true");

        // Get all the nonResidentalEstatesList where price is null
        defaultNonResidentalEstatesShouldNotBeFound("price.specified=false");
    }

    @Test
    @Transactional
    void getAllNonResidentalEstatesByPriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        nonResidentalEstatesRepository.saveAndFlush(nonResidentalEstates);

        // Get all the nonResidentalEstatesList where price is greater than or equal to DEFAULT_PRICE
        defaultNonResidentalEstatesShouldBeFound("price.greaterThanOrEqual=" + DEFAULT_PRICE);

        // Get all the nonResidentalEstatesList where price is greater than or equal to UPDATED_PRICE
        defaultNonResidentalEstatesShouldNotBeFound("price.greaterThanOrEqual=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    void getAllNonResidentalEstatesByPriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        nonResidentalEstatesRepository.saveAndFlush(nonResidentalEstates);

        // Get all the nonResidentalEstatesList where price is less than or equal to DEFAULT_PRICE
        defaultNonResidentalEstatesShouldBeFound("price.lessThanOrEqual=" + DEFAULT_PRICE);

        // Get all the nonResidentalEstatesList where price is less than or equal to SMALLER_PRICE
        defaultNonResidentalEstatesShouldNotBeFound("price.lessThanOrEqual=" + SMALLER_PRICE);
    }

    @Test
    @Transactional
    void getAllNonResidentalEstatesByPriceIsLessThanSomething() throws Exception {
        // Initialize the database
        nonResidentalEstatesRepository.saveAndFlush(nonResidentalEstates);

        // Get all the nonResidentalEstatesList where price is less than DEFAULT_PRICE
        defaultNonResidentalEstatesShouldNotBeFound("price.lessThan=" + DEFAULT_PRICE);

        // Get all the nonResidentalEstatesList where price is less than UPDATED_PRICE
        defaultNonResidentalEstatesShouldBeFound("price.lessThan=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    void getAllNonResidentalEstatesByPriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        nonResidentalEstatesRepository.saveAndFlush(nonResidentalEstates);

        // Get all the nonResidentalEstatesList where price is greater than DEFAULT_PRICE
        defaultNonResidentalEstatesShouldNotBeFound("price.greaterThan=" + DEFAULT_PRICE);

        // Get all the nonResidentalEstatesList where price is greater than SMALLER_PRICE
        defaultNonResidentalEstatesShouldBeFound("price.greaterThan=" + SMALLER_PRICE);
    }

    @Test
    @Transactional
    void getAllNonResidentalEstatesByCommissioningDateIsEqualToSomething() throws Exception {
        // Initialize the database
        nonResidentalEstatesRepository.saveAndFlush(nonResidentalEstates);

        // Get all the nonResidentalEstatesList where commissioningDate equals to DEFAULT_COMMISSIONING_DATE
        defaultNonResidentalEstatesShouldBeFound("commissioningDate.equals=" + DEFAULT_COMMISSIONING_DATE);

        // Get all the nonResidentalEstatesList where commissioningDate equals to UPDATED_COMMISSIONING_DATE
        defaultNonResidentalEstatesShouldNotBeFound("commissioningDate.equals=" + UPDATED_COMMISSIONING_DATE);
    }

    @Test
    @Transactional
    void getAllNonResidentalEstatesByCommissioningDateIsInShouldWork() throws Exception {
        // Initialize the database
        nonResidentalEstatesRepository.saveAndFlush(nonResidentalEstates);

        // Get all the nonResidentalEstatesList where commissioningDate in DEFAULT_COMMISSIONING_DATE or UPDATED_COMMISSIONING_DATE
        defaultNonResidentalEstatesShouldBeFound("commissioningDate.in=" + DEFAULT_COMMISSIONING_DATE + "," + UPDATED_COMMISSIONING_DATE);

        // Get all the nonResidentalEstatesList where commissioningDate equals to UPDATED_COMMISSIONING_DATE
        defaultNonResidentalEstatesShouldNotBeFound("commissioningDate.in=" + UPDATED_COMMISSIONING_DATE);
    }

    @Test
    @Transactional
    void getAllNonResidentalEstatesByCommissioningDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        nonResidentalEstatesRepository.saveAndFlush(nonResidentalEstates);

        // Get all the nonResidentalEstatesList where commissioningDate is not null
        defaultNonResidentalEstatesShouldBeFound("commissioningDate.specified=true");

        // Get all the nonResidentalEstatesList where commissioningDate is null
        defaultNonResidentalEstatesShouldNotBeFound("commissioningDate.specified=false");
    }

    @Test
    @Transactional
    void getAllNonResidentalEstatesByCommissioningDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        nonResidentalEstatesRepository.saveAndFlush(nonResidentalEstates);

        // Get all the nonResidentalEstatesList where commissioningDate is greater than or equal to DEFAULT_COMMISSIONING_DATE
        defaultNonResidentalEstatesShouldBeFound("commissioningDate.greaterThanOrEqual=" + DEFAULT_COMMISSIONING_DATE);

        // Get all the nonResidentalEstatesList where commissioningDate is greater than or equal to UPDATED_COMMISSIONING_DATE
        defaultNonResidentalEstatesShouldNotBeFound("commissioningDate.greaterThanOrEqual=" + UPDATED_COMMISSIONING_DATE);
    }

    @Test
    @Transactional
    void getAllNonResidentalEstatesByCommissioningDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        nonResidentalEstatesRepository.saveAndFlush(nonResidentalEstates);

        // Get all the nonResidentalEstatesList where commissioningDate is less than or equal to DEFAULT_COMMISSIONING_DATE
        defaultNonResidentalEstatesShouldBeFound("commissioningDate.lessThanOrEqual=" + DEFAULT_COMMISSIONING_DATE);

        // Get all the nonResidentalEstatesList where commissioningDate is less than or equal to SMALLER_COMMISSIONING_DATE
        defaultNonResidentalEstatesShouldNotBeFound("commissioningDate.lessThanOrEqual=" + SMALLER_COMMISSIONING_DATE);
    }

    @Test
    @Transactional
    void getAllNonResidentalEstatesByCommissioningDateIsLessThanSomething() throws Exception {
        // Initialize the database
        nonResidentalEstatesRepository.saveAndFlush(nonResidentalEstates);

        // Get all the nonResidentalEstatesList where commissioningDate is less than DEFAULT_COMMISSIONING_DATE
        defaultNonResidentalEstatesShouldNotBeFound("commissioningDate.lessThan=" + DEFAULT_COMMISSIONING_DATE);

        // Get all the nonResidentalEstatesList where commissioningDate is less than UPDATED_COMMISSIONING_DATE
        defaultNonResidentalEstatesShouldBeFound("commissioningDate.lessThan=" + UPDATED_COMMISSIONING_DATE);
    }

    @Test
    @Transactional
    void getAllNonResidentalEstatesByCommissioningDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        nonResidentalEstatesRepository.saveAndFlush(nonResidentalEstates);

        // Get all the nonResidentalEstatesList where commissioningDate is greater than DEFAULT_COMMISSIONING_DATE
        defaultNonResidentalEstatesShouldNotBeFound("commissioningDate.greaterThan=" + DEFAULT_COMMISSIONING_DATE);

        // Get all the nonResidentalEstatesList where commissioningDate is greater than SMALLER_COMMISSIONING_DATE
        defaultNonResidentalEstatesShouldBeFound("commissioningDate.greaterThan=" + SMALLER_COMMISSIONING_DATE);
    }

    @Test
    @Transactional
    void getAllNonResidentalEstatesByCadastralNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        nonResidentalEstatesRepository.saveAndFlush(nonResidentalEstates);

        // Get all the nonResidentalEstatesList where cadastralNumber equals to DEFAULT_CADASTRAL_NUMBER
        defaultNonResidentalEstatesShouldBeFound("cadastralNumber.equals=" + DEFAULT_CADASTRAL_NUMBER);

        // Get all the nonResidentalEstatesList where cadastralNumber equals to UPDATED_CADASTRAL_NUMBER
        defaultNonResidentalEstatesShouldNotBeFound("cadastralNumber.equals=" + UPDATED_CADASTRAL_NUMBER);
    }

    @Test
    @Transactional
    void getAllNonResidentalEstatesByCadastralNumberIsInShouldWork() throws Exception {
        // Initialize the database
        nonResidentalEstatesRepository.saveAndFlush(nonResidentalEstates);

        // Get all the nonResidentalEstatesList where cadastralNumber in DEFAULT_CADASTRAL_NUMBER or UPDATED_CADASTRAL_NUMBER
        defaultNonResidentalEstatesShouldBeFound("cadastralNumber.in=" + DEFAULT_CADASTRAL_NUMBER + "," + UPDATED_CADASTRAL_NUMBER);

        // Get all the nonResidentalEstatesList where cadastralNumber equals to UPDATED_CADASTRAL_NUMBER
        defaultNonResidentalEstatesShouldNotBeFound("cadastralNumber.in=" + UPDATED_CADASTRAL_NUMBER);
    }

    @Test
    @Transactional
    void getAllNonResidentalEstatesByCadastralNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        nonResidentalEstatesRepository.saveAndFlush(nonResidentalEstates);

        // Get all the nonResidentalEstatesList where cadastralNumber is not null
        defaultNonResidentalEstatesShouldBeFound("cadastralNumber.specified=true");

        // Get all the nonResidentalEstatesList where cadastralNumber is null
        defaultNonResidentalEstatesShouldNotBeFound("cadastralNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllNonResidentalEstatesByCadastralNumberIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        nonResidentalEstatesRepository.saveAndFlush(nonResidentalEstates);

        // Get all the nonResidentalEstatesList where cadastralNumber is greater than or equal to DEFAULT_CADASTRAL_NUMBER
        defaultNonResidentalEstatesShouldBeFound("cadastralNumber.greaterThanOrEqual=" + DEFAULT_CADASTRAL_NUMBER);

        // Get all the nonResidentalEstatesList where cadastralNumber is greater than or equal to UPDATED_CADASTRAL_NUMBER
        defaultNonResidentalEstatesShouldNotBeFound("cadastralNumber.greaterThanOrEqual=" + UPDATED_CADASTRAL_NUMBER);
    }

    @Test
    @Transactional
    void getAllNonResidentalEstatesByCadastralNumberIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        nonResidentalEstatesRepository.saveAndFlush(nonResidentalEstates);

        // Get all the nonResidentalEstatesList where cadastralNumber is less than or equal to DEFAULT_CADASTRAL_NUMBER
        defaultNonResidentalEstatesShouldBeFound("cadastralNumber.lessThanOrEqual=" + DEFAULT_CADASTRAL_NUMBER);

        // Get all the nonResidentalEstatesList where cadastralNumber is less than or equal to SMALLER_CADASTRAL_NUMBER
        defaultNonResidentalEstatesShouldNotBeFound("cadastralNumber.lessThanOrEqual=" + SMALLER_CADASTRAL_NUMBER);
    }

    @Test
    @Transactional
    void getAllNonResidentalEstatesByCadastralNumberIsLessThanSomething() throws Exception {
        // Initialize the database
        nonResidentalEstatesRepository.saveAndFlush(nonResidentalEstates);

        // Get all the nonResidentalEstatesList where cadastralNumber is less than DEFAULT_CADASTRAL_NUMBER
        defaultNonResidentalEstatesShouldNotBeFound("cadastralNumber.lessThan=" + DEFAULT_CADASTRAL_NUMBER);

        // Get all the nonResidentalEstatesList where cadastralNumber is less than UPDATED_CADASTRAL_NUMBER
        defaultNonResidentalEstatesShouldBeFound("cadastralNumber.lessThan=" + UPDATED_CADASTRAL_NUMBER);
    }

    @Test
    @Transactional
    void getAllNonResidentalEstatesByCadastralNumberIsGreaterThanSomething() throws Exception {
        // Initialize the database
        nonResidentalEstatesRepository.saveAndFlush(nonResidentalEstates);

        // Get all the nonResidentalEstatesList where cadastralNumber is greater than DEFAULT_CADASTRAL_NUMBER
        defaultNonResidentalEstatesShouldNotBeFound("cadastralNumber.greaterThan=" + DEFAULT_CADASTRAL_NUMBER);

        // Get all the nonResidentalEstatesList where cadastralNumber is greater than SMALLER_CADASTRAL_NUMBER
        defaultNonResidentalEstatesShouldBeFound("cadastralNumber.greaterThan=" + SMALLER_CADASTRAL_NUMBER);
    }

    @Test
    @Transactional
    void getAllNonResidentalEstatesByTotalSpaceIsEqualToSomething() throws Exception {
        // Initialize the database
        nonResidentalEstatesRepository.saveAndFlush(nonResidentalEstates);

        // Get all the nonResidentalEstatesList where totalSpace equals to DEFAULT_TOTAL_SPACE
        defaultNonResidentalEstatesShouldBeFound("totalSpace.equals=" + DEFAULT_TOTAL_SPACE);

        // Get all the nonResidentalEstatesList where totalSpace equals to UPDATED_TOTAL_SPACE
        defaultNonResidentalEstatesShouldNotBeFound("totalSpace.equals=" + UPDATED_TOTAL_SPACE);
    }

    @Test
    @Transactional
    void getAllNonResidentalEstatesByTotalSpaceIsInShouldWork() throws Exception {
        // Initialize the database
        nonResidentalEstatesRepository.saveAndFlush(nonResidentalEstates);

        // Get all the nonResidentalEstatesList where totalSpace in DEFAULT_TOTAL_SPACE or UPDATED_TOTAL_SPACE
        defaultNonResidentalEstatesShouldBeFound("totalSpace.in=" + DEFAULT_TOTAL_SPACE + "," + UPDATED_TOTAL_SPACE);

        // Get all the nonResidentalEstatesList where totalSpace equals to UPDATED_TOTAL_SPACE
        defaultNonResidentalEstatesShouldNotBeFound("totalSpace.in=" + UPDATED_TOTAL_SPACE);
    }

    @Test
    @Transactional
    void getAllNonResidentalEstatesByTotalSpaceIsNullOrNotNull() throws Exception {
        // Initialize the database
        nonResidentalEstatesRepository.saveAndFlush(nonResidentalEstates);

        // Get all the nonResidentalEstatesList where totalSpace is not null
        defaultNonResidentalEstatesShouldBeFound("totalSpace.specified=true");

        // Get all the nonResidentalEstatesList where totalSpace is null
        defaultNonResidentalEstatesShouldNotBeFound("totalSpace.specified=false");
    }

    @Test
    @Transactional
    void getAllNonResidentalEstatesByTotalSpaceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        nonResidentalEstatesRepository.saveAndFlush(nonResidentalEstates);

        // Get all the nonResidentalEstatesList where totalSpace is greater than or equal to DEFAULT_TOTAL_SPACE
        defaultNonResidentalEstatesShouldBeFound("totalSpace.greaterThanOrEqual=" + DEFAULT_TOTAL_SPACE);

        // Get all the nonResidentalEstatesList where totalSpace is greater than or equal to UPDATED_TOTAL_SPACE
        defaultNonResidentalEstatesShouldNotBeFound("totalSpace.greaterThanOrEqual=" + UPDATED_TOTAL_SPACE);
    }

    @Test
    @Transactional
    void getAllNonResidentalEstatesByTotalSpaceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        nonResidentalEstatesRepository.saveAndFlush(nonResidentalEstates);

        // Get all the nonResidentalEstatesList where totalSpace is less than or equal to DEFAULT_TOTAL_SPACE
        defaultNonResidentalEstatesShouldBeFound("totalSpace.lessThanOrEqual=" + DEFAULT_TOTAL_SPACE);

        // Get all the nonResidentalEstatesList where totalSpace is less than or equal to SMALLER_TOTAL_SPACE
        defaultNonResidentalEstatesShouldNotBeFound("totalSpace.lessThanOrEqual=" + SMALLER_TOTAL_SPACE);
    }

    @Test
    @Transactional
    void getAllNonResidentalEstatesByTotalSpaceIsLessThanSomething() throws Exception {
        // Initialize the database
        nonResidentalEstatesRepository.saveAndFlush(nonResidentalEstates);

        // Get all the nonResidentalEstatesList where totalSpace is less than DEFAULT_TOTAL_SPACE
        defaultNonResidentalEstatesShouldNotBeFound("totalSpace.lessThan=" + DEFAULT_TOTAL_SPACE);

        // Get all the nonResidentalEstatesList where totalSpace is less than UPDATED_TOTAL_SPACE
        defaultNonResidentalEstatesShouldBeFound("totalSpace.lessThan=" + UPDATED_TOTAL_SPACE);
    }

    @Test
    @Transactional
    void getAllNonResidentalEstatesByTotalSpaceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        nonResidentalEstatesRepository.saveAndFlush(nonResidentalEstates);

        // Get all the nonResidentalEstatesList where totalSpace is greater than DEFAULT_TOTAL_SPACE
        defaultNonResidentalEstatesShouldNotBeFound("totalSpace.greaterThan=" + DEFAULT_TOTAL_SPACE);

        // Get all the nonResidentalEstatesList where totalSpace is greater than SMALLER_TOTAL_SPACE
        defaultNonResidentalEstatesShouldBeFound("totalSpace.greaterThan=" + SMALLER_TOTAL_SPACE);
    }

    @Test
    @Transactional
    void getAllNonResidentalEstatesByPurposeOfNonResidentalEstateIdIsEqualToSomething() throws Exception {
        PurposesOfNonResidentalEstate purposeOfNonResidentalEstateId;
        if (TestUtil.findAll(em, PurposesOfNonResidentalEstate.class).isEmpty()) {
            nonResidentalEstatesRepository.saveAndFlush(nonResidentalEstates);
            purposeOfNonResidentalEstateId = PurposesOfNonResidentalEstateResourceIT.createEntity(em);
        } else {
            purposeOfNonResidentalEstateId = TestUtil.findAll(em, PurposesOfNonResidentalEstate.class).get(0);
        }
        em.persist(purposeOfNonResidentalEstateId);
        em.flush();
        nonResidentalEstates.setPurposeOfNonResidentalEstateId(purposeOfNonResidentalEstateId);
        nonResidentalEstatesRepository.saveAndFlush(nonResidentalEstates);
        Long purposeOfNonResidentalEstateIdId = purposeOfNonResidentalEstateId.getId();

        // Get all the nonResidentalEstatesList where purposeOfNonResidentalEstateId equals to purposeOfNonResidentalEstateIdId
        defaultNonResidentalEstatesShouldBeFound("purposeOfNonResidentalEstateIdId.equals=" + purposeOfNonResidentalEstateIdId);

        // Get all the nonResidentalEstatesList where purposeOfNonResidentalEstateId equals to (purposeOfNonResidentalEstateIdId + 1)
        defaultNonResidentalEstatesShouldNotBeFound("purposeOfNonResidentalEstateIdId.equals=" + (purposeOfNonResidentalEstateIdId + 1));
    }

    @Test
    @Transactional
    void getAllNonResidentalEstatesByBuildingTypeOfNonResidentalEstateIdIsEqualToSomething() throws Exception {
        BuildingTypeOfNonResidentalEstate buildingTypeOfNonResidentalEstateId;
        if (TestUtil.findAll(em, BuildingTypeOfNonResidentalEstate.class).isEmpty()) {
            nonResidentalEstatesRepository.saveAndFlush(nonResidentalEstates);
            buildingTypeOfNonResidentalEstateId = BuildingTypeOfNonResidentalEstateResourceIT.createEntity(em);
        } else {
            buildingTypeOfNonResidentalEstateId = TestUtil.findAll(em, BuildingTypeOfNonResidentalEstate.class).get(0);
        }
        em.persist(buildingTypeOfNonResidentalEstateId);
        em.flush();
        nonResidentalEstates.setBuildingTypeOfNonResidentalEstateId(buildingTypeOfNonResidentalEstateId);
        nonResidentalEstatesRepository.saveAndFlush(nonResidentalEstates);
        Long buildingTypeOfNonResidentalEstateIdId = buildingTypeOfNonResidentalEstateId.getId();

        // Get all the nonResidentalEstatesList where buildingTypeOfNonResidentalEstateId equals to buildingTypeOfNonResidentalEstateIdId
        defaultNonResidentalEstatesShouldBeFound("buildingTypeOfNonResidentalEstateIdId.equals=" + buildingTypeOfNonResidentalEstateIdId);

        // Get all the nonResidentalEstatesList where buildingTypeOfNonResidentalEstateId equals to (buildingTypeOfNonResidentalEstateIdId + 1)
        defaultNonResidentalEstatesShouldNotBeFound(
            "buildingTypeOfNonResidentalEstateIdId.equals=" + (buildingTypeOfNonResidentalEstateIdId + 1)
        );
    }

    @Test
    @Transactional
    void getAllNonResidentalEstatesByAddressIdIsEqualToSomething() throws Exception {
        Addresses addressId;
        if (TestUtil.findAll(em, Addresses.class).isEmpty()) {
            nonResidentalEstatesRepository.saveAndFlush(nonResidentalEstates);
            addressId = AddressesResourceIT.createEntity(em);
        } else {
            addressId = TestUtil.findAll(em, Addresses.class).get(0);
        }
        em.persist(addressId);
        em.flush();
        nonResidentalEstates.setAddressId(addressId);
        nonResidentalEstatesRepository.saveAndFlush(nonResidentalEstates);
        Long addressIdId = addressId.getId();

        // Get all the nonResidentalEstatesList where addressId equals to addressIdId
        defaultNonResidentalEstatesShouldBeFound("addressIdId.equals=" + addressIdId);

        // Get all the nonResidentalEstatesList where addressId equals to (addressIdId + 1)
        defaultNonResidentalEstatesShouldNotBeFound("addressIdId.equals=" + (addressIdId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultNonResidentalEstatesShouldBeFound(String filter) throws Exception {
        restNonResidentalEstatesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nonResidentalEstates.getId().intValue())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(sameNumber(DEFAULT_PRICE))))
            .andExpect(jsonPath("$.[*].commissioningDate").value(hasItem(DEFAULT_COMMISSIONING_DATE.toString())))
            .andExpect(jsonPath("$.[*].cadastralNumber").value(hasItem(DEFAULT_CADASTRAL_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].totalSpace").value(hasItem(DEFAULT_TOTAL_SPACE.doubleValue())));

        // Check, that the count call also returns 1
        restNonResidentalEstatesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultNonResidentalEstatesShouldNotBeFound(String filter) throws Exception {
        restNonResidentalEstatesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restNonResidentalEstatesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingNonResidentalEstates() throws Exception {
        // Get the nonResidentalEstates
        restNonResidentalEstatesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingNonResidentalEstates() throws Exception {
        // Initialize the database
        nonResidentalEstatesRepository.saveAndFlush(nonResidentalEstates);

        int databaseSizeBeforeUpdate = nonResidentalEstatesRepository.findAll().size();

        // Update the nonResidentalEstates
        NonResidentalEstates updatedNonResidentalEstates = nonResidentalEstatesRepository.findById(nonResidentalEstates.getId()).get();
        // Disconnect from session so that the updates on updatedNonResidentalEstates are not directly saved in db
        em.detach(updatedNonResidentalEstates);
        updatedNonResidentalEstates
            .price(UPDATED_PRICE)
            .commissioningDate(UPDATED_COMMISSIONING_DATE)
            .cadastralNumber(UPDATED_CADASTRAL_NUMBER)
            .totalSpace(UPDATED_TOTAL_SPACE);
        NonResidentalEstatesDTO nonResidentalEstatesDTO = nonResidentalEstatesMapper.toDto(updatedNonResidentalEstates);

        restNonResidentalEstatesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, nonResidentalEstatesDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(nonResidentalEstatesDTO))
            )
            .andExpect(status().isOk());

        // Validate the NonResidentalEstates in the database
        List<NonResidentalEstates> nonResidentalEstatesList = nonResidentalEstatesRepository.findAll();
        assertThat(nonResidentalEstatesList).hasSize(databaseSizeBeforeUpdate);
        NonResidentalEstates testNonResidentalEstates = nonResidentalEstatesList.get(nonResidentalEstatesList.size() - 1);
        assertThat(testNonResidentalEstates.getPrice()).isEqualByComparingTo(UPDATED_PRICE);
        assertThat(testNonResidentalEstates.getCommissioningDate()).isEqualTo(UPDATED_COMMISSIONING_DATE);
        assertThat(testNonResidentalEstates.getCadastralNumber()).isEqualTo(UPDATED_CADASTRAL_NUMBER);
        assertThat(testNonResidentalEstates.getTotalSpace()).isEqualTo(UPDATED_TOTAL_SPACE);
    }

    @Test
    @Transactional
    void putNonExistingNonResidentalEstates() throws Exception {
        int databaseSizeBeforeUpdate = nonResidentalEstatesRepository.findAll().size();
        nonResidentalEstates.setId(count.incrementAndGet());

        // Create the NonResidentalEstates
        NonResidentalEstatesDTO nonResidentalEstatesDTO = nonResidentalEstatesMapper.toDto(nonResidentalEstates);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNonResidentalEstatesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, nonResidentalEstatesDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(nonResidentalEstatesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NonResidentalEstates in the database
        List<NonResidentalEstates> nonResidentalEstatesList = nonResidentalEstatesRepository.findAll();
        assertThat(nonResidentalEstatesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchNonResidentalEstates() throws Exception {
        int databaseSizeBeforeUpdate = nonResidentalEstatesRepository.findAll().size();
        nonResidentalEstates.setId(count.incrementAndGet());

        // Create the NonResidentalEstates
        NonResidentalEstatesDTO nonResidentalEstatesDTO = nonResidentalEstatesMapper.toDto(nonResidentalEstates);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNonResidentalEstatesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(nonResidentalEstatesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NonResidentalEstates in the database
        List<NonResidentalEstates> nonResidentalEstatesList = nonResidentalEstatesRepository.findAll();
        assertThat(nonResidentalEstatesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamNonResidentalEstates() throws Exception {
        int databaseSizeBeforeUpdate = nonResidentalEstatesRepository.findAll().size();
        nonResidentalEstates.setId(count.incrementAndGet());

        // Create the NonResidentalEstates
        NonResidentalEstatesDTO nonResidentalEstatesDTO = nonResidentalEstatesMapper.toDto(nonResidentalEstates);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNonResidentalEstatesMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(nonResidentalEstatesDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the NonResidentalEstates in the database
        List<NonResidentalEstates> nonResidentalEstatesList = nonResidentalEstatesRepository.findAll();
        assertThat(nonResidentalEstatesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateNonResidentalEstatesWithPatch() throws Exception {
        // Initialize the database
        nonResidentalEstatesRepository.saveAndFlush(nonResidentalEstates);

        int databaseSizeBeforeUpdate = nonResidentalEstatesRepository.findAll().size();

        // Update the nonResidentalEstates using partial update
        NonResidentalEstates partialUpdatedNonResidentalEstates = new NonResidentalEstates();
        partialUpdatedNonResidentalEstates.setId(nonResidentalEstates.getId());

        partialUpdatedNonResidentalEstates.cadastralNumber(UPDATED_CADASTRAL_NUMBER);

        restNonResidentalEstatesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNonResidentalEstates.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNonResidentalEstates))
            )
            .andExpect(status().isOk());

        // Validate the NonResidentalEstates in the database
        List<NonResidentalEstates> nonResidentalEstatesList = nonResidentalEstatesRepository.findAll();
        assertThat(nonResidentalEstatesList).hasSize(databaseSizeBeforeUpdate);
        NonResidentalEstates testNonResidentalEstates = nonResidentalEstatesList.get(nonResidentalEstatesList.size() - 1);
        assertThat(testNonResidentalEstates.getPrice()).isEqualByComparingTo(DEFAULT_PRICE);
        assertThat(testNonResidentalEstates.getCommissioningDate()).isEqualTo(DEFAULT_COMMISSIONING_DATE);
        assertThat(testNonResidentalEstates.getCadastralNumber()).isEqualTo(UPDATED_CADASTRAL_NUMBER);
        assertThat(testNonResidentalEstates.getTotalSpace()).isEqualTo(DEFAULT_TOTAL_SPACE);
    }

    @Test
    @Transactional
    void fullUpdateNonResidentalEstatesWithPatch() throws Exception {
        // Initialize the database
        nonResidentalEstatesRepository.saveAndFlush(nonResidentalEstates);

        int databaseSizeBeforeUpdate = nonResidentalEstatesRepository.findAll().size();

        // Update the nonResidentalEstates using partial update
        NonResidentalEstates partialUpdatedNonResidentalEstates = new NonResidentalEstates();
        partialUpdatedNonResidentalEstates.setId(nonResidentalEstates.getId());

        partialUpdatedNonResidentalEstates
            .price(UPDATED_PRICE)
            .commissioningDate(UPDATED_COMMISSIONING_DATE)
            .cadastralNumber(UPDATED_CADASTRAL_NUMBER)
            .totalSpace(UPDATED_TOTAL_SPACE);

        restNonResidentalEstatesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNonResidentalEstates.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNonResidentalEstates))
            )
            .andExpect(status().isOk());

        // Validate the NonResidentalEstates in the database
        List<NonResidentalEstates> nonResidentalEstatesList = nonResidentalEstatesRepository.findAll();
        assertThat(nonResidentalEstatesList).hasSize(databaseSizeBeforeUpdate);
        NonResidentalEstates testNonResidentalEstates = nonResidentalEstatesList.get(nonResidentalEstatesList.size() - 1);
        assertThat(testNonResidentalEstates.getPrice()).isEqualByComparingTo(UPDATED_PRICE);
        assertThat(testNonResidentalEstates.getCommissioningDate()).isEqualTo(UPDATED_COMMISSIONING_DATE);
        assertThat(testNonResidentalEstates.getCadastralNumber()).isEqualTo(UPDATED_CADASTRAL_NUMBER);
        assertThat(testNonResidentalEstates.getTotalSpace()).isEqualTo(UPDATED_TOTAL_SPACE);
    }

    @Test
    @Transactional
    void patchNonExistingNonResidentalEstates() throws Exception {
        int databaseSizeBeforeUpdate = nonResidentalEstatesRepository.findAll().size();
        nonResidentalEstates.setId(count.incrementAndGet());

        // Create the NonResidentalEstates
        NonResidentalEstatesDTO nonResidentalEstatesDTO = nonResidentalEstatesMapper.toDto(nonResidentalEstates);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNonResidentalEstatesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, nonResidentalEstatesDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(nonResidentalEstatesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NonResidentalEstates in the database
        List<NonResidentalEstates> nonResidentalEstatesList = nonResidentalEstatesRepository.findAll();
        assertThat(nonResidentalEstatesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchNonResidentalEstates() throws Exception {
        int databaseSizeBeforeUpdate = nonResidentalEstatesRepository.findAll().size();
        nonResidentalEstates.setId(count.incrementAndGet());

        // Create the NonResidentalEstates
        NonResidentalEstatesDTO nonResidentalEstatesDTO = nonResidentalEstatesMapper.toDto(nonResidentalEstates);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNonResidentalEstatesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(nonResidentalEstatesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NonResidentalEstates in the database
        List<NonResidentalEstates> nonResidentalEstatesList = nonResidentalEstatesRepository.findAll();
        assertThat(nonResidentalEstatesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamNonResidentalEstates() throws Exception {
        int databaseSizeBeforeUpdate = nonResidentalEstatesRepository.findAll().size();
        nonResidentalEstates.setId(count.incrementAndGet());

        // Create the NonResidentalEstates
        NonResidentalEstatesDTO nonResidentalEstatesDTO = nonResidentalEstatesMapper.toDto(nonResidentalEstates);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNonResidentalEstatesMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(nonResidentalEstatesDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the NonResidentalEstates in the database
        List<NonResidentalEstates> nonResidentalEstatesList = nonResidentalEstatesRepository.findAll();
        assertThat(nonResidentalEstatesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteNonResidentalEstates() throws Exception {
        // Initialize the database
        nonResidentalEstatesRepository.saveAndFlush(nonResidentalEstates);

        int databaseSizeBeforeDelete = nonResidentalEstatesRepository.findAll().size();

        // Delete the nonResidentalEstates
        restNonResidentalEstatesMockMvc
            .perform(delete(ENTITY_API_URL_ID, nonResidentalEstates.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<NonResidentalEstates> nonResidentalEstatesList = nonResidentalEstatesRepository.findAll();
        assertThat(nonResidentalEstatesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
