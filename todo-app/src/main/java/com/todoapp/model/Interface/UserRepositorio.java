package com.todoapp.model.Interface;

import com.todoapp.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepositorio extends JpaRepository<User, Long> {
}
