package com.bsu.app.service;

import com.bsu.app.domain.*; // for static metamodels
import com.bsu.app.domain.NonResidentalEstates;
import com.bsu.app.repository.NonResidentalEstatesRepository;
import com.bsu.app.service.criteria.NonResidentalEstatesCriteria;
import com.bsu.app.service.dto.NonResidentalEstatesDTO;
import com.bsu.app.service.mapper.NonResidentalEstatesMapper;
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
 * Service for executing complex queries for {@link NonResidentalEstates} entities in the database.
 * The main input is a {@link NonResidentalEstatesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link NonResidentalEstatesDTO} or a {@link Page} of {@link NonResidentalEstatesDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class NonResidentalEstatesQueryService extends QueryService<NonResidentalEstates> {

    private final Logger log = LoggerFactory.getLogger(NonResidentalEstatesQueryService.class);

    private final NonResidentalEstatesRepository nonResidentalEstatesRepository;

    private final NonResidentalEstatesMapper nonResidentalEstatesMapper;

    public NonResidentalEstatesQueryService(
        NonResidentalEstatesRepository nonResidentalEstatesRepository,
        NonResidentalEstatesMapper nonResidentalEstatesMapper
    ) {
        this.nonResidentalEstatesRepository = nonResidentalEstatesRepository;
        this.nonResidentalEstatesMapper = nonResidentalEstatesMapper;
    }

    /**
     * Return a {@link List} of {@link NonResidentalEstatesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<NonResidentalEstatesDTO> findByCriteria(NonResidentalEstatesCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<NonResidentalEstates> specification = createSpecification(criteria);
        return nonResidentalEstatesMapper.toDto(nonResidentalEstatesRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link NonResidentalEstatesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<NonResidentalEstatesDTO> findByCriteria(NonResidentalEstatesCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<NonResidentalEstates> specification = createSpecification(criteria);
        return nonResidentalEstatesRepository.findAll(specification, page).map(nonResidentalEstatesMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(NonResidentalEstatesCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<NonResidentalEstates> specification = createSpecification(criteria);
        return nonResidentalEstatesRepository.count(specification);
    }

    /**
     * Function to convert {@link NonResidentalEstatesCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<NonResidentalEstates> createSpecification(NonResidentalEstatesCriteria criteria) {
        Specification<NonResidentalEstates> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), NonResidentalEstates_.id));
            }
            if (criteria.getPrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPrice(), NonResidentalEstates_.price));
            }
            if (criteria.getCommissioningDate() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getCommissioningDate(), NonResidentalEstates_.commissioningDate));
            }
            if (criteria.getCadastralNumber() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getCadastralNumber(), NonResidentalEstates_.cadastralNumber));
            }
            if (criteria.getTotalSpace() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotalSpace(), NonResidentalEstates_.totalSpace));
            }
            if (criteria.getPurposeOfNonResidentalEstateIdId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getPurposeOfNonResidentalEstateIdId(),
                            root ->
                                root
                                    .join(NonResidentalEstates_.purposeOfNonResidentalEstateId, JoinType.LEFT)
                                    .get(PurposesOfNonResidentalEstate_.id)
                        )
                    );
            }
            if (criteria.getBuildingTypeOfNonResidentalEstateIdId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getBuildingTypeOfNonResidentalEstateIdId(),
                            root ->
                                root
                                    .join(NonResidentalEstates_.buildingTypeOfNonResidentalEstateId, JoinType.LEFT)
                                    .get(BuildingTypeOfNonResidentalEstate_.id)
                        )
                    );
            }
            if (criteria.getAddressIdId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getAddressIdId(),
                            root -> root.join(NonResidentalEstates_.addressId, JoinType.LEFT).get(Addresses_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
