package com.bsu.app.service;

import com.bsu.app.service.dto.PurposesOfNonResidentalEstateDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.bsu.app.domain.PurposesOfNonResidentalEstate}.
 */
public interface PurposesOfNonResidentalEstateService {
    /**
     * Save a purposesOfNonResidentalEstate.
     *
     * @param purposesOfNonResidentalEstateDTO the entity to save.
     * @return the persisted entity.
     */
    PurposesOfNonResidentalEstateDTO save(PurposesOfNonResidentalEstateDTO purposesOfNonResidentalEstateDTO);

    /**
     * Updates a purposesOfNonResidentalEstate.
     *
     * @param purposesOfNonResidentalEstateDTO the entity to update.
     * @return the persisted entity.
     */
    PurposesOfNonResidentalEstateDTO update(PurposesOfNonResidentalEstateDTO purposesOfNonResidentalEstateDTO);

    /**
     * Partially updates a purposesOfNonResidentalEstate.
     *
     * @param purposesOfNonResidentalEstateDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PurposesOfNonResidentalEstateDTO> partialUpdate(PurposesOfNonResidentalEstateDTO purposesOfNonResidentalEstateDTO);

    /**
     * Get all the purposesOfNonResidentalEstates.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PurposesOfNonResidentalEstateDTO> findAll(Pageable pageable);

    /**
     * Get the "id" purposesOfNonResidentalEstate.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PurposesOfNonResidentalEstateDTO> findOne(Long id);

    /**
     * Delete the "id" purposesOfNonResidentalEstate.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
