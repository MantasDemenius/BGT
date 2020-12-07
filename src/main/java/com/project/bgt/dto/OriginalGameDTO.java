package com.project.bgt.dto;

import com.sun.istack.NotNull;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class OriginalGameDTO {

  @Id
  @NotNull
  long id;

  @NotBlank
  @Size(min = 3, max = 20)
  String language;

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
