package com.todoapp.model.Interface;

import com.todoapp.entities.Tarefa;

import java.util.List;

public interface TarefaDAO {
    void inserir(Tarefa tarefa);
    void atualizar(Tarefa tarefa);
    void deletePorId(long id);
    Tarefa encontrarPorId(long id);
    List<Tarefa> encontrarTodos();
}
