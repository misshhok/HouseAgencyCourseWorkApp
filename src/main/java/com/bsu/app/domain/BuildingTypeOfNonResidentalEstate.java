package com.bsu.app.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * A BuildingTypeOfNonResidentalEstate.
 */
@Entity
@Table(name = "building_type_of_non_residental_estate")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BuildingTypeOfNonResidentalEstate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public BuildingTypeOfNonResidentalEstate id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public BuildingTypeOfNonResidentalEstate title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BuildingTypeOfNonResidentalEstate)) {
            return false;
        }
        return id != null && id.equals(((BuildingTypeOfNonResidentalEstate) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BuildingTypeOfNonResidentalEstate{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            "}";
    }
}
