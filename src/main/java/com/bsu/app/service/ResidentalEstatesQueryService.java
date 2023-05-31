package com.bsu.app.service;

import com.bsu.app.domain.*; // for static metamodels
import com.bsu.app.domain.ResidentalEstates;
import com.bsu.app.repository.ResidentalEstatesRepository;
import com.bsu.app.service.criteria.ResidentalEstatesCriteria;
import com.bsu.app.service.dto.ResidentalEstatesDTO;
import com.bsu.app.service.mapper.ResidentalEstatesMapper;
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
 * Service for executing complex queries for {@link ResidentalEstates} entities in the database.
 * The main input is a {@link ResidentalEstatesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ResidentalEstatesDTO} or a {@link Page} of {@link ResidentalEstatesDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ResidentalEstatesQueryService extends QueryService<ResidentalEstates> {

    private final Logger log = LoggerFactory.getLogger(ResidentalEstatesQueryService.class);

    private final ResidentalEstatesRepository residentalEstatesRepository;

    private final ResidentalEstatesMapper residentalEstatesMapper;

    public ResidentalEstatesQueryService(
        ResidentalEstatesRepository residentalEstatesRepository,
        ResidentalEstatesMapper residentalEstatesMapper
    ) {
        this.residentalEstatesRepository = residentalEstatesRepository;
        this.residentalEstatesMapper = residentalEstatesMapper;
    }

    /**
     * Return a {@link List} of {@link ResidentalEstatesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ResidentalEstatesDTO> findByCriteria(ResidentalEstatesCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ResidentalEstates> specification = createSpecification(criteria);
        return residentalEstatesMapper.toDto(residentalEstatesRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ResidentalEstatesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ResidentalEstatesDTO> findByCriteria(ResidentalEstatesCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ResidentalEstates> specification = createSpecification(criteria);
        return residentalEstatesRepository.findAll(specification, page).map(residentalEstatesMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ResidentalEstatesCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ResidentalEstates> specification = createSpecification(criteria);
        return residentalEstatesRepository.count(specification);
    }

    /**
     * Function to convert {@link ResidentalEstatesCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ResidentalEstates> createSpecification(ResidentalEstatesCriteria criteria) {
        Specification<ResidentalEstates> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ResidentalEstates_.id));
            }
            if (criteria.getLivingSpace() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLivingSpace(), ResidentalEstates_.livingSpace));
            }
            if (criteria.getCadastralNumber() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getCadastralNumber(), ResidentalEstates_.cadastralNumber));
            }
            if (criteria.getCommissioningDate() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getCommissioningDate(), ResidentalEstates_.commissioningDate));
            }
            if (criteria.getNumberOfRooms() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNumberOfRooms(), ResidentalEstates_.numberOfRooms));
            }
            if (criteria.getFurnishType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFurnishType(), ResidentalEstates_.furnishType));
            }
            if (criteria.getHasBalcony() != null) {
                specification = specification.and(buildSpecification(criteria.getHasBalcony(), ResidentalEstates_.hasBalcony));
            }
            if (criteria.getBathroomType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBathroomType(), ResidentalEstates_.bathroomType));
            }
            if (criteria.getCeilingHeight() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCeilingHeight(), ResidentalEstates_.ceilingHeight));
            }
            if (criteria.getPrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPrice(), ResidentalEstates_.price));
            }
            if (criteria.getAddressIdId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getAddressIdId(),
                            root -> root.join(ResidentalEstates_.addressId, JoinType.LEFT).get(Addresses_.id)
                        )
                    );
            }
            if (criteria.getTypeOfResidentalEstateIdId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getTypeOfResidentalEstateIdId(),
                            root -> root.join(ResidentalEstates_.typeOfResidentalEstateId, JoinType.LEFT).get(TypesOfResidentalEstate_.id)
                        )
                    );
            }
            if (criteria.getStatusOfResidentalEstateIdId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getStatusOfResidentalEstateIdId(),
                            root ->
                                root.join(ResidentalEstates_.statusOfResidentalEstateId, JoinType.LEFT).get(StatusesOfResidentalEstate_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
