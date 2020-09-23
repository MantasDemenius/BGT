package com.project.bgt.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

public class GameDto extends ValueBase{

  private long originalGameId;

  public GameDto(String title, String description, String languageCode) {
    super(title, description, languageCode);
  }

  public long getOriginalGameId() {
    return originalGameId;
  }

  public void setOriginalGameId(long originalGameId) {
    this.originalGameId = originalGameId;
  }
}
