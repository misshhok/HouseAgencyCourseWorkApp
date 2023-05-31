package com.bsu.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.bsu.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ResidentalEstatesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ResidentalEstates.class);
        ResidentalEstates residentalEstates1 = new ResidentalEstates();
        residentalEstates1.setId(1L);
        ResidentalEstates residentalEstates2 = new ResidentalEstates();
        residentalEstates2.setId(residentalEstates1.getId());
        assertThat(residentalEstates1).isEqualTo(residentalEstates2);
        residentalEstates2.setId(2L);
        assertThat(residentalEstates1).isNotEqualTo(residentalEstates2);
        residentalEstates1.setId(null);
        assertThat(residentalEstates1).isNotEqualTo(residentalEstates2);
    }
}
