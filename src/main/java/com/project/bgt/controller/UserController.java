package com.project.bgt.controller;

import com.project.bgt.common.constant.PathConst;
import com.project.bgt.dto.UserDTO;
import com.project.bgt.dto.UserRoleDTO;
import com.project.bgt.exception.RecordNotFoundException;
import com.project.bgt.security.SecurityService;
import com.project.bgt.service.UserService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(PathConst.PATH)
public class UserController {

  @Autowired
  private final SecurityService securityService;
  @Autowired
  private final UserService userService;

  public UserController(SecurityService securityService,
    UserService userService) {
    this.securityService = securityService;
    this.userService = userService;
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @GetMapping(PathConst.USER_PATH)
  public List<UserDTO> getUsers() {
    try {
      return userService.getUsers();
    } catch (Exception ex) {
      throw new RecordNotFoundException();
    }
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @GetMapping(PathConst.USER_PATH + "/{userId}")
  public UserDTO getUserById(@PathVariable(value = "userId") long userId) {
    return userService.getUserDTO(userId);
  }

  @PostMapping(PathConst.USER_PATH)
  public ResponseEntity createUser(@Valid @RequestBody UserDTO userDTO) {
    return securityService.createUser(userDTO);
  }

  @PreAuthorize("hasAnyAuthority('BASIC', 'CREATOR', 'ADMIN')")
  @PutMapping(PathConst.USER_PATH + "/{userId}")
  public ResponseEntity updateUser(@PathVariable(value = "userId") long userId, @RequestBody UserDTO userDTO) {
    return userService.updateUser(userDTO, userId);
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @PutMapping(PathConst.USER_PATH + "/{userId}" + "/role")
  public ResponseEntity updateUserRole(@PathVariable(value = "userId") long userId, @Valid @RequestBody List<UserRoleDTO> userRoleDTO) {
    return userService.updateUserRole(userRoleDTO, userId);
  }

  @PreAuthorize("hasAnyAuthority('BASIC', 'CREATOR', 'ADMIN')")
  @DeleteMapping(PathConst.USER_PATH + "/{userId}")
  public ResponseEntity deleteUser(@PathVariable(value = "userId") long userId) {
    return userService.deleteUser(userId);
  }
}
