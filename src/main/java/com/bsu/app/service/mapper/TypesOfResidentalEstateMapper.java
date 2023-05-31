package com.bsu.app.service.mapper;

import com.bsu.app.domain.TypesOfResidentalEstate;
import com.bsu.app.service.dto.TypesOfResidentalEstateDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TypesOfResidentalEstate} and its DTO {@link TypesOfResidentalEstateDTO}.
 */
@Mapper(componentModel = "spring")
public interface TypesOfResidentalEstateMapper extends EntityMapper<TypesOfResidentalEstateDTO, TypesOfResidentalEstate> {}
