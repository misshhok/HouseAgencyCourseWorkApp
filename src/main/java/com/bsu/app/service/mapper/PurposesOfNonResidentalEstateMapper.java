package com.bsu.app.service.mapper;

import com.bsu.app.domain.PurposesOfNonResidentalEstate;
import com.bsu.app.service.dto.PurposesOfNonResidentalEstateDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PurposesOfNonResidentalEstate} and its DTO {@link PurposesOfNonResidentalEstateDTO}.
 */
@Mapper(componentModel = "spring")
public interface PurposesOfNonResidentalEstateMapper
    extends EntityMapper<PurposesOfNonResidentalEstateDTO, PurposesOfNonResidentalEstate> {}
