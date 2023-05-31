package com.bsu.app.service.impl;

import com.bsu.app.domain.Cities;
import com.bsu.app.repository.CitiesRepository;
import com.bsu.app.service.CitiesService;
import com.bsu.app.service.dto.CitiesDTO;
import com.bsu.app.service.mapper.CitiesMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Cities}.
 */
@Service
@Transactional
public class CitiesServiceImpl implements CitiesService {

    private final Logger log = LoggerFactory.getLogger(CitiesServiceImpl.class);

    private final CitiesRepository citiesRepository;

    private final CitiesMapper citiesMapper;

    public CitiesServiceImpl(CitiesRepository citiesRepository, CitiesMapper citiesMapper) {
        this.citiesRepository = citiesRepository;
        this.citiesMapper = citiesMapper;
    }

    @Override
    public CitiesDTO save(CitiesDTO citiesDTO) {
        log.debug("Request to save Cities : {}", citiesDTO);
        Cities cities = citiesMapper.toEntity(citiesDTO);
        cities = citiesRepository.save(cities);
        return citiesMapper.toDto(cities);
    }

    @Override
    public CitiesDTO update(CitiesDTO citiesDTO) {
        log.debug("Request to update Cities : {}", citiesDTO);
        Cities cities = citiesMapper.toEntity(citiesDTO);
        cities = citiesRepository.save(cities);
        return citiesMapper.toDto(cities);
    }

    @Override
    public Optional<CitiesDTO> partialUpdate(CitiesDTO citiesDTO) {
        log.debug("Request to partially update Cities : {}", citiesDTO);

        return citiesRepository
            .findById(citiesDTO.getId())
            .map(existingCities -> {
                citiesMapper.partialUpdate(existingCities, citiesDTO);

                return existingCities;
            })
            .map(citiesRepository::save)
            .map(citiesMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CitiesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Cities");
        return citiesRepository.findAll(pageable).map(citiesMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CitiesDTO> findOne(Long id) {
        log.debug("Request to get Cities : {}", id);
        return citiesRepository.findById(id).map(citiesMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Cities : {}", id);
        citiesRepository.deleteById(id);
    }
}
