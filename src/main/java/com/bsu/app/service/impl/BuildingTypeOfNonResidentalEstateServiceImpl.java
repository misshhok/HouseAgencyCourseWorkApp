package com.bsu.app.service.impl;

import com.bsu.app.domain.BuildingTypeOfNonResidentalEstate;
import com.bsu.app.repository.BuildingTypeOfNonResidentalEstateRepository;
import com.bsu.app.service.BuildingTypeOfNonResidentalEstateService;
import com.bsu.app.service.dto.BuildingTypeOfNonResidentalEstateDTO;
import com.bsu.app.service.mapper.BuildingTypeOfNonResidentalEstateMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link BuildingTypeOfNonResidentalEstate}.
 */
@Service
@Transactional
public class BuildingTypeOfNonResidentalEstateServiceImpl implements BuildingTypeOfNonResidentalEstateService {

    private final Logger log = LoggerFactory.getLogger(BuildingTypeOfNonResidentalEstateServiceImpl.class);

    private final BuildingTypeOfNonResidentalEstateRepository buildingTypeOfNonResidentalEstateRepository;

    private final BuildingTypeOfNonResidentalEstateMapper buildingTypeOfNonResidentalEstateMapper;

    public BuildingTypeOfNonResidentalEstateServiceImpl(
        BuildingTypeOfNonResidentalEstateRepository buildingTypeOfNonResidentalEstateRepository,
        BuildingTypeOfNonResidentalEstateMapper buildingTypeOfNonResidentalEstateMapper
    ) {
        this.buildingTypeOfNonResidentalEstateRepository = buildingTypeOfNonResidentalEstateRepository;
        this.buildingTypeOfNonResidentalEstateMapper = buildingTypeOfNonResidentalEstateMapper;
    }

    @Override
    public BuildingTypeOfNonResidentalEstateDTO save(BuildingTypeOfNonResidentalEstateDTO buildingTypeOfNonResidentalEstateDTO) {
        log.debug("Request to save BuildingTypeOfNonResidentalEstate : {}", buildingTypeOfNonResidentalEstateDTO);
        BuildingTypeOfNonResidentalEstate buildingTypeOfNonResidentalEstate = buildingTypeOfNonResidentalEstateMapper.toEntity(
            buildingTypeOfNonResidentalEstateDTO
        );
        buildingTypeOfNonResidentalEstate = buildingTypeOfNonResidentalEstateRepository.save(buildingTypeOfNonResidentalEstate);
        return buildingTypeOfNonResidentalEstateMapper.toDto(buildingTypeOfNonResidentalEstate);
    }

    @Override
    public BuildingTypeOfNonResidentalEstateDTO update(BuildingTypeOfNonResidentalEstateDTO buildingTypeOfNonResidentalEstateDTO) {
        log.debug("Request to update BuildingTypeOfNonResidentalEstate : {}", buildingTypeOfNonResidentalEstateDTO);
        BuildingTypeOfNonResidentalEstate buildingTypeOfNonResidentalEstate = buildingTypeOfNonResidentalEstateMapper.toEntity(
            buildingTypeOfNonResidentalEstateDTO
        );
        buildingTypeOfNonResidentalEstate = buildingTypeOfNonResidentalEstateRepository.save(buildingTypeOfNonResidentalEstate);
        return buildingTypeOfNonResidentalEstateMapper.toDto(buildingTypeOfNonResidentalEstate);
    }

    @Override
    public Optional<BuildingTypeOfNonResidentalEstateDTO> partialUpdate(
        BuildingTypeOfNonResidentalEstateDTO buildingTypeOfNonResidentalEstateDTO
    ) {
        log.debug("Request to partially update BuildingTypeOfNonResidentalEstate : {}", buildingTypeOfNonResidentalEstateDTO);

        return buildingTypeOfNonResidentalEstateRepository
            .findById(buildingTypeOfNonResidentalEstateDTO.getId())
            .map(existingBuildingTypeOfNonResidentalEstate -> {
                buildingTypeOfNonResidentalEstateMapper.partialUpdate(
                    existingBuildingTypeOfNonResidentalEstate,
                    buildingTypeOfNonResidentalEstateDTO
                );

                return existingBuildingTypeOfNonResidentalEstate;
            })
            .map(buildingTypeOfNonResidentalEstateRepository::save)
            .map(buildingTypeOfNonResidentalEstateMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BuildingTypeOfNonResidentalEstateDTO> findAll(Pageable pageable) {
        log.debug("Request to get all BuildingTypeOfNonResidentalEstates");
        return buildingTypeOfNonResidentalEstateRepository.findAll(pageable).map(buildingTypeOfNonResidentalEstateMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BuildingTypeOfNonResidentalEstateDTO> findOne(Long id) {
        log.debug("Request to get BuildingTypeOfNonResidentalEstate : {}", id);
        return buildingTypeOfNonResidentalEstateRepository.findById(id).map(buildingTypeOfNonResidentalEstateMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete BuildingTypeOfNonResidentalEstate : {}", id);
        buildingTypeOfNonResidentalEstateRepository.deleteById(id);
    }
}
