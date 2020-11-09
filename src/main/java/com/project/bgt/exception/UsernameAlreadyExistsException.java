package com.project.bgt.exception;

import com.project.bgt.common.message.ErrorMessages;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UsernameAlreadyExistsException extends RuntimeException{
  public UsernameAlreadyExistsException(String exception){
    super(exception);
  }

  public UsernameAlreadyExistsException(){
    super(ErrorMessages.WORKING_ON_IT);
  }
}

