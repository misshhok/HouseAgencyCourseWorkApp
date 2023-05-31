package com.bsu.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import javax.persistence.*;

/**
 * A NonResidentalEstates.
 */
@Entity
@Table(name = "non_residental_estates")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class NonResidentalEstates implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "price", precision = 21, scale = 2)
    private BigDecimal price;

    @Column(name = "commissioning_date")
    private LocalDate commissioningDate;

    @Column(name = "cadastral_number")
    private Long cadastralNumber;

    @Column(name = "total_space")
    private Double totalSpace;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "purpose_of_non_residental_estate_id")
    private PurposesOfNonResidentalEstate purposeOfNonResidentalEstateId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "building_type_of_non_residental_estate_id")
    private BuildingTypeOfNonResidentalEstate buildingTypeOfNonResidentalEstateId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "address_id")
    @JsonIgnoreProperties(value = { "streetOfCityId" }, allowSetters = true)
    private Addresses addressId;

  // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public NonResidentalEstates id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public NonResidentalEstates price(BigDecimal price) {
        this.setPrice(price);
        return this;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public LocalDate getCommissioningDate() {
        return this.commissioningDate;
    }

    public NonResidentalEstates commissioningDate(LocalDate commissioningDate) {
        this.setCommissioningDate(commissioningDate);
        return this;
    }

    public void setCommissioningDate(LocalDate commissioningDate) {
        this.commissioningDate = commissioningDate;
    }

    public Long getCadastralNumber() {
        return this.cadastralNumber;
    }

    public NonResidentalEstates cadastralNumber(Long cadastralNumber) {
        this.setCadastralNumber(cadastralNumber);
        return this;
    }

    public void setCadastralNumber(Long cadastralNumber) {
        this.cadastralNumber = cadastralNumber;
    }

    public Double getTotalSpace() {
        return this.totalSpace;
    }

    public NonResidentalEstates totalSpace(Double totalSpace) {
        this.setTotalSpace(totalSpace);
        return this;
    }

    public void setTotalSpace(Double totalSpace) {
        this.totalSpace = totalSpace;
    }

    public PurposesOfNonResidentalEstate getPurposeOfNonResidentalEstateId() {
        return this.purposeOfNonResidentalEstateId;
    }

    public void setPurposeOfNonResidentalEstateId(PurposesOfNonResidentalEstate purposesOfNonResidentalEstate) {
        this.purposeOfNonResidentalEstateId = purposesOfNonResidentalEstate;
    }

    public NonResidentalEstates purposeOfNonResidentalEstateId(PurposesOfNonResidentalEstate purposesOfNonResidentalEstate) {
        this.setPurposeOfNonResidentalEstateId(purposesOfNonResidentalEstate);
        return this;
    }

    public BuildingTypeOfNonResidentalEstate getBuildingTypeOfNonResidentalEstateId() {
        return this.buildingTypeOfNonResidentalEstateId;
    }

    public void setBuildingTypeOfNonResidentalEstateId(BuildingTypeOfNonResidentalEstate buildingTypeOfNonResidentalEstate) {
        this.buildingTypeOfNonResidentalEstateId = buildingTypeOfNonResidentalEstate;
    }

    public NonResidentalEstates buildingTypeOfNonResidentalEstateId(BuildingTypeOfNonResidentalEstate buildingTypeOfNonResidentalEstate) {
        this.setBuildingTypeOfNonResidentalEstateId(buildingTypeOfNonResidentalEstate);
        return this;
    }

    public Addresses getAddressId() {
        return this.addressId;
    }

    public void setAddressId(Addresses addresses) {
        this.addressId = addresses;
    }

    public NonResidentalEstates addressId(Addresses addresses) {
        this.setAddressId(addresses);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NonResidentalEstates)) {
            return false;
        }
        return id != null && id.equals(((NonResidentalEstates) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NonResidentalEstates{" +
            "id=" + getId() +
            ", price=" + getPrice() +
            ", commissioningDate='" + getCommissioningDate() + "'" +
            ", cadastralNumber=" + getCadastralNumber() +
            ", totalSpace=" + getTotalSpace() +
            "}";
    }
}
