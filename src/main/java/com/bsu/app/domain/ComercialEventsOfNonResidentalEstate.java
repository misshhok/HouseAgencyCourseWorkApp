package com.bsu.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;

/**
 * A ComercialEventsOfNonResidentalEstate.
 */
@Entity
@Table(name = "comercial_events_of_non_residental_estate")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ComercialEventsOfNonResidentalEstate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "agent_notes")
    private String agentNotes;

    @Column(name = "date_of_event")
    private LocalDate dateOfEvent;

    @ManyToOne
    private TypesOfCommercialEvents typeOfCommercialEventId;

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "purposeOfNonResidentalEstateId", "buildingTypeOfNonResidentalEstateId", "addressId" },
        allowSetters = true
    )
    private NonResidentalEstates nonResidentalEstateId;

    @ManyToOne
    private Clients clientId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ComercialEventsOfNonResidentalEstate id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAgentNotes() {
        return this.agentNotes;
    }

    public ComercialEventsOfNonResidentalEstate agentNotes(String agentNotes) {
        this.setAgentNotes(agentNotes);
        return this;
    }

    public void setAgentNotes(String agentNotes) {
        this.agentNotes = agentNotes;
    }

    public LocalDate getDateOfEvent() {
        return this.dateOfEvent;
    }

    public ComercialEventsOfNonResidentalEstate dateOfEvent(LocalDate dateOfEvent) {
        this.setDateOfEvent(dateOfEvent);
        return this;
    }

    public void setDateOfEvent(LocalDate dateOfEvent) {
        this.dateOfEvent = dateOfEvent;
    }

    public TypesOfCommercialEvents getTypeOfCommercialEventId() {
        return this.typeOfCommercialEventId;
    }

    public void setTypeOfCommercialEventId(TypesOfCommercialEvents typesOfCommercialEvents) {
        this.typeOfCommercialEventId = typesOfCommercialEvents;
    }

    public ComercialEventsOfNonResidentalEstate typeOfCommercialEventId(TypesOfCommercialEvents typesOfCommercialEvents) {
        this.setTypeOfCommercialEventId(typesOfCommercialEvents);
        return this;
    }

    public NonResidentalEstates getNonResidentalEstateId() {
        return this.nonResidentalEstateId;
    }

    public void setNonResidentalEstateId(NonResidentalEstates nonResidentalEstates) {
        this.nonResidentalEstateId = nonResidentalEstates;
    }

    public ComercialEventsOfNonResidentalEstate nonResidentalEstateId(NonResidentalEstates nonResidentalEstates) {
        this.setNonResidentalEstateId(nonResidentalEstates);
        return this;
    }

    public Clients getClientId() {
        return this.clientId;
    }

    public void setClientId(Clients clients) {
        this.clientId = clients;
    }

    public ComercialEventsOfNonResidentalEstate clientId(Clients clients) {
        this.setClientId(clients);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ComercialEventsOfNonResidentalEstate)) {
            return false;
        }
        return id != null && id.equals(((ComercialEventsOfNonResidentalEstate) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ComercialEventsOfNonResidentalEstate{" +
            "id=" + getId() +
            ", agentNotes='" + getAgentNotes() + "'" +
            ", dateOfEvent='" + getDateOfEvent() + "'" +
            "}";
    }
}
