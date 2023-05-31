package com.bsu.app.repository;

import com.bsu.app.domain.TypesOfResidentalEstate;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the TypesOfResidentalEstate entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TypesOfResidentalEstateRepository
    extends JpaRepository<TypesOfResidentalEstate, Long>, JpaSpecificationExecutor<TypesOfResidentalEstate> {}
