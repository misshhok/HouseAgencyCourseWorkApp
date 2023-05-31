package com.bsu.app.web.rest;

import com.bsu.app.repository.StreetsOfCitiesRepository;
import com.bsu.app.service.StreetsOfCitiesQueryService;
import com.bsu.app.service.StreetsOfCitiesService;
import com.bsu.app.service.criteria.StreetsOfCitiesCriteria;
import com.bsu.app.service.dto.StreetsOfCitiesDTO;
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
 * REST controller for managing {@link com.bsu.app.domain.StreetsOfCities}.
 */
@RestController
@RequestMapping("/api")
public class StreetsOfCitiesResource {

    private final Logger log = LoggerFactory.getLogger(StreetsOfCitiesResource.class);

    private static final String ENTITY_NAME = "streetsOfCities";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StreetsOfCitiesService streetsOfCitiesService;

    private final StreetsOfCitiesRepository streetsOfCitiesRepository;

    private final StreetsOfCitiesQueryService streetsOfCitiesQueryService;

    public StreetsOfCitiesResource(
        StreetsOfCitiesService streetsOfCitiesService,
        StreetsOfCitiesRepository streetsOfCitiesRepository,
        StreetsOfCitiesQueryService streetsOfCitiesQueryService
    ) {
        this.streetsOfCitiesService = streetsOfCitiesService;
        this.streetsOfCitiesRepository = streetsOfCitiesRepository;
        this.streetsOfCitiesQueryService = streetsOfCitiesQueryService;
    }

    /**
     * {@code POST  /streets-of-cities} : Create a new streetsOfCities.
     *
     * @param streetsOfCitiesDTO the streetsOfCitiesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new streetsOfCitiesDTO, or with status {@code 400 (Bad Request)} if the streetsOfCities has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/streets-of-cities")
    public ResponseEntity<StreetsOfCitiesDTO> createStreetsOfCities(@RequestBody StreetsOfCitiesDTO streetsOfCitiesDTO)
        throws URISyntaxException {
        log.debug("REST request to save StreetsOfCities : {}", streetsOfCitiesDTO);
        if (streetsOfCitiesDTO.getId() != null) {
            throw new BadRequestAlertException("A new streetsOfCities cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StreetsOfCitiesDTO result = streetsOfCitiesService.save(streetsOfCitiesDTO);
        return ResponseEntity
            .created(new URI("/api/streets-of-cities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /streets-of-cities/:id} : Updates an existing streetsOfCities.
     *
     * @param id the id of the streetsOfCitiesDTO to save.
     * @param streetsOfCitiesDTO the streetsOfCitiesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated streetsOfCitiesDTO,
     * or with status {@code 400 (Bad Request)} if the streetsOfCitiesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the streetsOfCitiesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/streets-of-cities/{id}")
    public ResponseEntity<StreetsOfCitiesDTO> updateStreetsOfCities(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody StreetsOfCitiesDTO streetsOfCitiesDTO
    ) throws URISyntaxException {
        log.debug("REST request to update StreetsOfCities : {}, {}", id, streetsOfCitiesDTO);
        if (streetsOfCitiesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, streetsOfCitiesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!streetsOfCitiesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        StreetsOfCitiesDTO result = streetsOfCitiesService.update(streetsOfCitiesDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, streetsOfCitiesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /streets-of-cities/:id} : Partial updates given fields of an existing streetsOfCities, field will ignore if it is null
     *
     * @param id the id of the streetsOfCitiesDTO to save.
     * @param streetsOfCitiesDTO the streetsOfCitiesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated streetsOfCitiesDTO,
     * or with status {@code 400 (Bad Request)} if the streetsOfCitiesDTO is not valid,
     * or with status {@code 404 (Not Found)} if the streetsOfCitiesDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the streetsOfCitiesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/streets-of-cities/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<StreetsOfCitiesDTO> partialUpdateStreetsOfCities(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody StreetsOfCitiesDTO streetsOfCitiesDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update StreetsOfCities partially : {}, {}", id, streetsOfCitiesDTO);
        if (streetsOfCitiesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, streetsOfCitiesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!streetsOfCitiesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<StreetsOfCitiesDTO> result = streetsOfCitiesService.partialUpdate(streetsOfCitiesDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, streetsOfCitiesDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /streets-of-cities} : get all the streetsOfCities.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of streetsOfCities in body.
     */
    @GetMapping("/streets-of-cities")
    public ResponseEntity<List<StreetsOfCitiesDTO>> getAllStreetsOfCities(
        StreetsOfCitiesCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get StreetsOfCities by criteria: {}", criteria);
        Page<StreetsOfCitiesDTO> page = streetsOfCitiesQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /streets-of-cities/count} : count all the streetsOfCities.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/streets-of-cities/count")
    public ResponseEntity<Long> countStreetsOfCities(StreetsOfCitiesCriteria criteria) {
        log.debug("REST request to count StreetsOfCities by criteria: {}", criteria);
        return ResponseEntity.ok().body(streetsOfCitiesQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /streets-of-cities/:id} : get the "id" streetsOfCities.
     *
     * @param id the id of the streetsOfCitiesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the streetsOfCitiesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/streets-of-cities/{id}")
    public ResponseEntity<StreetsOfCitiesDTO> getStreetsOfCities(@PathVariable Long id) {
        log.debug("REST request to get StreetsOfCities : {}", id);
        Optional<StreetsOfCitiesDTO> streetsOfCitiesDTO = streetsOfCitiesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(streetsOfCitiesDTO);
    }

    /**
     * {@code DELETE  /streets-of-cities/:id} : delete the "id" streetsOfCities.
     *
     * @param id the id of the streetsOfCitiesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/streets-of-cities/{id}")
    public ResponseEntity<Void> deleteStreetsOfCities(@PathVariable Long id) {
        log.debug("REST request to delete StreetsOfCities : {}", id);
        streetsOfCitiesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
