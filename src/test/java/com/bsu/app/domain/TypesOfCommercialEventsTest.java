package com.bsu.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.bsu.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TypesOfCommercialEventsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypesOfCommercialEvents.class);
        TypesOfCommercialEvents typesOfCommercialEvents1 = new TypesOfCommercialEvents();
        typesOfCommercialEvents1.setId(1L);
        TypesOfCommercialEvents typesOfCommercialEvents2 = new TypesOfCommercialEvents();
        typesOfCommercialEvents2.setId(typesOfCommercialEvents1.getId());
        assertThat(typesOfCommercialEvents1).isEqualTo(typesOfCommercialEvents2);
        typesOfCommercialEvents2.setId(2L);
        assertThat(typesOfCommercialEvents1).isNotEqualTo(typesOfCommercialEvents2);
        typesOfCommercialEvents1.setId(null);
        assertThat(typesOfCommercialEvents1).isNotEqualTo(typesOfCommercialEvents2);
    }
}
