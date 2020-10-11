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

  @JsonCreator
  public static ComponentCategory decode(final String code) {
    return Stream.of(ComponentCategory.values()).filter(targetEnum -> targetEnum.code.equals(code)).findFirst().orElse(null);
  }

  @JsonValue
  public String getCode() {
    return code;
  }
}
