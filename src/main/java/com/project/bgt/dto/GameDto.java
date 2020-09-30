package com.project.bgt.dto;

public class GameDto extends ComponentBase {

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
