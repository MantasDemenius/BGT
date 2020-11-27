package com.project.bgt.security;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.MockitoAnnotations.initMocks;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.AuthenticationException;

class HttpAuthenticationEntryPointTest {

  @Mock
  private ObjectMapper mockObjectMapper;

  @InjectMocks
  private HttpAuthenticationEntryPoint httpAuthenticationEntryPointUnderTest;

  @BeforeEach
  void setUp() {
    initMocks(this);
  }

  @Test
  void testCommence() throws Exception {
    // Setup
    final HttpServletRequest request = new MockHttpServletRequest();
    final HttpServletResponse response = new MockHttpServletResponse();
    final AuthenticationException ex = null;

    // Run the test
    httpAuthenticationEntryPointUnderTest.commence(request, response, ex);

    // Verify the results
  }
}
