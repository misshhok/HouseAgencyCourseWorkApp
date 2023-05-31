package com.bsu.app.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * A StreetsOfCities.
 */
@Entity
@Table(name = "streets_of_cities")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class StreetsOfCities implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @ManyToOne
    private Cities cityId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public StreetsOfCities id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public StreetsOfCities title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Cities getCityId() {
        return this.cityId;
    }

    public void setCityId(Cities cities) {
        this.cityId = cities;
    }

    public StreetsOfCities cityId(Cities cities) {
        this.setCityId(cities);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StreetsOfCities)) {
            return false;
        }
        return id != null && id.equals(((StreetsOfCities) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StreetsOfCities{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            "}";
    }
}
