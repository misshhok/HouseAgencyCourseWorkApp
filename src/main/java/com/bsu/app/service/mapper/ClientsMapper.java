package com.bsu.app.service.mapper;

import com.bsu.app.domain.Clients;
import com.bsu.app.service.dto.ClientsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Clients} and its DTO {@link ClientsDTO}.
 */
@Mapper(componentModel = "spring")
public interface ClientsMapper extends EntityMapper<ClientsDTO, Clients> {}
