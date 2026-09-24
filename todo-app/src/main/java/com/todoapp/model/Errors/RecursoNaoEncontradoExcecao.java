package com.todoapp.model.Errors;

public class RecursoNaoEncontradoExcecao extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public RecursoNaoEncontradoExcecao(Object id) {
        super("recurso não encontrado: " + id);
    }
}
