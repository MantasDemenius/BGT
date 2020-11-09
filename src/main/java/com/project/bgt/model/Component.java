package com.project.bgt.model;

import com.project.bgt.common.PostgreSQLEnumType;
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
@Table(name = "component", schema = "public")
public class Component {

  @ManyToMany(mappedBy = "originalComponents")
  List<Component> translatedComponents = new ArrayList<Component>();

  @ManyToMany
  @JoinTable(
    name = "component_relationship",
    joinColumns = @JoinColumn(name = "translated_component_id"),
    inverseJoinColumns = @JoinColumn(name = "original_component_id"))
  List<Component> originalComponents = new ArrayList<Component>();

  @ManyToMany
  @JoinTable(
    name = "game_component",
    joinColumns = @JoinColumn(name = "component_id"),
    inverseJoinColumns = @JoinColumn(name = "game_id"))
  List<Game> componentsGame = new ArrayList<Game>();

  @Id
  @Column(name = "id", unique = true, nullable = false, updatable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(name = "title", length = 50)
  private String title;

  @Column(name = "description")
  private String description;

  @Column(name = "category", columnDefinition = "component_category")
  @Enumerated(EnumType.STRING)
  @Type(type = "pgsql_enum")
  private ComponentCategory category;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "language_id")
  private Language language;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  public Component(Language language, User user, Game game, String title, String description,
    ComponentCategory category) {
    this.language = language;
    this.user = user;
    this.componentsGame.add(game);
    this.title = title;
    this.description = description;
    this.category = category;
  }

//  public Component(Language language, User user, Game game, String title, String description) {
//    this.language = language;
//    this.user = user;
//    this.componentsGame.add(game);
//    this.title = title;
//    this.description = description;
//  }
}
