package com.bsu.app.service;

import com.bsu.app.service.dto.TypesOfResidentalEstateDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.bsu.app.domain.TypesOfResidentalEstate}.
 */
public interface TypesOfResidentalEstateService {
    /**
     * Save a typesOfResidentalEstate.
     *
     * @param typesOfResidentalEstateDTO the entity to save.
     * @return the persisted entity.
     */
    TypesOfResidentalEstateDTO save(TypesOfResidentalEstateDTO typesOfResidentalEstateDTO);

    /**
     * Updates a typesOfResidentalEstate.
     *
     * @param typesOfResidentalEstateDTO the entity to update.
     * @return the persisted entity.
     */
    TypesOfResidentalEstateDTO update(TypesOfResidentalEstateDTO typesOfResidentalEstateDTO);

    /**
     * Partially updates a typesOfResidentalEstate.
     *
     * @param typesOfResidentalEstateDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TypesOfResidentalEstateDTO> partialUpdate(TypesOfResidentalEstateDTO typesOfResidentalEstateDTO);

    /**
     * Get all the typesOfResidentalEstates.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TypesOfResidentalEstateDTO> findAll(Pageable pageable);

    /**
     * Get the "id" typesOfResidentalEstate.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TypesOfResidentalEstateDTO> findOne(Long id);

    /**
     * Delete the "id" typesOfResidentalEstate.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
