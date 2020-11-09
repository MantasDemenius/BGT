package com.project.bgt.dto;

import com.project.bgt.model.ComponentCategory;
import com.sun.istack.NotNull;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ComponentDTO {

  @Id
  @NotNull
  long id;
  //might find and take from game
  @NotNull
  long languageId;

  @NotNull
  long userId;

  @NotNull
  long gameId;

  @NotNull
  long originalComponentId;

  @NotBlank
  @Size(min = 3, max = 100)
  String title;

  @NotBlank
  @Size(min = 3, max = 2000)
  String description;

  ComponentCategory category;

  public boolean languageIdEquals(long languageId){
    return this.languageId == languageId;
  }

}
