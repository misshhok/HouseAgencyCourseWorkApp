package com.bsu.app.service.impl;

import com.bsu.app.domain.StatusesOfResidentalEstate;
import com.bsu.app.repository.StatusesOfResidentalEstateRepository;
import com.bsu.app.service.StatusesOfResidentalEstateService;
import com.bsu.app.service.dto.StatusesOfResidentalEstateDTO;
import com.bsu.app.service.mapper.StatusesOfResidentalEstateMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link StatusesOfResidentalEstate}.
 */
@Service
@Transactional
public class StatusesOfResidentalEstateServiceImpl implements StatusesOfResidentalEstateService {

    private final Logger log = LoggerFactory.getLogger(StatusesOfResidentalEstateServiceImpl.class);

    private final StatusesOfResidentalEstateRepository statusesOfResidentalEstateRepository;

    private final StatusesOfResidentalEstateMapper statusesOfResidentalEstateMapper;

    public StatusesOfResidentalEstateServiceImpl(
        StatusesOfResidentalEstateRepository statusesOfResidentalEstateRepository,
        StatusesOfResidentalEstateMapper statusesOfResidentalEstateMapper
    ) {
        this.statusesOfResidentalEstateRepository = statusesOfResidentalEstateRepository;
        this.statusesOfResidentalEstateMapper = statusesOfResidentalEstateMapper;
    }

    @Override
    public StatusesOfResidentalEstateDTO save(StatusesOfResidentalEstateDTO statusesOfResidentalEstateDTO) {
        log.debug("Request to save StatusesOfResidentalEstate : {}", statusesOfResidentalEstateDTO);
        StatusesOfResidentalEstate statusesOfResidentalEstate = statusesOfResidentalEstateMapper.toEntity(statusesOfResidentalEstateDTO);
        statusesOfResidentalEstate = statusesOfResidentalEstateRepository.save(statusesOfResidentalEstate);
        return statusesOfResidentalEstateMapper.toDto(statusesOfResidentalEstate);
    }

    @Override
    public StatusesOfResidentalEstateDTO update(StatusesOfResidentalEstateDTO statusesOfResidentalEstateDTO) {
        log.debug("Request to update StatusesOfResidentalEstate : {}", statusesOfResidentalEstateDTO);
        StatusesOfResidentalEstate statusesOfResidentalEstate = statusesOfResidentalEstateMapper.toEntity(statusesOfResidentalEstateDTO);
        statusesOfResidentalEstate = statusesOfResidentalEstateRepository.save(statusesOfResidentalEstate);
        return statusesOfResidentalEstateMapper.toDto(statusesOfResidentalEstate);
    }

    @Override
    public Optional<StatusesOfResidentalEstateDTO> partialUpdate(StatusesOfResidentalEstateDTO statusesOfResidentalEstateDTO) {
        log.debug("Request to partially update StatusesOfResidentalEstate : {}", statusesOfResidentalEstateDTO);

        return statusesOfResidentalEstateRepository
            .findById(statusesOfResidentalEstateDTO.getId())
            .map(existingStatusesOfResidentalEstate -> {
                statusesOfResidentalEstateMapper.partialUpdate(existingStatusesOfResidentalEstate, statusesOfResidentalEstateDTO);

                return existingStatusesOfResidentalEstate;
            })
            .map(statusesOfResidentalEstateRepository::save)
            .map(statusesOfResidentalEstateMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<StatusesOfResidentalEstateDTO> findAll(Pageable pageable) {
        log.debug("Request to get all StatusesOfResidentalEstates");
        return statusesOfResidentalEstateRepository.findAll(pageable).map(statusesOfResidentalEstateMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<StatusesOfResidentalEstateDTO> findOne(Long id) {
        log.debug("Request to get StatusesOfResidentalEstate : {}", id);
        return statusesOfResidentalEstateRepository.findById(id).map(statusesOfResidentalEstateMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete StatusesOfResidentalEstate : {}", id);
        statusesOfResidentalEstateRepository.deleteById(id);
    }
}
