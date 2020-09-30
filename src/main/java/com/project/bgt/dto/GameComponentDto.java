package com.project.bgt.dto;

import com.project.bgt.model.Card;
import java.util.List;

public class GameComponentDto {

  private GameDto game;
  private List<CardDto> cards;

  public GameDto getGame() {
    return game;
  }

  public void setGame(GameDto game) {
    this.game = game;
  }

  public List<CardDto> getCards() {
    return cards;
  }

  public void setCards(List<CardDto> cards) {
    this.cards = cards;
  }
}
