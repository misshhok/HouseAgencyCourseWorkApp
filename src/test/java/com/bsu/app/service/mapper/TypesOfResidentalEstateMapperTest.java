package com.bsu.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TypesOfResidentalEstateMapperTest {

    private TypesOfResidentalEstateMapper typesOfResidentalEstateMapper;

    @BeforeEach
    public void setUp() {
        typesOfResidentalEstateMapper = new TypesOfResidentalEstateMapperImpl();
    }
}
