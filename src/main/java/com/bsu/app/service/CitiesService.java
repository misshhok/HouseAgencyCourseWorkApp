package com.bsu.app.service;

import com.bsu.app.service.dto.CitiesDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.bsu.app.domain.Cities}.
 */
public interface CitiesService {
    /**
     * Save a cities.
     *
     * @param citiesDTO the entity to save.
     * @return the persisted entity.
     */
    CitiesDTO save(CitiesDTO citiesDTO);

    /**
     * Updates a cities.
     *
     * @param citiesDTO the entity to update.
     * @return the persisted entity.
     */
    CitiesDTO update(CitiesDTO citiesDTO);

    /**
     * Partially updates a cities.
     *
     * @param citiesDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CitiesDTO> partialUpdate(CitiesDTO citiesDTO);

    /**
     * Get all the cities.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CitiesDTO> findAll(Pageable pageable);

    /**
     * Get the "id" cities.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CitiesDTO> findOne(Long id);

    /**
     * Delete the "id" cities.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
