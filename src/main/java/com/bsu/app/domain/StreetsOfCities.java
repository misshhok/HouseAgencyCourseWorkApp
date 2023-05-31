package com.bsu.app.domain;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "streets_of_cities")
public class StreetsOfCities {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 255)
    @Column(name = "title")
    private String title;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "city_id")
    private Cities cityId;

    @OneToMany(mappedBy = "streetOfCityId")
    private Set<Addresses> addresses = new LinkedHashSet<>();

    public Long getId() {
        return this.id;
    }

    public StreetsOfCities id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }
    public StreetsOfCities title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Cities getCityId() {
        return this.cityId;
    }

    public void setCityId(Cities cities) {
        this.cityId = cities;
    }

    public StreetsOfCities cityId(Cities cities) {
        this.setCityId(cities);
        return this;
    }

}
