package com.todoapp.DTO.request;

import com.todoapp.model.Enums.Prioridade;
import com.todoapp.model.Enums.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record TarefaResquestDTO (
        @NotBlank(message = "Campo titulo obrigatório.")
        @Size(min = 3, max = 100, message = "O titulo deve ter entre 3 a 100 caracteres.")
        String titulo,
        @NotBlank(message = "Campo descrição obrigatório.")
        @Size(max = 500, message = "A descrição deve ter no máximo 500 caracteres.")
        String descricao,
        @NotNull(message = "Campo status obrigatório.")
        Status status,
        @NotNull(message = "Campo prioridade obrigatório")
        Prioridade prioridade,
        @NotNull(message = "Campo data obrigatório.")
        LocalDate date,
        @NotNull(message = "Usuário não identificado.")
        Long idUser
) {}
