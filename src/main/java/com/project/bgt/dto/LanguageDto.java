package com.project.bgt.dto;

import com.project.bgt.model.Language;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LanguageDto {

  long id;

  @NotNull
  String name;
  @NotNull
  String code;
}
