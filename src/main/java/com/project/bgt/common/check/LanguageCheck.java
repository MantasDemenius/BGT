package com.project.bgt.common.check;

import com.project.bgt.common.message.ErrorMessages;
import com.project.bgt.exception.BadRequestException;
import com.project.bgt.model.Language;

public class LanguageCheck {

  public static void checkLanguage(Language language) {
    if (language == null) {
      throw new BadRequestException(ErrorMessages.LANGUAGE_NO_CODE);
    }
  }

  public static void checkIfLanguageProvided(String languageCode) {
    if (languageCode == null) {
      throw new BadRequestException(ErrorMessages.LANGUAGE_REQUIRED);
    }
  }
}
