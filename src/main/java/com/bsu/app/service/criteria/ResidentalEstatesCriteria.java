package com.bsu.app.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.bsu.app.domain.ResidentalEstates} entity. This class is used
 * in {@link com.bsu.app.web.rest.ResidentalEstatesResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /residental-estates?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ResidentalEstatesCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private DoubleFilter livingSpace;

    private LongFilter cadastralNumber;

    private LocalDateFilter commissioningDate;

    private IntegerFilter numberOfRooms;

    private StringFilter furnishType;

    private BooleanFilter hasBalcony;

    private StringFilter bathroomType;

    private DoubleFilter ceilingHeight;

    private BigDecimalFilter price;

    private LongFilter addressIdId;

    private LongFilter typeOfResidentalEstateIdId;

    private LongFilter statusOfResidentalEstateIdId;

    private Boolean distinct;

    public ResidentalEstatesCriteria() {}

    public ResidentalEstatesCriteria(ResidentalEstatesCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.livingSpace = other.livingSpace == null ? null : other.livingSpace.copy();
        this.cadastralNumber = other.cadastralNumber == null ? null : other.cadastralNumber.copy();
        this.commissioningDate = other.commissioningDate == null ? null : other.commissioningDate.copy();
        this.numberOfRooms = other.numberOfRooms == null ? null : other.numberOfRooms.copy();
        this.furnishType = other.furnishType == null ? null : other.furnishType.copy();
        this.hasBalcony = other.hasBalcony == null ? null : other.hasBalcony.copy();
        this.bathroomType = other.bathroomType == null ? null : other.bathroomType.copy();
        this.ceilingHeight = other.ceilingHeight == null ? null : other.ceilingHeight.copy();
        this.price = other.price == null ? null : other.price.copy();
        this.addressIdId = other.addressIdId == null ? null : other.addressIdId.copy();
        this.typeOfResidentalEstateIdId = other.typeOfResidentalEstateIdId == null ? null : other.typeOfResidentalEstateIdId.copy();
        this.statusOfResidentalEstateIdId = other.statusOfResidentalEstateIdId == null ? null : other.statusOfResidentalEstateIdId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ResidentalEstatesCriteria copy() {
        return new ResidentalEstatesCriteria(this);
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

    public DoubleFilter getLivingSpace() {
        return livingSpace;
    }

    public DoubleFilter livingSpace() {
        if (livingSpace == null) {
            livingSpace = new DoubleFilter();
        }
        return livingSpace;
    }

    public void setLivingSpace(DoubleFilter livingSpace) {
        this.livingSpace = livingSpace;
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

    public IntegerFilter getNumberOfRooms() {
        return numberOfRooms;
    }

    public IntegerFilter numberOfRooms() {
        if (numberOfRooms == null) {
            numberOfRooms = new IntegerFilter();
        }
        return numberOfRooms;
    }

    public void setNumberOfRooms(IntegerFilter numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }

    public StringFilter getFurnishType() {
        return furnishType;
    }

    public StringFilter furnishType() {
        if (furnishType == null) {
            furnishType = new StringFilter();
        }
        return furnishType;
    }

    public void setFurnishType(StringFilter furnishType) {
        this.furnishType = furnishType;
    }

    public BooleanFilter getHasBalcony() {
        return hasBalcony;
    }

    public BooleanFilter hasBalcony() {
        if (hasBalcony == null) {
            hasBalcony = new BooleanFilter();
        }
        return hasBalcony;
    }

    public void setHasBalcony(BooleanFilter hasBalcony) {
        this.hasBalcony = hasBalcony;
    }

    public StringFilter getBathroomType() {
        return bathroomType;
    }

    public StringFilter bathroomType() {
        if (bathroomType == null) {
            bathroomType = new StringFilter();
        }
        return bathroomType;
    }

    public void setBathroomType(StringFilter bathroomType) {
        this.bathroomType = bathroomType;
    }

    public DoubleFilter getCeilingHeight() {
        return ceilingHeight;
    }

    public DoubleFilter ceilingHeight() {
        if (ceilingHeight == null) {
            ceilingHeight = new DoubleFilter();
        }
        return ceilingHeight;
    }

    public void setCeilingHeight(DoubleFilter ceilingHeight) {
        this.ceilingHeight = ceilingHeight;
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

    public LongFilter getTypeOfResidentalEstateIdId() {
        return typeOfResidentalEstateIdId;
    }

    public LongFilter typeOfResidentalEstateIdId() {
        if (typeOfResidentalEstateIdId == null) {
            typeOfResidentalEstateIdId = new LongFilter();
        }
        return typeOfResidentalEstateIdId;
    }

    public void setTypeOfResidentalEstateIdId(LongFilter typeOfResidentalEstateIdId) {
        this.typeOfResidentalEstateIdId = typeOfResidentalEstateIdId;
    }

    public LongFilter getStatusOfResidentalEstateIdId() {
        return statusOfResidentalEstateIdId;
    }

    public LongFilter statusOfResidentalEstateIdId() {
        if (statusOfResidentalEstateIdId == null) {
            statusOfResidentalEstateIdId = new LongFilter();
        }
        return statusOfResidentalEstateIdId;
    }

    public void setStatusOfResidentalEstateIdId(LongFilter statusOfResidentalEstateIdId) {
        this.statusOfResidentalEstateIdId = statusOfResidentalEstateIdId;
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
        final ResidentalEstatesCriteria that = (ResidentalEstatesCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(livingSpace, that.livingSpace) &&
            Objects.equals(cadastralNumber, that.cadastralNumber) &&
            Objects.equals(commissioningDate, that.commissioningDate) &&
            Objects.equals(numberOfRooms, that.numberOfRooms) &&
            Objects.equals(furnishType, that.furnishType) &&
            Objects.equals(hasBalcony, that.hasBalcony) &&
            Objects.equals(bathroomType, that.bathroomType) &&
            Objects.equals(ceilingHeight, that.ceilingHeight) &&
            Objects.equals(price, that.price) &&
            Objects.equals(addressIdId, that.addressIdId) &&
            Objects.equals(typeOfResidentalEstateIdId, that.typeOfResidentalEstateIdId) &&
            Objects.equals(statusOfResidentalEstateIdId, that.statusOfResidentalEstateIdId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            livingSpace,
            cadastralNumber,
            commissioningDate,
            numberOfRooms,
            furnishType,
            hasBalcony,
            bathroomType,
            ceilingHeight,
            price,
            addressIdId,
            typeOfResidentalEstateIdId,
            statusOfResidentalEstateIdId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ResidentalEstatesCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (livingSpace != null ? "livingSpace=" + livingSpace + ", " : "") +
            (cadastralNumber != null ? "cadastralNumber=" + cadastralNumber + ", " : "") +
            (commissioningDate != null ? "commissioningDate=" + commissioningDate + ", " : "") +
            (numberOfRooms != null ? "numberOfRooms=" + numberOfRooms + ", " : "") +
            (furnishType != null ? "furnishType=" + furnishType + ", " : "") +
            (hasBalcony != null ? "hasBalcony=" + hasBalcony + ", " : "") +
            (bathroomType != null ? "bathroomType=" + bathroomType + ", " : "") +
            (ceilingHeight != null ? "ceilingHeight=" + ceilingHeight + ", " : "") +
            (price != null ? "price=" + price + ", " : "") +
            (addressIdId != null ? "addressIdId=" + addressIdId + ", " : "") +
            (typeOfResidentalEstateIdId != null ? "typeOfResidentalEstateIdId=" + typeOfResidentalEstateIdId + ", " : "") +
            (statusOfResidentalEstateIdId != null ? "statusOfResidentalEstateIdId=" + statusOfResidentalEstateIdId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
