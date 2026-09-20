package com.todoapp.model.Interface;

import com.todoapp.entities.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TarefaRepositorio extends JpaRepository<Tarefa, Long> {
}
