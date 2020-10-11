package com.project.bgt.controller;

import com.project.bgt.common.constant.PathConst;
import com.project.bgt.dto.UserDTO;
import com.project.bgt.exception.RecordNotFoundException;
import com.project.bgt.model.User;
import com.project.bgt.service.UserService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(PathConst.USER_PATH)
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("")
  public List<UserDTO> getUsers() {
    try {
      return userService.getUsers();
    } catch (Exception ex) {
      throw new RecordNotFoundException();
    }
  }
}
