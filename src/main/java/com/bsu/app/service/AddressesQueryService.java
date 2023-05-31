package com.bsu.app.service;

import com.bsu.app.domain.*; // for static metamodels
import com.bsu.app.domain.Addresses;
import com.bsu.app.repository.AddressesRepository;
import com.bsu.app.service.criteria.AddressesCriteria;
import com.bsu.app.service.dto.AddressesDTO;
import com.bsu.app.service.mapper.AddressesMapper;
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
 * Service for executing complex queries for {@link Addresses} entities in the database.
 * The main input is a {@link AddressesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AddressesDTO} or a {@link Page} of {@link AddressesDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AddressesQueryService extends QueryService<Addresses> {

    private final Logger log = LoggerFactory.getLogger(AddressesQueryService.class);

    private final AddressesRepository addressesRepository;

    private final AddressesMapper addressesMapper;

    public AddressesQueryService(AddressesRepository addressesRepository, AddressesMapper addressesMapper) {
        this.addressesRepository = addressesRepository;
        this.addressesMapper = addressesMapper;
    }

    /**
     * Return a {@link List} of {@link AddressesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AddressesDTO> findByCriteria(AddressesCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Addresses> specification = createSpecification(criteria);
        return addressesMapper.toDto(addressesRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AddressesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AddressesDTO> findByCriteria(AddressesCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Addresses> specification = createSpecification(criteria);
        return addressesRepository.findAll(specification, page).map(addressesMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AddressesCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Addresses> specification = createSpecification(criteria);
        return addressesRepository.count(specification);
    }

    /**
     * Function to convert {@link AddressesCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Addresses> createSpecification(AddressesCriteria criteria) {
        Specification<Addresses> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Addresses_.id));
            }
            if (criteria.getHouseNumber() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getHouseNumber(), Addresses_.houseNumber));
            }
            if (criteria.getStreetOfCityIdId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getStreetOfCityIdId(),
                            root -> root.join(Addresses_.streetOfCityId, JoinType.LEFT).get(StreetsOfCities_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
