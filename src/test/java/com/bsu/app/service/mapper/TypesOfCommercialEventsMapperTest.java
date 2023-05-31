package com.bsu.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TypesOfCommercialEventsMapperTest {

    private TypesOfCommercialEventsMapper typesOfCommercialEventsMapper;

    @BeforeEach
    public void setUp() {
        typesOfCommercialEventsMapper = new TypesOfCommercialEventsMapperImpl();
    }
}
