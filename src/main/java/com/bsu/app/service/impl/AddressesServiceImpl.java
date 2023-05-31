package com.bsu.app.service.impl;

import com.bsu.app.domain.Addresses;
import com.bsu.app.repository.AddressesRepository;
import com.bsu.app.service.AddressesService;
import com.bsu.app.service.dto.AddressesDTO;
import com.bsu.app.service.mapper.AddressesMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Addresses}.
 */
@Service
@Transactional
public class AddressesServiceImpl implements AddressesService {

    private final Logger log = LoggerFactory.getLogger(AddressesServiceImpl.class);

    private final AddressesRepository addressesRepository;

    private final AddressesMapper addressesMapper;

    public AddressesServiceImpl(AddressesRepository addressesRepository, AddressesMapper addressesMapper) {
        this.addressesRepository = addressesRepository;
        this.addressesMapper = addressesMapper;
    }

    @Override
    public AddressesDTO save(AddressesDTO addressesDTO) {
        log.debug("Request to save Addresses : {}", addressesDTO);
        Addresses addresses = addressesMapper.toEntity(addressesDTO);
        addresses = addressesRepository.save(addresses);
        return addressesMapper.toDto(addresses);
    }

    @Override
    public AddressesDTO update(AddressesDTO addressesDTO) {
        log.debug("Request to update Addresses : {}", addressesDTO);
        Addresses addresses = addressesMapper.toEntity(addressesDTO);
        addresses = addressesRepository.save(addresses);
        return addressesMapper.toDto(addresses);
    }

    @Override
    public Optional<AddressesDTO> partialUpdate(AddressesDTO addressesDTO) {
        log.debug("Request to partially update Addresses : {}", addressesDTO);

        return addressesRepository
            .findById(addressesDTO.getId())
            .map(existingAddresses -> {
                addressesMapper.partialUpdate(existingAddresses, addressesDTO);

                return existingAddresses;
            })
            .map(addressesRepository::save)
            .map(addressesMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AddressesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Addresses");
        return addressesRepository.findAll(pageable).map(addressesMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AddressesDTO> findOne(Long id) {
        log.debug("Request to get Addresses : {}", id);
        return addressesRepository.findById(id).map(addressesMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Addresses : {}", id);
        addressesRepository.deleteById(id);
    }
}
