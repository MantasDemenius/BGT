package com.project.bgt.model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "language", schema = "public")
public class Language {

  @Id
  @Column(name = "id", unique = true, nullable = false, updatable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(name = "name", unique = true, nullable = false)
  private String name;

  @Column(name = "code", unique = true, nullable = false)
  private String code;

  @OneToMany(mappedBy = "language")
  private List<Component> components = new ArrayList<Component>();

  @OneToMany(mappedBy = "language")
  private List<Game> games = new ArrayList<Game>();

  public Language(){}

  public Language(String name, String code) {
    this.name = name;
    this.code = code;
  }
}
