package com.project.bgt.dto;

import com.project.bgt.model.Language;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LanguageDto {

  long id;
  String name;
  String code;
}
