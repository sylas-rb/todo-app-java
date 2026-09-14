package com.todoapp.model.Interface;

import com.todoapp.entities.Tarefa;

import java.util.List;

public interface OrdemTarefaInterface {
    List<Tarefa> ordenarTarefaPrioridade(List<Tarefa> tarefa);
    List<Tarefa> ordenarTarefaStatusPendente(List<Tarefa> tarefa);
    List<Tarefa> ordenarTarefaStatusConcluido(List<Tarefa> tarefa);
    List<Tarefa> ordenarTarefaData(List<Tarefa> tarefa);
    List<Tarefa> ordenarTarefaVencimento(List<Tarefa> tarefa);
}
