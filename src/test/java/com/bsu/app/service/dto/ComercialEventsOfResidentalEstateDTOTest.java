package com.bsu.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.bsu.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ComercialEventsOfResidentalEstateDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ComercialEventsOfResidentalEstateDTO.class);
        ComercialEventsOfResidentalEstateDTO comercialEventsOfResidentalEstateDTO1 = new ComercialEventsOfResidentalEstateDTO();
        comercialEventsOfResidentalEstateDTO1.setId(1L);
        ComercialEventsOfResidentalEstateDTO comercialEventsOfResidentalEstateDTO2 = new ComercialEventsOfResidentalEstateDTO();
        assertThat(comercialEventsOfResidentalEstateDTO1).isNotEqualTo(comercialEventsOfResidentalEstateDTO2);
        comercialEventsOfResidentalEstateDTO2.setId(comercialEventsOfResidentalEstateDTO1.getId());
        assertThat(comercialEventsOfResidentalEstateDTO1).isEqualTo(comercialEventsOfResidentalEstateDTO2);
        comercialEventsOfResidentalEstateDTO2.setId(2L);
        assertThat(comercialEventsOfResidentalEstateDTO1).isNotEqualTo(comercialEventsOfResidentalEstateDTO2);
        comercialEventsOfResidentalEstateDTO1.setId(null);
        assertThat(comercialEventsOfResidentalEstateDTO1).isNotEqualTo(comercialEventsOfResidentalEstateDTO2);
    }
}
