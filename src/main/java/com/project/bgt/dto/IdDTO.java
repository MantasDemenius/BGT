package com.project.bgt.dto;

import com.sun.istack.NotNull;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IdDTO {
  @Id
  @NotNull
  long id;
}
