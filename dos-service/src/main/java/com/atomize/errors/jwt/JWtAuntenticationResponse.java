package com.atomize.errors.jwt;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class JWtAuntenticationResponse {
    @JsonProperty("message")
    private final String message;
    @JsonProperty("error-message")
    private final String errorMessage;
    @JsonProperty("error-status")
    private final HttpStatus errorStatus;
    @JsonProperty("statusCode")
    private final Integer statusCode;
}
