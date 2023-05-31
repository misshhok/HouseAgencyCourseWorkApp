package com.bsu.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class NonResidentalEstatesMapperTest {

    private NonResidentalEstatesMapper nonResidentalEstatesMapper;

    @BeforeEach
    public void setUp() {
        nonResidentalEstatesMapper = new NonResidentalEstatesMapperImpl();
    }
}
