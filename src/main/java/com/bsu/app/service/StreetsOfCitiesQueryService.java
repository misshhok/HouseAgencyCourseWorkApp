package com.bsu.app.service;

import com.bsu.app.domain.*; // for static metamodels
import com.bsu.app.domain.StreetsOfCities;
import com.bsu.app.repository.StreetsOfCitiesRepository;
import com.bsu.app.service.criteria.StreetsOfCitiesCriteria;
import com.bsu.app.service.dto.StreetsOfCitiesDTO;
import com.bsu.app.service.mapper.StreetsOfCitiesMapper;
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
 * Service for executing complex queries for {@link StreetsOfCities} entities in the database.
 * The main input is a {@link StreetsOfCitiesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link StreetsOfCitiesDTO} or a {@link Page} of {@link StreetsOfCitiesDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class StreetsOfCitiesQueryService extends QueryService<StreetsOfCities> {

    private final Logger log = LoggerFactory.getLogger(StreetsOfCitiesQueryService.class);

    private final StreetsOfCitiesRepository streetsOfCitiesRepository;

    private final StreetsOfCitiesMapper streetsOfCitiesMapper;

    public StreetsOfCitiesQueryService(StreetsOfCitiesRepository streetsOfCitiesRepository, StreetsOfCitiesMapper streetsOfCitiesMapper) {
        this.streetsOfCitiesRepository = streetsOfCitiesRepository;
        this.streetsOfCitiesMapper = streetsOfCitiesMapper;
    }

    /**
     * Return a {@link List} of {@link StreetsOfCitiesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<StreetsOfCitiesDTO> findByCriteria(StreetsOfCitiesCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<StreetsOfCities> specification = createSpecification(criteria);
        return streetsOfCitiesMapper.toDto(streetsOfCitiesRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link StreetsOfCitiesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<StreetsOfCitiesDTO> findByCriteria(StreetsOfCitiesCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<StreetsOfCities> specification = createSpecification(criteria);
        return streetsOfCitiesRepository.findAll(specification, page).map(streetsOfCitiesMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(StreetsOfCitiesCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<StreetsOfCities> specification = createSpecification(criteria);
        return streetsOfCitiesRepository.count(specification);
    }

    /**
     * Function to convert {@link StreetsOfCitiesCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<StreetsOfCities> createSpecification(StreetsOfCitiesCriteria criteria) {
        Specification<StreetsOfCities> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), StreetsOfCities_.id));
            }
            if (criteria.getTitle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitle(), StreetsOfCities_.title));
            }
            if (criteria.getCityIdId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCityIdId(),
                            root -> root.join(StreetsOfCities_.cityId, JoinType.LEFT).get(Cities_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
