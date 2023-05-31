package com.bsu.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;

/**
 * A ComercialEventsOfResidentalEstate.
 */
@Entity
@Table(name = "comercial_events_of_residental_estate")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ComercialEventsOfResidentalEstate implements Serializable {

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
    private Clients clientId;

    @ManyToOne
    @JsonIgnoreProperties(value = { "addressId", "typeOfResidentalEstateId", "statusOfResidentalEstateId" }, allowSetters = true)
    private ResidentalEstates residentalEstateId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ComercialEventsOfResidentalEstate id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAgentNotes() {
        return this.agentNotes;
    }

    public ComercialEventsOfResidentalEstate agentNotes(String agentNotes) {
        this.setAgentNotes(agentNotes);
        return this;
    }

    public void setAgentNotes(String agentNotes) {
        this.agentNotes = agentNotes;
    }

    public LocalDate getDateOfEvent() {
        return this.dateOfEvent;
    }

    public ComercialEventsOfResidentalEstate dateOfEvent(LocalDate dateOfEvent) {
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

    public ComercialEventsOfResidentalEstate typeOfCommercialEventId(TypesOfCommercialEvents typesOfCommercialEvents) {
        this.setTypeOfCommercialEventId(typesOfCommercialEvents);
        return this;
    }

    public Clients getClientId() {
        return this.clientId;
    }

    public void setClientId(Clients clients) {
        this.clientId = clients;
    }

    public ComercialEventsOfResidentalEstate clientId(Clients clients) {
        this.setClientId(clients);
        return this;
    }

    public ResidentalEstates getResidentalEstateId() {
        return this.residentalEstateId;
    }

    public void setResidentalEstateId(ResidentalEstates residentalEstates) {
        this.residentalEstateId = residentalEstates;
    }

    public ComercialEventsOfResidentalEstate residentalEstateId(ResidentalEstates residentalEstates) {
        this.setResidentalEstateId(residentalEstates);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ComercialEventsOfResidentalEstate)) {
            return false;
        }
        return id != null && id.equals(((ComercialEventsOfResidentalEstate) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ComercialEventsOfResidentalEstate{" +
            "id=" + getId() +
            ", agentNotes='" + getAgentNotes() + "'" +
            ", dateOfEvent='" + getDateOfEvent() + "'" +
            "}";
    }
}
