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
        List<TarefaResponseDTO> dto = tarefas.stream().map(tarefa -> new TarefaResponseDTO(
                tarefa.getId(),
                tarefa.getTitulo(),
                tarefa.getDescricao(),
                tarefa.getStatus(),
                tarefa.getPrioridade(),
                tarefa.getDate(),
                tarefa.getVencimento()
        )).toList();
        return ResponseEntity.ok().body(dto);
    }

    @GetMapping(value="/{id}")
    public ResponseEntity<TarefaResponseDTO> findById(@PathVariable Long id) {
        Tarefa tarefa = servico.encontrarPorId(id);
        TarefaResponseDTO dto = new TarefaResponseDTO(
                tarefa.getId(),
                tarefa.getTitulo(),
                tarefa.getDescricao(),
                tarefa.getStatus(),
                tarefa.getPrioridade(),
                tarefa.getDate(),
                tarefa.getVencimento()
        );
        return ResponseEntity.ok().body(dto);
    }

    @PostMapping
    public ResponseEntity<Tarefa> salvar(@RequestBody TarefaResquestDTO dto) {
        User user = userServico.encontrarPorId(dto.idUser());
        Tarefa tarefa = new Tarefa();

        tarefa.setTitulo(dto.titulo());
        tarefa.setDescricao(dto.descricao());
        tarefa.setStatus(dto.status());
        tarefa.setPrioridade(dto.prioridade());
        tarefa.setDate(dto.date());
        tarefa.setVencimento();
        tarefa.setUsuario(user);

        tarefa =  servico.salvar(tarefa);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(tarefa.getId()).toUri();
        return ResponseEntity.created(uri).body(tarefa);
    }

    @PutMapping(value="/{id}")
    public ResponseEntity<Tarefa>  update(@PathVariable Long id, @RequestBody TarefaResquestDTO dto) {
        Tarefa tarefa = servico.encontrarPorId(id);

        tarefa.setTitulo(dto.titulo());
        tarefa.setDescricao(dto.descricao());
        tarefa.setStatus(dto.status());
        tarefa.setPrioridade(dto.prioridade());
        tarefa.setDate(dto.date());
        tarefa.setVencimento();
        tarefa.setUsuario(userServico.encontrarPorId(dto.idUser()));

        servico.salvar(tarefa);
        return ResponseEntity.ok().body(tarefa);
    }

    @DeleteMapping(value="/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        servico.remover(id);
        return ResponseEntity.noContent().build();
    }
}
