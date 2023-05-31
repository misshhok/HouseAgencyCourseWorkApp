package com.bsu.app.repository;

import com.bsu.app.domain.ComercialEventsOfNonResidentalEstate;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ComercialEventsOfNonResidentalEstate entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ComercialEventsOfNonResidentalEstateRepository
    extends JpaRepository<ComercialEventsOfNonResidentalEstate, Long>, JpaSpecificationExecutor<ComercialEventsOfNonResidentalEstate> {}
