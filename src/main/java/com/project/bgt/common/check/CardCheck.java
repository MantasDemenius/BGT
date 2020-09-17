package com.project.bgt.common.check;

import com.project.bgt.common.message.ErrorMessages;
import com.project.bgt.dto.CardDto;
import com.project.bgt.exception.BadRequestException;

public class CardCheck {

  public static void checkCard(CardDto card) {
    checkTitle(card);
    checkDescription(card);
    checkTitleLength(card);
    checkDescriptionLength(card);
  }

  public static void checkDescription(CardDto card) {
    try {
      if (card.getDescription().isEmpty()) {
        throw new BadRequestException(ErrorMessages.DESCRIPTION_NOT_EMPTY);
      }
    } catch (NullPointerException ex) {
      throw new BadRequestException(ErrorMessages.DESCRIPTION_REQUIRED);
    }
  }

  public static void checkTitle(CardDto card) {
    try {
      if (card.getTitle().isEmpty()) {
        throw new BadRequestException(ErrorMessages.TITLE_NOT_EMPTY);
      }
    } catch (NullPointerException ex) {
      throw new BadRequestException(ErrorMessages.TITLE_REQUIRED);
    }
  }

  public static void checkDescriptionLength(CardDto card) {
    if (card.getDescription().length() > 500) {
      throw new BadRequestException(ErrorMessages.DESCRIPTION_LONG);
    }
  }

  public static void checkTitleLength(CardDto card) {
    if (card.getTitle().length() > 500) {
      throw new BadRequestException(ErrorMessages.TITLE_LONG);
    }
  }
}
