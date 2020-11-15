package com.project.bgt.common.serviceHelper;

import static org.assertj.core.api.Assertions.assertThat;

import com.project.bgt.dto.UserDTO;
import com.project.bgt.model.User;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserServiceHelperTest {

  private UserServiceHelper userServiceHelperUnderTest;

  @BeforeEach
  void setUp() {
    userServiceHelperUnderTest = new UserServiceHelper();
  }

  @Test
  void convertUsersToUserDTOs_SimpleUserList_ConvertedListToDTO() {
    // Setup
    final List<User> users = List.of(new User("username", "email", "password"));
    final List<UserDTO> expectedResult = List.of(new UserDTO(0L, "username", "email", "password"));

    // Run the test
    final List<UserDTO> result = userServiceHelperUnderTest.convertUsersToUserDTOs(users);

    // Verify the results
    assertThat(result).isEqualTo(expectedResult);
  }

  @Test
  void convertUsersToUserDTOs_NullList_DTONullList() {
    // Setup
    final List<User> users = List.of();
    final List<UserDTO> expectedResult = List.of();

    // Run the test
    final List<UserDTO> result = userServiceHelperUnderTest.convertUsersToUserDTOs(users);

    // Verify the results
    assertThat(result).isEqualTo(expectedResult);
  }

  @Test
  void convertUserToUserDTO_SimpleUser_ConvertedUserToDTO() {
    // Setup
    final User user = new User("username", "email", "password");
    final UserDTO expectedResult = new UserDTO(0L, "username", "email", "password");

    // Run the test
    final UserDTO result = userServiceHelperUnderTest.convertUserToUserDTO(user);

    // Verify the results
    assertThat(result).isEqualTo(expectedResult);
  }

  @Test
  void convertUserToUserDTO_Null_Null() {
    // Setup
    final User user = null;
    final UserDTO expectedResult = null;

    // Run the test
    final UserDTO result = userServiceHelperUnderTest.convertUserToUserDTO(user);

    // Verify the results
    assertThat(result).isEqualTo(expectedResult);
  }
}
