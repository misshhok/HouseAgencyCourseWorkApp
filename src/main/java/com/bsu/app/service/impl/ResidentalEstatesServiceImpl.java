package com.bsu.app.service.impl;

import com.bsu.app.domain.ResidentalEstates;
import com.bsu.app.repository.ResidentalEstatesRepository;
import com.bsu.app.service.ResidentalEstatesService;
import com.bsu.app.service.dto.ResidentalEstatesDTO;
import com.bsu.app.service.mapper.ResidentalEstatesMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ResidentalEstates}.
 */
@Service
@Transactional
public class ResidentalEstatesServiceImpl implements ResidentalEstatesService {

    private final Logger log = LoggerFactory.getLogger(ResidentalEstatesServiceImpl.class);

    private final ResidentalEstatesRepository residentalEstatesRepository;

    private final ResidentalEstatesMapper residentalEstatesMapper;

    public ResidentalEstatesServiceImpl(
        ResidentalEstatesRepository residentalEstatesRepository,
        ResidentalEstatesMapper residentalEstatesMapper
    ) {
        this.residentalEstatesRepository = residentalEstatesRepository;
        this.residentalEstatesMapper = residentalEstatesMapper;
    }

    @Override
    public ResidentalEstatesDTO save(ResidentalEstatesDTO residentalEstatesDTO) {
        log.debug("Request to save ResidentalEstates : {}", residentalEstatesDTO);
        ResidentalEstates residentalEstates = residentalEstatesMapper.toEntity(residentalEstatesDTO);
        residentalEstates = residentalEstatesRepository.save(residentalEstates);
        return residentalEstatesMapper.toDto(residentalEstates);
    }

    @Override
    public ResidentalEstatesDTO update(ResidentalEstatesDTO residentalEstatesDTO) {
        log.debug("Request to update ResidentalEstates : {}", residentalEstatesDTO);
        ResidentalEstates residentalEstates = residentalEstatesMapper.toEntity(residentalEstatesDTO);
        residentalEstates = residentalEstatesRepository.save(residentalEstates);
        return residentalEstatesMapper.toDto(residentalEstates);
    }

    @Override
    public Optional<ResidentalEstatesDTO> partialUpdate(ResidentalEstatesDTO residentalEstatesDTO) {
        log.debug("Request to partially update ResidentalEstates : {}", residentalEstatesDTO);

        return residentalEstatesRepository
            .findById(residentalEstatesDTO.getId())
            .map(existingResidentalEstates -> {
                residentalEstatesMapper.partialUpdate(existingResidentalEstates, residentalEstatesDTO);

                return existingResidentalEstates;
            })
            .map(residentalEstatesRepository::save)
            .map(residentalEstatesMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ResidentalEstatesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ResidentalEstates");
        return residentalEstatesRepository.findAll(pageable).map(residentalEstatesMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ResidentalEstatesDTO> findOne(Long id) {
        log.debug("Request to get ResidentalEstates : {}", id);
        return residentalEstatesRepository.findById(id).map(residentalEstatesMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ResidentalEstates : {}", id);
        residentalEstatesRepository.deleteById(id);
    }
}
