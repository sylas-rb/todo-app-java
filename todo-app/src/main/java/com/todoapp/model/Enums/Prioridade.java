package com.todoapp.model.Enums;

public enum Prioridade {
    ALTA(7),
    MEDIA(15),
    BAIXA(0);

    private final int dias;
    Prioridade(int dias) {
        this.dias = dias;
    }

    public int getDiasVencimento() {
        return this.dias;
    }
}
