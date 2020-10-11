package com.project.bgt.controller;

import com.project.bgt.common.constant.PathConst;
import com.project.bgt.dto.ComponentDTO;
import com.project.bgt.dto.UserDTO;
import com.project.bgt.exception.RecordNotFoundException;
import com.project.bgt.model.User;
import com.project.bgt.service.UserService;
import java.util.List;
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
@RequestMapping(PathConst.USER_PATH)
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping
  public List<UserDTO> getUsers() {
    try {
      return userService.getUsers();
    } catch (Exception ex) {
      throw new RecordNotFoundException();
    }
  }

  @GetMapping("/{userId}")
  public UserDTO getUserById(@PathVariable(value = "userId") long userId) {
    return userService.getUserDTO(userId);
  }

  @PostMapping
  public ResponseEntity createUser(@RequestBody UserDTO userDTO) {
    return userService.createUser(userDTO);
  }

  @PutMapping("/{userId}")
  public ResponseEntity updateUser(@PathVariable(value = "userId") long userId, @RequestBody UserDTO userDTO) {
    return userService.updateUser(userDTO, userId);
  }

  @DeleteMapping("/{userId}")
  public ResponseEntity deleteUser(@PathVariable(value = "userId") long userId) {
    return userService.deleteUser(userId);
  }
}
