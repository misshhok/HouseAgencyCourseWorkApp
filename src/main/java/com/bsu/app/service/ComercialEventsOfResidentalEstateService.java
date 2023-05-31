package com.bsu.app.service;

import com.bsu.app.service.dto.ComercialEventsOfResidentalEstateDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.bsu.app.domain.ComercialEventsOfResidentalEstate}.
 */
public interface ComercialEventsOfResidentalEstateService {
    /**
     * Save a comercialEventsOfResidentalEstate.
     *
     * @param comercialEventsOfResidentalEstateDTO the entity to save.
     * @return the persisted entity.
     */
    ComercialEventsOfResidentalEstateDTO save(ComercialEventsOfResidentalEstateDTO comercialEventsOfResidentalEstateDTO);

    /**
     * Updates a comercialEventsOfResidentalEstate.
     *
     * @param comercialEventsOfResidentalEstateDTO the entity to update.
     * @return the persisted entity.
     */
    ComercialEventsOfResidentalEstateDTO update(ComercialEventsOfResidentalEstateDTO comercialEventsOfResidentalEstateDTO);

    /**
     * Partially updates a comercialEventsOfResidentalEstate.
     *
     * @param comercialEventsOfResidentalEstateDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ComercialEventsOfResidentalEstateDTO> partialUpdate(ComercialEventsOfResidentalEstateDTO comercialEventsOfResidentalEstateDTO);

    /**
     * Get all the comercialEventsOfResidentalEstates.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ComercialEventsOfResidentalEstateDTO> findAll(Pageable pageable);

    /**
     * Get the "id" comercialEventsOfResidentalEstate.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ComercialEventsOfResidentalEstateDTO> findOne(Long id);

    /**
     * Delete the "id" comercialEventsOfResidentalEstate.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
