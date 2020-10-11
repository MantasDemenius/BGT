package com.project.bgt.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.stream.Stream;

public enum ComponentCategory {
  RULES("RULES"),
  CARD("CARD");

  private String code;

  private ComponentCategory(String code) {
    this.code=code;
  }

  @JsonValue
  public String getCode() {
    return code;
  }
}
