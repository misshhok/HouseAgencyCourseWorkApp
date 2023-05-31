package com.bsu.app.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.bsu.app.domain.Addresses} entity. This class is used
 * in {@link com.bsu.app.web.rest.AddressesResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /addresses?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AddressesCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter houseNumber;

    private LongFilter streetOfCityIdId;

    private Boolean distinct;

    public AddressesCriteria() {}

    public AddressesCriteria(AddressesCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.houseNumber = other.houseNumber == null ? null : other.houseNumber.copy();
        this.streetOfCityIdId = other.streetOfCityIdId == null ? null : other.streetOfCityIdId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public AddressesCriteria copy() {
        return new AddressesCriteria(this);
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

    public IntegerFilter getHouseNumber() {
        return houseNumber;
    }

    public IntegerFilter houseNumber() {
        if (houseNumber == null) {
            houseNumber = new IntegerFilter();
        }
        return houseNumber;
    }

    public void setHouseNumber(IntegerFilter houseNumber) {
        this.houseNumber = houseNumber;
    }

    public LongFilter getStreetOfCityIdId() {
        return streetOfCityIdId;
    }

    public LongFilter streetOfCityIdId() {
        if (streetOfCityIdId == null) {
            streetOfCityIdId = new LongFilter();
        }
        return streetOfCityIdId;
    }

    public void setStreetOfCityIdId(LongFilter streetOfCityIdId) {
        this.streetOfCityIdId = streetOfCityIdId;
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
        final AddressesCriteria that = (AddressesCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(houseNumber, that.houseNumber) &&
            Objects.equals(streetOfCityIdId, that.streetOfCityIdId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, houseNumber, streetOfCityIdId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AddressesCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (houseNumber != null ? "houseNumber=" + houseNumber + ", " : "") +
            (streetOfCityIdId != null ? "streetOfCityIdId=" + streetOfCityIdId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
