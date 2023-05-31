package com.bsu.app.service;

import com.bsu.app.service.dto.BuildingTypeOfNonResidentalEstateDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.bsu.app.domain.BuildingTypeOfNonResidentalEstate}.
 */
public interface BuildingTypeOfNonResidentalEstateService {
    /**
     * Save a buildingTypeOfNonResidentalEstate.
     *
     * @param buildingTypeOfNonResidentalEstateDTO the entity to save.
     * @return the persisted entity.
     */
    BuildingTypeOfNonResidentalEstateDTO save(BuildingTypeOfNonResidentalEstateDTO buildingTypeOfNonResidentalEstateDTO);

    /**
     * Updates a buildingTypeOfNonResidentalEstate.
     *
     * @param buildingTypeOfNonResidentalEstateDTO the entity to update.
     * @return the persisted entity.
     */
    BuildingTypeOfNonResidentalEstateDTO update(BuildingTypeOfNonResidentalEstateDTO buildingTypeOfNonResidentalEstateDTO);

    /**
     * Partially updates a buildingTypeOfNonResidentalEstate.
     *
     * @param buildingTypeOfNonResidentalEstateDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<BuildingTypeOfNonResidentalEstateDTO> partialUpdate(BuildingTypeOfNonResidentalEstateDTO buildingTypeOfNonResidentalEstateDTO);

    /**
     * Get all the buildingTypeOfNonResidentalEstates.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<BuildingTypeOfNonResidentalEstateDTO> findAll(Pageable pageable);

    /**
     * Get the "id" buildingTypeOfNonResidentalEstate.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BuildingTypeOfNonResidentalEstateDTO> findOne(Long id);

    /**
     * Delete the "id" buildingTypeOfNonResidentalEstate.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
