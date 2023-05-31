package com.bsu.app.web.rest;

import com.bsu.app.repository.NonResidentalEstatesRepository;
import com.bsu.app.service.NonResidentalEstatesQueryService;
import com.bsu.app.service.NonResidentalEstatesService;
import com.bsu.app.service.criteria.NonResidentalEstatesCriteria;
import com.bsu.app.service.dto.NonResidentalEstatesDTO;
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
 * REST controller for managing {@link com.bsu.app.domain.NonResidentalEstates}.
 */
@RestController
@RequestMapping("/api")
public class NonResidentalEstatesResource {

    private final Logger log = LoggerFactory.getLogger(NonResidentalEstatesResource.class);

    private static final String ENTITY_NAME = "nonResidentalEstates";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NonResidentalEstatesService nonResidentalEstatesService;

    private final NonResidentalEstatesRepository nonResidentalEstatesRepository;

    private final NonResidentalEstatesQueryService nonResidentalEstatesQueryService;

    public NonResidentalEstatesResource(
        NonResidentalEstatesService nonResidentalEstatesService,
        NonResidentalEstatesRepository nonResidentalEstatesRepository,
        NonResidentalEstatesQueryService nonResidentalEstatesQueryService
    ) {
        this.nonResidentalEstatesService = nonResidentalEstatesService;
        this.nonResidentalEstatesRepository = nonResidentalEstatesRepository;
        this.nonResidentalEstatesQueryService = nonResidentalEstatesQueryService;
    }

    /**
     * {@code POST  /non-residental-estates} : Create a new nonResidentalEstates.
     *
     * @param nonResidentalEstatesDTO the nonResidentalEstatesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new nonResidentalEstatesDTO, or with status {@code 400 (Bad Request)} if the nonResidentalEstates has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/non-residental-estates")
    public ResponseEntity<NonResidentalEstatesDTO> createNonResidentalEstates(@RequestBody NonResidentalEstatesDTO nonResidentalEstatesDTO)
        throws URISyntaxException {
        log.debug("REST request to save NonResidentalEstates : {}", nonResidentalEstatesDTO);
        if (nonResidentalEstatesDTO.getId() != null) {
            throw new BadRequestAlertException("A new nonResidentalEstates cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NonResidentalEstatesDTO result = nonResidentalEstatesService.save(nonResidentalEstatesDTO);
        return ResponseEntity
            .created(new URI("/api/non-residental-estates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /non-residental-estates/:id} : Updates an existing nonResidentalEstates.
     *
     * @param id the id of the nonResidentalEstatesDTO to save.
     * @param nonResidentalEstatesDTO the nonResidentalEstatesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated nonResidentalEstatesDTO,
     * or with status {@code 400 (Bad Request)} if the nonResidentalEstatesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the nonResidentalEstatesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/non-residental-estates/{id}")
    public ResponseEntity<NonResidentalEstatesDTO> updateNonResidentalEstates(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody NonResidentalEstatesDTO nonResidentalEstatesDTO
    ) throws URISyntaxException {
        log.debug("REST request to update NonResidentalEstates : {}, {}", id, nonResidentalEstatesDTO);
        if (nonResidentalEstatesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, nonResidentalEstatesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!nonResidentalEstatesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        NonResidentalEstatesDTO result = nonResidentalEstatesService.update(nonResidentalEstatesDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, nonResidentalEstatesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /non-residental-estates/:id} : Partial updates given fields of an existing nonResidentalEstates, field will ignore if it is null
     *
     * @param id the id of the nonResidentalEstatesDTO to save.
     * @param nonResidentalEstatesDTO the nonResidentalEstatesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated nonResidentalEstatesDTO,
     * or with status {@code 400 (Bad Request)} if the nonResidentalEstatesDTO is not valid,
     * or with status {@code 404 (Not Found)} if the nonResidentalEstatesDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the nonResidentalEstatesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/non-residental-estates/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<NonResidentalEstatesDTO> partialUpdateNonResidentalEstates(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody NonResidentalEstatesDTO nonResidentalEstatesDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update NonResidentalEstates partially : {}, {}", id, nonResidentalEstatesDTO);
        if (nonResidentalEstatesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, nonResidentalEstatesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!nonResidentalEstatesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<NonResidentalEstatesDTO> result = nonResidentalEstatesService.partialUpdate(nonResidentalEstatesDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, nonResidentalEstatesDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /non-residental-estates} : get all the nonResidentalEstates.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of nonResidentalEstates in body.
     */
    @GetMapping("/non-residental-estates")
    public ResponseEntity<List<NonResidentalEstatesDTO>> getAllNonResidentalEstates(
        NonResidentalEstatesCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get NonResidentalEstates by criteria: {}", criteria);
        Page<NonResidentalEstatesDTO> page = nonResidentalEstatesQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /non-residental-estates/count} : count all the nonResidentalEstates.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/non-residental-estates/count")
    public ResponseEntity<Long> countNonResidentalEstates(NonResidentalEstatesCriteria criteria) {
        log.debug("REST request to count NonResidentalEstates by criteria: {}", criteria);
        return ResponseEntity.ok().body(nonResidentalEstatesQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /non-residental-estates/:id} : get the "id" nonResidentalEstates.
     *
     * @param id the id of the nonResidentalEstatesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the nonResidentalEstatesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/non-residental-estates/{id}")
    public ResponseEntity<NonResidentalEstatesDTO> getNonResidentalEstates(@PathVariable Long id) {
        log.debug("REST request to get NonResidentalEstates : {}", id);
        Optional<NonResidentalEstatesDTO> nonResidentalEstatesDTO = nonResidentalEstatesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(nonResidentalEstatesDTO);
    }

    /**
     * {@code DELETE  /non-residental-estates/:id} : delete the "id" nonResidentalEstates.
     *
     * @param id the id of the nonResidentalEstatesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/non-residental-estates/{id}")
    public ResponseEntity<Void> deleteNonResidentalEstates(@PathVariable Long id) {
        log.debug("REST request to delete NonResidentalEstates : {}", id);
        nonResidentalEstatesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
