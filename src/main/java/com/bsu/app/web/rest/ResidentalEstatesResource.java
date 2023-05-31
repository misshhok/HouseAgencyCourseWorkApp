package com.bsu.app.web.rest;

import com.bsu.app.repository.ResidentalEstatesRepository;
import com.bsu.app.service.ResidentalEstatesQueryService;
import com.bsu.app.service.ResidentalEstatesService;
import com.bsu.app.service.criteria.ResidentalEstatesCriteria;
import com.bsu.app.service.dto.ResidentalEstatesDTO;
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
 * REST controller for managing {@link com.bsu.app.domain.ResidentalEstates}.
 */
@RestController
@RequestMapping("/api")
public class ResidentalEstatesResource {

    private final Logger log = LoggerFactory.getLogger(ResidentalEstatesResource.class);

    private static final String ENTITY_NAME = "residentalEstates";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ResidentalEstatesService residentalEstatesService;

    private final ResidentalEstatesRepository residentalEstatesRepository;

    private final ResidentalEstatesQueryService residentalEstatesQueryService;

    public ResidentalEstatesResource(
        ResidentalEstatesService residentalEstatesService,
        ResidentalEstatesRepository residentalEstatesRepository,
        ResidentalEstatesQueryService residentalEstatesQueryService
    ) {
        this.residentalEstatesService = residentalEstatesService;
        this.residentalEstatesRepository = residentalEstatesRepository;
        this.residentalEstatesQueryService = residentalEstatesQueryService;
    }

    /**
     * {@code POST  /residental-estates} : Create a new residentalEstates.
     *
     * @param residentalEstatesDTO the residentalEstatesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new residentalEstatesDTO, or with status {@code 400 (Bad Request)} if the residentalEstates has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/residental-estates")
    public ResponseEntity<ResidentalEstatesDTO> createResidentalEstates(@RequestBody ResidentalEstatesDTO residentalEstatesDTO)
        throws URISyntaxException {
        log.debug("REST request to save ResidentalEstates : {}", residentalEstatesDTO);
        if (residentalEstatesDTO.getId() != null) {
            throw new BadRequestAlertException("A new residentalEstates cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ResidentalEstatesDTO result = residentalEstatesService.save(residentalEstatesDTO);
        return ResponseEntity
            .created(new URI("/api/residental-estates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /residental-estates/:id} : Updates an existing residentalEstates.
     *
     * @param id the id of the residentalEstatesDTO to save.
     * @param residentalEstatesDTO the residentalEstatesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated residentalEstatesDTO,
     * or with status {@code 400 (Bad Request)} if the residentalEstatesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the residentalEstatesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/residental-estates/{id}")
    public ResponseEntity<ResidentalEstatesDTO> updateResidentalEstates(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ResidentalEstatesDTO residentalEstatesDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ResidentalEstates : {}, {}", id, residentalEstatesDTO);
        if (residentalEstatesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, residentalEstatesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!residentalEstatesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ResidentalEstatesDTO result = residentalEstatesService.update(residentalEstatesDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, residentalEstatesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /residental-estates/:id} : Partial updates given fields of an existing residentalEstates, field will ignore if it is null
     *
     * @param id the id of the residentalEstatesDTO to save.
     * @param residentalEstatesDTO the residentalEstatesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated residentalEstatesDTO,
     * or with status {@code 400 (Bad Request)} if the residentalEstatesDTO is not valid,
     * or with status {@code 404 (Not Found)} if the residentalEstatesDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the residentalEstatesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/residental-estates/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ResidentalEstatesDTO> partialUpdateResidentalEstates(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ResidentalEstatesDTO residentalEstatesDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ResidentalEstates partially : {}, {}", id, residentalEstatesDTO);
        if (residentalEstatesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, residentalEstatesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!residentalEstatesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ResidentalEstatesDTO> result = residentalEstatesService.partialUpdate(residentalEstatesDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, residentalEstatesDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /residental-estates} : get all the residentalEstates.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of residentalEstates in body.
     */
    @GetMapping("/residental-estates")
    public ResponseEntity<List<ResidentalEstatesDTO>> getAllResidentalEstates(
        ResidentalEstatesCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get ResidentalEstates by criteria: {}", criteria);
        Page<ResidentalEstatesDTO> page = residentalEstatesQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /residental-estates/count} : count all the residentalEstates.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/residental-estates/count")
    public ResponseEntity<Long> countResidentalEstates(ResidentalEstatesCriteria criteria) {
        log.debug("REST request to count ResidentalEstates by criteria: {}", criteria);
        return ResponseEntity.ok().body(residentalEstatesQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /residental-estates/:id} : get the "id" residentalEstates.
     *
     * @param id the id of the residentalEstatesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the residentalEstatesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/residental-estates/{id}")
    public ResponseEntity<ResidentalEstatesDTO> getResidentalEstates(@PathVariable Long id) {
        log.debug("REST request to get ResidentalEstates : {}", id);
        Optional<ResidentalEstatesDTO> residentalEstatesDTO = residentalEstatesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(residentalEstatesDTO);
    }

    /**
     * {@code DELETE  /residental-estates/:id} : delete the "id" residentalEstates.
     *
     * @param id the id of the residentalEstatesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/residental-estates/{id}")
    public ResponseEntity<Void> deleteResidentalEstates(@PathVariable Long id) {
        log.debug("REST request to delete ResidentalEstates : {}", id);
        residentalEstatesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
