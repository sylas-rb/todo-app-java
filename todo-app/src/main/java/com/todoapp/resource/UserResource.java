package com.todoapp.resource;

import com.todoapp.entities.Tarefa;
import com.todoapp.service.UserServico;
import com.todoapp.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserResource {
    private UserServico servico;

    public UserResource(UserServico servico) {
        this.servico = servico;
    }

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

    @PostMapping
    public ResponseEntity<User> salvar(@RequestBody User user) {
        user = servico.salvar(user);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(uri).body(user);
    }

    @PutMapping(value="atualizar/{id}")
    public ResponseEntity<User> update(@PathVariable Long id, @RequestBody User user) {
        User userAtualizado = servico.atualizar(id, user);
        return ResponseEntity.ok().body(userAtualizado);
    }

    @DeleteMapping(value="delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        servico.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
