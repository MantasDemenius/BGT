package com.project.bgt.model;

import com.sun.istack.NotNull;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "card")
public class Card {

  @Id
  @Column(name = "id", unique = true, nullable = false, updatable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(name = "title",  length = 50)
  private String title;

  @Column(name = "description")
  private String description;

  public Card(){}

  public Card(String title, String description) {
    this.title = title;
    this.description = description;
  }
}
