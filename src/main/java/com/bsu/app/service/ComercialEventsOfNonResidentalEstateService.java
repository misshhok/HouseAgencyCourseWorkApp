package com.bsu.app.service;

import com.bsu.app.service.dto.ComercialEventsOfNonResidentalEstateDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.bsu.app.domain.ComercialEventsOfNonResidentalEstate}.
 */
public interface ComercialEventsOfNonResidentalEstateService {
    /**
     * Save a comercialEventsOfNonResidentalEstate.
     *
     * @param comercialEventsOfNonResidentalEstateDTO the entity to save.
     * @return the persisted entity.
     */
    ComercialEventsOfNonResidentalEstateDTO save(ComercialEventsOfNonResidentalEstateDTO comercialEventsOfNonResidentalEstateDTO);

    /**
     * Updates a comercialEventsOfNonResidentalEstate.
     *
     * @param comercialEventsOfNonResidentalEstateDTO the entity to update.
     * @return the persisted entity.
     */
    ComercialEventsOfNonResidentalEstateDTO update(ComercialEventsOfNonResidentalEstateDTO comercialEventsOfNonResidentalEstateDTO);

    /**
     * Partially updates a comercialEventsOfNonResidentalEstate.
     *
     * @param comercialEventsOfNonResidentalEstateDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ComercialEventsOfNonResidentalEstateDTO> partialUpdate(
        ComercialEventsOfNonResidentalEstateDTO comercialEventsOfNonResidentalEstateDTO
    );

    /**
     * Get all the comercialEventsOfNonResidentalEstates.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ComercialEventsOfNonResidentalEstateDTO> findAll(Pageable pageable);

    /**
     * Get the "id" comercialEventsOfNonResidentalEstate.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ComercialEventsOfNonResidentalEstateDTO> findOne(Long id);

    /**
     * Delete the "id" comercialEventsOfNonResidentalEstate.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
