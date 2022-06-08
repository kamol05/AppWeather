package com.example.appweather.model;

import lombok.Getter;

@Getter
public enum Role {
    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER");

    private String name;

    Role(String name) {
        this.name = name;
    }
}
