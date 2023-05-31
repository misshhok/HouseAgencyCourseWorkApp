package com.bsu.app.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.bsu.app.domain.Addresses} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AddressesDTO implements Serializable {

    private Long id;

    private Integer houseNumber;

    private StreetsOfCitiesDTO streetOfCityId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(Integer houseNumber) {
        this.houseNumber = houseNumber;
    }

    public StreetsOfCitiesDTO getStreetOfCityId() {
        return streetOfCityId;
    }

    public void setStreetOfCityId(StreetsOfCitiesDTO streetOfCityId) {
        this.streetOfCityId = streetOfCityId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AddressesDTO)) {
            return false;
        }

        AddressesDTO addressesDTO = (AddressesDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, addressesDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AddressesDTO{" +
            "id=" + getId() +
            ", houseNumber=" + getHouseNumber() +
            ", streetOfCityId=" + getStreetOfCityId() +
            "}";
    }
}
