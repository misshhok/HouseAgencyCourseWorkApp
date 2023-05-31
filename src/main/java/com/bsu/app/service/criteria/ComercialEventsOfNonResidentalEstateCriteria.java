package com.bsu.app.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.bsu.app.domain.ComercialEventsOfNonResidentalEstate} entity. This class is used
 * in {@link com.bsu.app.web.rest.ComercialEventsOfNonResidentalEstateResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /comercial-events-of-non-residental-estates?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ComercialEventsOfNonResidentalEstateCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter agentNotes;

    private LocalDateFilter dateOfEvent;

    private LongFilter typeOfCommercialEventIdId;

    private LongFilter nonResidentalEstateIdId;

    private LongFilter clientIdId;

    private Boolean distinct;

    public ComercialEventsOfNonResidentalEstateCriteria() {}

    public ComercialEventsOfNonResidentalEstateCriteria(ComercialEventsOfNonResidentalEstateCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.agentNotes = other.agentNotes == null ? null : other.agentNotes.copy();
        this.dateOfEvent = other.dateOfEvent == null ? null : other.dateOfEvent.copy();
        this.typeOfCommercialEventIdId = other.typeOfCommercialEventIdId == null ? null : other.typeOfCommercialEventIdId.copy();
        this.nonResidentalEstateIdId = other.nonResidentalEstateIdId == null ? null : other.nonResidentalEstateIdId.copy();
        this.clientIdId = other.clientIdId == null ? null : other.clientIdId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ComercialEventsOfNonResidentalEstateCriteria copy() {
        return new ComercialEventsOfNonResidentalEstateCriteria(this);
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

    public LongFilter getNonResidentalEstateIdId() {
        return nonResidentalEstateIdId;
    }

    public LongFilter nonResidentalEstateIdId() {
        if (nonResidentalEstateIdId == null) {
            nonResidentalEstateIdId = new LongFilter();
        }
        return nonResidentalEstateIdId;
    }

    public void setNonResidentalEstateIdId(LongFilter nonResidentalEstateIdId) {
        this.nonResidentalEstateIdId = nonResidentalEstateIdId;
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
        final ComercialEventsOfNonResidentalEstateCriteria that = (ComercialEventsOfNonResidentalEstateCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(agentNotes, that.agentNotes) &&
            Objects.equals(dateOfEvent, that.dateOfEvent) &&
            Objects.equals(typeOfCommercialEventIdId, that.typeOfCommercialEventIdId) &&
            Objects.equals(nonResidentalEstateIdId, that.nonResidentalEstateIdId) &&
            Objects.equals(clientIdId, that.clientIdId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, agentNotes, dateOfEvent, typeOfCommercialEventIdId, nonResidentalEstateIdId, clientIdId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ComercialEventsOfNonResidentalEstateCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (agentNotes != null ? "agentNotes=" + agentNotes + ", " : "") +
            (dateOfEvent != null ? "dateOfEvent=" + dateOfEvent + ", " : "") +
            (typeOfCommercialEventIdId != null ? "typeOfCommercialEventIdId=" + typeOfCommercialEventIdId + ", " : "") +
            (nonResidentalEstateIdId != null ? "nonResidentalEstateIdId=" + nonResidentalEstateIdId + ", " : "") +
            (clientIdId != null ? "clientIdId=" + clientIdId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
