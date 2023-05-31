package com.bsu.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurposesOfNonResidentalEstateMapperTest {

    private PurposesOfNonResidentalEstateMapper purposesOfNonResidentalEstateMapper;

    @BeforeEach
    public void setUp() {
        purposesOfNonResidentalEstateMapper = new PurposesOfNonResidentalEstateMapperImpl();
    }
}
