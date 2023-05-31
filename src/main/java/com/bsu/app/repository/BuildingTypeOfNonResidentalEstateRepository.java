package com.bsu.app.repository;

import com.bsu.app.domain.BuildingTypeOfNonResidentalEstate;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the BuildingTypeOfNonResidentalEstate entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BuildingTypeOfNonResidentalEstateRepository
    extends JpaRepository<BuildingTypeOfNonResidentalEstate, Long>, JpaSpecificationExecutor<BuildingTypeOfNonResidentalEstate> {}
