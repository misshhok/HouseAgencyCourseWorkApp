package com.bsu.app.service.mapper;

import com.bsu.app.domain.StatusesOfResidentalEstate;
import com.bsu.app.service.dto.StatusesOfResidentalEstateDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link StatusesOfResidentalEstate} and its DTO {@link StatusesOfResidentalEstateDTO}.
 */
@Mapper(componentModel = "spring")
public interface StatusesOfResidentalEstateMapper extends EntityMapper<StatusesOfResidentalEstateDTO, StatusesOfResidentalEstate> {}
