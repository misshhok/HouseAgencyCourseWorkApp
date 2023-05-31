package com.bsu.app.service.impl;

import com.bsu.app.domain.ComercialEventsOfResidentalEstate;
import com.bsu.app.repository.ComercialEventsOfResidentalEstateRepository;
import com.bsu.app.service.ComercialEventsOfResidentalEstateService;
import com.bsu.app.service.dto.ComercialEventsOfResidentalEstateDTO;
import com.bsu.app.service.mapper.ComercialEventsOfResidentalEstateMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ComercialEventsOfResidentalEstate}.
 */
@Service
@Transactional
public class ComercialEventsOfResidentalEstateServiceImpl implements ComercialEventsOfResidentalEstateService {

    private final Logger log = LoggerFactory.getLogger(ComercialEventsOfResidentalEstateServiceImpl.class);

    private final ComercialEventsOfResidentalEstateRepository comercialEventsOfResidentalEstateRepository;

    private final ComercialEventsOfResidentalEstateMapper comercialEventsOfResidentalEstateMapper;

    public ComercialEventsOfResidentalEstateServiceImpl(
        ComercialEventsOfResidentalEstateRepository comercialEventsOfResidentalEstateRepository,
        ComercialEventsOfResidentalEstateMapper comercialEventsOfResidentalEstateMapper
    ) {
        this.comercialEventsOfResidentalEstateRepository = comercialEventsOfResidentalEstateRepository;
        this.comercialEventsOfResidentalEstateMapper = comercialEventsOfResidentalEstateMapper;
    }

    @Override
    public ComercialEventsOfResidentalEstateDTO save(ComercialEventsOfResidentalEstateDTO comercialEventsOfResidentalEstateDTO) {
        log.debug("Request to save ComercialEventsOfResidentalEstate : {}", comercialEventsOfResidentalEstateDTO);
        ComercialEventsOfResidentalEstate comercialEventsOfResidentalEstate = comercialEventsOfResidentalEstateMapper.toEntity(
            comercialEventsOfResidentalEstateDTO
        );
        comercialEventsOfResidentalEstate = comercialEventsOfResidentalEstateRepository.save(comercialEventsOfResidentalEstate);
        return comercialEventsOfResidentalEstateMapper.toDto(comercialEventsOfResidentalEstate);
    }

    @Override
    public ComercialEventsOfResidentalEstateDTO update(ComercialEventsOfResidentalEstateDTO comercialEventsOfResidentalEstateDTO) {
        log.debug("Request to update ComercialEventsOfResidentalEstate : {}", comercialEventsOfResidentalEstateDTO);
        ComercialEventsOfResidentalEstate comercialEventsOfResidentalEstate = comercialEventsOfResidentalEstateMapper.toEntity(
            comercialEventsOfResidentalEstateDTO
        );
        comercialEventsOfResidentalEstate = comercialEventsOfResidentalEstateRepository.save(comercialEventsOfResidentalEstate);
        return comercialEventsOfResidentalEstateMapper.toDto(comercialEventsOfResidentalEstate);
    }

    @Override
    public Optional<ComercialEventsOfResidentalEstateDTO> partialUpdate(
        ComercialEventsOfResidentalEstateDTO comercialEventsOfResidentalEstateDTO
    ) {
        log.debug("Request to partially update ComercialEventsOfResidentalEstate : {}", comercialEventsOfResidentalEstateDTO);

        return comercialEventsOfResidentalEstateRepository
            .findById(comercialEventsOfResidentalEstateDTO.getId())
            .map(existingComercialEventsOfResidentalEstate -> {
                comercialEventsOfResidentalEstateMapper.partialUpdate(
                    existingComercialEventsOfResidentalEstate,
                    comercialEventsOfResidentalEstateDTO
                );

                return existingComercialEventsOfResidentalEstate;
            })
            .map(comercialEventsOfResidentalEstateRepository::save)
            .map(comercialEventsOfResidentalEstateMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ComercialEventsOfResidentalEstateDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ComercialEventsOfResidentalEstates");
        return comercialEventsOfResidentalEstateRepository.findAll(pageable).map(comercialEventsOfResidentalEstateMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ComercialEventsOfResidentalEstateDTO> findOne(Long id) {
        log.debug("Request to get ComercialEventsOfResidentalEstate : {}", id);
        return comercialEventsOfResidentalEstateRepository.findById(id).map(comercialEventsOfResidentalEstateMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ComercialEventsOfResidentalEstate : {}", id);
        comercialEventsOfResidentalEstateRepository.deleteById(id);
    }
}
