package com.bsu.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.bsu.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ResidentalEstatesDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ResidentalEstatesDTO.class);
        ResidentalEstatesDTO residentalEstatesDTO1 = new ResidentalEstatesDTO();
        residentalEstatesDTO1.setId(1L);
        ResidentalEstatesDTO residentalEstatesDTO2 = new ResidentalEstatesDTO();
        assertThat(residentalEstatesDTO1).isNotEqualTo(residentalEstatesDTO2);
        residentalEstatesDTO2.setId(residentalEstatesDTO1.getId());
        assertThat(residentalEstatesDTO1).isEqualTo(residentalEstatesDTO2);
        residentalEstatesDTO2.setId(2L);
        assertThat(residentalEstatesDTO1).isNotEqualTo(residentalEstatesDTO2);
        residentalEstatesDTO1.setId(null);
        assertThat(residentalEstatesDTO1).isNotEqualTo(residentalEstatesDTO2);
    }
}
