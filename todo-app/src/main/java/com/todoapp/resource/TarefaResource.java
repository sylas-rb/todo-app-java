package com.todoapp.resource;

import com.todoapp.DTO.request.TarefaResquestDTO;
import com.todoapp.DTO.response.TarefaResponseDTO;
import com.todoapp.service.TarefasServico;
import com.todoapp.entities.Tarefa;
import com.todoapp.service.UserServico;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
    public ResponseEntity<List<TarefaResponseDTO>> findAll(Authentication authentication) {

        List<Tarefa> tarefas = servico.encontrarTudo(Long.parseLong(authentication.getName()));
        List<TarefaResponseDTO> dto = new ArrayList<>();
        for (Tarefa tarefa : tarefas) {
            dto.add(servico.enviandoDTO(tarefa));
        }

        return ResponseEntity.ok().body(dto);
    }

    @GetMapping(value="/{idTarefa}")
    public ResponseEntity<TarefaResponseDTO> findById(@PathVariable Long idTarefa, Authentication authentication) {

        Tarefa tarefa = servico.encontrarPorId(idTarefa, Long.parseLong(authentication.getName()));
        TarefaResponseDTO dto = servico.enviandoDTO(tarefa);

        return ResponseEntity.ok().body(dto);
    }

    @PostMapping()
    public ResponseEntity<TarefaResponseDTO> salvar(@RequestBody TarefaResquestDTO dto, Authentication authentication) {
        Tarefa tarefa = servico.recebendoDTO(null, dto, Long.parseLong(authentication.getName()));
        servico.salvar(tarefa);
        TarefaResponseDTO retorno = servico.enviandoDTO(tarefa);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(tarefa.getId()).toUri();

        return ResponseEntity.created(uri).body(retorno);
    }

    @PutMapping(value="/{idTarefa}")
    public ResponseEntity<TarefaResponseDTO>  update(@PathVariable Long idTarefa, @RequestBody TarefaResquestDTO dto, Authentication authentication) {
        Tarefa tarefa = servico.recebendoDTO(idTarefa, dto, Long.parseLong(authentication.getName()));
        servico.salvar(tarefa);
        TarefaResponseDTO retorno = servico.enviandoDTO(tarefa);

        return ResponseEntity.ok().body(retorno);
    }

    @DeleteMapping(value="/{idTarefa}")
    public ResponseEntity<Void> deleteById(@PathVariable Long idTarefa, Authentication authentication) {
        servico.remover(idTarefa, Long.parseLong(authentication.getName()));
        return ResponseEntity.noContent().build();
    }
}
