package com.bsu.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.bsu.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PurposesOfNonResidentalEstateDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PurposesOfNonResidentalEstateDTO.class);
        PurposesOfNonResidentalEstateDTO purposesOfNonResidentalEstateDTO1 = new PurposesOfNonResidentalEstateDTO();
        purposesOfNonResidentalEstateDTO1.setId(1L);
        PurposesOfNonResidentalEstateDTO purposesOfNonResidentalEstateDTO2 = new PurposesOfNonResidentalEstateDTO();
        assertThat(purposesOfNonResidentalEstateDTO1).isNotEqualTo(purposesOfNonResidentalEstateDTO2);
        purposesOfNonResidentalEstateDTO2.setId(purposesOfNonResidentalEstateDTO1.getId());
        assertThat(purposesOfNonResidentalEstateDTO1).isEqualTo(purposesOfNonResidentalEstateDTO2);
        purposesOfNonResidentalEstateDTO2.setId(2L);
        assertThat(purposesOfNonResidentalEstateDTO1).isNotEqualTo(purposesOfNonResidentalEstateDTO2);
        purposesOfNonResidentalEstateDTO1.setId(null);
        assertThat(purposesOfNonResidentalEstateDTO1).isNotEqualTo(purposesOfNonResidentalEstateDTO2);
    }
}
