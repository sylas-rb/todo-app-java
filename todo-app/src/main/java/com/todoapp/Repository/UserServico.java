package com.todoapp.Repository;

import com.todoapp.entities.User;
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
        Optional<User> User = UserRepositorio.findById(id);
        return User.get();
    }
}
