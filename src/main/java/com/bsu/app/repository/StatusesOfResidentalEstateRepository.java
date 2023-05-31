package com.bsu.app.repository;

import com.bsu.app.domain.StatusesOfResidentalEstate;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the StatusesOfResidentalEstate entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StatusesOfResidentalEstateRepository
    extends JpaRepository<StatusesOfResidentalEstate, Long>, JpaSpecificationExecutor<StatusesOfResidentalEstate> {}
