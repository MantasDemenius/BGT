package com.project.bgt.dto;

import com.project.bgt.model.UserRoleName;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDTO {

  long id;

  @NotNull
  String username;
  @NotNull
  String email;
  @NotNull
  String password;

}
