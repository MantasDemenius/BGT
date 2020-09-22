package com.project.bgt.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

public class CardDto extends ValueBase{

  public CardDto(String title, String description, String languageCode) {
    super(title, description, languageCode);
  }
}
