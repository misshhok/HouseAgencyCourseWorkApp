package com.bsu.app.service.impl;

import com.bsu.app.domain.TypesOfResidentalEstate;
import com.bsu.app.repository.TypesOfResidentalEstateRepository;
import com.bsu.app.service.TypesOfResidentalEstateService;
import com.bsu.app.service.dto.TypesOfResidentalEstateDTO;
import com.bsu.app.service.mapper.TypesOfResidentalEstateMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TypesOfResidentalEstate}.
 */
@Service
@Transactional
public class TypesOfResidentalEstateServiceImpl implements TypesOfResidentalEstateService {

    private final Logger log = LoggerFactory.getLogger(TypesOfResidentalEstateServiceImpl.class);

    private final TypesOfResidentalEstateRepository typesOfResidentalEstateRepository;

    private final TypesOfResidentalEstateMapper typesOfResidentalEstateMapper;

    public TypesOfResidentalEstateServiceImpl(
        TypesOfResidentalEstateRepository typesOfResidentalEstateRepository,
        TypesOfResidentalEstateMapper typesOfResidentalEstateMapper
    ) {
        this.typesOfResidentalEstateRepository = typesOfResidentalEstateRepository;
        this.typesOfResidentalEstateMapper = typesOfResidentalEstateMapper;
    }

    @Override
    public TypesOfResidentalEstateDTO save(TypesOfResidentalEstateDTO typesOfResidentalEstateDTO) {
        log.debug("Request to save TypesOfResidentalEstate : {}", typesOfResidentalEstateDTO);
        TypesOfResidentalEstate typesOfResidentalEstate = typesOfResidentalEstateMapper.toEntity(typesOfResidentalEstateDTO);
        typesOfResidentalEstate = typesOfResidentalEstateRepository.save(typesOfResidentalEstate);
        return typesOfResidentalEstateMapper.toDto(typesOfResidentalEstate);
    }

    @Override
    public TypesOfResidentalEstateDTO update(TypesOfResidentalEstateDTO typesOfResidentalEstateDTO) {
        log.debug("Request to update TypesOfResidentalEstate : {}", typesOfResidentalEstateDTO);
        TypesOfResidentalEstate typesOfResidentalEstate = typesOfResidentalEstateMapper.toEntity(typesOfResidentalEstateDTO);
        typesOfResidentalEstate = typesOfResidentalEstateRepository.save(typesOfResidentalEstate);
        return typesOfResidentalEstateMapper.toDto(typesOfResidentalEstate);
    }

    @Override
    public Optional<TypesOfResidentalEstateDTO> partialUpdate(TypesOfResidentalEstateDTO typesOfResidentalEstateDTO) {
        log.debug("Request to partially update TypesOfResidentalEstate : {}", typesOfResidentalEstateDTO);

        return typesOfResidentalEstateRepository
            .findById(typesOfResidentalEstateDTO.getId())
            .map(existingTypesOfResidentalEstate -> {
                typesOfResidentalEstateMapper.partialUpdate(existingTypesOfResidentalEstate, typesOfResidentalEstateDTO);

                return existingTypesOfResidentalEstate;
            })
            .map(typesOfResidentalEstateRepository::save)
            .map(typesOfResidentalEstateMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TypesOfResidentalEstateDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TypesOfResidentalEstates");
        return typesOfResidentalEstateRepository.findAll(pageable).map(typesOfResidentalEstateMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TypesOfResidentalEstateDTO> findOne(Long id) {
        log.debug("Request to get TypesOfResidentalEstate : {}", id);
        return typesOfResidentalEstateRepository.findById(id).map(typesOfResidentalEstateMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TypesOfResidentalEstate : {}", id);
        typesOfResidentalEstateRepository.deleteById(id);
    }
}
