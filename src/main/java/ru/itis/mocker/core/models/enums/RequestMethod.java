package ru.itis.mocker.core.models.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum RequestMethod {
    @JsonProperty("GET")
    GET("GET"),

    @JsonProperty("POST")
    POST("POST"),

    @JsonProperty("PUT")
    PUT("PUT"),

    @JsonProperty("DELETE")
    DELETE("DELETE"),

    @JsonProperty("PATCH")
    PATCH("PATCH");

    private String name;

    RequestMethod(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
