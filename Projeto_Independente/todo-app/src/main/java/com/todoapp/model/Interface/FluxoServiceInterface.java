package com.todoapp.model.Interface;

import com.todoapp.entities.Tarefa;
import com.todoapp.model.Enums.Prioridade;

public interface FluxoServiceInterface {
    void criarTarefas(Tarefa tarefa);
    String mostrarTarefa(long id);
    Tarefa mudancaPrioridade(long id, Prioridade prioridade);
    Tarefa mudancaTitulo(long id, String titulo);
    Tarefa mudancaDescricao(long id, String descricao);
    void marcaTarefa(long id);
    String removendoTarefa(long id);
}
