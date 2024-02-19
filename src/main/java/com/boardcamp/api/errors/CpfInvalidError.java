package com.boardcamp.api.errors;

public class CpfInvalidError extends RuntimeException {

    public CpfInvalidError(String message) {
        super(message);
    }

}
