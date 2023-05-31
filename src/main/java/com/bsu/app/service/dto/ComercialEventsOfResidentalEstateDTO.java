package com.bsu.app.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.bsu.app.domain.ComercialEventsOfResidentalEstate} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ComercialEventsOfResidentalEstateDTO implements Serializable {

    private Long id;

    private String agentNotes;

    private LocalDate dateOfEvent;

    private TypesOfCommercialEventsDTO typeOfCommercialEventId;

    private ClientsDTO clientId;

    private ResidentalEstatesDTO residentalEstateId;

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

    public ClientsDTO getClientId() {
        return clientId;
    }

    public void setClientId(ClientsDTO clientId) {
        this.clientId = clientId;
    }

    public ResidentalEstatesDTO getResidentalEstateId() {
        return residentalEstateId;
    }

    public void setResidentalEstateId(ResidentalEstatesDTO residentalEstateId) {
        this.residentalEstateId = residentalEstateId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ComercialEventsOfResidentalEstateDTO)) {
            return false;
        }

        ComercialEventsOfResidentalEstateDTO comercialEventsOfResidentalEstateDTO = (ComercialEventsOfResidentalEstateDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, comercialEventsOfResidentalEstateDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ComercialEventsOfResidentalEstateDTO{" +
            "id=" + getId() +
            ", agentNotes='" + getAgentNotes() + "'" +
            ", dateOfEvent='" + getDateOfEvent() + "'" +
            ", typeOfCommercialEventId=" + getTypeOfCommercialEventId() +
            ", clientId=" + getClientId() +
            ", residentalEstateId=" + getResidentalEstateId() +
            "}";
    }
}
