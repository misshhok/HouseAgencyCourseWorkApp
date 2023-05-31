package com.bsu.app.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.bsu.app.domain.BuildingTypeOfNonResidentalEstate} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BuildingTypeOfNonResidentalEstateDTO implements Serializable {

    private Long id;

    private String title;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BuildingTypeOfNonResidentalEstateDTO)) {
            return false;
        }

        BuildingTypeOfNonResidentalEstateDTO buildingTypeOfNonResidentalEstateDTO = (BuildingTypeOfNonResidentalEstateDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, buildingTypeOfNonResidentalEstateDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BuildingTypeOfNonResidentalEstateDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            "}";
    }
}
