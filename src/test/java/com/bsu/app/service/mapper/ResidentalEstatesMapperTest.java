package com.bsu.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ResidentalEstatesMapperTest {

    private ResidentalEstatesMapper residentalEstatesMapper;

    @BeforeEach
    public void setUp() {
        residentalEstatesMapper = new ResidentalEstatesMapperImpl();
    }
}
