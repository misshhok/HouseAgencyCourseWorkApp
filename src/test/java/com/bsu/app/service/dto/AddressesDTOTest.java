package com.bsu.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.bsu.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AddressesDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AddressesDTO.class);
        AddressesDTO addressesDTO1 = new AddressesDTO();
        addressesDTO1.setId(1L);
        AddressesDTO addressesDTO2 = new AddressesDTO();
        assertThat(addressesDTO1).isNotEqualTo(addressesDTO2);
        addressesDTO2.setId(addressesDTO1.getId());
        assertThat(addressesDTO1).isEqualTo(addressesDTO2);
        addressesDTO2.setId(2L);
        assertThat(addressesDTO1).isNotEqualTo(addressesDTO2);
        addressesDTO1.setId(null);
        assertThat(addressesDTO1).isNotEqualTo(addressesDTO2);
    }
}
