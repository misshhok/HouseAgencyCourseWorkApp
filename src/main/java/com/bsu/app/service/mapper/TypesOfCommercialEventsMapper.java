package com.bsu.app.service.mapper;

import com.bsu.app.domain.TypesOfCommercialEvents;
import com.bsu.app.service.dto.TypesOfCommercialEventsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TypesOfCommercialEvents} and its DTO {@link TypesOfCommercialEventsDTO}.
 */
@Mapper(componentModel = "spring")
public interface TypesOfCommercialEventsMapper extends EntityMapper<TypesOfCommercialEventsDTO, TypesOfCommercialEvents> {}
