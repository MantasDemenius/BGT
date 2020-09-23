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
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Entity
@AllArgsConstructor
@Table(name = "game", schema = "bgtmin")
public class Game {

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

  @ManyToMany(mappedBy = "originalGames")
  @JsonIgnore
  List<Game> translatedGames = new ArrayList<Game>();

  @ManyToMany
  @JoinTable(
    name = "game_relationship",
    joinColumns = @JoinColumn(name = "translated_game_id"),
    inverseJoinColumns = @JoinColumn(name = "original_game_id"))
  @JsonIgnore
  List<Game> originalGames = new ArrayList<Game>();

  @ManyToMany(mappedBy = "cardsGame")
  @JsonIgnore
  List<Card> gameCards = new ArrayList<Card>();

  public Game(){}

  public Game(String title, String description, Language language) {
    this.title = title;
    this.description = description;
    this.language = language;
  }
}
