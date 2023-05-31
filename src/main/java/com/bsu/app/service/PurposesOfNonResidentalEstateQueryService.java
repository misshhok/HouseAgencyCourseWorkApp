package com.bsu.app.service;

import com.bsu.app.domain.*; // for static metamodels
import com.bsu.app.domain.PurposesOfNonResidentalEstate;
import com.bsu.app.repository.PurposesOfNonResidentalEstateRepository;
import com.bsu.app.service.criteria.PurposesOfNonResidentalEstateCriteria;
import com.bsu.app.service.dto.PurposesOfNonResidentalEstateDTO;
import com.bsu.app.service.mapper.PurposesOfNonResidentalEstateMapper;
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
 * Service for executing complex queries for {@link PurposesOfNonResidentalEstate} entities in the database.
 * The main input is a {@link PurposesOfNonResidentalEstateCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PurposesOfNonResidentalEstateDTO} or a {@link Page} of {@link PurposesOfNonResidentalEstateDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PurposesOfNonResidentalEstateQueryService extends QueryService<PurposesOfNonResidentalEstate> {

    private final Logger log = LoggerFactory.getLogger(PurposesOfNonResidentalEstateQueryService.class);

    private final PurposesOfNonResidentalEstateRepository purposesOfNonResidentalEstateRepository;

    private final PurposesOfNonResidentalEstateMapper purposesOfNonResidentalEstateMapper;

    public PurposesOfNonResidentalEstateQueryService(
        PurposesOfNonResidentalEstateRepository purposesOfNonResidentalEstateRepository,
        PurposesOfNonResidentalEstateMapper purposesOfNonResidentalEstateMapper
    ) {
        this.purposesOfNonResidentalEstateRepository = purposesOfNonResidentalEstateRepository;
        this.purposesOfNonResidentalEstateMapper = purposesOfNonResidentalEstateMapper;
    }

    /**
     * Return a {@link List} of {@link PurposesOfNonResidentalEstateDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PurposesOfNonResidentalEstateDTO> findByCriteria(PurposesOfNonResidentalEstateCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<PurposesOfNonResidentalEstate> specification = createSpecification(criteria);
        return purposesOfNonResidentalEstateMapper.toDto(purposesOfNonResidentalEstateRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PurposesOfNonResidentalEstateDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PurposesOfNonResidentalEstateDTO> findByCriteria(PurposesOfNonResidentalEstateCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PurposesOfNonResidentalEstate> specification = createSpecification(criteria);
        return purposesOfNonResidentalEstateRepository.findAll(specification, page).map(purposesOfNonResidentalEstateMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PurposesOfNonResidentalEstateCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PurposesOfNonResidentalEstate> specification = createSpecification(criteria);
        return purposesOfNonResidentalEstateRepository.count(specification);
    }

    /**
     * Function to convert {@link PurposesOfNonResidentalEstateCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<PurposesOfNonResidentalEstate> createSpecification(PurposesOfNonResidentalEstateCriteria criteria) {
        Specification<PurposesOfNonResidentalEstate> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), PurposesOfNonResidentalEstate_.id));
            }
            if (criteria.getTitle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitle(), PurposesOfNonResidentalEstate_.title));
            }
        }
        return specification;
    }
}
