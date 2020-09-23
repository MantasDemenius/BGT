package com.project.bgt.common.check;

import com.project.bgt.common.message.ErrorMessages;
import com.project.bgt.dto.GameDto;
import com.project.bgt.dto.LanguageDto;
import com.project.bgt.dto.ValueBase;
import com.project.bgt.exception.BadRequestException;
import com.project.bgt.model.Language;

public class LanguageCheck {

  public static void checkLanguage(LanguageDto languageDto) {
    checkName(languageDto);
    checkCode(languageDto);
    checkNameLength(languageDto);
    checkCodeLength(languageDto);
  }

  public static void checkName(LanguageDto languageDto) {
    try {
      if (languageDto.getName().isEmpty()) {
        throw new BadRequestException(ErrorMessages.NAME_NOT_EMPTY);
      }
    } catch (NullPointerException ex) {
      throw new BadRequestException(ErrorMessages.NAME_REQUIRED);
    }
  }

  public static void checkCode(LanguageDto languageDto) {
    try {
      if (languageDto.getCode().isEmpty()) {
        throw new BadRequestException(ErrorMessages.CODE_NOT_EMPTY);
      }
    } catch (NullPointerException ex) {
      throw new BadRequestException(ErrorMessages.CODE_REQUIRED);
    }
  }

  public static void checkNameLength(LanguageDto languageDto) {
    if (languageDto.getName().length() > 20) {
      throw new BadRequestException(ErrorMessages.NAME_LONG);
    }
  }

  public static void checkCodeLength(LanguageDto languageDto) {
    if (languageDto.getCode().length() > 5) {
      throw new BadRequestException(ErrorMessages.CODE_LONG);
    }
  }
}
