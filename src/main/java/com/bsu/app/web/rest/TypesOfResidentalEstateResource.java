package com.bsu.app.web.rest;

import com.bsu.app.repository.TypesOfResidentalEstateRepository;
import com.bsu.app.service.TypesOfResidentalEstateQueryService;
import com.bsu.app.service.TypesOfResidentalEstateService;
import com.bsu.app.service.criteria.TypesOfResidentalEstateCriteria;
import com.bsu.app.service.dto.TypesOfResidentalEstateDTO;
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
 * REST controller for managing {@link com.bsu.app.domain.TypesOfResidentalEstate}.
 */
@RestController
@RequestMapping("/api")
public class TypesOfResidentalEstateResource {

    private final Logger log = LoggerFactory.getLogger(TypesOfResidentalEstateResource.class);

    private static final String ENTITY_NAME = "typesOfResidentalEstate";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TypesOfResidentalEstateService typesOfResidentalEstateService;

    private final TypesOfResidentalEstateRepository typesOfResidentalEstateRepository;

    private final TypesOfResidentalEstateQueryService typesOfResidentalEstateQueryService;

    public TypesOfResidentalEstateResource(
        TypesOfResidentalEstateService typesOfResidentalEstateService,
        TypesOfResidentalEstateRepository typesOfResidentalEstateRepository,
        TypesOfResidentalEstateQueryService typesOfResidentalEstateQueryService
    ) {
        this.typesOfResidentalEstateService = typesOfResidentalEstateService;
        this.typesOfResidentalEstateRepository = typesOfResidentalEstateRepository;
        this.typesOfResidentalEstateQueryService = typesOfResidentalEstateQueryService;
    }

    /**
     * {@code POST  /types-of-residental-estates} : Create a new typesOfResidentalEstate.
     *
     * @param typesOfResidentalEstateDTO the typesOfResidentalEstateDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new typesOfResidentalEstateDTO, or with status {@code 400 (Bad Request)} if the typesOfResidentalEstate has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/types-of-residental-estates")
    public ResponseEntity<TypesOfResidentalEstateDTO> createTypesOfResidentalEstate(
        @RequestBody TypesOfResidentalEstateDTO typesOfResidentalEstateDTO
    ) throws URISyntaxException {
        log.debug("REST request to save TypesOfResidentalEstate : {}", typesOfResidentalEstateDTO);
        if (typesOfResidentalEstateDTO.getId() != null) {
            throw new BadRequestAlertException("A new typesOfResidentalEstate cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TypesOfResidentalEstateDTO result = typesOfResidentalEstateService.save(typesOfResidentalEstateDTO);
        return ResponseEntity
            .created(new URI("/api/types-of-residental-estates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /types-of-residental-estates/:id} : Updates an existing typesOfResidentalEstate.
     *
     * @param id the id of the typesOfResidentalEstateDTO to save.
     * @param typesOfResidentalEstateDTO the typesOfResidentalEstateDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated typesOfResidentalEstateDTO,
     * or with status {@code 400 (Bad Request)} if the typesOfResidentalEstateDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the typesOfResidentalEstateDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/types-of-residental-estates/{id}")
    public ResponseEntity<TypesOfResidentalEstateDTO> updateTypesOfResidentalEstate(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TypesOfResidentalEstateDTO typesOfResidentalEstateDTO
    ) throws URISyntaxException {
        log.debug("REST request to update TypesOfResidentalEstate : {}, {}", id, typesOfResidentalEstateDTO);
        if (typesOfResidentalEstateDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, typesOfResidentalEstateDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!typesOfResidentalEstateRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TypesOfResidentalEstateDTO result = typesOfResidentalEstateService.update(typesOfResidentalEstateDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, typesOfResidentalEstateDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /types-of-residental-estates/:id} : Partial updates given fields of an existing typesOfResidentalEstate, field will ignore if it is null
     *
     * @param id the id of the typesOfResidentalEstateDTO to save.
     * @param typesOfResidentalEstateDTO the typesOfResidentalEstateDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated typesOfResidentalEstateDTO,
     * or with status {@code 400 (Bad Request)} if the typesOfResidentalEstateDTO is not valid,
     * or with status {@code 404 (Not Found)} if the typesOfResidentalEstateDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the typesOfResidentalEstateDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/types-of-residental-estates/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TypesOfResidentalEstateDTO> partialUpdateTypesOfResidentalEstate(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TypesOfResidentalEstateDTO typesOfResidentalEstateDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update TypesOfResidentalEstate partially : {}, {}", id, typesOfResidentalEstateDTO);
        if (typesOfResidentalEstateDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, typesOfResidentalEstateDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!typesOfResidentalEstateRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TypesOfResidentalEstateDTO> result = typesOfResidentalEstateService.partialUpdate(typesOfResidentalEstateDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, typesOfResidentalEstateDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /types-of-residental-estates} : get all the typesOfResidentalEstates.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of typesOfResidentalEstates in body.
     */
    @GetMapping("/types-of-residental-estates")
    public ResponseEntity<List<TypesOfResidentalEstateDTO>> getAllTypesOfResidentalEstates(
        TypesOfResidentalEstateCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get TypesOfResidentalEstates by criteria: {}", criteria);
        Page<TypesOfResidentalEstateDTO> page = typesOfResidentalEstateQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /types-of-residental-estates/count} : count all the typesOfResidentalEstates.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/types-of-residental-estates/count")
    public ResponseEntity<Long> countTypesOfResidentalEstates(TypesOfResidentalEstateCriteria criteria) {
        log.debug("REST request to count TypesOfResidentalEstates by criteria: {}", criteria);
        return ResponseEntity.ok().body(typesOfResidentalEstateQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /types-of-residental-estates/:id} : get the "id" typesOfResidentalEstate.
     *
     * @param id the id of the typesOfResidentalEstateDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the typesOfResidentalEstateDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/types-of-residental-estates/{id}")
    public ResponseEntity<TypesOfResidentalEstateDTO> getTypesOfResidentalEstate(@PathVariable Long id) {
        log.debug("REST request to get TypesOfResidentalEstate : {}", id);
        Optional<TypesOfResidentalEstateDTO> typesOfResidentalEstateDTO = typesOfResidentalEstateService.findOne(id);
        return ResponseUtil.wrapOrNotFound(typesOfResidentalEstateDTO);
    }

    /**
     * {@code DELETE  /types-of-residental-estates/:id} : delete the "id" typesOfResidentalEstate.
     *
     * @param id the id of the typesOfResidentalEstateDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/types-of-residental-estates/{id}")
    public ResponseEntity<Void> deleteTypesOfResidentalEstate(@PathVariable Long id) {
        log.debug("REST request to delete TypesOfResidentalEstate : {}", id);
        typesOfResidentalEstateService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
