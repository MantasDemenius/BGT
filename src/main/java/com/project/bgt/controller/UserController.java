package com.project.bgt.controller;

import com.project.bgt.common.constant.PathConst;
import com.project.bgt.dto.UserDTO;
import com.project.bgt.exception.RecordNotFoundException;
import com.project.bgt.security.SecurityService;
import com.project.bgt.service.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

  @GetMapping(PathConst.SECURED + PathConst.USER_PATH)
  public List<UserDTO> getUsers() {
    try {
      return userService.getUsers();
    } catch (Exception ex) {
      throw new RecordNotFoundException();
    }
  }

  @GetMapping(PathConst.USER_PATH + "/{userId}")
  public UserDTO getUserById(@PathVariable(value = "userId") long userId) {
    return userService.getUserDTO(userId);
  }

  @PostMapping(PathConst.USER_PATH)
  public ResponseEntity createUser(@RequestBody UserDTO userDTO) {
    return securityService.createUser(userDTO);
  }

  @PutMapping(PathConst.USER_PATH + PathConst.SECURED + "/{userId}")
  public ResponseEntity updateUser(@PathVariable(value = "userId") long userId, @RequestBody UserDTO userDTO) {
    return userService.updateUser(userDTO, userId);
  }

  @DeleteMapping(PathConst.USER_PATH + PathConst.SECURED + "/{userId}")
  public ResponseEntity deleteUser(@PathVariable(value = "userId") long userId) {
    return userService.deleteUser(userId);
  }
}
