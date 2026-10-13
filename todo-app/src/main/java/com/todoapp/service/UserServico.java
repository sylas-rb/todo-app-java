package com.todoapp.service;

import com.todoapp.DTO.request.UserResquestDTO;
import com.todoapp.DTO.response.UserResponseDTO;
import com.todoapp.entities.User;
import com.todoapp.model.Errors.DataBaseException;
import com.todoapp.model.Errors.RecursoNaoEncontradoExcecao;
import com.todoapp.model.Interface.UserRepositorio;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServico {
    private final UserRepositorio userRepositorio;

    public UserServico(UserRepositorio userRepositorio) {
        this.userRepositorio = userRepositorio;
    }

    public void recebendoDTO (UserResquestDTO dto, User user) {
        user.setNome(dto.nome());
        user.setEmail(dto.email());
        user.setSenha(dto.senha());
    }

    public UserResponseDTO enviandoDTO (User user) {
        UserResponseDTO dto = new UserResponseDTO(
                user.getNome(),
                user.getEmail()
        );

        return dto;
    }

    public void salvar(User user){
        userRepositorio.save(user);
    }

    public List<User> encontrarTudo() {
        return userRepositorio.findAll();

    }

    public User encontrarPorId (Long id) {
            return userRepositorio.findById(id)
                    .orElseThrow(() -> new RecursoNaoEncontradoExcecao(id));
    }

    public void deletar(Long id) {
        try {
            User user = encontrarPorId(id);
            userRepositorio.delete(user);
        } catch (DataIntegrityViolationException ex) {
            throw new DataBaseException(ex.getMessage());
        }
    }
}
