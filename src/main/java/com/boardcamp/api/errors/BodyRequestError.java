package com.boardcamp.api.errors;

public class BodyRequestError extends RuntimeException {
    public BodyRequestError(String message) {
        super(message);
    }
}
