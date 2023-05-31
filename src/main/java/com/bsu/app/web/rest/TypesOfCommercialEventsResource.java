package com.bsu.app.web.rest;

import com.bsu.app.repository.TypesOfCommercialEventsRepository;
import com.bsu.app.service.TypesOfCommercialEventsQueryService;
import com.bsu.app.service.TypesOfCommercialEventsService;
import com.bsu.app.service.criteria.TypesOfCommercialEventsCriteria;
import com.bsu.app.service.dto.TypesOfCommercialEventsDTO;
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
 * REST controller for managing {@link com.bsu.app.domain.TypesOfCommercialEvents}.
 */
@RestController
@RequestMapping("/api")
public class TypesOfCommercialEventsResource {

    private final Logger log = LoggerFactory.getLogger(TypesOfCommercialEventsResource.class);

    private static final String ENTITY_NAME = "typesOfCommercialEvents";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TypesOfCommercialEventsService typesOfCommercialEventsService;

    private final TypesOfCommercialEventsRepository typesOfCommercialEventsRepository;

    private final TypesOfCommercialEventsQueryService typesOfCommercialEventsQueryService;

    public TypesOfCommercialEventsResource(
        TypesOfCommercialEventsService typesOfCommercialEventsService,
        TypesOfCommercialEventsRepository typesOfCommercialEventsRepository,
        TypesOfCommercialEventsQueryService typesOfCommercialEventsQueryService
    ) {
        this.typesOfCommercialEventsService = typesOfCommercialEventsService;
        this.typesOfCommercialEventsRepository = typesOfCommercialEventsRepository;
        this.typesOfCommercialEventsQueryService = typesOfCommercialEventsQueryService;
    }

    /**
     * {@code POST  /types-of-commercial-events} : Create a new typesOfCommercialEvents.
     *
     * @param typesOfCommercialEventsDTO the typesOfCommercialEventsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new typesOfCommercialEventsDTO, or with status {@code 400 (Bad Request)} if the typesOfCommercialEvents has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/types-of-commercial-events")
    public ResponseEntity<TypesOfCommercialEventsDTO> createTypesOfCommercialEvents(
        @RequestBody TypesOfCommercialEventsDTO typesOfCommercialEventsDTO
    ) throws URISyntaxException {
        log.debug("REST request to save TypesOfCommercialEvents : {}", typesOfCommercialEventsDTO);
        if (typesOfCommercialEventsDTO.getId() != null) {
            throw new BadRequestAlertException("A new typesOfCommercialEvents cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TypesOfCommercialEventsDTO result = typesOfCommercialEventsService.save(typesOfCommercialEventsDTO);
        return ResponseEntity
            .created(new URI("/api/types-of-commercial-events/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /types-of-commercial-events/:id} : Updates an existing typesOfCommercialEvents.
     *
     * @param id the id of the typesOfCommercialEventsDTO to save.
     * @param typesOfCommercialEventsDTO the typesOfCommercialEventsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated typesOfCommercialEventsDTO,
     * or with status {@code 400 (Bad Request)} if the typesOfCommercialEventsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the typesOfCommercialEventsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/types-of-commercial-events/{id}")
    public ResponseEntity<TypesOfCommercialEventsDTO> updateTypesOfCommercialEvents(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TypesOfCommercialEventsDTO typesOfCommercialEventsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update TypesOfCommercialEvents : {}, {}", id, typesOfCommercialEventsDTO);
        if (typesOfCommercialEventsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, typesOfCommercialEventsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!typesOfCommercialEventsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TypesOfCommercialEventsDTO result = typesOfCommercialEventsService.update(typesOfCommercialEventsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, typesOfCommercialEventsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /types-of-commercial-events/:id} : Partial updates given fields of an existing typesOfCommercialEvents, field will ignore if it is null
     *
     * @param id the id of the typesOfCommercialEventsDTO to save.
     * @param typesOfCommercialEventsDTO the typesOfCommercialEventsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated typesOfCommercialEventsDTO,
     * or with status {@code 400 (Bad Request)} if the typesOfCommercialEventsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the typesOfCommercialEventsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the typesOfCommercialEventsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/types-of-commercial-events/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TypesOfCommercialEventsDTO> partialUpdateTypesOfCommercialEvents(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TypesOfCommercialEventsDTO typesOfCommercialEventsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update TypesOfCommercialEvents partially : {}, {}", id, typesOfCommercialEventsDTO);
        if (typesOfCommercialEventsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, typesOfCommercialEventsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!typesOfCommercialEventsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TypesOfCommercialEventsDTO> result = typesOfCommercialEventsService.partialUpdate(typesOfCommercialEventsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, typesOfCommercialEventsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /types-of-commercial-events} : get all the typesOfCommercialEvents.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of typesOfCommercialEvents in body.
     */
    @GetMapping("/types-of-commercial-events")
    public ResponseEntity<List<TypesOfCommercialEventsDTO>> getAllTypesOfCommercialEvents(
        TypesOfCommercialEventsCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get TypesOfCommercialEvents by criteria: {}", criteria);
        Page<TypesOfCommercialEventsDTO> page = typesOfCommercialEventsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /types-of-commercial-events/count} : count all the typesOfCommercialEvents.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/types-of-commercial-events/count")
    public ResponseEntity<Long> countTypesOfCommercialEvents(TypesOfCommercialEventsCriteria criteria) {
        log.debug("REST request to count TypesOfCommercialEvents by criteria: {}", criteria);
        return ResponseEntity.ok().body(typesOfCommercialEventsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /types-of-commercial-events/:id} : get the "id" typesOfCommercialEvents.
     *
     * @param id the id of the typesOfCommercialEventsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the typesOfCommercialEventsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/types-of-commercial-events/{id}")
    public ResponseEntity<TypesOfCommercialEventsDTO> getTypesOfCommercialEvents(@PathVariable Long id) {
        log.debug("REST request to get TypesOfCommercialEvents : {}", id);
        Optional<TypesOfCommercialEventsDTO> typesOfCommercialEventsDTO = typesOfCommercialEventsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(typesOfCommercialEventsDTO);
    }

    /**
     * {@code DELETE  /types-of-commercial-events/:id} : delete the "id" typesOfCommercialEvents.
     *
     * @param id the id of the typesOfCommercialEventsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/types-of-commercial-events/{id}")
    public ResponseEntity<Void> deleteTypesOfCommercialEvents(@PathVariable Long id) {
        log.debug("REST request to delete TypesOfCommercialEvents : {}", id);
        typesOfCommercialEventsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
