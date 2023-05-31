package com.bsu.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ComercialEventsOfResidentalEstateMapperTest {

    private ComercialEventsOfResidentalEstateMapper comercialEventsOfResidentalEstateMapper;

    @BeforeEach
    public void setUp() {
        comercialEventsOfResidentalEstateMapper = new ComercialEventsOfResidentalEstateMapperImpl();
    }
}
