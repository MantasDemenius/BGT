package com.project.bgt.dto;

import com.sun.istack.NotNull;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LanguageDTO {

  @Id
  @NotNull
  long id;

  @NotBlank
  @Size(min = 3, max = 20)
  String name;

  @NotBlank
  @Size(min = 2, max = 5)
  String code;
}
