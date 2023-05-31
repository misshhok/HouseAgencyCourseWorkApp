package com.bsu.app.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.bsu.app.domain.TypesOfCommercialEvents} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TypesOfCommercialEventsDTO implements Serializable {

    private Long id;

    private String title;

    private String description;

    private String estateType;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEstateType() {
        return estateType;
    }

    public void setEstateType(String estateType) {
        this.estateType = estateType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TypesOfCommercialEventsDTO)) {
            return false;
        }

        TypesOfCommercialEventsDTO typesOfCommercialEventsDTO = (TypesOfCommercialEventsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, typesOfCommercialEventsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TypesOfCommercialEventsDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", estateType='" + getEstateType() + "'" +
            "}";
    }
}
