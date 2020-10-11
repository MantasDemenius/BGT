package com.project.bgt.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "user", schema = "public")
public class User {

  @Id
  @Column(name = "id", unique = true, nullable = false, updatable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(name = "username",  length = 50)
  private String username;

  @Column(name = "email")
  private String email;

  @Column(name = "password")
  private String password;

  @Enumerated(EnumType.STRING)
  private UserRole role;

  @OneToMany(mappedBy = "user")
  private List<Game> games = new ArrayList<Game>();

  @OneToMany(mappedBy = "user")
  private List<Component> components = new ArrayList<Component>();

}
