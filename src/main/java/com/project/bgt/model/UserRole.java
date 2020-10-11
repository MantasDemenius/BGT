package com.project.bgt.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum UserRole {
    BASIC("BASIC"),
    CREATOR("CREATOR"),
    ADMINISTRATOR("ADMINISTRATOR");

    private String code;

    private UserRole(String code){
        this.code = code;
    }

    @JsonValue
    public String getCode() {
        return code;
    }

}
