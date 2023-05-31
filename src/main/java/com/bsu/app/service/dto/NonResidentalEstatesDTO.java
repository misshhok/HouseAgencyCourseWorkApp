package com.bsu.app.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.bsu.app.domain.NonResidentalEstates} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class NonResidentalEstatesDTO implements Serializable {

    private Long id;

    private BigDecimal price;

    private LocalDate commissioningDate;

    private Long cadastralNumber;

    private Double totalSpace;

    private PurposesOfNonResidentalEstateDTO purposeOfNonResidentalEstateId;

    private BuildingTypeOfNonResidentalEstateDTO buildingTypeOfNonResidentalEstateId;

    private AddressesDTO addressId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public LocalDate getCommissioningDate() {
        return commissioningDate;
    }

    public void setCommissioningDate(LocalDate commissioningDate) {
        this.commissioningDate = commissioningDate;
    }

    public Long getCadastralNumber() {
        return cadastralNumber;
    }

    public void setCadastralNumber(Long cadastralNumber) {
        this.cadastralNumber = cadastralNumber;
    }

    public Double getTotalSpace() {
        return totalSpace;
    }

    public void setTotalSpace(Double totalSpace) {
        this.totalSpace = totalSpace;
    }

    public PurposesOfNonResidentalEstateDTO getPurposeOfNonResidentalEstateId() {
        return purposeOfNonResidentalEstateId;
    }

    public void setPurposeOfNonResidentalEstateId(PurposesOfNonResidentalEstateDTO purposeOfNonResidentalEstateId) {
        this.purposeOfNonResidentalEstateId = purposeOfNonResidentalEstateId;
    }

    public BuildingTypeOfNonResidentalEstateDTO getBuildingTypeOfNonResidentalEstateId() {
        return buildingTypeOfNonResidentalEstateId;
    }

    public void setBuildingTypeOfNonResidentalEstateId(BuildingTypeOfNonResidentalEstateDTO buildingTypeOfNonResidentalEstateId) {
        this.buildingTypeOfNonResidentalEstateId = buildingTypeOfNonResidentalEstateId;
    }

    public AddressesDTO getAddressId() {
        return addressId;
    }

    public void setAddressId(AddressesDTO addressId) {
        this.addressId = addressId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NonResidentalEstatesDTO)) {
            return false;
        }

        NonResidentalEstatesDTO nonResidentalEstatesDTO = (NonResidentalEstatesDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, nonResidentalEstatesDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NonResidentalEstatesDTO{" +
            "id=" + getId() +
            ", price=" + getPrice() +
            ", commissioningDate='" + getCommissioningDate() + "'" +
            ", cadastralNumber=" + getCadastralNumber() +
            ", totalSpace=" + getTotalSpace() +
            ", purposeOfNonResidentalEstateId=" + getPurposeOfNonResidentalEstateId() +
            ", buildingTypeOfNonResidentalEstateId=" + getBuildingTypeOfNonResidentalEstateId() +
            ", addressId=" + getAddressId() +
            "}";
    }
}
