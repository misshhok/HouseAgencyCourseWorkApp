package com.bsu.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.bsu.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ComercialEventsOfResidentalEstateTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ComercialEventsOfResidentalEstate.class);
        ComercialEventsOfResidentalEstate comercialEventsOfResidentalEstate1 = new ComercialEventsOfResidentalEstate();
        comercialEventsOfResidentalEstate1.setId(1L);
        ComercialEventsOfResidentalEstate comercialEventsOfResidentalEstate2 = new ComercialEventsOfResidentalEstate();
        comercialEventsOfResidentalEstate2.setId(comercialEventsOfResidentalEstate1.getId());
        assertThat(comercialEventsOfResidentalEstate1).isEqualTo(comercialEventsOfResidentalEstate2);
        comercialEventsOfResidentalEstate2.setId(2L);
        assertThat(comercialEventsOfResidentalEstate1).isNotEqualTo(comercialEventsOfResidentalEstate2);
        comercialEventsOfResidentalEstate1.setId(null);
        assertThat(comercialEventsOfResidentalEstate1).isNotEqualTo(comercialEventsOfResidentalEstate2);
    }
}
