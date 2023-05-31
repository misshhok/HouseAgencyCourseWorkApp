package com.bsu.app.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.bsu.app.domain.StreetsOfCities} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class StreetsOfCitiesDTO implements Serializable {

    private Long id;

    private String title;

    private CitiesDTO cityId;

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

    public CitiesDTO getCityId() {
        return cityId;
    }

    public void setCityId(CitiesDTO cityId) {
        this.cityId = cityId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StreetsOfCitiesDTO)) {
            return false;
        }

        StreetsOfCitiesDTO streetsOfCitiesDTO = (StreetsOfCitiesDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, streetsOfCitiesDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StreetsOfCitiesDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", cityId=" + getCityId() +
            "}";
    }
}
