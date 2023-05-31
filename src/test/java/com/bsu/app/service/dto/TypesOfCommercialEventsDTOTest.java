package com.bsu.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.bsu.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TypesOfCommercialEventsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypesOfCommercialEventsDTO.class);
        TypesOfCommercialEventsDTO typesOfCommercialEventsDTO1 = new TypesOfCommercialEventsDTO();
        typesOfCommercialEventsDTO1.setId(1L);
        TypesOfCommercialEventsDTO typesOfCommercialEventsDTO2 = new TypesOfCommercialEventsDTO();
        assertThat(typesOfCommercialEventsDTO1).isNotEqualTo(typesOfCommercialEventsDTO2);
        typesOfCommercialEventsDTO2.setId(typesOfCommercialEventsDTO1.getId());
        assertThat(typesOfCommercialEventsDTO1).isEqualTo(typesOfCommercialEventsDTO2);
        typesOfCommercialEventsDTO2.setId(2L);
        assertThat(typesOfCommercialEventsDTO1).isNotEqualTo(typesOfCommercialEventsDTO2);
        typesOfCommercialEventsDTO1.setId(null);
        assertThat(typesOfCommercialEventsDTO1).isNotEqualTo(typesOfCommercialEventsDTO2);
    }
}
