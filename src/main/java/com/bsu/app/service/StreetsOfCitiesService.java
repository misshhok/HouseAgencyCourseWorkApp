package com.bsu.app.service;

import com.bsu.app.service.dto.StreetsOfCitiesDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.bsu.app.domain.StreetsOfCities}.
 */
public interface StreetsOfCitiesService {
    /**
     * Save a streetsOfCities.
     *
     * @param streetsOfCitiesDTO the entity to save.
     * @return the persisted entity.
     */
    StreetsOfCitiesDTO save(StreetsOfCitiesDTO streetsOfCitiesDTO);

    /**
     * Updates a streetsOfCities.
     *
     * @param streetsOfCitiesDTO the entity to update.
     * @return the persisted entity.
     */
    StreetsOfCitiesDTO update(StreetsOfCitiesDTO streetsOfCitiesDTO);

    /**
     * Partially updates a streetsOfCities.
     *
     * @param streetsOfCitiesDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<StreetsOfCitiesDTO> partialUpdate(StreetsOfCitiesDTO streetsOfCitiesDTO);

    /**
     * Get all the streetsOfCities.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<StreetsOfCitiesDTO> findAll(Pageable pageable);

    /**
     * Get the "id" streetsOfCities.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<StreetsOfCitiesDTO> findOne(Long id);

    /**
     * Delete the "id" streetsOfCities.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
