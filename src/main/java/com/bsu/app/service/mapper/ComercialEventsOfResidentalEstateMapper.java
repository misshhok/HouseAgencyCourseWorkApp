package com.bsu.app.service.mapper;

import com.bsu.app.domain.Clients;
import com.bsu.app.domain.ComercialEventsOfResidentalEstate;
import com.bsu.app.domain.ResidentalEstates;
import com.bsu.app.domain.TypesOfCommercialEvents;
import com.bsu.app.service.dto.ClientsDTO;
import com.bsu.app.service.dto.ComercialEventsOfResidentalEstateDTO;
import com.bsu.app.service.dto.ResidentalEstatesDTO;
import com.bsu.app.service.dto.TypesOfCommercialEventsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ComercialEventsOfResidentalEstate} and its DTO {@link ComercialEventsOfResidentalEstateDTO}.
 */
@Mapper(componentModel = "spring")
public interface ComercialEventsOfResidentalEstateMapper
    extends EntityMapper<ComercialEventsOfResidentalEstateDTO, ComercialEventsOfResidentalEstate> {
    @Mapping(target = "typeOfCommercialEventId", source = "typeOfCommercialEventId", qualifiedByName = "typesOfCommercialEventsId")
    @Mapping(target = "clientId", source = "clientId", qualifiedByName = "clientsId")
    @Mapping(target = "residentalEstateId", source = "residentalEstateId", qualifiedByName = "residentalEstatesId")
    ComercialEventsOfResidentalEstateDTO toDto(ComercialEventsOfResidentalEstate s);

    @Named("typesOfCommercialEventsId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TypesOfCommercialEventsDTO toDtoTypesOfCommercialEventsId(TypesOfCommercialEvents typesOfCommercialEvents);

    @Named("clientsId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ClientsDTO toDtoClientsId(Clients clients);

    @Named("residentalEstatesId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ResidentalEstatesDTO toDtoResidentalEstatesId(ResidentalEstates residentalEstates);
}
