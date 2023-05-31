package com.bsu.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.bsu.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TypesOfResidentalEstateTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypesOfResidentalEstate.class);
        TypesOfResidentalEstate typesOfResidentalEstate1 = new TypesOfResidentalEstate();
        typesOfResidentalEstate1.setId(1L);
        TypesOfResidentalEstate typesOfResidentalEstate2 = new TypesOfResidentalEstate();
        typesOfResidentalEstate2.setId(typesOfResidentalEstate1.getId());
        assertThat(typesOfResidentalEstate1).isEqualTo(typesOfResidentalEstate2);
        typesOfResidentalEstate2.setId(2L);
        assertThat(typesOfResidentalEstate1).isNotEqualTo(typesOfResidentalEstate2);
        typesOfResidentalEstate1.setId(null);
        assertThat(typesOfResidentalEstate1).isNotEqualTo(typesOfResidentalEstate2);
    }
}
