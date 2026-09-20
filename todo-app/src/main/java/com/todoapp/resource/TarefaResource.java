package com.todoapp.resource;

import com.todoapp.Repository.TarefasServico;
import com.todoapp.entities.Tarefa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
