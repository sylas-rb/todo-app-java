package com.todoapp.service;

import com.todoapp.entities.Tarefa;
import com.todoapp.model.Errors.TarefaException;
import com.todoapp.model.Interface.TarefaRepositorio;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TarefasServico{
    private final TarefaRepositorio tarefaRepositorio;

    public TarefasServico(TarefaRepositorio tarefaRepositorio) {
        this.tarefaRepositorio = tarefaRepositorio;
    }

    public Tarefa salvar(Tarefa tarefa){
        tarefaRepositorio.save(tarefa);
        return tarefa;
    }

    public List<Tarefa> encontrarTudo() {
        return tarefaRepositorio.findAll();

    }

    public Tarefa encontrarPorId (Long id) {
        Optional<Tarefa> tarefa = tarefaRepositorio.findById(id);
        if (tarefa.isPresent()) {
            return tarefa.get();
        } else {
            throw new TarefaException("não foi possível encontrar tarefa.");
        }
    }

    public Tarefa atualizar(Long id, Tarefa tarefaAtualizado) {
        Tarefa tarefa = encontrarPorId(id);
        tarefa.atualizar(tarefaAtualizado);
        return tarefaRepositorio.save(tarefa);
    }

    public void remover (Long id){
        Tarefa tarefa = encontrarPorId(id);
        tarefaRepositorio.delete(tarefa);
    }
}
