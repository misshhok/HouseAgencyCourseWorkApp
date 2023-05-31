package com.bsu.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.bsu.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class StatusesOfResidentalEstateTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StatusesOfResidentalEstate.class);
        StatusesOfResidentalEstate statusesOfResidentalEstate1 = new StatusesOfResidentalEstate();
        statusesOfResidentalEstate1.setId(1L);
        StatusesOfResidentalEstate statusesOfResidentalEstate2 = new StatusesOfResidentalEstate();
        statusesOfResidentalEstate2.setId(statusesOfResidentalEstate1.getId());
        assertThat(statusesOfResidentalEstate1).isEqualTo(statusesOfResidentalEstate2);
        statusesOfResidentalEstate2.setId(2L);
        assertThat(statusesOfResidentalEstate1).isNotEqualTo(statusesOfResidentalEstate2);
        statusesOfResidentalEstate1.setId(null);
        assertThat(statusesOfResidentalEstate1).isNotEqualTo(statusesOfResidentalEstate2);
    }
}
