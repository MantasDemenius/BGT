package com.project.bgt.service;

import com.project.bgt.common.LocationHeader;
import com.project.bgt.common.constant.PathConst;
import com.project.bgt.common.message.ErrorMessages;
import com.project.bgt.common.serviceHelper.UserServiceHelper;
import com.project.bgt.dto.UserDTO;
import com.project.bgt.dto.UserRoleDTO;
import com.project.bgt.exception.RecordNotFoundException;
import com.project.bgt.model.Role;
import com.project.bgt.model.User;
import com.project.bgt.model.UserRoleName;
import com.project.bgt.repository.RoleRepository;
import com.project.bgt.repository.UserRepository;
import com.project.bgt.security.SecurityService;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  @Autowired
  private SecurityService securityService;
  private final UserServiceHelper userServiceHelper = new UserServiceHelper();
  private UserRepository userRepository;
  private RoleRepository roleRepository;

  @Autowired
  public void setUserRepository(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Autowired
  public void setRoleRepository(RoleRepository roleRepository){
    this.roleRepository = roleRepository;
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

  public ResponseEntity updateUser(UserDTO userDTO, long userId) {
    //    ComponentCheck.checkComponents(newComponentDTO);
    User user = getUser(userId);

    user.setUsername(userDTO.getUsername());
    user.setEmail(userDTO.getEmail());
    user.setPassword(userDTO.getPassword());

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

  public ResponseEntity updateUserRole(List<UserRoleDTO> userRoleDTOS, long userId) {
    User user = getUser(userId);


    user.setRoles(userRoleDTOS.stream().map(
      role -> new Role(role.getId(), role.getRole()))
      .collect(Collectors.toSet()));

    userRepository.save(user);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
