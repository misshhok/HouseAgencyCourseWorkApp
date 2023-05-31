package com.bsu.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import javax.persistence.*;

/**
 * A ResidentalEstates.
 */
@Entity
@Table(name = "residental_estates")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ResidentalEstates implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "living_space")
    private Double livingSpace;

    @Column(name = "cadastral_number")
    private Long cadastralNumber;

    @Column(name = "commissioning_date")
    private LocalDate commissioningDate;

    @Column(name = "number_of_rooms")
    private Integer numberOfRooms;

    @Column(name = "furnish_type")
    private String furnishType;

    @Column(name = "has_balcony")
    private Boolean hasBalcony;

    @Column(name = "bathroom_type")
    private String bathroomType;

    @Column(name = "ceiling_height")
    private Double ceilingHeight;

    @Column(name = "price", precision = 21, scale = 2)
    private BigDecimal price;

    @ManyToOne
    @JsonIgnoreProperties(value = { "streetOfCityId" }, allowSetters = true)
    private Addresses addressId;

    @ManyToOne
    private TypesOfResidentalEstate typeOfResidentalEstateId;

    @ManyToOne
    private StatusesOfResidentalEstate statusOfResidentalEstateId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ResidentalEstates id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getLivingSpace() {
        return this.livingSpace;
    }

    public ResidentalEstates livingSpace(Double livingSpace) {
        this.setLivingSpace(livingSpace);
        return this;
    }

    public void setLivingSpace(Double livingSpace) {
        this.livingSpace = livingSpace;
    }

    public Long getCadastralNumber() {
        return this.cadastralNumber;
    }

    public ResidentalEstates cadastralNumber(Long cadastralNumber) {
        this.setCadastralNumber(cadastralNumber);
        return this;
    }

    public void setCadastralNumber(Long cadastralNumber) {
        this.cadastralNumber = cadastralNumber;
    }

    public LocalDate getCommissioningDate() {
        return this.commissioningDate;
    }

    public ResidentalEstates commissioningDate(LocalDate commissioningDate) {
        this.setCommissioningDate(commissioningDate);
        return this;
    }

    public void setCommissioningDate(LocalDate commissioningDate) {
        this.commissioningDate = commissioningDate;
    }

    public Integer getNumberOfRooms() {
        return this.numberOfRooms;
    }

    public ResidentalEstates numberOfRooms(Integer numberOfRooms) {
        this.setNumberOfRooms(numberOfRooms);
        return this;
    }

    public void setNumberOfRooms(Integer numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }

    public String getFurnishType() {
        return this.furnishType;
    }

    public ResidentalEstates furnishType(String furnishType) {
        this.setFurnishType(furnishType);
        return this;
    }

    public void setFurnishType(String furnishType) {
        this.furnishType = furnishType;
    }

    public Boolean getHasBalcony() {
        return this.hasBalcony;
    }

    public ResidentalEstates hasBalcony(Boolean hasBalcony) {
        this.setHasBalcony(hasBalcony);
        return this;
    }

    public void setHasBalcony(Boolean hasBalcony) {
        this.hasBalcony = hasBalcony;
    }

    public String getBathroomType() {
        return this.bathroomType;
    }

    public ResidentalEstates bathroomType(String bathroomType) {
        this.setBathroomType(bathroomType);
        return this;
    }

    public void setBathroomType(String bathroomType) {
        this.bathroomType = bathroomType;
    }

    public Double getCeilingHeight() {
        return this.ceilingHeight;
    }

    public ResidentalEstates ceilingHeight(Double ceilingHeight) {
        this.setCeilingHeight(ceilingHeight);
        return this;
    }

    public void setCeilingHeight(Double ceilingHeight) {
        this.ceilingHeight = ceilingHeight;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public ResidentalEstates price(BigDecimal price) {
        this.setPrice(price);
        return this;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Addresses getAddressId() {
        return this.addressId;
    }

    public void setAddressId(Addresses addresses) {
        this.addressId = addresses;
    }

    public ResidentalEstates addressId(Addresses addresses) {
        this.setAddressId(addresses);
        return this;
    }

    public TypesOfResidentalEstate getTypeOfResidentalEstateId() {
        return this.typeOfResidentalEstateId;
    }

    public void setTypeOfResidentalEstateId(TypesOfResidentalEstate typesOfResidentalEstate) {
        this.typeOfResidentalEstateId = typesOfResidentalEstate;
    }

    public ResidentalEstates typeOfResidentalEstateId(TypesOfResidentalEstate typesOfResidentalEstate) {
        this.setTypeOfResidentalEstateId(typesOfResidentalEstate);
        return this;
    }

    public StatusesOfResidentalEstate getStatusOfResidentalEstateId() {
        return this.statusOfResidentalEstateId;
    }

    public void setStatusOfResidentalEstateId(StatusesOfResidentalEstate statusesOfResidentalEstate) {
        this.statusOfResidentalEstateId = statusesOfResidentalEstate;
    }

    public ResidentalEstates statusOfResidentalEstateId(StatusesOfResidentalEstate statusesOfResidentalEstate) {
        this.setStatusOfResidentalEstateId(statusesOfResidentalEstate);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ResidentalEstates)) {
            return false;
        }
        return id != null && id.equals(((ResidentalEstates) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ResidentalEstates{" +
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
            "}";
    }
}
