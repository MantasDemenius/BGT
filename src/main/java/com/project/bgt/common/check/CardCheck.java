package com.project.bgt.common.check;

import com.project.bgt.common.message.ErrorMessages;
import com.project.bgt.dto.CardDto;
import com.project.bgt.dto.ValueBase;
import com.project.bgt.exception.BadRequestException;

public class CardCheck {

  public static void checkCard(ValueBase value) {
    checkTitle(value);
    checkDescription(value);
    checkTitleLength(value);
    checkDescriptionLength(value);
  }

  public static void checkDescription(ValueBase value) {
    try {
      if (value.getDescription().isEmpty()) {
        throw new BadRequestException(ErrorMessages.DESCRIPTION_NOT_EMPTY);
      }
    } catch (NullPointerException ex) {
      throw new BadRequestException(ErrorMessages.DESCRIPTION_REQUIRED);
    }
  }

  public static void checkTitle(ValueBase value) {
    try {
      if (value.getTitle().isEmpty()) {
        throw new BadRequestException(ErrorMessages.TITLE_NOT_EMPTY);
      }
    } catch (NullPointerException ex) {
      throw new BadRequestException(ErrorMessages.TITLE_REQUIRED);
    }
  }

  public static void checkDescriptionLength(ValueBase value) {
    if (value.getDescription().length() > 500) {
      throw new BadRequestException(ErrorMessages.DESCRIPTION_LONG);
    }
  }

  public static void checkTitleLength(ValueBase value) {
    if (value.getTitle().length() > 500) {
      throw new BadRequestException(ErrorMessages.TITLE_LONG);
    }
  }
}
