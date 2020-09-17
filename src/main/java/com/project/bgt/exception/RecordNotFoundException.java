package com.project.bgt.exception;

import com.project.bgt.common.message.ErrorMessages;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class RecordNotFoundException extends RuntimeException {

  public RecordNotFoundException(String exception){
    super(exception);
  }

  public RecordNotFoundException(){
    super(ErrorMessages.WORKING_ON_IT);
  }
}
