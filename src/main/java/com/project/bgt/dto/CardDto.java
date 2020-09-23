package com.project.bgt.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

public class CardDto extends ValueBase {

  long gameId;
  long originalCardId;

  public CardDto(String title, String description, String languageCode, long gameId) {
    super(title, description, languageCode);
    this.gameId = gameId;
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
