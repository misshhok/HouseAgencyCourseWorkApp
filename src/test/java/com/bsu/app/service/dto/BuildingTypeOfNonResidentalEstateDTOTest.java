package com.bsu.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.bsu.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BuildingTypeOfNonResidentalEstateDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BuildingTypeOfNonResidentalEstateDTO.class);
        BuildingTypeOfNonResidentalEstateDTO buildingTypeOfNonResidentalEstateDTO1 = new BuildingTypeOfNonResidentalEstateDTO();
        buildingTypeOfNonResidentalEstateDTO1.setId(1L);
        BuildingTypeOfNonResidentalEstateDTO buildingTypeOfNonResidentalEstateDTO2 = new BuildingTypeOfNonResidentalEstateDTO();
        assertThat(buildingTypeOfNonResidentalEstateDTO1).isNotEqualTo(buildingTypeOfNonResidentalEstateDTO2);
        buildingTypeOfNonResidentalEstateDTO2.setId(buildingTypeOfNonResidentalEstateDTO1.getId());
        assertThat(buildingTypeOfNonResidentalEstateDTO1).isEqualTo(buildingTypeOfNonResidentalEstateDTO2);
        buildingTypeOfNonResidentalEstateDTO2.setId(2L);
        assertThat(buildingTypeOfNonResidentalEstateDTO1).isNotEqualTo(buildingTypeOfNonResidentalEstateDTO2);
        buildingTypeOfNonResidentalEstateDTO1.setId(null);
        assertThat(buildingTypeOfNonResidentalEstateDTO1).isNotEqualTo(buildingTypeOfNonResidentalEstateDTO2);
    }
}
