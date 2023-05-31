package com.bsu.app.domain;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Getter
@Setter
@Entity
@Table(name = "authority")
public class Authority {
  @Id
  @Size(max = 50)
  @Column(name = "name", nullable = false, length = 50)
  private String name;

  //TODO [JPA Buddy] generate columns from DB
}
