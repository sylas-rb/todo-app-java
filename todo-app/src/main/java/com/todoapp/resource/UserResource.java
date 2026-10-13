package com.todoapp.resource;

import com.todoapp.DTO.request.UserResquestDTO;
import com.todoapp.DTO.response.UserResponseDTO;
import com.todoapp.service.UserServico;
import com.todoapp.entities.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserResource {
    private final UserServico servico;

    public UserResource(UserServico servico) {
        this.servico = servico;
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> findAll() {
        List<User> users = servico.encontrarTudo();

        List<UserResponseDTO> dto = new ArrayList<>();
        for (User user : users) {
            dto.add(servico.enviandoDTO(user));
        }

        return ResponseEntity.ok().body(dto);
    }

    @GetMapping(value="/{id}")
    public ResponseEntity<UserResponseDTO> findById(@PathVariable Long id) {

        User user = servico.encontrarPorId(id);
        UserResponseDTO dto = servico.enviandoDTO(user);

        return ResponseEntity.ok().body(dto);
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> salvar(@RequestBody UserResquestDTO dto) {
        User user = new User();

        servico.recebendoDTO(dto, user);
        servico.salvar(user);
        UserResponseDTO retorno = servico.enviandoDTO(user);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(uri).body(retorno);
    }

    @PutMapping(value="/{id}")
    public ResponseEntity<UserResponseDTO> update(@PathVariable Long id, @RequestBody UserResquestDTO dto) {

        User user = servico.encontrarPorId(id);
        servico.recebendoDTO(dto, user);
        servico.salvar(user);
        UserResponseDTO retorno = servico.enviandoDTO(user);

        return ResponseEntity.ok().body(retorno);
    }

    @DeleteMapping(value="/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        servico.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
