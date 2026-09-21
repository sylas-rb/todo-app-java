package com.todoapp.resource;

import com.todoapp.Repository.UserServico;
import com.todoapp.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
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
        User user = servico.encontrarPorId(id);
        return ResponseEntity.ok().body(user);
    }

    @PutMapping(value="/{id}")
    public ResponseEntity<User> update(@PathVariable Long id, @RequestBody User user) {
        User userAtualizado = servico.atualizar(id, user);
        return ResponseEntity.ok().body(userAtualizado);
    }

    @DeleteMapping(value="/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        servico.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
