package com.bsu.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BuildingTypeOfNonResidentalEstateMapperTest {

    private BuildingTypeOfNonResidentalEstateMapper buildingTypeOfNonResidentalEstateMapper;

    @BeforeEach
    public void setUp() {
        buildingTypeOfNonResidentalEstateMapper = new BuildingTypeOfNonResidentalEstateMapperImpl();
    }
}
