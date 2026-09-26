package com.todoapp.model.Errors;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class TratadorErrosGlobal {
    @ExceptionHandler(RecursoNaoEncontradoExcecao.class)
    public ResponseEntity<ErroPadrao> resourceNotFoundException(RecursoNaoEncontradoExcecao e, HttpServletRequest request) {
        String erro = "recurso não encontrado";
        HttpStatus status = HttpStatus.NOT_FOUND;
        ErroPadrao erroPadrao = new ErroPadrao(Instant.now(), status.value(), erro, e.getMessage(),  request.getRequestURI());
        return ResponseEntity.status(status).body(erroPadrao);
    }
    @ExceptionHandler(DataBaseException.class)
    public ResponseEntity<ErroPadrao> tratarErroTarefa(DataBaseException te,  HttpServletRequest request) {
        String erro = "Error no database";
        HttpStatus status = HttpStatus.CONFLICT;
        ErroPadrao erroPadrao = new ErroPadrao(Instant.now(), status.value(), erro, te.getMessage(),  request.getRequestURI());
        return ResponseEntity.status(status).body(erroPadrao);
    }
}
