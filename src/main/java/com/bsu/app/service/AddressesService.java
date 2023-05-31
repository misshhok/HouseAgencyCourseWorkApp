package com.bsu.app.service;

import com.bsu.app.service.dto.AddressesDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.bsu.app.domain.Addresses}.
 */
public interface AddressesService {
    /**
     * Save a addresses.
     *
     * @param addressesDTO the entity to save.
     * @return the persisted entity.
     */
    AddressesDTO save(AddressesDTO addressesDTO);

    /**
     * Updates a addresses.
     *
     * @param addressesDTO the entity to update.
     * @return the persisted entity.
     */
    AddressesDTO update(AddressesDTO addressesDTO);

    /**
     * Partially updates a addresses.
     *
     * @param addressesDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AddressesDTO> partialUpdate(AddressesDTO addressesDTO);

    /**
     * Get all the addresses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AddressesDTO> findAll(Pageable pageable);

    /**
     * Get the "id" addresses.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AddressesDTO> findOne(Long id);

    /**
     * Delete the "id" addresses.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
