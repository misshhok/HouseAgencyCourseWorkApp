package com.bsu.app.service;

import com.bsu.app.domain.*; // for static metamodels
import com.bsu.app.domain.Clients;
import com.bsu.app.repository.ClientsRepository;
import com.bsu.app.service.criteria.ClientsCriteria;
import com.bsu.app.service.dto.ClientsDTO;
import com.bsu.app.service.mapper.ClientsMapper;
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
 * Service for executing complex queries for {@link Clients} entities in the database.
 * The main input is a {@link ClientsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ClientsDTO} or a {@link Page} of {@link ClientsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ClientsQueryService extends QueryService<Clients> {

    private final Logger log = LoggerFactory.getLogger(ClientsQueryService.class);

    private final ClientsRepository clientsRepository;

    private final ClientsMapper clientsMapper;

    public ClientsQueryService(ClientsRepository clientsRepository, ClientsMapper clientsMapper) {
        this.clientsRepository = clientsRepository;
        this.clientsMapper = clientsMapper;
    }

    /**
     * Return a {@link List} of {@link ClientsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ClientsDTO> findByCriteria(ClientsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Clients> specification = createSpecification(criteria);
        return clientsMapper.toDto(clientsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ClientsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ClientsDTO> findByCriteria(ClientsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Clients> specification = createSpecification(criteria);
        return clientsRepository.findAll(specification, page).map(clientsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ClientsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Clients> specification = createSpecification(criteria);
        return clientsRepository.count(specification);
    }

    /**
     * Function to convert {@link ClientsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Clients> createSpecification(ClientsCriteria criteria) {
        Specification<Clients> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Clients_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Clients_.name));
            }
            if (criteria.getSurename() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSurename(), Clients_.surename));
            }
            if (criteria.getPatronymic() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPatronymic(), Clients_.patronymic));
            }
            if (criteria.getPhoneNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPhoneNumber(), Clients_.phoneNumber));
            }
        }
        return specification;
    }
}
