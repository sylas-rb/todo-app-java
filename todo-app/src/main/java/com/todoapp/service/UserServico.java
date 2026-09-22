package com.todoapp.service;

import com.todoapp.entities.User;
import com.todoapp.model.Errors.TarefaException;
import com.todoapp.model.Interface.UserRepositorio;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        Optional<User> user = userRepositorio.findById(id);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new TarefaException("usuário não encontrado.");
        }
    }

    public User atualizar(Long id, User userAtualizado) {
        User user = encontrarPorId(id);
        user.setNome(userAtualizado.getNome());
        user.setEmail(userAtualizado.getEmail());
        return userRepositorio.save(user);
    }

    public void deletar(Long id) {
        User user = encontrarPorId(id);
        userRepositorio.delete(user);
    }
}
