package com.bsu.app.service.mapper;

import com.bsu.app.domain.Addresses;
import com.bsu.app.domain.StreetsOfCities;
import com.bsu.app.service.dto.AddressesDTO;
import com.bsu.app.service.dto.StreetsOfCitiesDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Addresses} and its DTO {@link AddressesDTO}.
 */
@Mapper(componentModel = "spring")
public interface AddressesMapper extends EntityMapper<AddressesDTO, Addresses> {
    @Mapping(target = "streetOfCityId", source = "streetOfCityId", qualifiedByName = "streetsOfCitiesId")
    AddressesDTO toDto(Addresses s);

    @Named("streetsOfCitiesId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    StreetsOfCitiesDTO toDtoStreetsOfCitiesId(StreetsOfCities streetsOfCities);
}
