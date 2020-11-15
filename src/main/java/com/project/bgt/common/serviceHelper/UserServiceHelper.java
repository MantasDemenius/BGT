package com.project.bgt.common.serviceHelper;

import com.project.bgt.dto.UserDTO;
import com.project.bgt.exception.ResponseEntityBuilder;
import com.project.bgt.exception.UsernameAlreadyExistsException;
import com.project.bgt.model.ApiError;
import com.project.bgt.model.User;
import com.project.bgt.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

public class UserServiceHelper {

  public List<UserDTO> convertUsersToUserDTOs(List<User> users) {
    return users.stream()
      .map(this::convertUserToUserDTO)
      .collect(Collectors.toList());
  }

  public UserDTO convertUserToUserDTO(User user) {
    if(user == null ) return null;
    return new UserDTO(
      user.getId(),
      user.getUsername(),
      user.getEmail(),
      user.getPassword()
    );
  }



}
