package com.project.bgt.dto;

public class CardDto extends ValueBase {

  long gameId;
  long originalCardId;

//  public CardDto(String title, String description, long languageId, long gameId) {
//    super(title, description, languageId);
//    this.gameId = gameId;
//  }

  public CardDto(String title, String description, long languageId, long gameId, long originalCardId) {
    super(title, description, languageId);
    this.gameId = gameId;
    this.originalCardId = originalCardId;
  }

  public long getGameId() {
    return gameId;
  }

  public void setGameId(long gameId) {
    this.gameId = gameId;
  }

  public long getOriginalCardId() {
    return originalCardId;
  }

  public void setOriginalCardId(long originalCardId) {
    this.originalCardId = originalCardId;
  }
}
