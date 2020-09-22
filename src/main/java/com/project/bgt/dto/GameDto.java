package com.project.bgt.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

public class GameDto extends ValueBase{

  public GameDto(String title, String description, String languageCode) {
    super(title, description, languageCode);
  }
}
