package com.project.bgt.exception;

import com.project.bgt.model.Language;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

public class LanguageException {

  public static void checkLanguage(Language language) {
    if(language == null){
      throw new ResponseStatusException(
        HttpStatus.BAD_REQUEST, "There is no such language"
      );
    }
  }

  public static void checkIfLanguageProvided(String languageCode) {
    if(languageCode == null){
      throw new ResponseStatusException(
        HttpStatus.BAD_REQUEST, "language is required"
      );
    }
  }
}
