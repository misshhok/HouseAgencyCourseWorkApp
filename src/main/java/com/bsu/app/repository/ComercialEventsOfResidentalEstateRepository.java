package com.bsu.app.repository;

import com.bsu.app.domain.ComercialEventsOfResidentalEstate;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ComercialEventsOfResidentalEstate entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ComercialEventsOfResidentalEstateRepository
    extends JpaRepository<ComercialEventsOfResidentalEstate, Long>, JpaSpecificationExecutor<ComercialEventsOfResidentalEstate> {}
