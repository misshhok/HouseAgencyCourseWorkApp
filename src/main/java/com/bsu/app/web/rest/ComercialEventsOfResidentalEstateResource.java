package com.bsu.app.web.rest;

import com.bsu.app.repository.ComercialEventsOfResidentalEstateRepository;
import com.bsu.app.service.ComercialEventsOfResidentalEstateQueryService;
import com.bsu.app.service.ComercialEventsOfResidentalEstateService;
import com.bsu.app.service.criteria.ComercialEventsOfResidentalEstateCriteria;
import com.bsu.app.service.dto.ComercialEventsOfResidentalEstateDTO;
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
 * REST controller for managing {@link com.bsu.app.domain.ComercialEventsOfResidentalEstate}.
 */
@RestController
@RequestMapping("/api")
public class ComercialEventsOfResidentalEstateResource {

    private final Logger log = LoggerFactory.getLogger(ComercialEventsOfResidentalEstateResource.class);

    private static final String ENTITY_NAME = "comercialEventsOfResidentalEstate";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ComercialEventsOfResidentalEstateService comercialEventsOfResidentalEstateService;

    private final ComercialEventsOfResidentalEstateRepository comercialEventsOfResidentalEstateRepository;

    private final ComercialEventsOfResidentalEstateQueryService comercialEventsOfResidentalEstateQueryService;

    public ComercialEventsOfResidentalEstateResource(
        ComercialEventsOfResidentalEstateService comercialEventsOfResidentalEstateService,
        ComercialEventsOfResidentalEstateRepository comercialEventsOfResidentalEstateRepository,
        ComercialEventsOfResidentalEstateQueryService comercialEventsOfResidentalEstateQueryService
    ) {
        this.comercialEventsOfResidentalEstateService = comercialEventsOfResidentalEstateService;
        this.comercialEventsOfResidentalEstateRepository = comercialEventsOfResidentalEstateRepository;
        this.comercialEventsOfResidentalEstateQueryService = comercialEventsOfResidentalEstateQueryService;
    }

    /**
     * {@code POST  /comercial-events-of-residental-estates} : Create a new comercialEventsOfResidentalEstate.
     *
     * @param comercialEventsOfResidentalEstateDTO the comercialEventsOfResidentalEstateDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new comercialEventsOfResidentalEstateDTO, or with status {@code 400 (Bad Request)} if the comercialEventsOfResidentalEstate has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/comercial-events-of-residental-estates")
    public ResponseEntity<ComercialEventsOfResidentalEstateDTO> createComercialEventsOfResidentalEstate(
        @RequestBody ComercialEventsOfResidentalEstateDTO comercialEventsOfResidentalEstateDTO
    ) throws URISyntaxException {
        log.debug("REST request to save ComercialEventsOfResidentalEstate : {}", comercialEventsOfResidentalEstateDTO);
        if (comercialEventsOfResidentalEstateDTO.getId() != null) {
            throw new BadRequestAlertException(
                "A new comercialEventsOfResidentalEstate cannot already have an ID",
                ENTITY_NAME,
                "idexists"
            );
        }
        ComercialEventsOfResidentalEstateDTO result = comercialEventsOfResidentalEstateService.save(comercialEventsOfResidentalEstateDTO);
        return ResponseEntity
            .created(new URI("/api/comercial-events-of-residental-estates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /comercial-events-of-residental-estates/:id} : Updates an existing comercialEventsOfResidentalEstate.
     *
     * @param id the id of the comercialEventsOfResidentalEstateDTO to save.
     * @param comercialEventsOfResidentalEstateDTO the comercialEventsOfResidentalEstateDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated comercialEventsOfResidentalEstateDTO,
     * or with status {@code 400 (Bad Request)} if the comercialEventsOfResidentalEstateDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the comercialEventsOfResidentalEstateDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/comercial-events-of-residental-estates/{id}")
    public ResponseEntity<ComercialEventsOfResidentalEstateDTO> updateComercialEventsOfResidentalEstate(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ComercialEventsOfResidentalEstateDTO comercialEventsOfResidentalEstateDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ComercialEventsOfResidentalEstate : {}, {}", id, comercialEventsOfResidentalEstateDTO);
        if (comercialEventsOfResidentalEstateDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, comercialEventsOfResidentalEstateDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!comercialEventsOfResidentalEstateRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ComercialEventsOfResidentalEstateDTO result = comercialEventsOfResidentalEstateService.update(comercialEventsOfResidentalEstateDTO);
        return ResponseEntity
            .ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(
                    applicationName,
                    true,
                    ENTITY_NAME,
                    comercialEventsOfResidentalEstateDTO.getId().toString()
                )
            )
            .body(result);
    }

    /**
     * {@code PATCH  /comercial-events-of-residental-estates/:id} : Partial updates given fields of an existing comercialEventsOfResidentalEstate, field will ignore if it is null
     *
     * @param id the id of the comercialEventsOfResidentalEstateDTO to save.
     * @param comercialEventsOfResidentalEstateDTO the comercialEventsOfResidentalEstateDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated comercialEventsOfResidentalEstateDTO,
     * or with status {@code 400 (Bad Request)} if the comercialEventsOfResidentalEstateDTO is not valid,
     * or with status {@code 404 (Not Found)} if the comercialEventsOfResidentalEstateDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the comercialEventsOfResidentalEstateDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/comercial-events-of-residental-estates/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ComercialEventsOfResidentalEstateDTO> partialUpdateComercialEventsOfResidentalEstate(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ComercialEventsOfResidentalEstateDTO comercialEventsOfResidentalEstateDTO
    ) throws URISyntaxException {
        log.debug(
            "REST request to partial update ComercialEventsOfResidentalEstate partially : {}, {}",
            id,
            comercialEventsOfResidentalEstateDTO
        );
        if (comercialEventsOfResidentalEstateDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, comercialEventsOfResidentalEstateDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!comercialEventsOfResidentalEstateRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ComercialEventsOfResidentalEstateDTO> result = comercialEventsOfResidentalEstateService.partialUpdate(
            comercialEventsOfResidentalEstateDTO
        );

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, comercialEventsOfResidentalEstateDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /comercial-events-of-residental-estates} : get all the comercialEventsOfResidentalEstates.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of comercialEventsOfResidentalEstates in body.
     */
    @GetMapping("/comercial-events-of-residental-estates")
    public ResponseEntity<List<ComercialEventsOfResidentalEstateDTO>> getAllComercialEventsOfResidentalEstates(
        ComercialEventsOfResidentalEstateCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get ComercialEventsOfResidentalEstates by criteria: {}", criteria);
        Page<ComercialEventsOfResidentalEstateDTO> page = comercialEventsOfResidentalEstateQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /comercial-events-of-residental-estates/count} : count all the comercialEventsOfResidentalEstates.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/comercial-events-of-residental-estates/count")
    public ResponseEntity<Long> countComercialEventsOfResidentalEstates(ComercialEventsOfResidentalEstateCriteria criteria) {
        log.debug("REST request to count ComercialEventsOfResidentalEstates by criteria: {}", criteria);
        return ResponseEntity.ok().body(comercialEventsOfResidentalEstateQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /comercial-events-of-residental-estates/:id} : get the "id" comercialEventsOfResidentalEstate.
     *
     * @param id the id of the comercialEventsOfResidentalEstateDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the comercialEventsOfResidentalEstateDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/comercial-events-of-residental-estates/{id}")
    public ResponseEntity<ComercialEventsOfResidentalEstateDTO> getComercialEventsOfResidentalEstate(@PathVariable Long id) {
        log.debug("REST request to get ComercialEventsOfResidentalEstate : {}", id);
        Optional<ComercialEventsOfResidentalEstateDTO> comercialEventsOfResidentalEstateDTO = comercialEventsOfResidentalEstateService.findOne(
            id
        );
        return ResponseUtil.wrapOrNotFound(comercialEventsOfResidentalEstateDTO);
    }

    /**
     * {@code DELETE  /comercial-events-of-residental-estates/:id} : delete the "id" comercialEventsOfResidentalEstate.
     *
     * @param id the id of the comercialEventsOfResidentalEstateDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/comercial-events-of-residental-estates/{id}")
    public ResponseEntity<Void> deleteComercialEventsOfResidentalEstate(@PathVariable Long id) {
        log.debug("REST request to delete ComercialEventsOfResidentalEstate : {}", id);
        comercialEventsOfResidentalEstateService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
