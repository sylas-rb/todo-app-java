package com.todoapp.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.todoapp.entities.Tarefa;
import com.todoapp.model.Errors.TarefaException;
import com.todoapp.model.Interface.RepositorioInterface;
import com.todoapp.model.dao.TarefaDAO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TarefasRepositorio implements TarefaDAO {
    private final List<Tarefa> listaTarefa = new ArrayList<>();
    private RepositorioInterface pasta;

    private Long incrementoId = 0L;

    public TarefasRepositorio() {}

    public TarefasRepositorio(List<Tarefa> tarefas,  RepositorioInterface pasta) {
        if (tarefas != null) {
            this.listaTarefa.addAll(tarefas);
        }
        this.pasta = pasta;
    }

    public TarefasRepositorio(RepositorioInterface pasta) {
        this.pasta = pasta;
    }

    public TarefasRepositorio(List<Tarefa> tarefas) {
        if (tarefas != null) {
            this.listaTarefa.addAll(tarefas);
        }
    }

    public void addId(Tarefa t) {
        if (t == null) {
            throw new TarefaException("Não foi possível encontrar tarefa concluído.");
        }
        t.setId(this.incrementoId++);
    }

    public void carregarTarefa(ObjectMapper mapper) {
        try {
            listaTarefa.addAll(pasta.tarefasSalvas(mapper));
        } catch (IOException e) {
            throw new TarefaException("Erro ao carregar tarefa: " + e.getMessage());
        }
    }

    public Long retornoId() {
        for (Tarefa tarefa : listaTarefa) {
            if (tarefa.getId() > this.incrementoId) {
                this.incrementoId = tarefa.getId();
            }
        }
        return this.incrementoId;
    }

    public void salvarTarefa(ObjectMapper mapper) {
        pasta.salvarTarefa(mapper, listaTarefa);
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
    public void deletePorId(Long id) {
        if (!listaTarefa.removeIf(tarefa -> tarefa.getId().equals(id))) {
            throw new TarefaException("Não foi possível deleter tarefa, verifique id digitado.");
        }
    }

    @Override
    public Tarefa encontrarPorId(Long id) {
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
