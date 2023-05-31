package com.bsu.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.bsu.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NonResidentalEstatesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NonResidentalEstates.class);
        NonResidentalEstates nonResidentalEstates1 = new NonResidentalEstates();
        nonResidentalEstates1.setId(1L);
        NonResidentalEstates nonResidentalEstates2 = new NonResidentalEstates();
        nonResidentalEstates2.setId(nonResidentalEstates1.getId());
        assertThat(nonResidentalEstates1).isEqualTo(nonResidentalEstates2);
        nonResidentalEstates2.setId(2L);
        assertThat(nonResidentalEstates1).isNotEqualTo(nonResidentalEstates2);
        nonResidentalEstates1.setId(null);
        assertThat(nonResidentalEstates1).isNotEqualTo(nonResidentalEstates2);
    }
}
