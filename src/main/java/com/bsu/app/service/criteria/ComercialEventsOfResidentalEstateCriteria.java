package com.bsu.app.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.bsu.app.domain.ComercialEventsOfResidentalEstate} entity. This class is used
 * in {@link com.bsu.app.web.rest.ComercialEventsOfResidentalEstateResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /comercial-events-of-residental-estates?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ComercialEventsOfResidentalEstateCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter agentNotes;

    private LocalDateFilter dateOfEvent;

    private LongFilter typeOfCommercialEventIdId;

    private LongFilter clientIdId;

    private LongFilter residentalEstateIdId;

    private Boolean distinct;

    public ComercialEventsOfResidentalEstateCriteria() {}

    public ComercialEventsOfResidentalEstateCriteria(ComercialEventsOfResidentalEstateCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.agentNotes = other.agentNotes == null ? null : other.agentNotes.copy();
        this.dateOfEvent = other.dateOfEvent == null ? null : other.dateOfEvent.copy();
        this.typeOfCommercialEventIdId = other.typeOfCommercialEventIdId == null ? null : other.typeOfCommercialEventIdId.copy();
        this.clientIdId = other.clientIdId == null ? null : other.clientIdId.copy();
        this.residentalEstateIdId = other.residentalEstateIdId == null ? null : other.residentalEstateIdId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ComercialEventsOfResidentalEstateCriteria copy() {
        return new ComercialEventsOfResidentalEstateCriteria(this);
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

    public StringFilter getAgentNotes() {
        return agentNotes;
    }

    public StringFilter agentNotes() {
        if (agentNotes == null) {
            agentNotes = new StringFilter();
        }
        return agentNotes;
    }

    public void setAgentNotes(StringFilter agentNotes) {
        this.agentNotes = agentNotes;
    }

    public LocalDateFilter getDateOfEvent() {
        return dateOfEvent;
    }

    public LocalDateFilter dateOfEvent() {
        if (dateOfEvent == null) {
            dateOfEvent = new LocalDateFilter();
        }
        return dateOfEvent;
    }

    public void setDateOfEvent(LocalDateFilter dateOfEvent) {
        this.dateOfEvent = dateOfEvent;
    }

    public LongFilter getTypeOfCommercialEventIdId() {
        return typeOfCommercialEventIdId;
    }

    public LongFilter typeOfCommercialEventIdId() {
        if (typeOfCommercialEventIdId == null) {
            typeOfCommercialEventIdId = new LongFilter();
        }
        return typeOfCommercialEventIdId;
    }

    public void setTypeOfCommercialEventIdId(LongFilter typeOfCommercialEventIdId) {
        this.typeOfCommercialEventIdId = typeOfCommercialEventIdId;
    }

    public LongFilter getClientIdId() {
        return clientIdId;
    }

    public LongFilter clientIdId() {
        if (clientIdId == null) {
            clientIdId = new LongFilter();
        }
        return clientIdId;
    }

    public void setClientIdId(LongFilter clientIdId) {
        this.clientIdId = clientIdId;
    }

    public LongFilter getResidentalEstateIdId() {
        return residentalEstateIdId;
    }

    public LongFilter residentalEstateIdId() {
        if (residentalEstateIdId == null) {
            residentalEstateIdId = new LongFilter();
        }
        return residentalEstateIdId;
    }

    public void setResidentalEstateIdId(LongFilter residentalEstateIdId) {
        this.residentalEstateIdId = residentalEstateIdId;
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
        final ComercialEventsOfResidentalEstateCriteria that = (ComercialEventsOfResidentalEstateCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(agentNotes, that.agentNotes) &&
            Objects.equals(dateOfEvent, that.dateOfEvent) &&
            Objects.equals(typeOfCommercialEventIdId, that.typeOfCommercialEventIdId) &&
            Objects.equals(clientIdId, that.clientIdId) &&
            Objects.equals(residentalEstateIdId, that.residentalEstateIdId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, agentNotes, dateOfEvent, typeOfCommercialEventIdId, clientIdId, residentalEstateIdId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ComercialEventsOfResidentalEstateCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (agentNotes != null ? "agentNotes=" + agentNotes + ", " : "") +
            (dateOfEvent != null ? "dateOfEvent=" + dateOfEvent + ", " : "") +
            (typeOfCommercialEventIdId != null ? "typeOfCommercialEventIdId=" + typeOfCommercialEventIdId + ", " : "") +
            (clientIdId != null ? "clientIdId=" + clientIdId + ", " : "") +
            (residentalEstateIdId != null ? "residentalEstateIdId=" + residentalEstateIdId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
