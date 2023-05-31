package com.bsu.app.service.impl;

import com.bsu.app.domain.TypesOfCommercialEvents;
import com.bsu.app.repository.TypesOfCommercialEventsRepository;
import com.bsu.app.service.TypesOfCommercialEventsService;
import com.bsu.app.service.dto.TypesOfCommercialEventsDTO;
import com.bsu.app.service.mapper.TypesOfCommercialEventsMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TypesOfCommercialEvents}.
 */
@Service
@Transactional
public class TypesOfCommercialEventsServiceImpl implements TypesOfCommercialEventsService {

    private final Logger log = LoggerFactory.getLogger(TypesOfCommercialEventsServiceImpl.class);

    private final TypesOfCommercialEventsRepository typesOfCommercialEventsRepository;

    private final TypesOfCommercialEventsMapper typesOfCommercialEventsMapper;

    public TypesOfCommercialEventsServiceImpl(
        TypesOfCommercialEventsRepository typesOfCommercialEventsRepository,
        TypesOfCommercialEventsMapper typesOfCommercialEventsMapper
    ) {
        this.typesOfCommercialEventsRepository = typesOfCommercialEventsRepository;
        this.typesOfCommercialEventsMapper = typesOfCommercialEventsMapper;
    }

    @Override
    public TypesOfCommercialEventsDTO save(TypesOfCommercialEventsDTO typesOfCommercialEventsDTO) {
        log.debug("Request to save TypesOfCommercialEvents : {}", typesOfCommercialEventsDTO);
        TypesOfCommercialEvents typesOfCommercialEvents = typesOfCommercialEventsMapper.toEntity(typesOfCommercialEventsDTO);
        typesOfCommercialEvents = typesOfCommercialEventsRepository.save(typesOfCommercialEvents);
        return typesOfCommercialEventsMapper.toDto(typesOfCommercialEvents);
    }

    @Override
    public TypesOfCommercialEventsDTO update(TypesOfCommercialEventsDTO typesOfCommercialEventsDTO) {
        log.debug("Request to update TypesOfCommercialEvents : {}", typesOfCommercialEventsDTO);
        TypesOfCommercialEvents typesOfCommercialEvents = typesOfCommercialEventsMapper.toEntity(typesOfCommercialEventsDTO);
        typesOfCommercialEvents = typesOfCommercialEventsRepository.save(typesOfCommercialEvents);
        return typesOfCommercialEventsMapper.toDto(typesOfCommercialEvents);
    }

    @Override
    public Optional<TypesOfCommercialEventsDTO> partialUpdate(TypesOfCommercialEventsDTO typesOfCommercialEventsDTO) {
        log.debug("Request to partially update TypesOfCommercialEvents : {}", typesOfCommercialEventsDTO);

        return typesOfCommercialEventsRepository
            .findById(typesOfCommercialEventsDTO.getId())
            .map(existingTypesOfCommercialEvents -> {
                typesOfCommercialEventsMapper.partialUpdate(existingTypesOfCommercialEvents, typesOfCommercialEventsDTO);

                return existingTypesOfCommercialEvents;
            })
            .map(typesOfCommercialEventsRepository::save)
            .map(typesOfCommercialEventsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TypesOfCommercialEventsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TypesOfCommercialEvents");
        return typesOfCommercialEventsRepository.findAll(pageable).map(typesOfCommercialEventsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TypesOfCommercialEventsDTO> findOne(Long id) {
        log.debug("Request to get TypesOfCommercialEvents : {}", id);
        return typesOfCommercialEventsRepository.findById(id).map(typesOfCommercialEventsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TypesOfCommercialEvents : {}", id);
        typesOfCommercialEventsRepository.deleteById(id);
    }
}
