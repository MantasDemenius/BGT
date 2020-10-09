package com.project.bgt.dto;

import java.util.List;

public class GameComponentDto {

  private GameDto game;
  private List<ComponentDto> cards;

  public GameDto getGame() {
    return game;
  }

  public void setGame(GameDto game) {
    this.game = game;
  }

  public List<ComponentDto> getCards() {
    return cards;
  }

  public void setCards(List<ComponentDto> cards) {
    this.cards = cards;
  }
}
