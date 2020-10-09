package com.project.bgt.dto;

public class ComponentDto extends ComponentBase {

  long userId;
  long gameId;
  long originalComponentId;

  public ComponentDto(String title, String description, long languageId, long gameId, long originalComponentId, long userId) {
    super(title, description, languageId);
    this.gameId = gameId;
    this.originalComponentId = originalComponentId;
    this.userId = userId;
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

  public long getOriginalComponentId() {
    return originalComponentId;
  }

  public void setOriginalComponentId(long originalComponentId) {
    this.originalComponentId = originalComponentId;
  }
}
