package com.bsu.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ComercialEventsOfNonResidentalEstateMapperTest {

    private ComercialEventsOfNonResidentalEstateMapper comercialEventsOfNonResidentalEstateMapper;

    @BeforeEach
    public void setUp() {
        comercialEventsOfNonResidentalEstateMapper = new ComercialEventsOfNonResidentalEstateMapperImpl();
    }
}
