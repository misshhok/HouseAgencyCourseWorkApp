package com.bsu.app.service;

import com.bsu.app.domain.*; // for static metamodels
import com.bsu.app.domain.ComercialEventsOfResidentalEstate;
import com.bsu.app.repository.ComercialEventsOfResidentalEstateRepository;
import com.bsu.app.service.criteria.ComercialEventsOfResidentalEstateCriteria;
import com.bsu.app.service.dto.ComercialEventsOfResidentalEstateDTO;
import com.bsu.app.service.mapper.ComercialEventsOfResidentalEstateMapper;
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
 * Service for executing complex queries for {@link ComercialEventsOfResidentalEstate} entities in the database.
 * The main input is a {@link ComercialEventsOfResidentalEstateCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ComercialEventsOfResidentalEstateDTO} or a {@link Page} of {@link ComercialEventsOfResidentalEstateDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ComercialEventsOfResidentalEstateQueryService extends QueryService<ComercialEventsOfResidentalEstate> {

    private final Logger log = LoggerFactory.getLogger(ComercialEventsOfResidentalEstateQueryService.class);

    private final ComercialEventsOfResidentalEstateRepository comercialEventsOfResidentalEstateRepository;

    private final ComercialEventsOfResidentalEstateMapper comercialEventsOfResidentalEstateMapper;

    public ComercialEventsOfResidentalEstateQueryService(
        ComercialEventsOfResidentalEstateRepository comercialEventsOfResidentalEstateRepository,
        ComercialEventsOfResidentalEstateMapper comercialEventsOfResidentalEstateMapper
    ) {
        this.comercialEventsOfResidentalEstateRepository = comercialEventsOfResidentalEstateRepository;
        this.comercialEventsOfResidentalEstateMapper = comercialEventsOfResidentalEstateMapper;
    }

    /**
     * Return a {@link List} of {@link ComercialEventsOfResidentalEstateDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ComercialEventsOfResidentalEstateDTO> findByCriteria(ComercialEventsOfResidentalEstateCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ComercialEventsOfResidentalEstate> specification = createSpecification(criteria);
        return comercialEventsOfResidentalEstateMapper.toDto(comercialEventsOfResidentalEstateRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ComercialEventsOfResidentalEstateDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ComercialEventsOfResidentalEstateDTO> findByCriteria(ComercialEventsOfResidentalEstateCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ComercialEventsOfResidentalEstate> specification = createSpecification(criteria);
        return comercialEventsOfResidentalEstateRepository.findAll(specification, page).map(comercialEventsOfResidentalEstateMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ComercialEventsOfResidentalEstateCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ComercialEventsOfResidentalEstate> specification = createSpecification(criteria);
        return comercialEventsOfResidentalEstateRepository.count(specification);
    }

    /**
     * Function to convert {@link ComercialEventsOfResidentalEstateCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ComercialEventsOfResidentalEstate> createSpecification(ComercialEventsOfResidentalEstateCriteria criteria) {
        Specification<ComercialEventsOfResidentalEstate> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ComercialEventsOfResidentalEstate_.id));
            }
            if (criteria.getAgentNotes() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getAgentNotes(), ComercialEventsOfResidentalEstate_.agentNotes));
            }
            if (criteria.getDateOfEvent() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getDateOfEvent(), ComercialEventsOfResidentalEstate_.dateOfEvent));
            }
            if (criteria.getTypeOfCommercialEventIdId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getTypeOfCommercialEventIdId(),
                            root ->
                                root
                                    .join(ComercialEventsOfResidentalEstate_.typeOfCommercialEventId, JoinType.LEFT)
                                    .get(TypesOfCommercialEvents_.id)
                        )
                    );
            }
            if (criteria.getClientIdId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getClientIdId(),
                            root -> root.join(ComercialEventsOfResidentalEstate_.clientId, JoinType.LEFT).get(Clients_.id)
                        )
                    );
            }
            if (criteria.getResidentalEstateIdId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getResidentalEstateIdId(),
                            root ->
                                root.join(ComercialEventsOfResidentalEstate_.residentalEstateId, JoinType.LEFT).get(ResidentalEstates_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
