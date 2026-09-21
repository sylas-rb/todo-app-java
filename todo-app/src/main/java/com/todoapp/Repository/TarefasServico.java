package com.todoapp.Repository;

import com.todoapp.entities.Tarefa;
import com.todoapp.model.Errors.TarefaException;
import com.todoapp.model.Interface.TarefaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TarefasServico{
    @Autowired
    private TarefaRepositorio tarefaRepositorio;

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
        tarefa.setTitulo(tarefaAtualizado.getTitulo());
        tarefa.setDescricao(tarefaAtualizado.getDescricao());
        tarefa.setStatus(tarefaAtualizado.getStatus());
        tarefa.setDate(tarefaAtualizado.getDate());
        tarefa.setPrioridade(tarefaAtualizado.getPrioridade());
        tarefa.setVencimento(tarefaAtualizado.getVencimento());
        tarefa.setUsuario(tarefaAtualizado.getUsuario());
        return tarefaRepositorio.save(tarefa);
    }

    public void remover (Long id){
        Tarefa tarefa = encontrarPorId(id);
        tarefaRepositorio.delete(tarefa);
    }
}
