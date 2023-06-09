package com.bsu.app.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.bsu.app.domain.StreetsOfCities} entity. This class is used
 * in {@link com.bsu.app.web.rest.StreetsOfCitiesResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /streets-of-cities?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class StreetsOfCitiesCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter title;

    private LongFilter cityIdId;

    private Boolean distinct;

    public StreetsOfCitiesCriteria() {}

    public StreetsOfCitiesCriteria(StreetsOfCitiesCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.title = other.title == null ? null : other.title.copy();
        this.cityIdId = other.cityIdId == null ? null : other.cityIdId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public StreetsOfCitiesCriteria copy() {
        return new StreetsOfCitiesCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getTitle() {
        return title;
    }

    public StringFilter title() {
        if (title == null) {
            title = new StringFilter();
        }
        return title;
    }

    public void setTitle(StringFilter title) {
        this.title = title;
    }

    public LongFilter getCityIdId() {
        return cityIdId;
    }

    public LongFilter cityIdId() {
        if (cityIdId == null) {
            cityIdId = new LongFilter();
        }
        return cityIdId;
    }

    public void setCityIdId(LongFilter cityIdId) {
        this.cityIdId = cityIdId;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final StreetsOfCitiesCriteria that = (StreetsOfCitiesCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(title, that.title) &&
            Objects.equals(cityIdId, that.cityIdId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, cityIdId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StreetsOfCitiesCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (title != null ? "title=" + title + ", " : "") +
            (cityIdId != null ? "cityIdId=" + cityIdId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
