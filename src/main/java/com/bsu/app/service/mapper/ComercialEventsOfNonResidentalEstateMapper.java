package com.bsu.app.service.mapper;

import com.bsu.app.domain.Clients;
import com.bsu.app.domain.ComercialEventsOfNonResidentalEstate;
import com.bsu.app.domain.NonResidentalEstates;
import com.bsu.app.domain.TypesOfCommercialEvents;
import com.bsu.app.service.dto.ClientsDTO;
import com.bsu.app.service.dto.ComercialEventsOfNonResidentalEstateDTO;
import com.bsu.app.service.dto.NonResidentalEstatesDTO;
import com.bsu.app.service.dto.TypesOfCommercialEventsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ComercialEventsOfNonResidentalEstate} and its DTO {@link ComercialEventsOfNonResidentalEstateDTO}.
 */
@Mapper(componentModel = "spring")
public interface ComercialEventsOfNonResidentalEstateMapper
    extends EntityMapper<ComercialEventsOfNonResidentalEstateDTO, ComercialEventsOfNonResidentalEstate> {
    @Mapping(target = "typeOfCommercialEventId", source = "typeOfCommercialEventId", qualifiedByName = "typesOfCommercialEventsId")
    @Mapping(target = "nonResidentalEstateId", source = "nonResidentalEstateId", qualifiedByName = "nonResidentalEstatesId")
    @Mapping(target = "clientId", source = "clientId", qualifiedByName = "clientsId")
    ComercialEventsOfNonResidentalEstateDTO toDto(ComercialEventsOfNonResidentalEstate s);

    @Named("typesOfCommercialEventsId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TypesOfCommercialEventsDTO toDtoTypesOfCommercialEventsId(TypesOfCommercialEvents typesOfCommercialEvents);

    @Named("nonResidentalEstatesId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    NonResidentalEstatesDTO toDtoNonResidentalEstatesId(NonResidentalEstates nonResidentalEstates);

    @Named("clientsId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ClientsDTO toDtoClientsId(Clients clients);
}
