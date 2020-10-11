package com.project.bgt.service;

import com.project.bgt.common.LocationHeader;
import com.project.bgt.common.constant.PathConst;
import com.project.bgt.common.message.ErrorMessages;
import com.project.bgt.common.serviceHelper.UserServiceHelper;
import com.project.bgt.dto.UserDTO;
import com.project.bgt.exception.RecordNotFoundException;
import com.project.bgt.model.User;
import com.project.bgt.repository.UserRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private final UserServiceHelper userServiceHelper = new UserServiceHelper();
  private UserRepository userRepository;

  @Autowired
  public void setUserRepository(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public List<UserDTO> getUsers() {
    return userServiceHelper.convertUsersToUserDTOs(userRepository.findAll());
  }

  public User getUser(long userId) {
    return userRepository.findById(userId)
      .orElseThrow(() -> new RecordNotFoundException(ErrorMessages.USER_NOT_FOUND));
  }

  public UserDTO getUserDTO(long userId) {
    return userServiceHelper.convertUserToUserDTO(getUser(userId));
  }

  public ResponseEntity createUser(UserDTO userDTO) {
    //    ComponentCheck.checkComponents(componentDto);
    User newUser = userRepository.save(new User(
      userDTO.getUsername(),
      userDTO.getEmail(),
      userDTO.getPassword(),
      userDTO.getUserRole()
    ));

    return new ResponseEntity(
      LocationHeader.getLocationHeaders(PathConst.USER_PATH, newUser.getId()),
      HttpStatus.CREATED);
  }

  public ResponseEntity updateUser(UserDTO userDTO, long userId) {
    //    ComponentCheck.checkComponents(newComponentDTO);
    User user = getUser(userId);

    user.setUsername(userDTO.getUsername());
    user.setEmail(userDTO.getEmail());
    user.setPassword(userDTO.getPassword());
    user.setRole(userDTO.getUserRole());

    userRepository.save(user);

    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  public ResponseEntity deleteUser(long userId) {
    try {
      userRepository.deleteById(userId);
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (Exception ex) {
      throw new RecordNotFoundException(ErrorMessages.USER_NOT_FOUND);
    }
  }
}
