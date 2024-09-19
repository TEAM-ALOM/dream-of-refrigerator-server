package com.example.dream_of_refrigerator.global.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiErrorResponse {

    private String message;
    public ApiErrorResponse(String message) {
        this.message = message;
    }
}