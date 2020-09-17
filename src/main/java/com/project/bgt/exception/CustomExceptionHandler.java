package com.project.bgt.exception;

import com.project.bgt.model.ApiError;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException.BadRequest;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class CustomExceptionHandler {

  @ExceptionHandler(Exception.class)
  public final ResponseEntity<ApiError> handleAllExceptions(Exception ex, WebRequest request){
    ApiError apiError = new ApiError();
    return new ResponseEntity<ApiError>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(RecordNotFoundException.class)
  public final ResponseEntity<ApiError> handleRecordNotFoundException(RecordNotFoundException ex, WebRequest request) {
    ApiError apiError = new ApiError();
    if(ex.getLocalizedMessage() != null){
      apiError.setMessage(ex.getLocalizedMessage());
    }
    return new ResponseEntity<ApiError>(apiError, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(BadRequestException.class)
  public final ResponseEntity<ApiError> handleBadRequestException(BadRequestException ex, WebRequest request) {
    ApiError apiError = new ApiError();
    if(ex.getLocalizedMessage() != null){
      apiError.setMessage(ex.getLocalizedMessage());
    }
    return new ResponseEntity<ApiError>(apiError, HttpStatus.BAD_REQUEST);
  }
}
