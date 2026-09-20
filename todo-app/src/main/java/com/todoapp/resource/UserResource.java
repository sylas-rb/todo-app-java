package com.todoapp.resource;

import com.todoapp.Repository.UserServico;
import com.todoapp.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/Users")
public class UserResource {
    @Autowired
    private UserServico servico;

    @GetMapping
    public ResponseEntity<List<User>> findAll() {
        List<User> lista = servico.encontrarTudo();
        return ResponseEntity.ok().body(lista);
    }

    @GetMapping(value="/{id}")
    public ResponseEntity<User> findById(@PathVariable Long id) {
        User User = servico.encontrarPorId(id);
        return ResponseEntity.ok().body(User);
    }
}
