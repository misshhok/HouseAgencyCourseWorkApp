package com.bsu.app.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.bsu.app.domain.ComercialEventsOfNonResidentalEstate} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ComercialEventsOfNonResidentalEstateDTO implements Serializable {

    private Long id;

    private String agentNotes;

    private LocalDate dateOfEvent;

    private TypesOfCommercialEventsDTO typeOfCommercialEventId;

    private NonResidentalEstatesDTO nonResidentalEstateId;

    private ClientsDTO clientId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAgentNotes() {
        return agentNotes;
    }

    public void setAgentNotes(String agentNotes) {
        this.agentNotes = agentNotes;
    }

    public LocalDate getDateOfEvent() {
        return dateOfEvent;
    }

    public void setDateOfEvent(LocalDate dateOfEvent) {
        this.dateOfEvent = dateOfEvent;
    }

    public TypesOfCommercialEventsDTO getTypeOfCommercialEventId() {
        return typeOfCommercialEventId;
    }

    public void setTypeOfCommercialEventId(TypesOfCommercialEventsDTO typeOfCommercialEventId) {
        this.typeOfCommercialEventId = typeOfCommercialEventId;
    }

    public NonResidentalEstatesDTO getNonResidentalEstateId() {
        return nonResidentalEstateId;
    }

    public void setNonResidentalEstateId(NonResidentalEstatesDTO nonResidentalEstateId) {
        this.nonResidentalEstateId = nonResidentalEstateId;
    }

    public ClientsDTO getClientId() {
        return clientId;
    }

    public void setClientId(ClientsDTO clientId) {
        this.clientId = clientId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ComercialEventsOfNonResidentalEstateDTO)) {
            return false;
        }

        ComercialEventsOfNonResidentalEstateDTO comercialEventsOfNonResidentalEstateDTO = (ComercialEventsOfNonResidentalEstateDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, comercialEventsOfNonResidentalEstateDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ComercialEventsOfNonResidentalEstateDTO{" +
            "id=" + getId() +
            ", agentNotes='" + getAgentNotes() + "'" +
            ", dateOfEvent='" + getDateOfEvent() + "'" +
            ", typeOfCommercialEventId=" + getTypeOfCommercialEventId() +
            ", nonResidentalEstateId=" + getNonResidentalEstateId() +
            ", clientId=" + getClientId() +
            "}";
    }
}
