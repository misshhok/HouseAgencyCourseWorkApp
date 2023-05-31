package com.bsu.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.bsu.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ComercialEventsOfNonResidentalEstateTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ComercialEventsOfNonResidentalEstate.class);
        ComercialEventsOfNonResidentalEstate comercialEventsOfNonResidentalEstate1 = new ComercialEventsOfNonResidentalEstate();
        comercialEventsOfNonResidentalEstate1.setId(1L);
        ComercialEventsOfNonResidentalEstate comercialEventsOfNonResidentalEstate2 = new ComercialEventsOfNonResidentalEstate();
        comercialEventsOfNonResidentalEstate2.setId(comercialEventsOfNonResidentalEstate1.getId());
        assertThat(comercialEventsOfNonResidentalEstate1).isEqualTo(comercialEventsOfNonResidentalEstate2);
        comercialEventsOfNonResidentalEstate2.setId(2L);
        assertThat(comercialEventsOfNonResidentalEstate1).isNotEqualTo(comercialEventsOfNonResidentalEstate2);
        comercialEventsOfNonResidentalEstate1.setId(null);
        assertThat(comercialEventsOfNonResidentalEstate1).isNotEqualTo(comercialEventsOfNonResidentalEstate2);
    }
}
