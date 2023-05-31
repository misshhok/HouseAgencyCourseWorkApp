package com.bsu.app.service;

import com.bsu.app.domain.*; // for static metamodels
import com.bsu.app.domain.BuildingTypeOfNonResidentalEstate;
import com.bsu.app.repository.BuildingTypeOfNonResidentalEstateRepository;
import com.bsu.app.service.criteria.BuildingTypeOfNonResidentalEstateCriteria;
import com.bsu.app.service.dto.BuildingTypeOfNonResidentalEstateDTO;
import com.bsu.app.service.mapper.BuildingTypeOfNonResidentalEstateMapper;
import java.util.List;
import javax.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link BuildingTypeOfNonResidentalEstate} entities in the database.
 * The main input is a {@link BuildingTypeOfNonResidentalEstateCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link BuildingTypeOfNonResidentalEstateDTO} or a {@link Page} of {@link BuildingTypeOfNonResidentalEstateDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class BuildingTypeOfNonResidentalEstateQueryService extends QueryService<BuildingTypeOfNonResidentalEstate> {

    private final Logger log = LoggerFactory.getLogger(BuildingTypeOfNonResidentalEstateQueryService.class);

    private final BuildingTypeOfNonResidentalEstateRepository buildingTypeOfNonResidentalEstateRepository;

    private final BuildingTypeOfNonResidentalEstateMapper buildingTypeOfNonResidentalEstateMapper;

    public BuildingTypeOfNonResidentalEstateQueryService(
        BuildingTypeOfNonResidentalEstateRepository buildingTypeOfNonResidentalEstateRepository,
        BuildingTypeOfNonResidentalEstateMapper buildingTypeOfNonResidentalEstateMapper
    ) {
        this.buildingTypeOfNonResidentalEstateRepository = buildingTypeOfNonResidentalEstateRepository;
        this.buildingTypeOfNonResidentalEstateMapper = buildingTypeOfNonResidentalEstateMapper;
    }

    /**
     * Return a {@link List} of {@link BuildingTypeOfNonResidentalEstateDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<BuildingTypeOfNonResidentalEstateDTO> findByCriteria(BuildingTypeOfNonResidentalEstateCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<BuildingTypeOfNonResidentalEstate> specification = createSpecification(criteria);
        return buildingTypeOfNonResidentalEstateMapper.toDto(buildingTypeOfNonResidentalEstateRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link BuildingTypeOfNonResidentalEstateDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<BuildingTypeOfNonResidentalEstateDTO> findByCriteria(BuildingTypeOfNonResidentalEstateCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<BuildingTypeOfNonResidentalEstate> specification = createSpecification(criteria);
        return buildingTypeOfNonResidentalEstateRepository.findAll(specification, page).map(buildingTypeOfNonResidentalEstateMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(BuildingTypeOfNonResidentalEstateCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<BuildingTypeOfNonResidentalEstate> specification = createSpecification(criteria);
        return buildingTypeOfNonResidentalEstateRepository.count(specification);
    }

    /**
     * Function to convert {@link BuildingTypeOfNonResidentalEstateCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<BuildingTypeOfNonResidentalEstate> createSpecification(BuildingTypeOfNonResidentalEstateCriteria criteria) {
        Specification<BuildingTypeOfNonResidentalEstate> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), BuildingTypeOfNonResidentalEstate_.id));
            }
            if (criteria.getTitle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitle(), BuildingTypeOfNonResidentalEstate_.title));
            }
        }
        return specification;
    }
}
