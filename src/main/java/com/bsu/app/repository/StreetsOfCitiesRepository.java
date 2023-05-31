package com.bsu.app.repository;

import com.bsu.app.domain.StreetsOfCities;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the StreetsOfCities entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StreetsOfCitiesRepository extends JpaRepository<StreetsOfCities, Long>, JpaSpecificationExecutor<StreetsOfCities> {}
