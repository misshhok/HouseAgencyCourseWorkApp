package com.bsu.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StatusesOfResidentalEstateMapperTest {

    private StatusesOfResidentalEstateMapper statusesOfResidentalEstateMapper;

    @BeforeEach
    public void setUp() {
        statusesOfResidentalEstateMapper = new StatusesOfResidentalEstateMapperImpl();
    }
}
