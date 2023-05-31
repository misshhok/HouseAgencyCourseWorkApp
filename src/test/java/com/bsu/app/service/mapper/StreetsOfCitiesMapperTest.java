package com.bsu.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StreetsOfCitiesMapperTest {

    private StreetsOfCitiesMapper streetsOfCitiesMapper;

    @BeforeEach
    public void setUp() {
        streetsOfCitiesMapper = new StreetsOfCitiesMapperImpl();
    }
}
