package com.project.bgt.security;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import com.project.bgt.model.User;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

class PrincipalTest {

  @Mock
  private User mockUser;

  private Principal principalUnderTest;

  @BeforeEach
  void setUp() {
    initMocks(this);
    principalUnderTest = new Principal(mockUser, List.of());
  }

  @Test
  void testGetUsername() {
    // Setup
    when(mockUser.getUsername()).thenReturn("result");

    // Run the test
    final String result = principalUnderTest.getUsername();

    // Verify the results
    assertThat(result).isEqualTo("result");
  }

  @Test
  void testGetPassword() {
    // Setup
    when(mockUser.getPassword()).thenReturn("result");

    // Run the test
    final String result = principalUnderTest.getPassword();

    // Verify the results
    assertThat(result).isEqualTo("result");
  }

  @Test
  void testIsEnabled() {
    // Setup

    // Run the test
    final boolean result = principalUnderTest.isEnabled();

    // Verify the results
    assertThat(result).isTrue();
  }

  @Test
  void testIsAccountNonExpired() {
    // Setup

    // Run the test
    final boolean result = principalUnderTest.isAccountNonExpired();

    // Verify the results
    assertThat(result).isTrue();
  }

  @Test
  void testIsAccountNonLocked() {
    // Setup

    // Run the test
    final boolean result = principalUnderTest.isAccountNonLocked();

    // Verify the results
    assertThat(result).isTrue();
  }

  @Test
  void testIsCredentialsNonExpired() {
    // Setup

    // Run the test
    final boolean result = principalUnderTest.isCredentialsNonExpired();

    // Verify the results
    assertThat(result).isTrue();
  }
}
