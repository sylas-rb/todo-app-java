package com.todoapp.model.Errors;

public class DataBaseException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public DataBaseException(String e) {
        super(e);
    }
}
