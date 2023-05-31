package com.bsu.app.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.bsu.app.domain.Clients} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ClientsDTO implements Serializable {

    private Long id;

    private String name;

    private String surename;

    private String patronymic;

    private String phoneNumber;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurename() {
        return surename;
    }

    public void setSurename(String surename) {
        this.surename = surename;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ClientsDTO)) {
            return false;
        }

        ClientsDTO clientsDTO = (ClientsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, clientsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ClientsDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", surename='" + getSurename() + "'" +
            ", patronymic='" + getPatronymic() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            "}";
    }
}
