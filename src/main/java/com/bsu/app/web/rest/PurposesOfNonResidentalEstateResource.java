package com.bsu.app.web.rest;

import com.bsu.app.repository.PurposesOfNonResidentalEstateRepository;
import com.bsu.app.service.PurposesOfNonResidentalEstateQueryService;
import com.bsu.app.service.PurposesOfNonResidentalEstateService;
import com.bsu.app.service.criteria.PurposesOfNonResidentalEstateCriteria;
import com.bsu.app.service.dto.PurposesOfNonResidentalEstateDTO;
import com.bsu.app.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.bsu.app.domain.PurposesOfNonResidentalEstate}.
 */
@RestController
@RequestMapping("/api")
public class PurposesOfNonResidentalEstateResource {

    private final Logger log = LoggerFactory.getLogger(PurposesOfNonResidentalEstateResource.class);

    private static final String ENTITY_NAME = "purposesOfNonResidentalEstate";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PurposesOfNonResidentalEstateService purposesOfNonResidentalEstateService;

    private final PurposesOfNonResidentalEstateRepository purposesOfNonResidentalEstateRepository;

    private final PurposesOfNonResidentalEstateQueryService purposesOfNonResidentalEstateQueryService;

    public PurposesOfNonResidentalEstateResource(
        PurposesOfNonResidentalEstateService purposesOfNonResidentalEstateService,
        PurposesOfNonResidentalEstateRepository purposesOfNonResidentalEstateRepository,
        PurposesOfNonResidentalEstateQueryService purposesOfNonResidentalEstateQueryService
    ) {
        this.purposesOfNonResidentalEstateService = purposesOfNonResidentalEstateService;
        this.purposesOfNonResidentalEstateRepository = purposesOfNonResidentalEstateRepository;
        this.purposesOfNonResidentalEstateQueryService = purposesOfNonResidentalEstateQueryService;
    }

    /**
     * {@code POST  /purposes-of-non-residental-estates} : Create a new purposesOfNonResidentalEstate.
     *
     * @param purposesOfNonResidentalEstateDTO the purposesOfNonResidentalEstateDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new purposesOfNonResidentalEstateDTO, or with status {@code 400 (Bad Request)} if the purposesOfNonResidentalEstate has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/purposes-of-non-residental-estates")
    public ResponseEntity<PurposesOfNonResidentalEstateDTO> createPurposesOfNonResidentalEstate(
        @RequestBody PurposesOfNonResidentalEstateDTO purposesOfNonResidentalEstateDTO
    ) throws URISyntaxException {
        log.debug("REST request to save PurposesOfNonResidentalEstate : {}", purposesOfNonResidentalEstateDTO);
        if (purposesOfNonResidentalEstateDTO.getId() != null) {
            throw new BadRequestAlertException("A new purposesOfNonResidentalEstate cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PurposesOfNonResidentalEstateDTO result = purposesOfNonResidentalEstateService.save(purposesOfNonResidentalEstateDTO);
        return ResponseEntity
            .created(new URI("/api/purposes-of-non-residental-estates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /purposes-of-non-residental-estates/:id} : Updates an existing purposesOfNonResidentalEstate.
     *
     * @param id the id of the purposesOfNonResidentalEstateDTO to save.
     * @param purposesOfNonResidentalEstateDTO the purposesOfNonResidentalEstateDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated purposesOfNonResidentalEstateDTO,
     * or with status {@code 400 (Bad Request)} if the purposesOfNonResidentalEstateDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the purposesOfNonResidentalEstateDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/purposes-of-non-residental-estates/{id}")
    public ResponseEntity<PurposesOfNonResidentalEstateDTO> updatePurposesOfNonResidentalEstate(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PurposesOfNonResidentalEstateDTO purposesOfNonResidentalEstateDTO
    ) throws URISyntaxException {
        log.debug("REST request to update PurposesOfNonResidentalEstate : {}, {}", id, purposesOfNonResidentalEstateDTO);
        if (purposesOfNonResidentalEstateDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, purposesOfNonResidentalEstateDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!purposesOfNonResidentalEstateRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PurposesOfNonResidentalEstateDTO result = purposesOfNonResidentalEstateService.update(purposesOfNonResidentalEstateDTO);
        return ResponseEntity
            .ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, purposesOfNonResidentalEstateDTO.getId().toString())
            )
            .body(result);
    }

    /**
     * {@code PATCH  /purposes-of-non-residental-estates/:id} : Partial updates given fields of an existing purposesOfNonResidentalEstate, field will ignore if it is null
     *
     * @param id the id of the purposesOfNonResidentalEstateDTO to save.
     * @param purposesOfNonResidentalEstateDTO the purposesOfNonResidentalEstateDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated purposesOfNonResidentalEstateDTO,
     * or with status {@code 400 (Bad Request)} if the purposesOfNonResidentalEstateDTO is not valid,
     * or with status {@code 404 (Not Found)} if the purposesOfNonResidentalEstateDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the purposesOfNonResidentalEstateDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/purposes-of-non-residental-estates/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PurposesOfNonResidentalEstateDTO> partialUpdatePurposesOfNonResidentalEstate(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PurposesOfNonResidentalEstateDTO purposesOfNonResidentalEstateDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update PurposesOfNonResidentalEstate partially : {}, {}", id, purposesOfNonResidentalEstateDTO);
        if (purposesOfNonResidentalEstateDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, purposesOfNonResidentalEstateDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!purposesOfNonResidentalEstateRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PurposesOfNonResidentalEstateDTO> result = purposesOfNonResidentalEstateService.partialUpdate(
            purposesOfNonResidentalEstateDTO
        );

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, purposesOfNonResidentalEstateDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /purposes-of-non-residental-estates} : get all the purposesOfNonResidentalEstates.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of purposesOfNonResidentalEstates in body.
     */
    @GetMapping("/purposes-of-non-residental-estates")
    public ResponseEntity<List<PurposesOfNonResidentalEstateDTO>> getAllPurposesOfNonResidentalEstates(
        PurposesOfNonResidentalEstateCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get PurposesOfNonResidentalEstates by criteria: {}", criteria);
        Page<PurposesOfNonResidentalEstateDTO> page = purposesOfNonResidentalEstateQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /purposes-of-non-residental-estates/count} : count all the purposesOfNonResidentalEstates.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/purposes-of-non-residental-estates/count")
    public ResponseEntity<Long> countPurposesOfNonResidentalEstates(PurposesOfNonResidentalEstateCriteria criteria) {
        log.debug("REST request to count PurposesOfNonResidentalEstates by criteria: {}", criteria);
        return ResponseEntity.ok().body(purposesOfNonResidentalEstateQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /purposes-of-non-residental-estates/:id} : get the "id" purposesOfNonResidentalEstate.
     *
     * @param id the id of the purposesOfNonResidentalEstateDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the purposesOfNonResidentalEstateDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/purposes-of-non-residental-estates/{id}")
    public ResponseEntity<PurposesOfNonResidentalEstateDTO> getPurposesOfNonResidentalEstate(@PathVariable Long id) {
        log.debug("REST request to get PurposesOfNonResidentalEstate : {}", id);
        Optional<PurposesOfNonResidentalEstateDTO> purposesOfNonResidentalEstateDTO = purposesOfNonResidentalEstateService.findOne(id);
        return ResponseUtil.wrapOrNotFound(purposesOfNonResidentalEstateDTO);
    }

    /**
     * {@code DELETE  /purposes-of-non-residental-estates/:id} : delete the "id" purposesOfNonResidentalEstate.
     *
     * @param id the id of the purposesOfNonResidentalEstateDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/purposes-of-non-residental-estates/{id}")
    public ResponseEntity<Void> deletePurposesOfNonResidentalEstate(@PathVariable Long id) {
        log.debug("REST request to delete PurposesOfNonResidentalEstate : {}", id);
        purposesOfNonResidentalEstateService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
