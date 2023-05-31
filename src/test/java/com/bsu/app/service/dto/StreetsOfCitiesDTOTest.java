package com.bsu.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.bsu.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class StreetsOfCitiesDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(StreetsOfCitiesDTO.class);
        StreetsOfCitiesDTO streetsOfCitiesDTO1 = new StreetsOfCitiesDTO();
        streetsOfCitiesDTO1.setId(1L);
        StreetsOfCitiesDTO streetsOfCitiesDTO2 = new StreetsOfCitiesDTO();
        assertThat(streetsOfCitiesDTO1).isNotEqualTo(streetsOfCitiesDTO2);
        streetsOfCitiesDTO2.setId(streetsOfCitiesDTO1.getId());
        assertThat(streetsOfCitiesDTO1).isEqualTo(streetsOfCitiesDTO2);
        streetsOfCitiesDTO2.setId(2L);
        assertThat(streetsOfCitiesDTO1).isNotEqualTo(streetsOfCitiesDTO2);
        streetsOfCitiesDTO1.setId(null);
        assertThat(streetsOfCitiesDTO1).isNotEqualTo(streetsOfCitiesDTO2);
    }
}
