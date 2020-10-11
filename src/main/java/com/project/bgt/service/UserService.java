package com.project.bgt.service;

import com.project.bgt.common.message.ErrorMessages;
import com.project.bgt.dto.LanguageDto;
import com.project.bgt.dto.UserDTO;
import com.project.bgt.exception.RecordNotFoundException;
import com.project.bgt.model.Game;
import com.project.bgt.model.Language;
import com.project.bgt.model.User;
import com.project.bgt.repository.UserRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private UserRepository userRepository;

  @Autowired
  public void setUserRepository(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public List<UserDTO> getUsers() {
    return convertUsersToUserDTOs(userRepository.findAll());
  }

  public User getUser(long userId) {
    return userRepository.findById(userId)
      .orElseThrow(() -> new RecordNotFoundException(ErrorMessages.USER_NOT_FOUND_ID));
  }

  private List<UserDTO> convertUsersToUserDTOs(List<User> users) {
    return users.stream()
      .map(this::convertUserToUserDTO)
      .collect(Collectors.toList());
  }

  private UserDTO convertUserToUserDTO(User user) {
    return new UserDTO(
      user.getId(),
      user.getUsername(),
      user.getEmail(),
      user.getPassword(),
      user.getRole()
    );
  }
}
