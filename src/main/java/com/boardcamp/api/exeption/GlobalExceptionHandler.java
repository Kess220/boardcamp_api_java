package com.boardcamp.api.exeption;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.boardcamp.api.errors.BadRequestError;
import com.boardcamp.api.errors.BodyRequestError;
import com.boardcamp.api.errors.ClientNotFoundException;
import com.boardcamp.api.errors.CpfInvalidError;
import com.boardcamp.api.errors.DuplicateGameNameError;
import com.boardcamp.api.errors.ExistingCpfExceptionError;
import com.boardcamp.api.errors.NotFoundError;
import com.boardcamp.api.errors.RentalAlreadyReturnedError;
import com.boardcamp.api.errors.UnavailableGamesError;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({ ClientNotFoundException.class })
    public ResponseEntity<Object> handleClientNotFound(ClientNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

    @ExceptionHandler({ NotFoundError.class })
    public ResponseEntity<Object> handleNotFound(NotFoundError exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

    @ExceptionHandler({ BodyRequestError.class })
    public ResponseEntity<Object> handleBodyRequestError(BodyRequestError exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }

    @ExceptionHandler({ DuplicateGameNameError.class })
    public ResponseEntity<Object> handleDuplicateGameName(DuplicateGameNameError exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getMessage());
    }

    @ExceptionHandler({ BadRequestError.class })
    public ResponseEntity<Object> handleBadRequest(BadRequestError exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }

    @ExceptionHandler({ UnavailableGamesError.class })
    public ResponseEntity<Object> handleUnavailableGames(UnavailableGamesError exception) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(exception.getMessage());
    }

    @ExceptionHandler({ ExistingCpfExceptionError.class })
    public ResponseEntity<Object> handleExistingCpfException(ExistingCpfExceptionError exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getMessage());
    }

    @ExceptionHandler({ CpfInvalidError.class })
    public ResponseEntity<Object> handleCPFInvalido(CpfInvalidError exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }

    @ExceptionHandler({ RentalAlreadyReturnedError.class })
    public ResponseEntity<Object> handleRentalAlreadyReturned(RentalAlreadyReturnedError exception) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(exception.getMessage());
    }

    @ExceptionHandler({ Exception.class })
    public ResponseEntity<Object> handleGenericException(Exception exception) {
        String errorMessage = "Erro interno no servidor. Entre em contato com o suporte.";
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
    }
}
