package com.todoapp.model.Interface;

import com.todoapp.entities.Tarefa;

public interface TarefaFluxoInterface {
    void criarTarefas();
    void estaConcluida(Tarefa tarefa);
    void nivelPrioridade(Tarefa tarefa);
    boolean confirmacaoTarefa(long id);
    boolean confirmarModificacao(String mudanca, byte n, Tarefa tarefa);
    void modificaTarefa(Tarefa tarefa);
    void removendoTarefa();
}
