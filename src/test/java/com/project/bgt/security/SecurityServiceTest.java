package com.project.bgt.security;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import com.project.bgt.dto.UserDTO;
import com.project.bgt.model.Role;
import com.project.bgt.model.User;
import com.project.bgt.model.UserRoleName;
import com.project.bgt.repository.RoleRepository;
import com.project.bgt.repository.UserRepository;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

class SecurityServiceTest {

  @Mock
  private PasswordEncoder mockPasswordEncoder;
  @Mock
  private UserRepository mockUserRepository;
  @Mock
  private RoleRepository mockRoleRepository;

  @InjectMocks
  private SecurityService securityServiceUnderTest;

  @BeforeEach
  void setUp() {
    initMocks(this);
  }

//  @Test
//  void testLoadUserByUsername() {
//    // Setup
//    final User user = new User("username", "email", "password");
//    Collection<? extends GrantedAuthority> authorities = new HashSet<>();
//    final Principal expectedResult = new Principal(user, authorities);
//    when(mockUserRepository.findByUsername("username")).thenReturn(user);
//
//    // Run the test
//    final UserDetails result = securityServiceUnderTest.loadUserByUsername("username");
//
//    // Verify the results
//    assertThat(result).isEqualTo(expectedResult);
//  }

  @Test
  void testLoadUserByUsername_ThrowsUsernameNotFoundException() {
    // Setup

    // Run the test
    assertThatThrownBy(() -> securityServiceUnderTest.loadUserByUsername("username")).isInstanceOf(
      UsernameNotFoundException.class);
  }

//  @Test
//  void testGetCurrentUser() {
//    // Setup
//    final User expectedResult = new User("username", "email", "password");
//
//    // Configure UserRepository.findByUsername(...).
//    final User user = new User("username", "email", "password");
//    when(mockUserRepository.findByUsername("username")).thenReturn(user);
//
//    // Run the test
//    final User result = securityServiceUnderTest.getCurrentUser();
//
//    // Verify the results
//    assertThat(result).isEqualTo(expectedResult);
//  }
}
