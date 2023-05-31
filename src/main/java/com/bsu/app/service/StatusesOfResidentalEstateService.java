package com.bsu.app.service;

import com.bsu.app.service.dto.StatusesOfResidentalEstateDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.bsu.app.domain.StatusesOfResidentalEstate}.
 */
public interface StatusesOfResidentalEstateService {
    /**
     * Save a statusesOfResidentalEstate.
     *
     * @param statusesOfResidentalEstateDTO the entity to save.
     * @return the persisted entity.
     */
    StatusesOfResidentalEstateDTO save(StatusesOfResidentalEstateDTO statusesOfResidentalEstateDTO);

    /**
     * Updates a statusesOfResidentalEstate.
     *
     * @param statusesOfResidentalEstateDTO the entity to update.
     * @return the persisted entity.
     */
    StatusesOfResidentalEstateDTO update(StatusesOfResidentalEstateDTO statusesOfResidentalEstateDTO);

    /**
     * Partially updates a statusesOfResidentalEstate.
     *
     * @param statusesOfResidentalEstateDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<StatusesOfResidentalEstateDTO> partialUpdate(StatusesOfResidentalEstateDTO statusesOfResidentalEstateDTO);

    /**
     * Get all the statusesOfResidentalEstates.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<StatusesOfResidentalEstateDTO> findAll(Pageable pageable);

    /**
     * Get the "id" statusesOfResidentalEstate.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<StatusesOfResidentalEstateDTO> findOne(Long id);

    /**
     * Delete the "id" statusesOfResidentalEstate.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
