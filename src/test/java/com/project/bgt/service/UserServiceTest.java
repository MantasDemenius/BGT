package com.project.bgt.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import com.project.bgt.dto.UserDTO;
import com.project.bgt.dto.UserRoleDTO;
import com.project.bgt.exception.EmailAlreadyExistsException;
import com.project.bgt.exception.RecordNotFoundException;
import com.project.bgt.exception.UsernameAlreadyExistsException;
import com.project.bgt.model.Role;
import com.project.bgt.model.User;
import com.project.bgt.model.UserRoleName;
import com.project.bgt.repository.RoleRepository;
import com.project.bgt.repository.UserRepository;
import com.project.bgt.security.SecurityService;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

class UserServiceTest {

  @Mock
  private SecurityService mockSecurityService;
  @Mock
  private PasswordEncoder mockPasswordEncoder;
  @Mock
  private UserRepository userRepository;
  @Mock
  private RoleRepository mockRoleRepository;

  @InjectMocks
  private UserService userServiceUnderTest;

  @BeforeEach
  void setUp() {
    initMocks(this);
    userServiceUnderTest.setUserRepository(userRepository);
  }

  @Test
  void getUsers__SimpleUserList() {
    // Setup
    final List<UserDTO> expectedResult = List.of(new UserDTO(0L, "username", "email", "password"));
    final List<User> returnValue = List.of(new User("username", "email", "password"));
    when(userRepository.findAll()).thenReturn(returnValue);

    // Run the test
    final List<UserDTO> result = userServiceUnderTest.getUsers();

    // Verify the results
    assertThat(result).isEqualTo(expectedResult);
  }

  @Test
  void getUser_ExistingUserId_User() {
    // Setup
    final User expectedResult = new User("username", "email", "password");
    when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(expectedResult));

    // Run the test
    final User result = userServiceUnderTest.getUser(0L);

    // Verify the results
    assertThat(result).isEqualTo(expectedResult);
  }

  @Test
  void getUser_NonExistingUserId_RecordNotFoundException() {
    // Setup
    String expectedMessage = "User with id: 0 was not found!";

    // Run the test
    Exception exception = Assertions.assertThrows(RecordNotFoundException.class, () -> {
      userServiceUnderTest.getUser(0L);
    });

    // Verify the results
    String actualMessage = exception.getMessage();
    assertThat(actualMessage).isEqualTo(expectedMessage);
  }

  @Test
  void getUserDTO_ExistingUserId_UserDTO() {
    // Setup
    final UserDTO expectedResult = new UserDTO(0L, "username", "email", "password");
    when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(new User("username", "email", "password")));

    // Run the test
    final UserDTO result = userServiceUnderTest.getUserDTO(0L);

    // Verify the results
    assertThat(result).isEqualTo(expectedResult);
  }

  @Test
  void getUserDTO_ExistingUserId_RecordNotFoundException() {
    // Setup
    String expectedMessage = "User with id: 0 was not found!";

    // Run the test
    Exception exception = Assertions.assertThrows(RecordNotFoundException.class, () -> {
      userServiceUnderTest.getUserDTO(0L);
    });

    // Verify the results
    String actualMessage = exception.getMessage();
    assertThat(actualMessage).isEqualTo(expectedMessage);
  }

  @Test
  void updateUser_ExistingUser_StatusNoContent() {
    // Setup
    final UserDTO userDTO = new UserDTO(0L, "username", "email", "password");
    final User user = new User("username", "email", "password");
    ResponseEntity expectedResult = new ResponseEntity(HttpStatus.NO_CONTENT);

    when(userRepository.existsByUsernameIgnoreCase("username")).thenReturn(false);
    when(userRepository.existsByEmailIgnoreCase("email")).thenReturn(false);
    when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(user));
    when(userRepository.save(user)).thenReturn(user);
    when(mockPasswordEncoder.encode("rawPassword")).thenReturn("result");

   // Run the test
    final ResponseEntity result = userServiceUnderTest.updateUser(userDTO, 0L);

    // Verify the results
    assertThat(result).isEqualTo(expectedResult);
  }

  @Test
  void deleteUser_ExistingUser_StatusOk() {
    // Setup
    ResponseEntity expectedResult = new ResponseEntity(HttpStatus.OK);

    // Run the test
    final ResponseEntity result = userServiceUnderTest.deleteUser(1L);

    // Verify the results
    verify(userRepository).deleteById(1L);
    assertThat(result).isEqualTo(expectedResult);
  }

  @Test
  void deleteUser_NotExistingUser_RecordNotFoundException() {
    // Setup
    String expectedMessage = "User with id: 0 was not found!";
    // Run the test
    doThrow(new IllegalArgumentException()).when(userRepository).deleteById(0L);
    Exception exception = Assertions.assertThrows(RecordNotFoundException.class, () -> {
      userServiceUnderTest.deleteUser(0L);
    });

    // Verify the results
    String actualMessage = exception.getMessage();
    assertThat(actualMessage).isEqualTo(expectedMessage);
  }

  @Test
  void updateUserRole_BasicRole_StatusNoContent() {
    // Setup
    final UserRoleDTO userRoleDTO = new UserRoleDTO();
    userRoleDTO.setId(1L);
    userRoleDTO.setRole("BASIC");
    final List<UserRoleDTO> userRoleDTOS = List.of(userRoleDTO);
    final User user = new User("username", "email", "password");
    ResponseEntity expectedResult = new ResponseEntity(HttpStatus.NO_CONTENT);

    when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(user));
    when(userRepository.save(user)).thenReturn(user);

    // Run the test
    final ResponseEntity result = userServiceUnderTest.updateUserRole(userRoleDTOS, 1L);

    // Verify the results
    assertThat(result).isEqualTo(expectedResult);
  }

  @Test
  void userExistsByUsername_NonExistentUsername__() {
    // Setup
    when(userRepository.existsByUsernameIgnoreCase("username")).thenReturn(false);
    // Run the test
    userServiceUnderTest.userExistsByUsername("username");
    // Verify the results
  }

  @Test
  void userExistsByUsername_ExistentUsername_UsernameAlreadyExistsException() {
    // Setup
    String expectedMessage = "Username: username is already taken!";
    when(userRepository.existsByUsernameIgnoreCase("username")).thenReturn(true);

    // Run the test
    Exception exception = Assertions.assertThrows(UsernameAlreadyExistsException.class, () -> {
      userServiceUnderTest.userExistsByUsername("username");
    });

    // Verify the results
    String actualMessage = exception.getMessage();
    assertThat(actualMessage).isEqualTo(expectedMessage);
  }

  @Test
  void userExistsByEmail_NonExistentEmail__() {
    // Setup
    when(userRepository.existsByEmailIgnoreCase("email")).thenReturn(false);
    // Run the test
    userServiceUnderTest.userExistsByEmail("email");

    // Verify the results
  }

  @Test
  void userExistsByEmail_ExistentEmail_EmailAlreadyExistsException() {
    // Setup
    String expectedMessage = "Email: email is already taken!";
    when(userRepository.existsByEmailIgnoreCase("email")).thenReturn(true);

    // Run the test
    Exception exception = Assertions.assertThrows(EmailAlreadyExistsException.class, () -> {
      userServiceUnderTest.userExistsByEmail("email");
    });

    // Verify the results
    String actualMessage = exception.getMessage();
    assertThat(actualMessage).isEqualTo(expectedMessage);
  }

  @Test
  void createUser_NotExistingUser_StatusCreatedWithLocation(){
    // Setup
    HttpHeaders responseHeaders = new HttpHeaders();
    mockServletContext();
    URI location = ServletUriComponentsBuilder.
      fromCurrentContextPath().
      path("users/0")
      .build()
      .toUri();
    responseHeaders.setLocation(location);
    ResponseEntity expectedResult = new ResponseEntity(responseHeaders, HttpStatus.CREATED);

    final UserDTO userDTO = new UserDTO(0L, "username", "email", "password");
    final User user = new User("username", "email", "password");

    when(userRepository.existsByUsernameIgnoreCase("username")).thenReturn(false);
    when(userRepository.existsByEmailIgnoreCase("email")).thenReturn(false);
    when(userRepository.save(any(User.class))).thenReturn(user);
    when(mockPasswordEncoder.encode("password")).thenReturn("password");
    mockRoleRepositoryFindByName();

    // Run the test
    final ResponseEntity result = userServiceUnderTest.createUser(userDTO);

    // Verify the results
    assertThat(result).isEqualTo(expectedResult);
  }

  @Test
  void getUserRole_UserRoleNameBasic_RoleBasic() {
    // Setup
    final Role expectedResult = new Role(0L, "BASIC");
    mockRoleRepositoryFindByName();

    // Run the test
    final Role result = userServiceUnderTest.getUserRole(UserRoleName.BASIC);

    // Verify the results
    assertThat(result).isEqualTo(expectedResult);
  }

//  @Test
//  void getUserRole_UserRoleNameNotExisting_RecordNotFoundException() {
//    // Setup
//    when(mockRoleRepository.findByName(any(UserRoleName.class)))
//      .thenReturn(Optional.of(new Role()));
//    // Run the test
//    assertThatThrownBy(() -> userServiceUnderTest.getUserRole(UserRoleName.BASIC))
//      .isInstanceOf(RecordNotFoundException.class);
//    // Verify the results
//  }

  void mockRoleRepositoryFindByName(){
    when(mockRoleRepository.findByName(UserRoleName.BASIC))
      .thenReturn(Optional.of(new Role(0L, "BASIC")));
  }

  void mockServletContext() {
    MockHttpServletRequest request = new MockHttpServletRequest();
    RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
  }
}
