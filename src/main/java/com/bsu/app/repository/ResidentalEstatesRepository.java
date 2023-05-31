package com.bsu.app.repository;

import com.bsu.app.domain.ResidentalEstates;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ResidentalEstates entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ResidentalEstatesRepository extends JpaRepository<ResidentalEstates, Long>, JpaSpecificationExecutor<ResidentalEstates> {}
