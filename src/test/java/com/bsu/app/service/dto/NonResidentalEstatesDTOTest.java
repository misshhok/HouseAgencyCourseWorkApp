package com.bsu.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.bsu.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NonResidentalEstatesDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(NonResidentalEstatesDTO.class);
        NonResidentalEstatesDTO nonResidentalEstatesDTO1 = new NonResidentalEstatesDTO();
        nonResidentalEstatesDTO1.setId(1L);
        NonResidentalEstatesDTO nonResidentalEstatesDTO2 = new NonResidentalEstatesDTO();
        assertThat(nonResidentalEstatesDTO1).isNotEqualTo(nonResidentalEstatesDTO2);
        nonResidentalEstatesDTO2.setId(nonResidentalEstatesDTO1.getId());
        assertThat(nonResidentalEstatesDTO1).isEqualTo(nonResidentalEstatesDTO2);
        nonResidentalEstatesDTO2.setId(2L);
        assertThat(nonResidentalEstatesDTO1).isNotEqualTo(nonResidentalEstatesDTO2);
        nonResidentalEstatesDTO1.setId(null);
        assertThat(nonResidentalEstatesDTO1).isNotEqualTo(nonResidentalEstatesDTO2);
    }
}
