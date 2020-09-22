package com.project.bgt.dto;

import com.fasterxml.jackson.annotation.JsonFormat.Value;
import lombok.AllArgsConstructor;
import lombok.Data;

public class CardTranslationDto extends ValueBase {

  long cardId;

  public CardTranslationDto(String title, String description, String languageCode) {
    super(title, description, languageCode);
  }

  public long getCardId() {
    return cardId;
  }

  public void setCardId(long cardId) {
    this.cardId = cardId;
  }
}
