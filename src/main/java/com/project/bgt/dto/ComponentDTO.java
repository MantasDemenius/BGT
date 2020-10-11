package com.project.bgt.dto;

import com.project.bgt.model.ComponentCategory;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ComponentDTO {

  long id;
  //might find and take from game
  long languageId;
  long userId;
  long gameId;
  long originalComponentId;
  String title;
  String description;
  ComponentCategory category;

  public boolean languageIdEquals(long languageId){
    return this.languageId == languageId;
  }

}
