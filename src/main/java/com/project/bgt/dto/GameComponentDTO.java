package com.project.bgt.dto;

import java.util.List;
import lombok.Data;

@Data
public class GameComponentDTO {

  private GameDTO game;
  private List<ComponentDTO> components;
}
