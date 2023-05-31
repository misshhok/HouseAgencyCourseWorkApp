package com.bsu.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.bsu.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class StreetsOfCitiesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StreetsOfCities.class);
        StreetsOfCities streetsOfCities1 = new StreetsOfCities();
        streetsOfCities1.setId(1L);
        StreetsOfCities streetsOfCities2 = new StreetsOfCities();
        streetsOfCities2.setId(streetsOfCities1.getId());
        assertThat(streetsOfCities1).isEqualTo(streetsOfCities2);
        streetsOfCities2.setId(2L);
        assertThat(streetsOfCities1).isNotEqualTo(streetsOfCities2);
        streetsOfCities1.setId(null);
        assertThat(streetsOfCities1).isNotEqualTo(streetsOfCities2);
    }
}
