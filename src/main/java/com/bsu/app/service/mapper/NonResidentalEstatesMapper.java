package com.bsu.app.service.mapper;

import com.bsu.app.domain.Addresses;
import com.bsu.app.domain.BuildingTypeOfNonResidentalEstate;
import com.bsu.app.domain.NonResidentalEstates;
import com.bsu.app.domain.PurposesOfNonResidentalEstate;
import com.bsu.app.service.dto.AddressesDTO;
import com.bsu.app.service.dto.BuildingTypeOfNonResidentalEstateDTO;
import com.bsu.app.service.dto.NonResidentalEstatesDTO;
import com.bsu.app.service.dto.PurposesOfNonResidentalEstateDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link NonResidentalEstates} and its DTO {@link NonResidentalEstatesDTO}.
 */
@Mapper(componentModel = "spring")
public interface NonResidentalEstatesMapper extends EntityMapper<NonResidentalEstatesDTO, NonResidentalEstates> {
    @Mapping(
        target = "purposeOfNonResidentalEstateId",
        source = "purposeOfNonResidentalEstateId",
        qualifiedByName = "purposesOfNonResidentalEstateId"
    )
    @Mapping(
        target = "buildingTypeOfNonResidentalEstateId",
        source = "buildingTypeOfNonResidentalEstateId",
        qualifiedByName = "buildingTypeOfNonResidentalEstateId"
    )
    @Mapping(target = "addressId", source = "addressId", qualifiedByName = "addressesId")
    NonResidentalEstatesDTO toDto(NonResidentalEstates s);

    @Named("purposesOfNonResidentalEstateId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PurposesOfNonResidentalEstateDTO toDtoPurposesOfNonResidentalEstateId(PurposesOfNonResidentalEstate purposesOfNonResidentalEstate);

    @Named("buildingTypeOfNonResidentalEstateId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    BuildingTypeOfNonResidentalEstateDTO toDtoBuildingTypeOfNonResidentalEstateId(
        BuildingTypeOfNonResidentalEstate buildingTypeOfNonResidentalEstate
    );

    @Named("addressesId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AddressesDTO toDtoAddressesId(Addresses addresses);
}
