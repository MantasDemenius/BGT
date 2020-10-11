package com.project.bgt.model;

import com.project.bgt.common.PostgreSQLEnumType;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

@Data
@Entity
@NoArgsConstructor
@TypeDef(
  name = "pgsql_enum",
  typeClass = PostgreSQLEnumType.class
)
@Table(name = "user", schema = "public")
public class User {

  @Id
  @Column(name = "id", unique = true, nullable = false, updatable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(name = "username", length = 50)
  private String username;

  @Column(name = "email")
  private String email;

  @Column(name = "password")
  private String password;

  @Column(name = "role", columnDefinition = "user_role")
  @Enumerated(EnumType.STRING)
  @Type(type = "pgsql_enum")
  private UserRole role;

  @OneToMany(mappedBy = "user")
  private List<Game> games = new ArrayList<Game>();

  @OneToMany(mappedBy = "user")
  private List<Component> components = new ArrayList<Component>();

  public User(String username, String email, String password, UserRole role) {
    this.username = username;
    this.email = email;
    this.password = password;
    this.role = role;
  }
}
