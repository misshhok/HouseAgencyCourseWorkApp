package com.bsu.app.web.rest;

import com.bsu.app.repository.BuildingTypeOfNonResidentalEstateRepository;
import com.bsu.app.service.BuildingTypeOfNonResidentalEstateQueryService;
import com.bsu.app.service.BuildingTypeOfNonResidentalEstateService;
import com.bsu.app.service.criteria.BuildingTypeOfNonResidentalEstateCriteria;
import com.bsu.app.service.dto.BuildingTypeOfNonResidentalEstateDTO;
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
 * REST controller for managing {@link com.bsu.app.domain.BuildingTypeOfNonResidentalEstate}.
 */
@RestController
@RequestMapping("/api")
public class BuildingTypeOfNonResidentalEstateResource {

    private final Logger log = LoggerFactory.getLogger(BuildingTypeOfNonResidentalEstateResource.class);

    private static final String ENTITY_NAME = "buildingTypeOfNonResidentalEstate";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BuildingTypeOfNonResidentalEstateService buildingTypeOfNonResidentalEstateService;

    private final BuildingTypeOfNonResidentalEstateRepository buildingTypeOfNonResidentalEstateRepository;

    private final BuildingTypeOfNonResidentalEstateQueryService buildingTypeOfNonResidentalEstateQueryService;

    public BuildingTypeOfNonResidentalEstateResource(
        BuildingTypeOfNonResidentalEstateService buildingTypeOfNonResidentalEstateService,
        BuildingTypeOfNonResidentalEstateRepository buildingTypeOfNonResidentalEstateRepository,
        BuildingTypeOfNonResidentalEstateQueryService buildingTypeOfNonResidentalEstateQueryService
    ) {
        this.buildingTypeOfNonResidentalEstateService = buildingTypeOfNonResidentalEstateService;
        this.buildingTypeOfNonResidentalEstateRepository = buildingTypeOfNonResidentalEstateRepository;
        this.buildingTypeOfNonResidentalEstateQueryService = buildingTypeOfNonResidentalEstateQueryService;
    }

    /**
     * {@code POST  /building-type-of-non-residental-estates} : Create a new buildingTypeOfNonResidentalEstate.
     *
     * @param buildingTypeOfNonResidentalEstateDTO the buildingTypeOfNonResidentalEstateDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new buildingTypeOfNonResidentalEstateDTO, or with status {@code 400 (Bad Request)} if the buildingTypeOfNonResidentalEstate has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/building-type-of-non-residental-estates")
    public ResponseEntity<BuildingTypeOfNonResidentalEstateDTO> createBuildingTypeOfNonResidentalEstate(
        @RequestBody BuildingTypeOfNonResidentalEstateDTO buildingTypeOfNonResidentalEstateDTO
    ) throws URISyntaxException {
        log.debug("REST request to save BuildingTypeOfNonResidentalEstate : {}", buildingTypeOfNonResidentalEstateDTO);
        if (buildingTypeOfNonResidentalEstateDTO.getId() != null) {
            throw new BadRequestAlertException(
                "A new buildingTypeOfNonResidentalEstate cannot already have an ID",
                ENTITY_NAME,
                "idexists"
            );
        }
        BuildingTypeOfNonResidentalEstateDTO result = buildingTypeOfNonResidentalEstateService.save(buildingTypeOfNonResidentalEstateDTO);
        return ResponseEntity
            .created(new URI("/api/building-type-of-non-residental-estates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /building-type-of-non-residental-estates/:id} : Updates an existing buildingTypeOfNonResidentalEstate.
     *
     * @param id the id of the buildingTypeOfNonResidentalEstateDTO to save.
     * @param buildingTypeOfNonResidentalEstateDTO the buildingTypeOfNonResidentalEstateDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated buildingTypeOfNonResidentalEstateDTO,
     * or with status {@code 400 (Bad Request)} if the buildingTypeOfNonResidentalEstateDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the buildingTypeOfNonResidentalEstateDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/building-type-of-non-residental-estates/{id}")
    public ResponseEntity<BuildingTypeOfNonResidentalEstateDTO> updateBuildingTypeOfNonResidentalEstate(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody BuildingTypeOfNonResidentalEstateDTO buildingTypeOfNonResidentalEstateDTO
    ) throws URISyntaxException {
        log.debug("REST request to update BuildingTypeOfNonResidentalEstate : {}, {}", id, buildingTypeOfNonResidentalEstateDTO);
        if (buildingTypeOfNonResidentalEstateDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, buildingTypeOfNonResidentalEstateDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!buildingTypeOfNonResidentalEstateRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        BuildingTypeOfNonResidentalEstateDTO result = buildingTypeOfNonResidentalEstateService.update(buildingTypeOfNonResidentalEstateDTO);
        return ResponseEntity
            .ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(
                    applicationName,
                    true,
                    ENTITY_NAME,
                    buildingTypeOfNonResidentalEstateDTO.getId().toString()
                )
            )
            .body(result);
    }

    /**
     * {@code PATCH  /building-type-of-non-residental-estates/:id} : Partial updates given fields of an existing buildingTypeOfNonResidentalEstate, field will ignore if it is null
     *
     * @param id the id of the buildingTypeOfNonResidentalEstateDTO to save.
     * @param buildingTypeOfNonResidentalEstateDTO the buildingTypeOfNonResidentalEstateDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated buildingTypeOfNonResidentalEstateDTO,
     * or with status {@code 400 (Bad Request)} if the buildingTypeOfNonResidentalEstateDTO is not valid,
     * or with status {@code 404 (Not Found)} if the buildingTypeOfNonResidentalEstateDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the buildingTypeOfNonResidentalEstateDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(
        value = "/building-type-of-non-residental-estates/{id}",
        consumes = { "application/json", "application/merge-patch+json" }
    )
    public ResponseEntity<BuildingTypeOfNonResidentalEstateDTO> partialUpdateBuildingTypeOfNonResidentalEstate(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody BuildingTypeOfNonResidentalEstateDTO buildingTypeOfNonResidentalEstateDTO
    ) throws URISyntaxException {
        log.debug(
            "REST request to partial update BuildingTypeOfNonResidentalEstate partially : {}, {}",
            id,
            buildingTypeOfNonResidentalEstateDTO
        );
        if (buildingTypeOfNonResidentalEstateDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, buildingTypeOfNonResidentalEstateDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!buildingTypeOfNonResidentalEstateRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BuildingTypeOfNonResidentalEstateDTO> result = buildingTypeOfNonResidentalEstateService.partialUpdate(
            buildingTypeOfNonResidentalEstateDTO
        );

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, buildingTypeOfNonResidentalEstateDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /building-type-of-non-residental-estates} : get all the buildingTypeOfNonResidentalEstates.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of buildingTypeOfNonResidentalEstates in body.
     */
    @GetMapping("/building-type-of-non-residental-estates")
    public ResponseEntity<List<BuildingTypeOfNonResidentalEstateDTO>> getAllBuildingTypeOfNonResidentalEstates(
        BuildingTypeOfNonResidentalEstateCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get BuildingTypeOfNonResidentalEstates by criteria: {}", criteria);
        Page<BuildingTypeOfNonResidentalEstateDTO> page = buildingTypeOfNonResidentalEstateQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /building-type-of-non-residental-estates/count} : count all the buildingTypeOfNonResidentalEstates.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/building-type-of-non-residental-estates/count")
    public ResponseEntity<Long> countBuildingTypeOfNonResidentalEstates(BuildingTypeOfNonResidentalEstateCriteria criteria) {
        log.debug("REST request to count BuildingTypeOfNonResidentalEstates by criteria: {}", criteria);
        return ResponseEntity.ok().body(buildingTypeOfNonResidentalEstateQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /building-type-of-non-residental-estates/:id} : get the "id" buildingTypeOfNonResidentalEstate.
     *
     * @param id the id of the buildingTypeOfNonResidentalEstateDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the buildingTypeOfNonResidentalEstateDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/building-type-of-non-residental-estates/{id}")
    public ResponseEntity<BuildingTypeOfNonResidentalEstateDTO> getBuildingTypeOfNonResidentalEstate(@PathVariable Long id) {
        log.debug("REST request to get BuildingTypeOfNonResidentalEstate : {}", id);
        Optional<BuildingTypeOfNonResidentalEstateDTO> buildingTypeOfNonResidentalEstateDTO = buildingTypeOfNonResidentalEstateService.findOne(
            id
        );
        return ResponseUtil.wrapOrNotFound(buildingTypeOfNonResidentalEstateDTO);
    }

    /**
     * {@code DELETE  /building-type-of-non-residental-estates/:id} : delete the "id" buildingTypeOfNonResidentalEstate.
     *
     * @param id the id of the buildingTypeOfNonResidentalEstateDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/building-type-of-non-residental-estates/{id}")
    public ResponseEntity<Void> deleteBuildingTypeOfNonResidentalEstate(@PathVariable Long id) {
        log.debug("REST request to delete BuildingTypeOfNonResidentalEstate : {}", id);
        buildingTypeOfNonResidentalEstateService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
