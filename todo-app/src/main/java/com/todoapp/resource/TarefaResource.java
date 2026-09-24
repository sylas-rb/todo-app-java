package com.todoapp.resource;

import com.todoapp.service.TarefasServico;
import com.todoapp.entities.Tarefa;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/tarefas")
public class TarefaResource {
    private final TarefasServico servico;

    public TarefaResource(TarefasServico servico) {
        this.servico = servico;
    }

    @GetMapping
    public ResponseEntity<List<Tarefa>> findAll() {
        List<Tarefa> lista = servico.encontrarTudo();
        return ResponseEntity.ok().body(lista);
    }

    @GetMapping(value="/{id}")
    public ResponseEntity<Tarefa> findById(@PathVariable Long id) {
        Tarefa tarefa = servico.encontrarPorId(id);
        return ResponseEntity.ok().body(tarefa);
    }

    @PostMapping
    public ResponseEntity<Tarefa> salvar(@RequestBody Tarefa tarefa) {
        tarefa = servico.salvar(tarefa);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(tarefa.getId()).toUri();
        return ResponseEntity.created(uri).body(tarefa);
    }

    @PutMapping(value="/{id}")
    public ResponseEntity<Tarefa>  update(@PathVariable Long id, @RequestBody Tarefa tarefaAtualizado) {
        Tarefa tarefa = servico.atualizar(id, tarefaAtualizado);
        return ResponseEntity.ok().body(tarefa);
    }

    @DeleteMapping(value="/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        servico.remover(id);
        return ResponseEntity.noContent().build();
    }
}
