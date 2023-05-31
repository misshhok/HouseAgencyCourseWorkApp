package com.bsu.app.web.rest;

import com.bsu.app.repository.ComercialEventsOfNonResidentalEstateRepository;
import com.bsu.app.service.ComercialEventsOfNonResidentalEstateQueryService;
import com.bsu.app.service.ComercialEventsOfNonResidentalEstateService;
import com.bsu.app.service.criteria.ComercialEventsOfNonResidentalEstateCriteria;
import com.bsu.app.service.dto.ComercialEventsOfNonResidentalEstateDTO;
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
 * REST controller for managing {@link com.bsu.app.domain.ComercialEventsOfNonResidentalEstate}.
 */
@RestController
@RequestMapping("/api")
public class ComercialEventsOfNonResidentalEstateResource {

    private final Logger log = LoggerFactory.getLogger(ComercialEventsOfNonResidentalEstateResource.class);

    private static final String ENTITY_NAME = "comercialEventsOfNonResidentalEstate";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ComercialEventsOfNonResidentalEstateService comercialEventsOfNonResidentalEstateService;

    private final ComercialEventsOfNonResidentalEstateRepository comercialEventsOfNonResidentalEstateRepository;

    private final ComercialEventsOfNonResidentalEstateQueryService comercialEventsOfNonResidentalEstateQueryService;

    public ComercialEventsOfNonResidentalEstateResource(
        ComercialEventsOfNonResidentalEstateService comercialEventsOfNonResidentalEstateService,
        ComercialEventsOfNonResidentalEstateRepository comercialEventsOfNonResidentalEstateRepository,
        ComercialEventsOfNonResidentalEstateQueryService comercialEventsOfNonResidentalEstateQueryService
    ) {
        this.comercialEventsOfNonResidentalEstateService = comercialEventsOfNonResidentalEstateService;
        this.comercialEventsOfNonResidentalEstateRepository = comercialEventsOfNonResidentalEstateRepository;
        this.comercialEventsOfNonResidentalEstateQueryService = comercialEventsOfNonResidentalEstateQueryService;
    }

    /**
     * {@code POST  /comercial-events-of-non-residental-estates} : Create a new comercialEventsOfNonResidentalEstate.
     *
     * @param comercialEventsOfNonResidentalEstateDTO the comercialEventsOfNonResidentalEstateDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new comercialEventsOfNonResidentalEstateDTO, or with status {@code 400 (Bad Request)} if the comercialEventsOfNonResidentalEstate has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/comercial-events-of-non-residental-estates")
    public ResponseEntity<ComercialEventsOfNonResidentalEstateDTO> createComercialEventsOfNonResidentalEstate(
        @RequestBody ComercialEventsOfNonResidentalEstateDTO comercialEventsOfNonResidentalEstateDTO
    ) throws URISyntaxException {
        log.debug("REST request to save ComercialEventsOfNonResidentalEstate : {}", comercialEventsOfNonResidentalEstateDTO);
        if (comercialEventsOfNonResidentalEstateDTO.getId() != null) {
            throw new BadRequestAlertException(
                "A new comercialEventsOfNonResidentalEstate cannot already have an ID",
                ENTITY_NAME,
                "idexists"
            );
        }
        ComercialEventsOfNonResidentalEstateDTO result = comercialEventsOfNonResidentalEstateService.save(
            comercialEventsOfNonResidentalEstateDTO
        );
        return ResponseEntity
            .created(new URI("/api/comercial-events-of-non-residental-estates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /comercial-events-of-non-residental-estates/:id} : Updates an existing comercialEventsOfNonResidentalEstate.
     *
     * @param id the id of the comercialEventsOfNonResidentalEstateDTO to save.
     * @param comercialEventsOfNonResidentalEstateDTO the comercialEventsOfNonResidentalEstateDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated comercialEventsOfNonResidentalEstateDTO,
     * or with status {@code 400 (Bad Request)} if the comercialEventsOfNonResidentalEstateDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the comercialEventsOfNonResidentalEstateDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/comercial-events-of-non-residental-estates/{id}")
    public ResponseEntity<ComercialEventsOfNonResidentalEstateDTO> updateComercialEventsOfNonResidentalEstate(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ComercialEventsOfNonResidentalEstateDTO comercialEventsOfNonResidentalEstateDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ComercialEventsOfNonResidentalEstate : {}, {}", id, comercialEventsOfNonResidentalEstateDTO);
        if (comercialEventsOfNonResidentalEstateDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, comercialEventsOfNonResidentalEstateDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!comercialEventsOfNonResidentalEstateRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ComercialEventsOfNonResidentalEstateDTO result = comercialEventsOfNonResidentalEstateService.update(
            comercialEventsOfNonResidentalEstateDTO
        );
        return ResponseEntity
            .ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(
                    applicationName,
                    true,
                    ENTITY_NAME,
                    comercialEventsOfNonResidentalEstateDTO.getId().toString()
                )
            )
            .body(result);
    }

    /**
     * {@code PATCH  /comercial-events-of-non-residental-estates/:id} : Partial updates given fields of an existing comercialEventsOfNonResidentalEstate, field will ignore if it is null
     *
     * @param id the id of the comercialEventsOfNonResidentalEstateDTO to save.
     * @param comercialEventsOfNonResidentalEstateDTO the comercialEventsOfNonResidentalEstateDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated comercialEventsOfNonResidentalEstateDTO,
     * or with status {@code 400 (Bad Request)} if the comercialEventsOfNonResidentalEstateDTO is not valid,
     * or with status {@code 404 (Not Found)} if the comercialEventsOfNonResidentalEstateDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the comercialEventsOfNonResidentalEstateDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(
        value = "/comercial-events-of-non-residental-estates/{id}",
        consumes = { "application/json", "application/merge-patch+json" }
    )
    public ResponseEntity<ComercialEventsOfNonResidentalEstateDTO> partialUpdateComercialEventsOfNonResidentalEstate(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ComercialEventsOfNonResidentalEstateDTO comercialEventsOfNonResidentalEstateDTO
    ) throws URISyntaxException {
        log.debug(
            "REST request to partial update ComercialEventsOfNonResidentalEstate partially : {}, {}",
            id,
            comercialEventsOfNonResidentalEstateDTO
        );
        if (comercialEventsOfNonResidentalEstateDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, comercialEventsOfNonResidentalEstateDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!comercialEventsOfNonResidentalEstateRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ComercialEventsOfNonResidentalEstateDTO> result = comercialEventsOfNonResidentalEstateService.partialUpdate(
            comercialEventsOfNonResidentalEstateDTO
        );

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(
                applicationName,
                true,
                ENTITY_NAME,
                comercialEventsOfNonResidentalEstateDTO.getId().toString()
            )
        );
    }

    /**
     * {@code GET  /comercial-events-of-non-residental-estates} : get all the comercialEventsOfNonResidentalEstates.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of comercialEventsOfNonResidentalEstates in body.
     */
    @GetMapping("/comercial-events-of-non-residental-estates")
    public ResponseEntity<List<ComercialEventsOfNonResidentalEstateDTO>> getAllComercialEventsOfNonResidentalEstates(
        ComercialEventsOfNonResidentalEstateCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get ComercialEventsOfNonResidentalEstates by criteria: {}", criteria);
        Page<ComercialEventsOfNonResidentalEstateDTO> page = comercialEventsOfNonResidentalEstateQueryService.findByCriteria(
            criteria,
            pageable
        );
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /comercial-events-of-non-residental-estates/count} : count all the comercialEventsOfNonResidentalEstates.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/comercial-events-of-non-residental-estates/count")
    public ResponseEntity<Long> countComercialEventsOfNonResidentalEstates(ComercialEventsOfNonResidentalEstateCriteria criteria) {
        log.debug("REST request to count ComercialEventsOfNonResidentalEstates by criteria: {}", criteria);
        return ResponseEntity.ok().body(comercialEventsOfNonResidentalEstateQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /comercial-events-of-non-residental-estates/:id} : get the "id" comercialEventsOfNonResidentalEstate.
     *
     * @param id the id of the comercialEventsOfNonResidentalEstateDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the comercialEventsOfNonResidentalEstateDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/comercial-events-of-non-residental-estates/{id}")
    public ResponseEntity<ComercialEventsOfNonResidentalEstateDTO> getComercialEventsOfNonResidentalEstate(@PathVariable Long id) {
        log.debug("REST request to get ComercialEventsOfNonResidentalEstate : {}", id);
        Optional<ComercialEventsOfNonResidentalEstateDTO> comercialEventsOfNonResidentalEstateDTO = comercialEventsOfNonResidentalEstateService.findOne(
            id
        );
        return ResponseUtil.wrapOrNotFound(comercialEventsOfNonResidentalEstateDTO);
    }

    /**
     * {@code DELETE  /comercial-events-of-non-residental-estates/:id} : delete the "id" comercialEventsOfNonResidentalEstate.
     *
     * @param id the id of the comercialEventsOfNonResidentalEstateDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/comercial-events-of-non-residental-estates/{id}")
    public ResponseEntity<Void> deleteComercialEventsOfNonResidentalEstate(@PathVariable Long id) {
        log.debug("REST request to delete ComercialEventsOfNonResidentalEstate : {}", id);
        comercialEventsOfNonResidentalEstateService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
