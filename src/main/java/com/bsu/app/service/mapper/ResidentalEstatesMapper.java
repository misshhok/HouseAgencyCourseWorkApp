package com.bsu.app.service.mapper;

import com.bsu.app.domain.Addresses;
import com.bsu.app.domain.ResidentalEstates;
import com.bsu.app.domain.StatusesOfResidentalEstate;
import com.bsu.app.domain.TypesOfResidentalEstate;
import com.bsu.app.service.dto.AddressesDTO;
import com.bsu.app.service.dto.ResidentalEstatesDTO;
import com.bsu.app.service.dto.StatusesOfResidentalEstateDTO;
import com.bsu.app.service.dto.TypesOfResidentalEstateDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ResidentalEstates} and its DTO {@link ResidentalEstatesDTO}.
 */
@Mapper(componentModel = "spring")
public interface ResidentalEstatesMapper extends EntityMapper<ResidentalEstatesDTO, ResidentalEstates> {
    @Mapping(target = "addressId", source = "addressId", qualifiedByName = "addressesId")
    @Mapping(target = "typeOfResidentalEstateId", source = "typeOfResidentalEstateId", qualifiedByName = "typesOfResidentalEstateId")
    @Mapping(target = "statusOfResidentalEstateId", source = "statusOfResidentalEstateId", qualifiedByName = "statusesOfResidentalEstateId")
    ResidentalEstatesDTO toDto(ResidentalEstates s);

    @Named("addressesId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AddressesDTO toDtoAddressesId(Addresses addresses);

    @Named("typesOfResidentalEstateId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TypesOfResidentalEstateDTO toDtoTypesOfResidentalEstateId(TypesOfResidentalEstate typesOfResidentalEstate);

    @Named("statusesOfResidentalEstateId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    StatusesOfResidentalEstateDTO toDtoStatusesOfResidentalEstateId(StatusesOfResidentalEstate statusesOfResidentalEstate);
}
