package com.boardcamp.api.errors;

public class ExistingCpfExceptionError extends RuntimeException {
    public ExistingCpfExceptionError(String cpf) {
        super("There is already a registered customer with CPF: " + cpf);
    }
}
