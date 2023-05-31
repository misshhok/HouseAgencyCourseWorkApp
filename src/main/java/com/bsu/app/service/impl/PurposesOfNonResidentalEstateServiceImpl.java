package com.bsu.app.service.impl;

import com.bsu.app.domain.PurposesOfNonResidentalEstate;
import com.bsu.app.repository.PurposesOfNonResidentalEstateRepository;
import com.bsu.app.service.PurposesOfNonResidentalEstateService;
import com.bsu.app.service.dto.PurposesOfNonResidentalEstateDTO;
import com.bsu.app.service.mapper.PurposesOfNonResidentalEstateMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link PurposesOfNonResidentalEstate}.
 */
@Service
@Transactional
public class PurposesOfNonResidentalEstateServiceImpl implements PurposesOfNonResidentalEstateService {

    private final Logger log = LoggerFactory.getLogger(PurposesOfNonResidentalEstateServiceImpl.class);

    private final PurposesOfNonResidentalEstateRepository purposesOfNonResidentalEstateRepository;

    private final PurposesOfNonResidentalEstateMapper purposesOfNonResidentalEstateMapper;

    public PurposesOfNonResidentalEstateServiceImpl(
        PurposesOfNonResidentalEstateRepository purposesOfNonResidentalEstateRepository,
        PurposesOfNonResidentalEstateMapper purposesOfNonResidentalEstateMapper
    ) {
        this.purposesOfNonResidentalEstateRepository = purposesOfNonResidentalEstateRepository;
        this.purposesOfNonResidentalEstateMapper = purposesOfNonResidentalEstateMapper;
    }

    @Override
    public PurposesOfNonResidentalEstateDTO save(PurposesOfNonResidentalEstateDTO purposesOfNonResidentalEstateDTO) {
        log.debug("Request to save PurposesOfNonResidentalEstate : {}", purposesOfNonResidentalEstateDTO);
        PurposesOfNonResidentalEstate purposesOfNonResidentalEstate = purposesOfNonResidentalEstateMapper.toEntity(
            purposesOfNonResidentalEstateDTO
        );
        purposesOfNonResidentalEstate = purposesOfNonResidentalEstateRepository.save(purposesOfNonResidentalEstate);
        return purposesOfNonResidentalEstateMapper.toDto(purposesOfNonResidentalEstate);
    }

    @Override
    public PurposesOfNonResidentalEstateDTO update(PurposesOfNonResidentalEstateDTO purposesOfNonResidentalEstateDTO) {
        log.debug("Request to update PurposesOfNonResidentalEstate : {}", purposesOfNonResidentalEstateDTO);
        PurposesOfNonResidentalEstate purposesOfNonResidentalEstate = purposesOfNonResidentalEstateMapper.toEntity(
            purposesOfNonResidentalEstateDTO
        );
        purposesOfNonResidentalEstate = purposesOfNonResidentalEstateRepository.save(purposesOfNonResidentalEstate);
        return purposesOfNonResidentalEstateMapper.toDto(purposesOfNonResidentalEstate);
    }

    @Override
    public Optional<PurposesOfNonResidentalEstateDTO> partialUpdate(PurposesOfNonResidentalEstateDTO purposesOfNonResidentalEstateDTO) {
        log.debug("Request to partially update PurposesOfNonResidentalEstate : {}", purposesOfNonResidentalEstateDTO);

        return purposesOfNonResidentalEstateRepository
            .findById(purposesOfNonResidentalEstateDTO.getId())
            .map(existingPurposesOfNonResidentalEstate -> {
                purposesOfNonResidentalEstateMapper.partialUpdate(existingPurposesOfNonResidentalEstate, purposesOfNonResidentalEstateDTO);

                return existingPurposesOfNonResidentalEstate;
            })
            .map(purposesOfNonResidentalEstateRepository::save)
            .map(purposesOfNonResidentalEstateMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PurposesOfNonResidentalEstateDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PurposesOfNonResidentalEstates");
        return purposesOfNonResidentalEstateRepository.findAll(pageable).map(purposesOfNonResidentalEstateMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PurposesOfNonResidentalEstateDTO> findOne(Long id) {
        log.debug("Request to get PurposesOfNonResidentalEstate : {}", id);
        return purposesOfNonResidentalEstateRepository.findById(id).map(purposesOfNonResidentalEstateMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PurposesOfNonResidentalEstate : {}", id);
        purposesOfNonResidentalEstateRepository.deleteById(id);
    }
}
