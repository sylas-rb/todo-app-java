package com.todoapp.model.dao;

import com.todoapp.entities.Tarefa;

import java.util.List;

public interface TarefaDAO {
    void inserir(Tarefa tarefa);
    void atualizar(Tarefa tarefa);
    void deletePorId(Long id);
    Tarefa encontrarPorId(Long id);
    List<Tarefa> encontrarTodos();
}
