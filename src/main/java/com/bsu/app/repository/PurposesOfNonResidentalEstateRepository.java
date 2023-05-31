package com.bsu.app.repository;

import com.bsu.app.domain.PurposesOfNonResidentalEstate;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PurposesOfNonResidentalEstate entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PurposesOfNonResidentalEstateRepository
    extends JpaRepository<PurposesOfNonResidentalEstate, Long>, JpaSpecificationExecutor<PurposesOfNonResidentalEstate> {}
