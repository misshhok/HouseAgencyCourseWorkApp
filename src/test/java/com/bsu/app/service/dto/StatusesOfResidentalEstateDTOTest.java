package com.bsu.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.bsu.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class StatusesOfResidentalEstateDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(StatusesOfResidentalEstateDTO.class);
        StatusesOfResidentalEstateDTO statusesOfResidentalEstateDTO1 = new StatusesOfResidentalEstateDTO();
        statusesOfResidentalEstateDTO1.setId(1L);
        StatusesOfResidentalEstateDTO statusesOfResidentalEstateDTO2 = new StatusesOfResidentalEstateDTO();
        assertThat(statusesOfResidentalEstateDTO1).isNotEqualTo(statusesOfResidentalEstateDTO2);
        statusesOfResidentalEstateDTO2.setId(statusesOfResidentalEstateDTO1.getId());
        assertThat(statusesOfResidentalEstateDTO1).isEqualTo(statusesOfResidentalEstateDTO2);
        statusesOfResidentalEstateDTO2.setId(2L);
        assertThat(statusesOfResidentalEstateDTO1).isNotEqualTo(statusesOfResidentalEstateDTO2);
        statusesOfResidentalEstateDTO1.setId(null);
        assertThat(statusesOfResidentalEstateDTO1).isNotEqualTo(statusesOfResidentalEstateDTO2);
    }
}
