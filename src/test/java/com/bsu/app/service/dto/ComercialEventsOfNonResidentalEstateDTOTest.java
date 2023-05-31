package com.bsu.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.bsu.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ComercialEventsOfNonResidentalEstateDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ComercialEventsOfNonResidentalEstateDTO.class);
        ComercialEventsOfNonResidentalEstateDTO comercialEventsOfNonResidentalEstateDTO1 = new ComercialEventsOfNonResidentalEstateDTO();
        comercialEventsOfNonResidentalEstateDTO1.setId(1L);
        ComercialEventsOfNonResidentalEstateDTO comercialEventsOfNonResidentalEstateDTO2 = new ComercialEventsOfNonResidentalEstateDTO();
        assertThat(comercialEventsOfNonResidentalEstateDTO1).isNotEqualTo(comercialEventsOfNonResidentalEstateDTO2);
        comercialEventsOfNonResidentalEstateDTO2.setId(comercialEventsOfNonResidentalEstateDTO1.getId());
        assertThat(comercialEventsOfNonResidentalEstateDTO1).isEqualTo(comercialEventsOfNonResidentalEstateDTO2);
        comercialEventsOfNonResidentalEstateDTO2.setId(2L);
        assertThat(comercialEventsOfNonResidentalEstateDTO1).isNotEqualTo(comercialEventsOfNonResidentalEstateDTO2);
        comercialEventsOfNonResidentalEstateDTO1.setId(null);
        assertThat(comercialEventsOfNonResidentalEstateDTO1).isNotEqualTo(comercialEventsOfNonResidentalEstateDTO2);
    }
}
