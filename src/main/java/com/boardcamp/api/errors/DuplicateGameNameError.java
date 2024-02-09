package com.boardcamp.api.errors;

public class DuplicateGameNameError extends RuntimeException {
    public DuplicateGameNameError(String message) {
        super(message);
    }
}
