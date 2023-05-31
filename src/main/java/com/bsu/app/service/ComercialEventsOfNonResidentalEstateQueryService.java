package com.bsu.app.service;

import com.bsu.app.domain.*; // for static metamodels
import com.bsu.app.domain.ComercialEventsOfNonResidentalEstate;
import com.bsu.app.repository.ComercialEventsOfNonResidentalEstateRepository;
import com.bsu.app.service.criteria.ComercialEventsOfNonResidentalEstateCriteria;
import com.bsu.app.service.dto.ComercialEventsOfNonResidentalEstateDTO;
import com.bsu.app.service.mapper.ComercialEventsOfNonResidentalEstateMapper;
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
 * Service for executing complex queries for {@link ComercialEventsOfNonResidentalEstate} entities in the database.
 * The main input is a {@link ComercialEventsOfNonResidentalEstateCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ComercialEventsOfNonResidentalEstateDTO} or a {@link Page} of {@link ComercialEventsOfNonResidentalEstateDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ComercialEventsOfNonResidentalEstateQueryService extends QueryService<ComercialEventsOfNonResidentalEstate> {

    private final Logger log = LoggerFactory.getLogger(ComercialEventsOfNonResidentalEstateQueryService.class);

    private final ComercialEventsOfNonResidentalEstateRepository comercialEventsOfNonResidentalEstateRepository;

    private final ComercialEventsOfNonResidentalEstateMapper comercialEventsOfNonResidentalEstateMapper;

    public ComercialEventsOfNonResidentalEstateQueryService(
        ComercialEventsOfNonResidentalEstateRepository comercialEventsOfNonResidentalEstateRepository,
        ComercialEventsOfNonResidentalEstateMapper comercialEventsOfNonResidentalEstateMapper
    ) {
        this.comercialEventsOfNonResidentalEstateRepository = comercialEventsOfNonResidentalEstateRepository;
        this.comercialEventsOfNonResidentalEstateMapper = comercialEventsOfNonResidentalEstateMapper;
    }

    /**
     * Return a {@link List} of {@link ComercialEventsOfNonResidentalEstateDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ComercialEventsOfNonResidentalEstateDTO> findByCriteria(ComercialEventsOfNonResidentalEstateCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ComercialEventsOfNonResidentalEstate> specification = createSpecification(criteria);
        return comercialEventsOfNonResidentalEstateMapper.toDto(comercialEventsOfNonResidentalEstateRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ComercialEventsOfNonResidentalEstateDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ComercialEventsOfNonResidentalEstateDTO> findByCriteria(
        ComercialEventsOfNonResidentalEstateCriteria criteria,
        Pageable page
    ) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ComercialEventsOfNonResidentalEstate> specification = createSpecification(criteria);
        return comercialEventsOfNonResidentalEstateRepository
            .findAll(specification, page)
            .map(comercialEventsOfNonResidentalEstateMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ComercialEventsOfNonResidentalEstateCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ComercialEventsOfNonResidentalEstate> specification = createSpecification(criteria);
        return comercialEventsOfNonResidentalEstateRepository.count(specification);
    }

    /**
     * Function to convert {@link ComercialEventsOfNonResidentalEstateCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ComercialEventsOfNonResidentalEstate> createSpecification(
        ComercialEventsOfNonResidentalEstateCriteria criteria
    ) {
        Specification<ComercialEventsOfNonResidentalEstate> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ComercialEventsOfNonResidentalEstate_.id));
            }
            if (criteria.getAgentNotes() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getAgentNotes(), ComercialEventsOfNonResidentalEstate_.agentNotes));
            }
            if (criteria.getDateOfEvent() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(criteria.getDateOfEvent(), ComercialEventsOfNonResidentalEstate_.dateOfEvent)
                    );
            }
            if (criteria.getTypeOfCommercialEventIdId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getTypeOfCommercialEventIdId(),
                            root ->
                                root
                                    .join(ComercialEventsOfNonResidentalEstate_.typeOfCommercialEventId, JoinType.LEFT)
                                    .get(TypesOfCommercialEvents_.id)
                        )
                    );
            }
            if (criteria.getNonResidentalEstateIdId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getNonResidentalEstateIdId(),
                            root ->
                                root
                                    .join(ComercialEventsOfNonResidentalEstate_.nonResidentalEstateId, JoinType.LEFT)
                                    .get(NonResidentalEstates_.id)
                        )
                    );
            }
            if (criteria.getClientIdId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getClientIdId(),
                            root -> root.join(ComercialEventsOfNonResidentalEstate_.clientId, JoinType.LEFT).get(Clients_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
