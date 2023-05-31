package com.bsu.app.domain;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "\"user\"")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @Size(max = 50)
  @NotNull
  @Column(name = "login", nullable = false, length = 50)
  private String login;

  @Size(max = 60)
  @NotNull
  @Column(name = "password_hash", nullable = false, length = 60)
  private String password;

  @Size(max = 50)
  @Column(name = "first_name", length = 50)
  private String firstName;

  @Size(max = 50)
  @Column(name = "last_name", length = 50)
  private String lastName;

  @Size(max = 191)
  @Column(name = "email", length = 191)
  private String email;

  @Size(max = 256)
  @Column(name = "image_url", length = 256)
  private String imageUrl;

  @NotNull
  @Column(name = "activated", nullable = false)
  private boolean activated = false;

  @Size(max = 10)
  @Column(name = "lang_key", length = 10)
  private String langKey;

  @Size(max = 20)
  @Column(name = "activation_key", length = 20)
  private String activationKey;

  @Size(max = 20)
  @Column(name = "reset_key", length = 20)
  private String resetKey;

  @Size(max = 50)
  @Column(name = "created_by")
  private String createdBy;

  @Column(name = "created_date")
  private Instant createdDate;

  @Column(name = "reset_date")
  private Instant resetDate;

  @Size(max = 50)
  @Column(name = "last_modified_by", length = 50)
  private String lastModifiedBy;

  @Column(name = "last_modified_date")
  private Instant lastModifiedDate;

  @ManyToMany
  @JoinTable(name = "user_authority",
    joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "authority_name"))
  private Set<Authority> authorities = new LinkedHashSet<>();

  @OneToMany(mappedBy = "user")
  private Set<PersistentToken> persistentTokens = new LinkedHashSet<>();

}
