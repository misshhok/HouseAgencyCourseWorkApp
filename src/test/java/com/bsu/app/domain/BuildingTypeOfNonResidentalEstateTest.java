package com.bsu.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.bsu.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BuildingTypeOfNonResidentalEstateTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BuildingTypeOfNonResidentalEstate.class);
        BuildingTypeOfNonResidentalEstate buildingTypeOfNonResidentalEstate1 = new BuildingTypeOfNonResidentalEstate();
        buildingTypeOfNonResidentalEstate1.setId(1L);
        BuildingTypeOfNonResidentalEstate buildingTypeOfNonResidentalEstate2 = new BuildingTypeOfNonResidentalEstate();
        buildingTypeOfNonResidentalEstate2.setId(buildingTypeOfNonResidentalEstate1.getId());
        assertThat(buildingTypeOfNonResidentalEstate1).isEqualTo(buildingTypeOfNonResidentalEstate2);
        buildingTypeOfNonResidentalEstate2.setId(2L);
        assertThat(buildingTypeOfNonResidentalEstate1).isNotEqualTo(buildingTypeOfNonResidentalEstate2);
        buildingTypeOfNonResidentalEstate1.setId(null);
        assertThat(buildingTypeOfNonResidentalEstate1).isNotEqualTo(buildingTypeOfNonResidentalEstate2);
    }
}
