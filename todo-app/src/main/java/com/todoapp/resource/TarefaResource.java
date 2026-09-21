package com.todoapp.resource;

import com.todoapp.Repository.TarefasServico;
import com.todoapp.entities.Tarefa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tarefas")
public class TarefaResource {
    @Autowired
    private TarefasServico servico;

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
