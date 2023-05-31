package com.bsu.app.service.mapper;

import com.bsu.app.domain.Cities;
import com.bsu.app.service.dto.CitiesDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Cities} and its DTO {@link CitiesDTO}.
 */
@Mapper(componentModel = "spring")
public interface CitiesMapper extends EntityMapper<CitiesDTO, Cities> {}
