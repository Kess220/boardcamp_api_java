package com.boardcamp.api.errors;

public class UnavailableGamesError extends RuntimeException {
    public UnavailableGamesError(String message) {
        super(message);
    }
}
