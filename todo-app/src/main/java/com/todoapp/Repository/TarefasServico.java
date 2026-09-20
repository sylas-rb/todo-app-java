package com.todoapp.Repository;

import com.todoapp.entities.Tarefa;
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
        return tarefa.get();
    }
}
