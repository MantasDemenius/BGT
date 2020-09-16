package com.project.bgt.exception;

import com.project.bgt.dto.GameDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class GameException {

  public static void checkGame(GameDto game){
    checkTitle(game);
    checkDescription(game);
    checkTitleLength(game);
    checkDescriptionLength(game);
  }

  public static void checkDescription(GameDto game){
    try{
      if(game.getDescription().isEmpty()){
        throw new ResponseStatusException(
          HttpStatus.BAD_REQUEST , "description cannot be empty");
      }
    }catch(NullPointerException ex){
      throw new ResponseStatusException(
        HttpStatus.BAD_REQUEST, "description is required"
      );
    }
  }

  public static void checkTitle(GameDto game) {
    try{
    if(game.getTitle().isEmpty()){
      throw new ResponseStatusException(
        HttpStatus.BAD_REQUEST , "title cannot be empty");
    }
  }catch(NullPointerException ex){
    throw new ResponseStatusException(
      HttpStatus.BAD_REQUEST, "title is required"
    );
  }
  }

  public static void checkDescriptionLength(GameDto game) {
    if(game.getDescription().length() > 500){
      throw new ResponseStatusException(
        HttpStatus.BAD_REQUEST, "description is too long"
      );
    }
  }

  public static void checkTitleLength(GameDto game) {
    if(game.getTitle().length() > 500){
      throw new ResponseStatusException(
        HttpStatus.BAD_REQUEST, "title is too long"
      );
    }
  }
}
