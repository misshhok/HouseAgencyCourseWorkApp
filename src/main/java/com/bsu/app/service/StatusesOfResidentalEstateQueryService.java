package com.bsu.app.service;

import com.bsu.app.domain.*; // for static metamodels
import com.bsu.app.domain.StatusesOfResidentalEstate;
import com.bsu.app.repository.StatusesOfResidentalEstateRepository;
import com.bsu.app.service.criteria.StatusesOfResidentalEstateCriteria;
import com.bsu.app.service.dto.StatusesOfResidentalEstateDTO;
import com.bsu.app.service.mapper.StatusesOfResidentalEstateMapper;
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
 * Service for executing complex queries for {@link StatusesOfResidentalEstate} entities in the database.
 * The main input is a {@link StatusesOfResidentalEstateCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link StatusesOfResidentalEstateDTO} or a {@link Page} of {@link StatusesOfResidentalEstateDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class StatusesOfResidentalEstateQueryService extends QueryService<StatusesOfResidentalEstate> {

    private final Logger log = LoggerFactory.getLogger(StatusesOfResidentalEstateQueryService.class);

    private final StatusesOfResidentalEstateRepository statusesOfResidentalEstateRepository;

    private final StatusesOfResidentalEstateMapper statusesOfResidentalEstateMapper;

    public StatusesOfResidentalEstateQueryService(
        StatusesOfResidentalEstateRepository statusesOfResidentalEstateRepository,
        StatusesOfResidentalEstateMapper statusesOfResidentalEstateMapper
    ) {
        this.statusesOfResidentalEstateRepository = statusesOfResidentalEstateRepository;
        this.statusesOfResidentalEstateMapper = statusesOfResidentalEstateMapper;
    }

    /**
     * Return a {@link List} of {@link StatusesOfResidentalEstateDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<StatusesOfResidentalEstateDTO> findByCriteria(StatusesOfResidentalEstateCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<StatusesOfResidentalEstate> specification = createSpecification(criteria);
        return statusesOfResidentalEstateMapper.toDto(statusesOfResidentalEstateRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link StatusesOfResidentalEstateDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<StatusesOfResidentalEstateDTO> findByCriteria(StatusesOfResidentalEstateCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<StatusesOfResidentalEstate> specification = createSpecification(criteria);
        return statusesOfResidentalEstateRepository.findAll(specification, page).map(statusesOfResidentalEstateMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(StatusesOfResidentalEstateCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<StatusesOfResidentalEstate> specification = createSpecification(criteria);
        return statusesOfResidentalEstateRepository.count(specification);
    }

    /**
     * Function to convert {@link StatusesOfResidentalEstateCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<StatusesOfResidentalEstate> createSpecification(StatusesOfResidentalEstateCriteria criteria) {
        Specification<StatusesOfResidentalEstate> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), StatusesOfResidentalEstate_.id));
            }
            if (criteria.getTitle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitle(), StatusesOfResidentalEstate_.title));
            }
        }
        return specification;
    }
}
