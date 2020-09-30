package com.project.bgt.common.check;

import com.project.bgt.common.message.ErrorMessages;
import com.project.bgt.dto.ComponentBase;
import com.project.bgt.exception.BadRequestException;

public class ComponentCheck {

  public static void checkComponents(ComponentBase component) {
    checkTitle(component);
    checkDescription(component);
    checkTitleLength(component);
    checkDescriptionLength(component);
  }

  public static void checkDescription(ComponentBase component) {
    try {
      if (component.getDescription().isEmpty()) {
        throw new BadRequestException(ErrorMessages.DESCRIPTION_NOT_EMPTY);
      }
    } catch (NullPointerException ex) {
      throw new BadRequestException(ErrorMessages.DESCRIPTION_REQUIRED);
    }
  }

  public static void checkTitle(ComponentBase component) {
    try {
      if (component.getTitle().isEmpty()) {
        throw new BadRequestException(ErrorMessages.TITLE_NOT_EMPTY);
      }
    } catch (NullPointerException ex) {
      throw new BadRequestException(ErrorMessages.TITLE_REQUIRED);
    }
  }

  public static void checkDescriptionLength(ComponentBase component) {
    if (component.getDescription().length() > 500) {
      throw new BadRequestException(ErrorMessages.DESCRIPTION_LONG);
    }
  }

  public static void checkTitleLength(ComponentBase component) {
    if (component.getTitle().length() > 500) {
      throw new BadRequestException(ErrorMessages.TITLE_LONG);
    }
  }
}
