package com.bsu.app.service;

import com.bsu.app.service.dto.ResidentalEstatesDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.bsu.app.domain.ResidentalEstates}.
 */
public interface ResidentalEstatesService {
    /**
     * Save a residentalEstates.
     *
     * @param residentalEstatesDTO the entity to save.
     * @return the persisted entity.
     */
    ResidentalEstatesDTO save(ResidentalEstatesDTO residentalEstatesDTO);

    /**
     * Updates a residentalEstates.
     *
     * @param residentalEstatesDTO the entity to update.
     * @return the persisted entity.
     */
    ResidentalEstatesDTO update(ResidentalEstatesDTO residentalEstatesDTO);

    /**
     * Partially updates a residentalEstates.
     *
     * @param residentalEstatesDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ResidentalEstatesDTO> partialUpdate(ResidentalEstatesDTO residentalEstatesDTO);

    /**
     * Get all the residentalEstates.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ResidentalEstatesDTO> findAll(Pageable pageable);

    /**
     * Get the "id" residentalEstates.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ResidentalEstatesDTO> findOne(Long id);

    /**
     * Delete the "id" residentalEstates.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
