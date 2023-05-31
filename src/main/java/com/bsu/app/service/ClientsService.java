package com.bsu.app.service;

import com.bsu.app.service.dto.ClientsDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.bsu.app.domain.Clients}.
 */
public interface ClientsService {
    /**
     * Save a clients.
     *
     * @param clientsDTO the entity to save.
     * @return the persisted entity.
     */
    ClientsDTO save(ClientsDTO clientsDTO);

    /**
     * Updates a clients.
     *
     * @param clientsDTO the entity to update.
     * @return the persisted entity.
     */
    ClientsDTO update(ClientsDTO clientsDTO);

    /**
     * Partially updates a clients.
     *
     * @param clientsDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ClientsDTO> partialUpdate(ClientsDTO clientsDTO);

    /**
     * Get all the clients.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ClientsDTO> findAll(Pageable pageable);

    /**
     * Get the "id" clients.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ClientsDTO> findOne(Long id);

    /**
     * Delete the "id" clients.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
