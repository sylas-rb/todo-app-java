package com.todoapp.Repository;

import com.todoapp.entities.User;
import com.todoapp.model.Errors.TarefaException;
import com.todoapp.model.Interface.UserRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServico {
    @Autowired
    private UserRepositorio UserRepositorio;

    public List<User> encontrarTudo() {
        return UserRepositorio.findAll();

    }

    public User encontrarPorId (Long id) {
        Optional<User> user = UserRepositorio.findById(id);
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
        return UserRepositorio.save(user);
    }

    public void deletar(Long id) {
        User user = encontrarPorId(id);
        UserRepositorio.delete(user);
    }
}
