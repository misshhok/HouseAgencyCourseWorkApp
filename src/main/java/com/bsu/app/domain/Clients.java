package com.bsu.app.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * A Clients.
 */
@Entity
@Table(name = "clients")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Clients implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "surename")
    private String surename;

    @Column(name = "patronymic")
    private String patronymic;

    @Column(name = "phone_number")
    private String phoneNumber;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Clients id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Clients name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurename() {
        return this.surename;
    }

    public Clients surename(String surename) {
        this.setSurename(surename);
        return this;
    }

    public void setSurename(String surename) {
        this.surename = surename;
    }

    public String getPatronymic() {
        return this.patronymic;
    }

    public Clients patronymic(String patronymic) {
        this.setPatronymic(patronymic);
        return this;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public Clients phoneNumber(String phoneNumber) {
        this.setPhoneNumber(phoneNumber);
        return this;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Clients)) {
            return false;
        }
        return id != null && id.equals(((Clients) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Clients{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", surename='" + getSurename() + "'" +
            ", patronymic='" + getPatronymic() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            "}";
    }
}
