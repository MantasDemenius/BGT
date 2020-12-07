package com.project.bgt.dto;

import com.sun.istack.NotNull;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

  @Id
  @NotNull
  long id;

  @NotBlank
  @Size(min = 3, max = 20)
  String username;

  @NotBlank
  @Email()
  @Size(min = 3, max = 50)
  String email;

  @NotBlank
  @Size(min = 3, max = 50)
  String password;

}
