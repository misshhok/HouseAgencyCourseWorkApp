package com.bsu.app.service;

import com.bsu.app.service.dto.TypesOfCommercialEventsDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.bsu.app.domain.TypesOfCommercialEvents}.
 */
public interface TypesOfCommercialEventsService {
    /**
     * Save a typesOfCommercialEvents.
     *
     * @param typesOfCommercialEventsDTO the entity to save.
     * @return the persisted entity.
     */
    TypesOfCommercialEventsDTO save(TypesOfCommercialEventsDTO typesOfCommercialEventsDTO);

    /**
     * Updates a typesOfCommercialEvents.
     *
     * @param typesOfCommercialEventsDTO the entity to update.
     * @return the persisted entity.
     */
    TypesOfCommercialEventsDTO update(TypesOfCommercialEventsDTO typesOfCommercialEventsDTO);

    /**
     * Partially updates a typesOfCommercialEvents.
     *
     * @param typesOfCommercialEventsDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TypesOfCommercialEventsDTO> partialUpdate(TypesOfCommercialEventsDTO typesOfCommercialEventsDTO);

    /**
     * Get all the typesOfCommercialEvents.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TypesOfCommercialEventsDTO> findAll(Pageable pageable);

    /**
     * Get the "id" typesOfCommercialEvents.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TypesOfCommercialEventsDTO> findOne(Long id);

    /**
     * Delete the "id" typesOfCommercialEvents.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
