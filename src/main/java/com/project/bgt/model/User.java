package com.project.bgt.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "user", schema = "public")
public class User {

  @Id
  @Column(name = "id", unique = true, nullable = false, updatable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(name = "username", length = 20)
  private String username;

  @Column(name = "email")
  private String email;

  @Column(name = "password")
  private String password;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "user_role",
  joinColumns = @JoinColumn(name = "user_id"),
  inverseJoinColumns = @JoinColumn(name = "role_id"))
  private Set<Role> roles = new HashSet<Role>();

  @OneToMany(mappedBy = "user")
  private List<Game> games = new ArrayList<Game>();

  @OneToMany(mappedBy = "user")
  private List<Component> components = new ArrayList<Component>();

  public User(String username, String email, String password) {
    this.username = username;
    this.email = email;
    this.password = password;
  }
}
