package com.bsu.app.repository;

import com.bsu.app.domain.TypesOfCommercialEvents;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the TypesOfCommercialEvents entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TypesOfCommercialEventsRepository
    extends JpaRepository<TypesOfCommercialEvents, Long>, JpaSpecificationExecutor<TypesOfCommercialEvents> {}
