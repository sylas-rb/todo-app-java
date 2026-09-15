package com.todoapp.Repository;

import com.todoapp.entities.Tarefa;
import com.todoapp.model.Errors.TarefaException;
import com.todoapp.model.Interface.TarefaDAO;

import java.util.ArrayList;
import java.util.List;

public class TarefasRepositorio implements TarefaDAO {
    private final List<Tarefa> listaTarefa = new ArrayList<>();

    public TarefasRepositorio() {}

    public TarefasRepositorio(List<Tarefa> tarefas) {
        if (tarefas != null) {
            this.listaTarefa.addAll(tarefas);
        }
    }

    @Override
    public void inserir(Tarefa tarefa) {
        if (tarefa == null) {
            throw new TarefaException("Não foi possível adicionar tarefa para tarefa concluída, tarefa vazia.");
        }
        listaTarefa.add(tarefa);
    }

    //função vazia, pois não usa banco de dados externo para ter que utilizar os dados
    @Override
    public void atualizar(Tarefa tarefa) {

    }

    @Override
    public void deletePorId(long id) {
        if (!listaTarefa.removeIf(tarefa -> tarefa.getId().equals(id))) {
            throw new TarefaException("Não foi possível deleter tarefa, verifique id digitado.");
        }
    }

    @Override
    public Tarefa encontrarPorId(long id) {
        return listaTarefa.stream().filter(tarefa -> tarefa.getId().equals(id)).findFirst().orElse(null);
    }

    @Override
    public List<Tarefa> encontrarTodos() {
        if (listaTarefa.isEmpty()) {
            return null;
        } else {
            return listaTarefa;
        }
    }
}
