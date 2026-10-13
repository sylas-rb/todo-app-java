package com.todoapp.resource;

import com.todoapp.DTO.request.TarefaResquestDTO;
import com.todoapp.DTO.response.TarefaResponseDTO;
import com.todoapp.entities.User;
import com.todoapp.service.TarefasServico;
import com.todoapp.entities.Tarefa;
import com.todoapp.service.UserServico;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/tarefas")
public class TarefaResource {
    private final TarefasServico servico;
    private final UserServico userServico;

    public TarefaResource(TarefasServico servico,  UserServico userServico) {
        this.servico = servico;
        this.userServico = userServico;
    }

    @GetMapping
    public ResponseEntity<List<TarefaResponseDTO>> findAll() {

        List<Tarefa> tarefas = servico.encontrarTudo();
        List<TarefaResponseDTO> dto = new ArrayList<>();
        for (Tarefa tarefa : tarefas) {
            dto.add(servico.enviandoDTO(tarefa));
        }

        return ResponseEntity.ok().body(dto);
    }

    @GetMapping(value="/{id}")
    public ResponseEntity<TarefaResponseDTO> findById(@PathVariable Long id) {

        Tarefa tarefa = servico.encontrarPorId(id);
        TarefaResponseDTO dto = servico.enviandoDTO(tarefa);

        return ResponseEntity.ok().body(dto);
    }

    @PostMapping
    public ResponseEntity<TarefaResponseDTO> salvar(@RequestBody TarefaResquestDTO dto) {
        User user = userServico.encontrarPorId(dto.idUser());

        Tarefa tarefa = servico.recebendoDTO(null, dto, user);
        servico.salvar(tarefa);
        TarefaResponseDTO retorno = servico.enviandoDTO(tarefa);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(tarefa.getId()).toUri();

        return ResponseEntity.created(uri).body(retorno);
    }

    @PutMapping(value="/{id}")
    public ResponseEntity<TarefaResponseDTO>  update(@PathVariable Long id, @RequestBody TarefaResquestDTO dto) {
        User user = userServico.encontrarPorId(dto.idUser());

        Tarefa tarefa = servico.recebendoDTO(id, dto, user);
        servico.salvar(tarefa);
        TarefaResponseDTO retorno = servico.enviandoDTO(tarefa);

        return ResponseEntity.ok().body(retorno);
    }

    @DeleteMapping(value="/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        servico.remover(id);
        return ResponseEntity.noContent().build();
    }
}
