package com.project.bgt.exception;

import com.project.bgt.common.message.ErrorMessages;
import com.project.bgt.model.ApiError;
import java.net.URISyntaxException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

//https://www.toptal.com/java/spring-boot-rest-api-error-handling

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler({Exception.class, URISyntaxException.class})
  public final ResponseEntity<ApiError> handleAllExceptions(Exception ex, WebRequest request) {
    ApiError apiError = new ApiError();
    return new ResponseEntity<ApiError>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(RecordNotFoundException.class)
  public final ResponseEntity<ApiError> handleRecordNotFoundException(RecordNotFoundException ex,
    WebRequest request) {
    ApiError apiError = new ApiError();
    if (ex.getLocalizedMessage() != null) {
      apiError.setMessage(ex.getLocalizedMessage());
    }
    return new ResponseEntity<ApiError>(apiError, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(BadRequestException.class)
  public final ResponseEntity<ApiError> handleBadRequestException(BadRequestException ex,
    WebRequest request) {
    ApiError apiError = new ApiError();
    if (ex.getLocalizedMessage() != null) {
      apiError.setMessage(ex.getLocalizedMessage());
    }
    return new ResponseEntity<ApiError>(apiError, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public final ResponseEntity<ApiError> handleTypeMismatchException(
    MethodArgumentTypeMismatchException ex, WebRequest request) {
    ApiError apiError = new ApiError();
    apiError.setMessage(ErrorMessages.TYPE_INCORRECT);
    return new ResponseEntity<ApiError>(apiError, HttpStatus.BAD_REQUEST);
  }

  @Override
  protected ResponseEntity<Object> handleMissingServletRequestParameter(
    MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status,
    WebRequest request) {
    ApiError apiError = new ApiError();
    apiError.setMessage(ErrorMessages.REQUIRED_PARAMETER_NOT_PRESENT);
    return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(NullPointerException.class)
  public final ResponseEntity<ApiError> handleNullPointerException(
    NullPointerException ex, WebRequest request) {
    ApiError apiError = new ApiError();
    apiError.setMessage(ErrorMessages.PATH_VARIABLE_NOT_PROVIDED);
    return new ResponseEntity<ApiError>(apiError, HttpStatus.BAD_REQUEST);
  }
}
