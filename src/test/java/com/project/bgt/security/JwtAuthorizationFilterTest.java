package com.project.bgt.security;

import static org.mockito.MockitoAnnotations.initMocks;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;

class JwtAuthorizationFilterTest {

  @Mock
  private SecurityService mockSecurityService;
  @Mock
  private TokenProvider mockTokenProvider;

  @InjectMocks
  private JwtAuthorizationFilter jwtAuthorizationFilterUnderTest;

  @BeforeEach
  void setUp() {
    initMocks(this);
  }
}
