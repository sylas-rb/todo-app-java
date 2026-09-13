package com.todoapp.model.Errors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TratadorErrosGlobal {
    @ExceptionHandler(TarefaException.class)
    public ResponseEntity<String> tratarErroTarefa(TarefaException te) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(te.getMessage());
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<String> tratarErroNullPointerException(NullPointerException te) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno no processamento dos dados.");
    }
}
