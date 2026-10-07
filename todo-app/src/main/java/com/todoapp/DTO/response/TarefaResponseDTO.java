package com.todoapp.DTO.response;

import com.todoapp.model.Enums.Prioridade;
import com.todoapp.model.Enums.Status;

import java.time.LocalDate;

public record TarefaResponseDTO(
        Long id,
        String titulo,
        String descricao,
        Status status,
        Prioridade prioridade,
        LocalDate date,
        LocalDate vencimento
) {}
