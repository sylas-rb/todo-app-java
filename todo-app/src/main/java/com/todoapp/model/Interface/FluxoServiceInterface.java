package com.todoapp.model.Interface;

import com.todoapp.entities.Tarefa;
import com.todoapp.model.Enums.Prioridade;
import com.todoapp.model.Enums.Status;

public interface FluxoServiceInterface {
    void criarTarefas(Tarefa tarefa);
    String mostrarTarefa(long id);
    Tarefa mudancaPrioridade(long id, Prioridade prioridade);
    Tarefa mudancaTitulo(long id, String titulo);
    Tarefa mudancaDescricao(long id, String descricao);
    void marcaTarefa(long id, Status status);
    void removendoTarefa(long id);
}
