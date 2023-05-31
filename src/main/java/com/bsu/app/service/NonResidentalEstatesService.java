package com.bsu.app.service;

import com.bsu.app.service.dto.NonResidentalEstatesDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.bsu.app.domain.NonResidentalEstates}.
 */
public interface NonResidentalEstatesService {
    /**
     * Save a nonResidentalEstates.
     *
     * @param nonResidentalEstatesDTO the entity to save.
     * @return the persisted entity.
     */
    NonResidentalEstatesDTO save(NonResidentalEstatesDTO nonResidentalEstatesDTO);

    /**
     * Updates a nonResidentalEstates.
     *
     * @param nonResidentalEstatesDTO the entity to update.
     * @return the persisted entity.
     */
    NonResidentalEstatesDTO update(NonResidentalEstatesDTO nonResidentalEstatesDTO);

    /**
     * Partially updates a nonResidentalEstates.
     *
     * @param nonResidentalEstatesDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<NonResidentalEstatesDTO> partialUpdate(NonResidentalEstatesDTO nonResidentalEstatesDTO);

    /**
     * Get all the nonResidentalEstates.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<NonResidentalEstatesDTO> findAll(Pageable pageable);

    /**
     * Get the "id" nonResidentalEstates.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<NonResidentalEstatesDTO> findOne(Long id);

    /**
     * Delete the "id" nonResidentalEstates.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
