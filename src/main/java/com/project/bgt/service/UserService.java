package com.project.bgt.service;

import com.project.bgt.common.LocationHeader;
import com.project.bgt.common.constant.PathConst;
import com.project.bgt.common.message.ErrorMessages;
import com.project.bgt.common.serviceHelper.UserServiceHelper;
import com.project.bgt.dto.UserDTO;
import com.project.bgt.dto.UserRoleDTO;
import com.project.bgt.exception.EmailAlreadyExistsException;
import com.project.bgt.exception.RecordNotFoundException;
import com.project.bgt.exception.ResponseEntityBuilder;
import com.project.bgt.exception.UsernameAlreadyExistsException;
import com.project.bgt.model.ApiError;
import com.project.bgt.model.Role;
import com.project.bgt.model.User;
import com.project.bgt.model.UserRoleName;
import com.project.bgt.repository.RoleRepository;
import com.project.bgt.repository.UserRepository;
import com.project.bgt.security.SecurityService;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  @Autowired
  private SecurityService securityService;
  private final UserServiceHelper userServiceHelper = new UserServiceHelper();
  private UserRepository userRepository;
  @Autowired
  private RoleRepository roleRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  public void setUserRepository(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public List<UserDTO> getUsers() {
    return userServiceHelper.convertUsersToUserDTOs(userRepository.findAll());
  }

  public User getUser(long userId) {
    return userRepository.findById(userId)
      .orElseThrow(() -> new RecordNotFoundException("User with id: " + userId + " was not found!"));
  }

  public UserDTO getUserDTO(long userId) {
    return userServiceHelper.convertUserToUserDTO(getUser(userId));
  }

  public ResponseEntity createUser(UserDTO userDTO) {
    userExistsByUsername(userDTO.getUsername());
    userExistsByEmail(userDTO.getEmail());

    User user = new User(
      userDTO.getUsername(),
      userDTO.getEmail(),
      passwordEncoder.encode(userDTO.getPassword())
    );
    user.setRoles(Collections.singleton(getUserRole(UserRoleName.BASIC)));

    User newUser = userRepository.save(user);

    return new ResponseEntity(
      LocationHeader.getLocationHeaders(PathConst.USER_PATH, newUser.getId()),
      HttpStatus.CREATED);
  }

  public Role getUserRole(UserRoleName userRole) {
    return roleRepository.findByName(userRole)
        .orElseThrow(() -> new RecordNotFoundException("Role: " + userRole + " was not found!"));
  }

  public ResponseEntity updateUser(UserDTO userDTO, long userId) {
    userExistsByUsername(userDTO.getUsername());
    userExistsByEmail(userDTO.getEmail());
    User user = getUser(userId);

    user.setUsername(userDTO.getUsername());
    user.setEmail(userDTO.getEmail());
    user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

    userRepository.save(user);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  public ResponseEntity deleteUser(long userId) {
    try {
      userRepository.deleteById(userId);
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (Exception ex) {
      throw new RecordNotFoundException("User with id: " + userId + " was not found!");
    }
  }

  public ResponseEntity updateUserRole(List<UserRoleDTO> userRoleDTOS, long userId) {
    User user = getUser(userId);

    user.setRoles(userRoleDTOS.stream().map(
      role -> new Role(role.getId(), role.getRole()))
      .collect(Collectors.toSet()));

    userRepository.save(user);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  public void userExistsByUsername(String username) {
    if (userRepository.existsByUsername(username)) {
      throw new UsernameAlreadyExistsException("Username: " + username + " is already taken!");
    }
  }

  public void userExistsByEmail(String email) {
    if (userRepository.existsByEmail(email)) {
      throw new EmailAlreadyExistsException("Email: " + email + " is already taken!");
    }
  }
}
