package com.bsu.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.bsu.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TypesOfResidentalEstateDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypesOfResidentalEstateDTO.class);
        TypesOfResidentalEstateDTO typesOfResidentalEstateDTO1 = new TypesOfResidentalEstateDTO();
        typesOfResidentalEstateDTO1.setId(1L);
        TypesOfResidentalEstateDTO typesOfResidentalEstateDTO2 = new TypesOfResidentalEstateDTO();
        assertThat(typesOfResidentalEstateDTO1).isNotEqualTo(typesOfResidentalEstateDTO2);
        typesOfResidentalEstateDTO2.setId(typesOfResidentalEstateDTO1.getId());
        assertThat(typesOfResidentalEstateDTO1).isEqualTo(typesOfResidentalEstateDTO2);
        typesOfResidentalEstateDTO2.setId(2L);
        assertThat(typesOfResidentalEstateDTO1).isNotEqualTo(typesOfResidentalEstateDTO2);
        typesOfResidentalEstateDTO1.setId(null);
        assertThat(typesOfResidentalEstateDTO1).isNotEqualTo(typesOfResidentalEstateDTO2);
    }
}
