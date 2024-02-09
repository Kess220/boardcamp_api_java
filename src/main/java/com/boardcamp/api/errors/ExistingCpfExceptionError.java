package com.boardcamp.api.errors;

public class ExistingCpfExceptionError extends RuntimeException {
    public ExistingCpfExceptionError(String cpf) {
        super("Já existe um cliente registrado com o CPF: " + cpf);
    }
}
