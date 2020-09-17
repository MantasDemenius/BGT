package com.project.bgt.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "card_translation")
public class CardTranslation {

  @Id
  @Column(name = "id", unique = true, nullable = false, updatable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(name = "title",  length = 50)
  private String title;

  @Column(name = "description")
  private String description;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "card_id")
  @JsonIgnore
  private Card card;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "language_id")
  @JsonIgnore
  private Language language;

  public CardTranslation(){}

  public CardTranslation(String title, String description, Language language) {
    this.title = title;
    this.description = description;
    this.language = language;
  }
}
