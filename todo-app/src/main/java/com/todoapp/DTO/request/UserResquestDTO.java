package com.todoapp.DTO.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserResquestDTO(
        @NotBlank(message = "Campo nome obrigatório.")
        @Size(min = 3, max = 100, message = "O titulo deve ter entre 3 a 100 caracteres.")
        String nome,
        @NotBlank(message = "Campo descrição obrigatório.")
        @Size(max = 244, message = "A email deve ter no máximo 500 caracteres.")
        String email,
        @NotBlank(message = "Campo senha obrigatório.")
        String senha
) {}
