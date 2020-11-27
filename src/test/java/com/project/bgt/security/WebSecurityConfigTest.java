package com.project.bgt.security;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.MockitoAnnotations.initMocks;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

class WebSecurityConfigTest {

  @Mock
  private HttpAuthenticationEntryPoint mockHttpAuthenticationEntryPoint;
  @Mock
  private UserDetailsService mockUserDetailsService;

  @InjectMocks
  private WebSecurityConfig webSecurityConfigUnderTest;

  @BeforeEach
  void setUp() {
    initMocks(this);
  }

  @Test
  void testPasswordEncoder() {
    // Setup

    // Run the test
    final PasswordEncoder result = webSecurityConfigUnderTest.passwordEncoder();

    // Verify the results
  }

  @Test
  void testAuthenticationManagerBean_ThrowsException() {
    // Setup

    // Run the test
    assertThatThrownBy(() -> webSecurityConfigUnderTest.authenticationManagerBean()).isInstanceOf(
      Exception.class);
  }

  @Test
  void testAuthenticationTokenFilterBean() {
    // Setup

    // Run the test
    final JwtAuthorizationFilter result = webSecurityConfigUnderTest
      .authenticationTokenFilterBean();

    // Verify the results
  }
}
