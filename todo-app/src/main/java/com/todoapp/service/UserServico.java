package com.todoapp.service;

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

    public User salvar(User user){
        userRepositorio.save(user);
        return user;
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
