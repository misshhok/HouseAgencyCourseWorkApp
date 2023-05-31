package com.bsu.app.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.bsu.app.domain.NonResidentalEstates} entity. This class is used
 * in {@link com.bsu.app.web.rest.NonResidentalEstatesResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /non-residental-estates?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class NonResidentalEstatesCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private BigDecimalFilter price;

    private LocalDateFilter commissioningDate;

    private LongFilter cadastralNumber;

    private DoubleFilter totalSpace;

    private LongFilter purposeOfNonResidentalEstateIdId;

    private LongFilter buildingTypeOfNonResidentalEstateIdId;

    private LongFilter addressIdId;

    private Boolean distinct;

    public NonResidentalEstatesCriteria() {}

    public NonResidentalEstatesCriteria(NonResidentalEstatesCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.price = other.price == null ? null : other.price.copy();
        this.commissioningDate = other.commissioningDate == null ? null : other.commissioningDate.copy();
        this.cadastralNumber = other.cadastralNumber == null ? null : other.cadastralNumber.copy();
        this.totalSpace = other.totalSpace == null ? null : other.totalSpace.copy();
        this.purposeOfNonResidentalEstateIdId =
            other.purposeOfNonResidentalEstateIdId == null ? null : other.purposeOfNonResidentalEstateIdId.copy();
        this.buildingTypeOfNonResidentalEstateIdId =
            other.buildingTypeOfNonResidentalEstateIdId == null ? null : other.buildingTypeOfNonResidentalEstateIdId.copy();
        this.addressIdId = other.addressIdId == null ? null : other.addressIdId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public NonResidentalEstatesCriteria copy() {
        return new NonResidentalEstatesCriteria(this);
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

    public BigDecimalFilter getPrice() {
        return price;
    }

    public BigDecimalFilter price() {
        if (price == null) {
            price = new BigDecimalFilter();
        }
        return price;
    }

    public void setPrice(BigDecimalFilter price) {
        this.price = price;
    }

    public LocalDateFilter getCommissioningDate() {
        return commissioningDate;
    }

    public LocalDateFilter commissioningDate() {
        if (commissioningDate == null) {
            commissioningDate = new LocalDateFilter();
        }
        return commissioningDate;
    }

    public void setCommissioningDate(LocalDateFilter commissioningDate) {
        this.commissioningDate = commissioningDate;
    }

    public LongFilter getCadastralNumber() {
        return cadastralNumber;
    }

    public LongFilter cadastralNumber() {
        if (cadastralNumber == null) {
            cadastralNumber = new LongFilter();
        }
        return cadastralNumber;
    }

    public void setCadastralNumber(LongFilter cadastralNumber) {
        this.cadastralNumber = cadastralNumber;
    }

    public DoubleFilter getTotalSpace() {
        return totalSpace;
    }

    public DoubleFilter totalSpace() {
        if (totalSpace == null) {
            totalSpace = new DoubleFilter();
        }
        return totalSpace;
    }

    public void setTotalSpace(DoubleFilter totalSpace) {
        this.totalSpace = totalSpace;
    }

    public LongFilter getPurposeOfNonResidentalEstateIdId() {
        return purposeOfNonResidentalEstateIdId;
    }

    public LongFilter purposeOfNonResidentalEstateIdId() {
        if (purposeOfNonResidentalEstateIdId == null) {
            purposeOfNonResidentalEstateIdId = new LongFilter();
        }
        return purposeOfNonResidentalEstateIdId;
    }

    public void setPurposeOfNonResidentalEstateIdId(LongFilter purposeOfNonResidentalEstateIdId) {
        this.purposeOfNonResidentalEstateIdId = purposeOfNonResidentalEstateIdId;
    }

    public LongFilter getBuildingTypeOfNonResidentalEstateIdId() {
        return buildingTypeOfNonResidentalEstateIdId;
    }

    public LongFilter buildingTypeOfNonResidentalEstateIdId() {
        if (buildingTypeOfNonResidentalEstateIdId == null) {
            buildingTypeOfNonResidentalEstateIdId = new LongFilter();
        }
        return buildingTypeOfNonResidentalEstateIdId;
    }

    public void setBuildingTypeOfNonResidentalEstateIdId(LongFilter buildingTypeOfNonResidentalEstateIdId) {
        this.buildingTypeOfNonResidentalEstateIdId = buildingTypeOfNonResidentalEstateIdId;
    }

    public LongFilter getAddressIdId() {
        return addressIdId;
    }

    public LongFilter addressIdId() {
        if (addressIdId == null) {
            addressIdId = new LongFilter();
        }
        return addressIdId;
    }

    public void setAddressIdId(LongFilter addressIdId) {
        this.addressIdId = addressIdId;
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
        final NonResidentalEstatesCriteria that = (NonResidentalEstatesCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(price, that.price) &&
            Objects.equals(commissioningDate, that.commissioningDate) &&
            Objects.equals(cadastralNumber, that.cadastralNumber) &&
            Objects.equals(totalSpace, that.totalSpace) &&
            Objects.equals(purposeOfNonResidentalEstateIdId, that.purposeOfNonResidentalEstateIdId) &&
            Objects.equals(buildingTypeOfNonResidentalEstateIdId, that.buildingTypeOfNonResidentalEstateIdId) &&
            Objects.equals(addressIdId, that.addressIdId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            price,
            commissioningDate,
            cadastralNumber,
            totalSpace,
            purposeOfNonResidentalEstateIdId,
            buildingTypeOfNonResidentalEstateIdId,
            addressIdId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NonResidentalEstatesCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (price != null ? "price=" + price + ", " : "") +
            (commissioningDate != null ? "commissioningDate=" + commissioningDate + ", " : "") +
            (cadastralNumber != null ? "cadastralNumber=" + cadastralNumber + ", " : "") +
            (totalSpace != null ? "totalSpace=" + totalSpace + ", " : "") +
            (purposeOfNonResidentalEstateIdId != null ? "purposeOfNonResidentalEstateIdId=" + purposeOfNonResidentalEstateIdId + ", " : "") +
            (buildingTypeOfNonResidentalEstateIdId != null ? "buildingTypeOfNonResidentalEstateIdId=" + buildingTypeOfNonResidentalEstateIdId + ", " : "") +
            (addressIdId != null ? "addressIdId=" + addressIdId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
