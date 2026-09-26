package com.todoapp.service;

import com.todoapp.entities.Tarefa;
import com.todoapp.model.Errors.DataBaseException;
import com.todoapp.model.Errors.RecursoNaoEncontradoExcecao;
import com.todoapp.model.Interface.TarefaRepositorio;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

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
        return tarefaRepositorio.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoExcecao(id));
    }

    public Tarefa atualizar(Long id, Tarefa tarefaAtualizado) {
        Tarefa tarefa = encontrarPorId(id);
        tarefa.atualizar(tarefaAtualizado);
        return tarefaRepositorio.save(tarefa);
    }

    public void remover (Long id){
        try {
            Tarefa tarefa = encontrarPorId(id);
            tarefaRepositorio.delete(tarefa);
        } catch (DataIntegrityViolationException e) {
            throw new DataBaseException(e.getMessage());
        }
    }
}
