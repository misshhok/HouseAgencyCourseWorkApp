package com.bsu.app.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.bsu.app.domain.Clients} entity. This class is used
 * in {@link com.bsu.app.web.rest.ClientsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /clients?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ClientsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter surename;

    private StringFilter patronymic;

    private StringFilter phoneNumber;

    private Boolean distinct;

    public ClientsCriteria() {}

    public ClientsCriteria(ClientsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.surename = other.surename == null ? null : other.surename.copy();
        this.patronymic = other.patronymic == null ? null : other.patronymic.copy();
        this.phoneNumber = other.phoneNumber == null ? null : other.phoneNumber.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ClientsCriteria copy() {
        return new ClientsCriteria(this);
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

    public StringFilter getName() {
        return name;
    }

    public StringFilter name() {
        if (name == null) {
            name = new StringFilter();
        }
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getSurename() {
        return surename;
    }

    public StringFilter surename() {
        if (surename == null) {
            surename = new StringFilter();
        }
        return surename;
    }

    public void setSurename(StringFilter surename) {
        this.surename = surename;
    }

    public StringFilter getPatronymic() {
        return patronymic;
    }

    public StringFilter patronymic() {
        if (patronymic == null) {
            patronymic = new StringFilter();
        }
        return patronymic;
    }

    public void setPatronymic(StringFilter patronymic) {
        this.patronymic = patronymic;
    }

    public StringFilter getPhoneNumber() {
        return phoneNumber;
    }

    public StringFilter phoneNumber() {
        if (phoneNumber == null) {
            phoneNumber = new StringFilter();
        }
        return phoneNumber;
    }

    public void setPhoneNumber(StringFilter phoneNumber) {
        this.phoneNumber = phoneNumber;
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
        final ClientsCriteria that = (ClientsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(surename, that.surename) &&
            Objects.equals(patronymic, that.patronymic) &&
            Objects.equals(phoneNumber, that.phoneNumber) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surename, patronymic, phoneNumber, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ClientsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (surename != null ? "surename=" + surename + ", " : "") +
            (patronymic != null ? "patronymic=" + patronymic + ", " : "") +
            (phoneNumber != null ? "phoneNumber=" + phoneNumber + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
