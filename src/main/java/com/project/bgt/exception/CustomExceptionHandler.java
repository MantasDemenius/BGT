package com.project.bgt.exception;

import com.project.bgt.model.ApiError;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

//https://www.toptal.com/java/spring-boot-rest-api-error-handling

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(UsernameAlreadyExistsException.class)
  public final ResponseEntity<Object> handleUsernameAlreadyExistsException(UsernameAlreadyExistsException ex,
    WebRequest request) {
    List<String> errors = new ArrayList<String>();

    errors.add(ex.getMessage());

    ApiError err = new ApiError(
      LocalDateTime.now(),
      HttpStatus.BAD_REQUEST,
      errors
    );

    return ResponseEntityBuilder.build(err);
  }

  @ExceptionHandler(EmailAlreadyExistsException.class)
  public final ResponseEntity<Object> handleEmailAlreadyExistsException(EmailAlreadyExistsException ex,
    WebRequest request) {
    List<String> errors = new ArrayList<String>();

    errors.add(ex.getMessage());

    ApiError err = new ApiError(
      LocalDateTime.now(),
      HttpStatus.BAD_REQUEST,
      errors
    );

    return ResponseEntityBuilder.build(err);
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
    MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status,
    WebRequest request) {

    StringBuilder errorMessage = new StringBuilder();
    List<String> errors = new ArrayList<String>();

    for (ObjectError err : ex.getBindingResult().getAllErrors()) {
      errors.add(((FieldError) err).getField() + " " + err.getDefaultMessage());
    }

    ApiError err = new ApiError(
      LocalDateTime.now(),
      HttpStatus.BAD_REQUEST,
      errors
    );

    return ResponseEntityBuilder.build(err);
  }


  @ExceptionHandler(BadCredentialsException.class)
  public final ResponseEntity<Object> handleBadCredentialsException(BadCredentialsException ex,
    WebRequest request) {
    List<String> errors = new ArrayList<String>();

    errors.add("Username or Password was incorrect");

    ApiError err = new ApiError(
      LocalDateTime.now(),
      HttpStatus.UNAUTHORIZED,
      errors
    );

    return ResponseEntityBuilder.build(err);
  }


  @ExceptionHandler(RecordNotFoundException.class)
  public final ResponseEntity<Object> handleRecordNotFoundException(RecordNotFoundException ex,
    WebRequest request) {
    List<String> errors = new ArrayList<String>();
    errors.add(ex.getMessage());

    ApiError err = new ApiError(
      LocalDateTime.now(),
      HttpStatus.NOT_FOUND,
      errors
    );

    return ResponseEntityBuilder.build(err);
  }

  @ExceptionHandler(AccessDeniedException.class)
  public final ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException ex,
    WebRequest request) {
    List<String> errors = new ArrayList<String>();
    errors.add(ex.getMessage());

    ApiError err = new ApiError(
      LocalDateTime.now(),
      HttpStatus.FORBIDDEN,
      errors
    );
    return ResponseEntityBuilder.build(err);
  }

  @ExceptionHandler(UsernameNotFoundException.class)
  public final ResponseEntity<Object> handleUsernameNotFoundException(UsernameNotFoundException ex,
    WebRequest request) {
    List<String> errors = new ArrayList<String>();
    errors.add(ex.getMessage());

    ApiError err = new ApiError(
      LocalDateTime.now(),
      HttpStatus.NOT_FOUND,
      errors
    );
    return ResponseEntityBuilder.build(err);
  }

  @ExceptionHandler({Exception.class, URISyntaxException.class})
  public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
    List<String> errors = new ArrayList<String>();
    errors.add(ex.getMessage());

    ApiError err = new ApiError(
      LocalDateTime.now(),
      HttpStatus.INTERNAL_SERVER_ERROR,
      errors
    );

    return ResponseEntityBuilder.build(err);
  }

//
//  @ExceptionHandler(BadRequestException.class)
//  public final ResponseEntity<ApiError> handleBadRequestException(BadRequestException ex,
//    WebRequest request) {
//    ApiError apiError = new ApiError();
//    if (ex.getLocalizedMessage() != null) {
//      apiError.setMessage(ex.getLocalizedMessage());
//    }
//    return new ResponseEntity<ApiError>(apiError, HttpStatus.BAD_REQUEST);
//  }
//
//  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
//  public final ResponseEntity<ApiError> handleTypeMismatchException(
//    MethodArgumentTypeMismatchException ex, WebRequest request) {
//    ApiError apiError = new ApiError();
//    apiError.setMessage(ErrorMessages.TYPE_INCORRECT);
//    return new ResponseEntity<ApiError>(apiError, HttpStatus.BAD_REQUEST);
//  }
//
//  @Override
//  protected ResponseEntity<Object> handleMissingServletRequestParameter(
//    MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status,
//    WebRequest request) {
//    ApiError apiError = new ApiError();
//    apiError.setMessage(ErrorMessages.REQUIRED_PARAMETER_NOT_PRESENT);
//    return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
//  }
//
//  @ExceptionHandler(NullPointerException.class)
//  public final ResponseEntity<ApiError> handleNullPointerException(
//    NullPointerException ex, WebRequest request) {
//    ApiError apiError = new ApiError();
//    apiError.setMessage(ErrorMessages.PATH_VARIABLE_NOT_PROVIDED);
//    return new ResponseEntity<ApiError>(apiError, HttpStatus.BAD_REQUEST);
//  }
}
