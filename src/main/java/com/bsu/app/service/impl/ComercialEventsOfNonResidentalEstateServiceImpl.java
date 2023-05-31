package com.bsu.app.service.impl;

import com.bsu.app.domain.ComercialEventsOfNonResidentalEstate;
import com.bsu.app.repository.ComercialEventsOfNonResidentalEstateRepository;
import com.bsu.app.service.ComercialEventsOfNonResidentalEstateService;
import com.bsu.app.service.dto.ComercialEventsOfNonResidentalEstateDTO;
import com.bsu.app.service.mapper.ComercialEventsOfNonResidentalEstateMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ComercialEventsOfNonResidentalEstate}.
 */
@Service
@Transactional
public class ComercialEventsOfNonResidentalEstateServiceImpl implements ComercialEventsOfNonResidentalEstateService {

    private final Logger log = LoggerFactory.getLogger(ComercialEventsOfNonResidentalEstateServiceImpl.class);

    private final ComercialEventsOfNonResidentalEstateRepository comercialEventsOfNonResidentalEstateRepository;

    private final ComercialEventsOfNonResidentalEstateMapper comercialEventsOfNonResidentalEstateMapper;

    public ComercialEventsOfNonResidentalEstateServiceImpl(
        ComercialEventsOfNonResidentalEstateRepository comercialEventsOfNonResidentalEstateRepository,
        ComercialEventsOfNonResidentalEstateMapper comercialEventsOfNonResidentalEstateMapper
    ) {
        this.comercialEventsOfNonResidentalEstateRepository = comercialEventsOfNonResidentalEstateRepository;
        this.comercialEventsOfNonResidentalEstateMapper = comercialEventsOfNonResidentalEstateMapper;
    }

    @Override
    public ComercialEventsOfNonResidentalEstateDTO save(ComercialEventsOfNonResidentalEstateDTO comercialEventsOfNonResidentalEstateDTO) {
        log.debug("Request to save ComercialEventsOfNonResidentalEstate : {}", comercialEventsOfNonResidentalEstateDTO);
        ComercialEventsOfNonResidentalEstate comercialEventsOfNonResidentalEstate = comercialEventsOfNonResidentalEstateMapper.toEntity(
            comercialEventsOfNonResidentalEstateDTO
        );
        comercialEventsOfNonResidentalEstate = comercialEventsOfNonResidentalEstateRepository.save(comercialEventsOfNonResidentalEstate);
        return comercialEventsOfNonResidentalEstateMapper.toDto(comercialEventsOfNonResidentalEstate);
    }

    @Override
    public ComercialEventsOfNonResidentalEstateDTO update(ComercialEventsOfNonResidentalEstateDTO comercialEventsOfNonResidentalEstateDTO) {
        log.debug("Request to update ComercialEventsOfNonResidentalEstate : {}", comercialEventsOfNonResidentalEstateDTO);
        ComercialEventsOfNonResidentalEstate comercialEventsOfNonResidentalEstate = comercialEventsOfNonResidentalEstateMapper.toEntity(
            comercialEventsOfNonResidentalEstateDTO
        );
        comercialEventsOfNonResidentalEstate = comercialEventsOfNonResidentalEstateRepository.save(comercialEventsOfNonResidentalEstate);
        return comercialEventsOfNonResidentalEstateMapper.toDto(comercialEventsOfNonResidentalEstate);
    }

    @Override
    public Optional<ComercialEventsOfNonResidentalEstateDTO> partialUpdate(
        ComercialEventsOfNonResidentalEstateDTO comercialEventsOfNonResidentalEstateDTO
    ) {
        log.debug("Request to partially update ComercialEventsOfNonResidentalEstate : {}", comercialEventsOfNonResidentalEstateDTO);

        return comercialEventsOfNonResidentalEstateRepository
            .findById(comercialEventsOfNonResidentalEstateDTO.getId())
            .map(existingComercialEventsOfNonResidentalEstate -> {
                comercialEventsOfNonResidentalEstateMapper.partialUpdate(
                    existingComercialEventsOfNonResidentalEstate,
                    comercialEventsOfNonResidentalEstateDTO
                );

                return existingComercialEventsOfNonResidentalEstate;
            })
            .map(comercialEventsOfNonResidentalEstateRepository::save)
            .map(comercialEventsOfNonResidentalEstateMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ComercialEventsOfNonResidentalEstateDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ComercialEventsOfNonResidentalEstates");
        return comercialEventsOfNonResidentalEstateRepository.findAll(pageable).map(comercialEventsOfNonResidentalEstateMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ComercialEventsOfNonResidentalEstateDTO> findOne(Long id) {
        log.debug("Request to get ComercialEventsOfNonResidentalEstate : {}", id);
        return comercialEventsOfNonResidentalEstateRepository.findById(id).map(comercialEventsOfNonResidentalEstateMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ComercialEventsOfNonResidentalEstate : {}", id);
        comercialEventsOfNonResidentalEstateRepository.deleteById(id);
    }
}
