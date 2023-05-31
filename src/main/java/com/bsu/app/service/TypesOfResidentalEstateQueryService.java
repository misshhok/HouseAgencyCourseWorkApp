package com.bsu.app.service;

import com.bsu.app.domain.*; // for static metamodels
import com.bsu.app.domain.TypesOfResidentalEstate;
import com.bsu.app.repository.TypesOfResidentalEstateRepository;
import com.bsu.app.service.criteria.TypesOfResidentalEstateCriteria;
import com.bsu.app.service.dto.TypesOfResidentalEstateDTO;
import com.bsu.app.service.mapper.TypesOfResidentalEstateMapper;
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
 * Service for executing complex queries for {@link TypesOfResidentalEstate} entities in the database.
 * The main input is a {@link TypesOfResidentalEstateCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TypesOfResidentalEstateDTO} or a {@link Page} of {@link TypesOfResidentalEstateDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TypesOfResidentalEstateQueryService extends QueryService<TypesOfResidentalEstate> {

    private final Logger log = LoggerFactory.getLogger(TypesOfResidentalEstateQueryService.class);

    private final TypesOfResidentalEstateRepository typesOfResidentalEstateRepository;

    private final TypesOfResidentalEstateMapper typesOfResidentalEstateMapper;

    public TypesOfResidentalEstateQueryService(
        TypesOfResidentalEstateRepository typesOfResidentalEstateRepository,
        TypesOfResidentalEstateMapper typesOfResidentalEstateMapper
    ) {
        this.typesOfResidentalEstateRepository = typesOfResidentalEstateRepository;
        this.typesOfResidentalEstateMapper = typesOfResidentalEstateMapper;
    }

    /**
     * Return a {@link List} of {@link TypesOfResidentalEstateDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TypesOfResidentalEstateDTO> findByCriteria(TypesOfResidentalEstateCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<TypesOfResidentalEstate> specification = createSpecification(criteria);
        return typesOfResidentalEstateMapper.toDto(typesOfResidentalEstateRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link TypesOfResidentalEstateDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TypesOfResidentalEstateDTO> findByCriteria(TypesOfResidentalEstateCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<TypesOfResidentalEstate> specification = createSpecification(criteria);
        return typesOfResidentalEstateRepository.findAll(specification, page).map(typesOfResidentalEstateMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TypesOfResidentalEstateCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<TypesOfResidentalEstate> specification = createSpecification(criteria);
        return typesOfResidentalEstateRepository.count(specification);
    }

    /**
     * Function to convert {@link TypesOfResidentalEstateCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<TypesOfResidentalEstate> createSpecification(TypesOfResidentalEstateCriteria criteria) {
        Specification<TypesOfResidentalEstate> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), TypesOfResidentalEstate_.id));
            }
            if (criteria.getTitle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitle(), TypesOfResidentalEstate_.title));
            }
        }
        return specification;
    }
}
