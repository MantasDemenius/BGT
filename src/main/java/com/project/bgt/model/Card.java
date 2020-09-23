package com.project.bgt.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.List;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "card", schema = "public")
public class Card {

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

  @ManyToMany(mappedBy = "originalCards")
  @JsonIgnore
  List<Card> translatedCards = new ArrayList<Card>();

  @ManyToMany
  @JoinTable(
    name = "card_relationship",
    joinColumns = @JoinColumn(name = "translated_card_id"),
    inverseJoinColumns = @JoinColumn(name = "original_card_id"))
  @JsonIgnore
  List<Card> originalCards = new ArrayList<Card>();

  @ManyToMany
  @JoinTable(
    name = "game_card",
    joinColumns = @JoinColumn(name = "card_id"),
    inverseJoinColumns = @JoinColumn(name = "game_id"))
  @JsonIgnore
  List<Game> cardsGame = new ArrayList<Game>();

  public Card(){}

  public Card(String title, String description, Language language) {
    this.title = title;
    this.description = description;
    this.language = language;
  }
}
