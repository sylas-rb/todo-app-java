package com.todoapp.model.Interface;

import com.todoapp.entities.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TarefaRepositorio extends JpaRepository<Tarefa, Long> {
    List<Tarefa> findByUsuarioId(Long id);
    Optional<Tarefa> findByIdAndUsuarioId(Long idTarefa, Long idUser);
}
