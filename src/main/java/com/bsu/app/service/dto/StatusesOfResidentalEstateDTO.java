package com.bsu.app.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.bsu.app.domain.StatusesOfResidentalEstate} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class StatusesOfResidentalEstateDTO implements Serializable {

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
        if (!(o instanceof StatusesOfResidentalEstateDTO)) {
            return false;
        }

        StatusesOfResidentalEstateDTO statusesOfResidentalEstateDTO = (StatusesOfResidentalEstateDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, statusesOfResidentalEstateDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StatusesOfResidentalEstateDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            "}";
    }
}
