package com.bsu.app.service;

import com.bsu.app.domain.*; // for static metamodels
import com.bsu.app.domain.TypesOfCommercialEvents;
import com.bsu.app.repository.TypesOfCommercialEventsRepository;
import com.bsu.app.service.criteria.TypesOfCommercialEventsCriteria;
import com.bsu.app.service.dto.TypesOfCommercialEventsDTO;
import com.bsu.app.service.mapper.TypesOfCommercialEventsMapper;
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
 * Service for executing complex queries for {@link TypesOfCommercialEvents} entities in the database.
 * The main input is a {@link TypesOfCommercialEventsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TypesOfCommercialEventsDTO} or a {@link Page} of {@link TypesOfCommercialEventsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TypesOfCommercialEventsQueryService extends QueryService<TypesOfCommercialEvents> {

    private final Logger log = LoggerFactory.getLogger(TypesOfCommercialEventsQueryService.class);

    private final TypesOfCommercialEventsRepository typesOfCommercialEventsRepository;

    private final TypesOfCommercialEventsMapper typesOfCommercialEventsMapper;

    public TypesOfCommercialEventsQueryService(
        TypesOfCommercialEventsRepository typesOfCommercialEventsRepository,
        TypesOfCommercialEventsMapper typesOfCommercialEventsMapper
    ) {
        this.typesOfCommercialEventsRepository = typesOfCommercialEventsRepository;
        this.typesOfCommercialEventsMapper = typesOfCommercialEventsMapper;
    }

    /**
     * Return a {@link List} of {@link TypesOfCommercialEventsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TypesOfCommercialEventsDTO> findByCriteria(TypesOfCommercialEventsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<TypesOfCommercialEvents> specification = createSpecification(criteria);
        return typesOfCommercialEventsMapper.toDto(typesOfCommercialEventsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link TypesOfCommercialEventsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TypesOfCommercialEventsDTO> findByCriteria(TypesOfCommercialEventsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<TypesOfCommercialEvents> specification = createSpecification(criteria);
        return typesOfCommercialEventsRepository.findAll(specification, page).map(typesOfCommercialEventsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TypesOfCommercialEventsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<TypesOfCommercialEvents> specification = createSpecification(criteria);
        return typesOfCommercialEventsRepository.count(specification);
    }

    /**
     * Function to convert {@link TypesOfCommercialEventsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<TypesOfCommercialEvents> createSpecification(TypesOfCommercialEventsCriteria criteria) {
        Specification<TypesOfCommercialEvents> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), TypesOfCommercialEvents_.id));
            }
            if (criteria.getTitle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitle(), TypesOfCommercialEvents_.title));
            }
            if (criteria.getDescription() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getDescription(), TypesOfCommercialEvents_.description));
            }
        }
        return specification;
    }
}
