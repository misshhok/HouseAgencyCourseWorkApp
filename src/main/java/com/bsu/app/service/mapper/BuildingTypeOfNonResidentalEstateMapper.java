package com.bsu.app.service.mapper;

import com.bsu.app.domain.BuildingTypeOfNonResidentalEstate;
import com.bsu.app.service.dto.BuildingTypeOfNonResidentalEstateDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link BuildingTypeOfNonResidentalEstate} and its DTO {@link BuildingTypeOfNonResidentalEstateDTO}.
 */
@Mapper(componentModel = "spring")
public interface BuildingTypeOfNonResidentalEstateMapper
    extends EntityMapper<BuildingTypeOfNonResidentalEstateDTO, BuildingTypeOfNonResidentalEstate> {}
