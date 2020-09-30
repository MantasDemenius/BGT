package com.project.bgt.dto;

public class CardDto extends ComponentBase {

  long gameId;
  long originalCardId;

  public CardDto(String title, String description, long languageId, long gameId, long originalCardId) {
    super(title, description, languageId);
    this.gameId = gameId;
    this.originalCardId = originalCardId;
  }

  public boolean languageIdEquals(long languageId){
    return this.languageId == languageId;
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
