package com.bsu.app.repository;

import com.bsu.app.domain.NonResidentalEstates;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the NonResidentalEstates entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NonResidentalEstatesRepository
    extends JpaRepository<NonResidentalEstates, Long>, JpaSpecificationExecutor<NonResidentalEstates> {}
