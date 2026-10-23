package com.todoapp.service;

import com.todoapp.DTO.request.UserResquestDTO;
import com.todoapp.DTO.response.UserResponseDTO;
import com.todoapp.entities.User;
import com.todoapp.model.Errors.DataBaseException;
import com.todoapp.model.Errors.RecursoNaoEncontradoExcecao;
import com.todoapp.model.Interface.UserRepositorio;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServico {
    private final UserRepositorio userRepositorio;
    private final PasswordEncoder encoder;

    public UserServico(UserRepositorio userRepositorio, PasswordEncoder encoder) {
        this.userRepositorio = userRepositorio;
        this.encoder = encoder;
    }

    public void recebendoDTO (UserResquestDTO dto, User user) {
        user.setNome(dto.nome());
        user.setEmail(dto.email());
        user.setSenha(encoder.encode(dto.senha()));
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
