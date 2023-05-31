package com.bsu.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.bsu.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PurposesOfNonResidentalEstateTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PurposesOfNonResidentalEstate.class);
        PurposesOfNonResidentalEstate purposesOfNonResidentalEstate1 = new PurposesOfNonResidentalEstate();
        purposesOfNonResidentalEstate1.setId(1L);
        PurposesOfNonResidentalEstate purposesOfNonResidentalEstate2 = new PurposesOfNonResidentalEstate();
        purposesOfNonResidentalEstate2.setId(purposesOfNonResidentalEstate1.getId());
        assertThat(purposesOfNonResidentalEstate1).isEqualTo(purposesOfNonResidentalEstate2);
        purposesOfNonResidentalEstate2.setId(2L);
        assertThat(purposesOfNonResidentalEstate1).isNotEqualTo(purposesOfNonResidentalEstate2);
        purposesOfNonResidentalEstate1.setId(null);
        assertThat(purposesOfNonResidentalEstate1).isNotEqualTo(purposesOfNonResidentalEstate2);
    }
}
