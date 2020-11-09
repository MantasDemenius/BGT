package com.project.bgt.dto;

import com.sun.istack.NotNull;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GameDTO {

  @Id
  @NotNull
  long id;

  @NotNull
  long languageId;

  @NotNull
  long userId;

  @NotNull
  long originalGameId;

  @NotBlank
  @Size(min = 3, max = 20)
  String author;

  @NotBlank
  @Size(min = 3, max = 100)
  String title;

  @NotBlank
  @Size(min = 3, max = 2000)
  String description;
}
