package com.bsu.app.service.mapper;

import com.bsu.app.domain.Cities;
import com.bsu.app.domain.StreetsOfCities;
import com.bsu.app.service.dto.CitiesDTO;
import com.bsu.app.service.dto.StreetsOfCitiesDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link StreetsOfCities} and its DTO {@link StreetsOfCitiesDTO}.
 */
@Mapper(componentModel = "spring")
public interface StreetsOfCitiesMapper extends EntityMapper<StreetsOfCitiesDTO, StreetsOfCities> {
    @Mapping(target = "cityId", source = "cityId", qualifiedByName = "citiesId")
    StreetsOfCitiesDTO toDto(StreetsOfCities s);

    @Named("citiesId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CitiesDTO toDtoCitiesId(Cities cities);
}
