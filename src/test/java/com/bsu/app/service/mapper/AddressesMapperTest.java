package com.bsu.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AddressesMapperTest {

    private AddressesMapper addressesMapper;

    @BeforeEach
    public void setUp() {
        addressesMapper = new AddressesMapperImpl();
    }
}
