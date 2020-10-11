package com.project.bgt.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GameDTO {

  long id;
  long languageId;
  long userId;
  long originalGameId;
  String author;
  String title;
  String description;
}
