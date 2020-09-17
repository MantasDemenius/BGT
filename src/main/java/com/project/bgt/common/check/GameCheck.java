package com.project.bgt.common.check;

import com.project.bgt.common.message.ErrorMessages;
import com.project.bgt.dto.GameDto;
import com.project.bgt.exception.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class GameCheck {

  public static void checkGame(GameDto game) {
    checkTitle(game);
    checkDescription(game);
    checkTitleLength(game);
    checkDescriptionLength(game);
  }

  public static void checkDescription(GameDto game) {
    try {
      if (game.getDescription().isEmpty()) {
        throw new BadRequestException(ErrorMessages.DESCRIPTION_NOT_EMPTY);
      }
    } catch (NullPointerException ex) {
      throw new BadRequestException(ErrorMessages.DESCRIPTION_REQUIRED);
    }
  }

  public static void checkTitle(GameDto game) {
    try {
      if (game.getTitle().isEmpty()) {
        throw new BadRequestException(ErrorMessages.TITLE_NOT_EMPTY);
      }
    } catch (NullPointerException ex) {
      throw new BadRequestException(ErrorMessages.TITLE_REQUIRED);
    }
  }

  public static void checkDescriptionLength(GameDto game) {
    if (game.getDescription().length() > 500) {
      throw new BadRequestException(ErrorMessages.DESCRIPTION_LONG);
    }
  }

  public static void checkTitleLength(GameDto game) {
    if (game.getTitle().length() > 500) {
      throw new BadRequestException(ErrorMessages.TITLE_LONG);
    }
  }
}
