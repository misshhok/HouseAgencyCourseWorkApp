package com.bsu.app.service.impl;

import com.bsu.app.domain.StreetsOfCities;
import com.bsu.app.repository.StreetsOfCitiesRepository;
import com.bsu.app.service.StreetsOfCitiesService;
import com.bsu.app.service.dto.StreetsOfCitiesDTO;
import com.bsu.app.service.mapper.StreetsOfCitiesMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link StreetsOfCities}.
 */
@Service
@Transactional
public class StreetsOfCitiesServiceImpl implements StreetsOfCitiesService {

    private final Logger log = LoggerFactory.getLogger(StreetsOfCitiesServiceImpl.class);

    private final StreetsOfCitiesRepository streetsOfCitiesRepository;

    private final StreetsOfCitiesMapper streetsOfCitiesMapper;

    public StreetsOfCitiesServiceImpl(StreetsOfCitiesRepository streetsOfCitiesRepository, StreetsOfCitiesMapper streetsOfCitiesMapper) {
        this.streetsOfCitiesRepository = streetsOfCitiesRepository;
        this.streetsOfCitiesMapper = streetsOfCitiesMapper;
    }

    @Override
    public StreetsOfCitiesDTO save(StreetsOfCitiesDTO streetsOfCitiesDTO) {
        log.debug("Request to save StreetsOfCities : {}", streetsOfCitiesDTO);
        StreetsOfCities streetsOfCities = streetsOfCitiesMapper.toEntity(streetsOfCitiesDTO);
        streetsOfCities = streetsOfCitiesRepository.save(streetsOfCities);
        return streetsOfCitiesMapper.toDto(streetsOfCities);
    }

    @Override
    public StreetsOfCitiesDTO update(StreetsOfCitiesDTO streetsOfCitiesDTO) {
        log.debug("Request to update StreetsOfCities : {}", streetsOfCitiesDTO);
        StreetsOfCities streetsOfCities = streetsOfCitiesMapper.toEntity(streetsOfCitiesDTO);
        streetsOfCities = streetsOfCitiesRepository.save(streetsOfCities);
        return streetsOfCitiesMapper.toDto(streetsOfCities);
    }

    @Override
    public Optional<StreetsOfCitiesDTO> partialUpdate(StreetsOfCitiesDTO streetsOfCitiesDTO) {
        log.debug("Request to partially update StreetsOfCities : {}", streetsOfCitiesDTO);

        return streetsOfCitiesRepository
            .findById(streetsOfCitiesDTO.getId())
            .map(existingStreetsOfCities -> {
                streetsOfCitiesMapper.partialUpdate(existingStreetsOfCities, streetsOfCitiesDTO);

                return existingStreetsOfCities;
            })
            .map(streetsOfCitiesRepository::save)
            .map(streetsOfCitiesMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<StreetsOfCitiesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all StreetsOfCities");
        return streetsOfCitiesRepository.findAll(pageable).map(streetsOfCitiesMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<StreetsOfCitiesDTO> findOne(Long id) {
        log.debug("Request to get StreetsOfCities : {}", id);
        return streetsOfCitiesRepository.findById(id).map(streetsOfCitiesMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete StreetsOfCities : {}", id);
        streetsOfCitiesRepository.deleteById(id);
    }
}
