package com.project.bgt.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "component", schema = "public")
public class Component {

  @Id
  @Column(name = "id", unique = true, nullable = false, updatable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(name = "title",  length = 50)
  private String title;

  @Column(name = "description")
  private String description;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "language_id")
  @JsonIgnore
  private Language language;

  @ManyToMany(mappedBy = "originalComponents")
  @JsonIgnore
  List<Component> translatedComponents = new ArrayList<Component>();

  @ManyToMany
  @JoinTable(
    name = "component_relationship",
    joinColumns = @JoinColumn(name = "translated_component_id"),
    inverseJoinColumns = @JoinColumn(name = "original_component_id"))
  @JsonIgnore
  List<Component> originalComponents = new ArrayList<Component>();

  @ManyToMany
  @JoinTable(
    name = "game_component",
    joinColumns = @JoinColumn(name = "component_id"),
    inverseJoinColumns = @JoinColumn(name = "game_id"))
  @JsonIgnore
  List<Game> componentsGame = new ArrayList<Game>();

  public Component(){}

  public Component(String title, String description, Language language) {
    this.title = title;
    this.description = description;
    this.language = language;
  }
}
