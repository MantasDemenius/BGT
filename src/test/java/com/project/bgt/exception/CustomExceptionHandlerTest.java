package com.project.bgt.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.context.request.WebRequest;

class CustomExceptionHandlerTest {

  private CustomExceptionHandler customExceptionHandlerUnderTest;

  @BeforeEach
  void setUp() {
    customExceptionHandlerUnderTest = new CustomExceptionHandler();
  }

  @Test
  void testHandleAllExceptions() {
    // Setup
    final Exception ex = new Exception("message");
    final WebRequest request = null;

    // Run the test
    final ResponseEntity<Object> result = customExceptionHandlerUnderTest
      .handleAllExceptions(ex, request);

    // Verify the results
  }

  @Test
  void testHandleUsernameAlreadyExistsException() {
    // Setup
    final UsernameAlreadyExistsException ex = new UsernameAlreadyExistsException("exception");
    final WebRequest request = null;

    // Run the test
    final ResponseEntity<Object> result = customExceptionHandlerUnderTest
      .handleUsernameAlreadyExistsException(ex, request);

    // Verify the results
  }

  @Test
  void testHandleEmailAlreadyExistsException() {
    // Setup
    final EmailAlreadyExistsException ex = new EmailAlreadyExistsException("exception");
    final WebRequest request = null;

    // Run the test
    final ResponseEntity<Object> result = customExceptionHandlerUnderTest
      .handleEmailAlreadyExistsException(ex, request);

    // Verify the results
  }

  @Test
  void testHandleBadCredentialsException() {
    // Setup
    final BadCredentialsException ex = new BadCredentialsException("msg");
    final WebRequest request = null;

    // Run the test
    final ResponseEntity<Object> result = customExceptionHandlerUnderTest
      .handleBadCredentialsException(ex, request);

    // Verify the results
  }

  @Test
  void testHandleRecordNotFoundException() {
    // Setup
    final RecordNotFoundException ex = new RecordNotFoundException("exception");
    final WebRequest request = null;

    // Run the test
    final ResponseEntity<Object> result = customExceptionHandlerUnderTest
      .handleRecordNotFoundException(ex, request);

    // Verify the results
  }

  @Test
  void testHandleAccessDeniedException() {
    // Setup
    final AccessDeniedException ex = new AccessDeniedException("msg");
    final WebRequest request = null;

    // Run the test
    final ResponseEntity<Object> result = customExceptionHandlerUnderTest
      .handleAccessDeniedException(ex, request);

    // Verify the results
  }
}
