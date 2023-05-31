package com.bsu.app.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.bsu.app.domain.ResidentalEstates} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ResidentalEstatesDTO implements Serializable {

    private Long id;

    private Double livingSpace;

    private Long cadastralNumber;

    private LocalDate commissioningDate;

    private Integer numberOfRooms;

    private String furnishType;

    private Boolean hasBalcony;

    private String bathroomType;

    private Double ceilingHeight;

    private BigDecimal price;

    private AddressesDTO addressId;

    private TypesOfResidentalEstateDTO typeOfResidentalEstateId;

    private StatusesOfResidentalEstateDTO statusOfResidentalEstateId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getLivingSpace() {
        return livingSpace;
    }

    public void setLivingSpace(Double livingSpace) {
        this.livingSpace = livingSpace;
    }

    public Long getCadastralNumber() {
        return cadastralNumber;
    }

    public void setCadastralNumber(Long cadastralNumber) {
        this.cadastralNumber = cadastralNumber;
    }

    public LocalDate getCommissioningDate() {
        return commissioningDate;
    }

    public void setCommissioningDate(LocalDate commissioningDate) {
        this.commissioningDate = commissioningDate;
    }

    public Integer getNumberOfRooms() {
        return numberOfRooms;
    }

    public void setNumberOfRooms(Integer numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }

    public String getFurnishType() {
        return furnishType;
    }

    public void setFurnishType(String furnishType) {
        this.furnishType = furnishType;
    }

    public Boolean getHasBalcony() {
        return hasBalcony;
    }

    public void setHasBalcony(Boolean hasBalcony) {
        this.hasBalcony = hasBalcony;
    }

    public String getBathroomType() {
        return bathroomType;
    }

    public void setBathroomType(String bathroomType) {
        this.bathroomType = bathroomType;
    }

    public Double getCeilingHeight() {
        return ceilingHeight;
    }

    public void setCeilingHeight(Double ceilingHeight) {
        this.ceilingHeight = ceilingHeight;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public AddressesDTO getAddressId() {
        return addressId;
    }

    public void setAddressId(AddressesDTO addressId) {
        this.addressId = addressId;
    }

    public TypesOfResidentalEstateDTO getTypeOfResidentalEstateId() {
        return typeOfResidentalEstateId;
    }

    public void setTypeOfResidentalEstateId(TypesOfResidentalEstateDTO typeOfResidentalEstateId) {
        this.typeOfResidentalEstateId = typeOfResidentalEstateId;
    }

    public StatusesOfResidentalEstateDTO getStatusOfResidentalEstateId() {
        return statusOfResidentalEstateId;
    }

    public void setStatusOfResidentalEstateId(StatusesOfResidentalEstateDTO statusOfResidentalEstateId) {
        this.statusOfResidentalEstateId = statusOfResidentalEstateId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ResidentalEstatesDTO)) {
            return false;
        }

        ResidentalEstatesDTO residentalEstatesDTO = (ResidentalEstatesDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, residentalEstatesDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ResidentalEstatesDTO{" +
            "id=" + getId() +
            ", livingSpace=" + getLivingSpace() +
            ", cadastralNumber=" + getCadastralNumber() +
            ", commissioningDate='" + getCommissioningDate() + "'" +
            ", numberOfRooms=" + getNumberOfRooms() +
            ", furnishType='" + getFurnishType() + "'" +
            ", hasBalcony='" + getHasBalcony() + "'" +
            ", bathroomType='" + getBathroomType() + "'" +
            ", ceilingHeight=" + getCeilingHeight() +
            ", price=" + getPrice() +
            ", addressId=" + getAddressId() +
            ", typeOfResidentalEstateId=" + getTypeOfResidentalEstateId() +
            ", statusOfResidentalEstateId=" + getStatusOfResidentalEstateId() +
            "}";
    }
}
