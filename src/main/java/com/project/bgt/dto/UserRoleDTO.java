package com.project.bgt.dto;

import com.project.bgt.model.Role;
import com.sun.istack.NotNull;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRoleDTO {

  @Id
  @NotNull
  long id;

  @NotBlank
  @Size(min = 3, max = 20)
  private String role;

}
