package com.todoapp.model.Interface;

import com.todoapp.entities.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AutenticacaoRepositorio extends CrudRepository<User, String> {

    Optional<User> findByNome(String nome);
}
