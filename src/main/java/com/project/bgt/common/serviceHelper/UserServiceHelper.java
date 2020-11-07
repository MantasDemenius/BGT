package com.project.bgt.common.serviceHelper;

import com.project.bgt.dto.UserDTO;
import com.project.bgt.model.User;
import java.util.List;
import java.util.stream.Collectors;
import lombok.NoArgsConstructor;

public class UserServiceHelper {

  public List<UserDTO> convertUsersToUserDTOs(List<User> users) {
    return users.stream()
      .map(this::convertUserToUserDTO)
      .collect(Collectors.toList());
  }

  public UserDTO convertUserToUserDTO(User user) {
    return new UserDTO(
      user.getId(),
      user.getUsername(),
      user.getEmail(),
      user.getPassword()
    );
  }

}
