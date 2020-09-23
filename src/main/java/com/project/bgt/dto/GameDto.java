package com.project.bgt.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

public class GameDto extends ValueBase{

  private long originalGameId;

  public GameDto(String title, String description, long languageId, long originalGameId) {
    super(title, description, languageId);
    this.originalGameId = originalGameId;
  }

  public long getOriginalGameId() {
    return originalGameId;
  }

  public void setOriginalGameId(long originalGameId) {
    this.originalGameId = originalGameId;
  }
}
