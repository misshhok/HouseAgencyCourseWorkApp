package com.bsu.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;

/**
 * A Addresses.
 */
@Entity
@Table(name = "addresses")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Addresses implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "house_number")
    private Integer houseNumber;

    @ManyToOne
    @JsonIgnoreProperties(value = { "cityId" }, allowSetters = true)
    private StreetsOfCities streetOfCityId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Addresses id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getHouseNumber() {
        return this.houseNumber;
    }

    public Addresses houseNumber(Integer houseNumber) {
        this.setHouseNumber(houseNumber);
        return this;
    }

    public void setHouseNumber(Integer houseNumber) {
        this.houseNumber = houseNumber;
    }

    public StreetsOfCities getStreetOfCityId() {
        return this.streetOfCityId;
    }

    public void setStreetOfCityId(StreetsOfCities streetsOfCities) {
        this.streetOfCityId = streetsOfCities;
    }

    public Addresses streetOfCityId(StreetsOfCities streetsOfCities) {
        this.setStreetOfCityId(streetsOfCities);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Addresses)) {
            return false;
        }
        return id != null && id.equals(((Addresses) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Addresses{" +
            "id=" + getId() +
            ", houseNumber=" + getHouseNumber() +
            "}";
    }
}
