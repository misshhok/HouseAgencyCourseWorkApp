package com.bsu.app.web.rest;

import com.bsu.app.repository.StatusesOfResidentalEstateRepository;
import com.bsu.app.service.StatusesOfResidentalEstateQueryService;
import com.bsu.app.service.StatusesOfResidentalEstateService;
import com.bsu.app.service.criteria.StatusesOfResidentalEstateCriteria;
import com.bsu.app.service.dto.StatusesOfResidentalEstateDTO;
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
 * REST controller for managing {@link com.bsu.app.domain.StatusesOfResidentalEstate}.
 */
@RestController
@RequestMapping("/api")
public class StatusesOfResidentalEstateResource {

    private final Logger log = LoggerFactory.getLogger(StatusesOfResidentalEstateResource.class);

    private static final String ENTITY_NAME = "statusesOfResidentalEstate";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StatusesOfResidentalEstateService statusesOfResidentalEstateService;

    private final StatusesOfResidentalEstateRepository statusesOfResidentalEstateRepository;

    private final StatusesOfResidentalEstateQueryService statusesOfResidentalEstateQueryService;

    public StatusesOfResidentalEstateResource(
        StatusesOfResidentalEstateService statusesOfResidentalEstateService,
        StatusesOfResidentalEstateRepository statusesOfResidentalEstateRepository,
        StatusesOfResidentalEstateQueryService statusesOfResidentalEstateQueryService
    ) {
        this.statusesOfResidentalEstateService = statusesOfResidentalEstateService;
        this.statusesOfResidentalEstateRepository = statusesOfResidentalEstateRepository;
        this.statusesOfResidentalEstateQueryService = statusesOfResidentalEstateQueryService;
    }

    /**
     * {@code POST  /statuses-of-residental-estates} : Create a new statusesOfResidentalEstate.
     *
     * @param statusesOfResidentalEstateDTO the statusesOfResidentalEstateDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new statusesOfResidentalEstateDTO, or with status {@code 400 (Bad Request)} if the statusesOfResidentalEstate has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/statuses-of-residental-estates")
    public ResponseEntity<StatusesOfResidentalEstateDTO> createStatusesOfResidentalEstate(
        @RequestBody StatusesOfResidentalEstateDTO statusesOfResidentalEstateDTO
    ) throws URISyntaxException {
        log.debug("REST request to save StatusesOfResidentalEstate : {}", statusesOfResidentalEstateDTO);
        if (statusesOfResidentalEstateDTO.getId() != null) {
            throw new BadRequestAlertException("A new statusesOfResidentalEstate cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StatusesOfResidentalEstateDTO result = statusesOfResidentalEstateService.save(statusesOfResidentalEstateDTO);
        return ResponseEntity
            .created(new URI("/api/statuses-of-residental-estates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /statuses-of-residental-estates/:id} : Updates an existing statusesOfResidentalEstate.
     *
     * @param id the id of the statusesOfResidentalEstateDTO to save.
     * @param statusesOfResidentalEstateDTO the statusesOfResidentalEstateDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated statusesOfResidentalEstateDTO,
     * or with status {@code 400 (Bad Request)} if the statusesOfResidentalEstateDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the statusesOfResidentalEstateDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/statuses-of-residental-estates/{id}")
    public ResponseEntity<StatusesOfResidentalEstateDTO> updateStatusesOfResidentalEstate(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody StatusesOfResidentalEstateDTO statusesOfResidentalEstateDTO
    ) throws URISyntaxException {
        log.debug("REST request to update StatusesOfResidentalEstate : {}, {}", id, statusesOfResidentalEstateDTO);
        if (statusesOfResidentalEstateDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, statusesOfResidentalEstateDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!statusesOfResidentalEstateRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        StatusesOfResidentalEstateDTO result = statusesOfResidentalEstateService.update(statusesOfResidentalEstateDTO);
        return ResponseEntity
            .ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, statusesOfResidentalEstateDTO.getId().toString())
            )
            .body(result);
    }

    /**
     * {@code PATCH  /statuses-of-residental-estates/:id} : Partial updates given fields of an existing statusesOfResidentalEstate, field will ignore if it is null
     *
     * @param id the id of the statusesOfResidentalEstateDTO to save.
     * @param statusesOfResidentalEstateDTO the statusesOfResidentalEstateDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated statusesOfResidentalEstateDTO,
     * or with status {@code 400 (Bad Request)} if the statusesOfResidentalEstateDTO is not valid,
     * or with status {@code 404 (Not Found)} if the statusesOfResidentalEstateDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the statusesOfResidentalEstateDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/statuses-of-residental-estates/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<StatusesOfResidentalEstateDTO> partialUpdateStatusesOfResidentalEstate(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody StatusesOfResidentalEstateDTO statusesOfResidentalEstateDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update StatusesOfResidentalEstate partially : {}, {}", id, statusesOfResidentalEstateDTO);
        if (statusesOfResidentalEstateDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, statusesOfResidentalEstateDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!statusesOfResidentalEstateRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<StatusesOfResidentalEstateDTO> result = statusesOfResidentalEstateService.partialUpdate(statusesOfResidentalEstateDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, statusesOfResidentalEstateDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /statuses-of-residental-estates} : get all the statusesOfResidentalEstates.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of statusesOfResidentalEstates in body.
     */
    @GetMapping("/statuses-of-residental-estates")
    public ResponseEntity<List<StatusesOfResidentalEstateDTO>> getAllStatusesOfResidentalEstates(
        StatusesOfResidentalEstateCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get StatusesOfResidentalEstates by criteria: {}", criteria);
        Page<StatusesOfResidentalEstateDTO> page = statusesOfResidentalEstateQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /statuses-of-residental-estates/count} : count all the statusesOfResidentalEstates.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/statuses-of-residental-estates/count")
    public ResponseEntity<Long> countStatusesOfResidentalEstates(StatusesOfResidentalEstateCriteria criteria) {
        log.debug("REST request to count StatusesOfResidentalEstates by criteria: {}", criteria);
        return ResponseEntity.ok().body(statusesOfResidentalEstateQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /statuses-of-residental-estates/:id} : get the "id" statusesOfResidentalEstate.
     *
     * @param id the id of the statusesOfResidentalEstateDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the statusesOfResidentalEstateDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/statuses-of-residental-estates/{id}")
    public ResponseEntity<StatusesOfResidentalEstateDTO> getStatusesOfResidentalEstate(@PathVariable Long id) {
        log.debug("REST request to get StatusesOfResidentalEstate : {}", id);
        Optional<StatusesOfResidentalEstateDTO> statusesOfResidentalEstateDTO = statusesOfResidentalEstateService.findOne(id);
        return ResponseUtil.wrapOrNotFound(statusesOfResidentalEstateDTO);
    }

    /**
     * {@code DELETE  /statuses-of-residental-estates/:id} : delete the "id" statusesOfResidentalEstate.
     *
     * @param id the id of the statusesOfResidentalEstateDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/statuses-of-residental-estates/{id}")
    public ResponseEntity<Void> deleteStatusesOfResidentalEstate(@PathVariable Long id) {
        log.debug("REST request to delete StatusesOfResidentalEstate : {}", id);
        statusesOfResidentalEstateService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
