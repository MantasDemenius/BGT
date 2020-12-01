package com.project.bgt.model;

import java.io.Serializable;
import lombok.Data;

@Data
public class JwtResponse implements Serializable {

  private final String token;

  public JwtResponse(String token) {
    this.token = token;
  }
}
