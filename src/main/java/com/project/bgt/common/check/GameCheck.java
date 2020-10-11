package com.project.bgt.common.check;

import com.project.bgt.common.message.ErrorMessages;
import com.project.bgt.dto.GameDTO;
import com.project.bgt.exception.BadRequestException;

public class GameCheck {

  public static void checkGame(GameDTO game) {
    checkTitle(game);
    checkDescription(game);
    checkTitleLength(game);
    checkDescriptionLength(game);
  }

  public static void checkDescription(GameDTO game) {
    try {
      if (game.getDescription().isEmpty()) {
        throw new BadRequestException(ErrorMessages.DESCRIPTION_NOT_EMPTY);
      }
    } catch (NullPointerException ex) {
      throw new BadRequestException(ErrorMessages.DESCRIPTION_REQUIRED);
    }
  }

  public static void checkTitle(GameDTO game) {
    try {
      if (game.getTitle().isEmpty()) {
        throw new BadRequestException(ErrorMessages.TITLE_NOT_EMPTY);
      }
    } catch (NullPointerException ex) {
      throw new BadRequestException(ErrorMessages.TITLE_REQUIRED);
    }
  }

  public static void checkDescriptionLength(GameDTO game) {
    if (game.getDescription().length() > 500) {
      throw new BadRequestException(ErrorMessages.DESCRIPTION_LONG);
    }
  }

  public static void checkTitleLength(GameDTO game) {
    if (game.getTitle().length() > 500) {
      throw new BadRequestException(ErrorMessages.TITLE_LONG);
    }
  }
}
