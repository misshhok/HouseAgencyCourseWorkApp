package com.bsu.app.repository;

import com.bsu.app.domain.Clients;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Clients entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClientsRepository extends JpaRepository<Clients, Long>, JpaSpecificationExecutor<Clients> {}
