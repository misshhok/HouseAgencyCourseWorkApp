package com.bsu.app.service.impl;

import com.bsu.app.domain.NonResidentalEstates;
import com.bsu.app.repository.NonResidentalEstatesRepository;
import com.bsu.app.service.NonResidentalEstatesService;
import com.bsu.app.service.dto.NonResidentalEstatesDTO;
import com.bsu.app.service.mapper.NonResidentalEstatesMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link NonResidentalEstates}.
 */
@Service
@Transactional
public class NonResidentalEstatesServiceImpl implements NonResidentalEstatesService {

    private final Logger log = LoggerFactory.getLogger(NonResidentalEstatesServiceImpl.class);

    private final NonResidentalEstatesRepository nonResidentalEstatesRepository;

    private final NonResidentalEstatesMapper nonResidentalEstatesMapper;

    public NonResidentalEstatesServiceImpl(
        NonResidentalEstatesRepository nonResidentalEstatesRepository,
        NonResidentalEstatesMapper nonResidentalEstatesMapper
    ) {
        this.nonResidentalEstatesRepository = nonResidentalEstatesRepository;
        this.nonResidentalEstatesMapper = nonResidentalEstatesMapper;
    }

    @Override
    public NonResidentalEstatesDTO save(NonResidentalEstatesDTO nonResidentalEstatesDTO) {
        log.debug("Request to save NonResidentalEstates : {}", nonResidentalEstatesDTO);
        NonResidentalEstates nonResidentalEstates = nonResidentalEstatesMapper.toEntity(nonResidentalEstatesDTO);
        nonResidentalEstates = nonResidentalEstatesRepository.save(nonResidentalEstates);
        return nonResidentalEstatesMapper.toDto(nonResidentalEstates);
    }

    @Override
    public NonResidentalEstatesDTO update(NonResidentalEstatesDTO nonResidentalEstatesDTO) {
        log.debug("Request to update NonResidentalEstates : {}", nonResidentalEstatesDTO);
        NonResidentalEstates nonResidentalEstates = nonResidentalEstatesMapper.toEntity(nonResidentalEstatesDTO);
        nonResidentalEstates = nonResidentalEstatesRepository.save(nonResidentalEstates);
        return nonResidentalEstatesMapper.toDto(nonResidentalEstates);
    }

    @Override
    public Optional<NonResidentalEstatesDTO> partialUpdate(NonResidentalEstatesDTO nonResidentalEstatesDTO) {
        log.debug("Request to partially update NonResidentalEstates : {}", nonResidentalEstatesDTO);

        return nonResidentalEstatesRepository
            .findById(nonResidentalEstatesDTO.getId())
            .map(existingNonResidentalEstates -> {
                nonResidentalEstatesMapper.partialUpdate(existingNonResidentalEstates, nonResidentalEstatesDTO);

                return existingNonResidentalEstates;
            })
            .map(nonResidentalEstatesRepository::save)
            .map(nonResidentalEstatesMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<NonResidentalEstatesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all NonResidentalEstates");
        return nonResidentalEstatesRepository.findAll(pageable).map(nonResidentalEstatesMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<NonResidentalEstatesDTO> findOne(Long id) {
        log.debug("Request to get NonResidentalEstates : {}", id);
        return nonResidentalEstatesRepository.findById(id).map(nonResidentalEstatesMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete NonResidentalEstates : {}", id);
        nonResidentalEstatesRepository.deleteById(id);
    }
}
