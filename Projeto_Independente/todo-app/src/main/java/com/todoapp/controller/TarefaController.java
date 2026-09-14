package com.todoapp.controller;

import com.todoapp.Repository.TarefasRepositorio;
import com.todoapp.entities.Tarefa;
import com.todoapp.model.Enums.Prioridade;
import com.todoapp.model.Interface.FluxoServiceInterface;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tarefas")
public class TarefaController {

    private final TarefasRepositorio repositorio;
    private final FluxoServiceInterface funcoes;

    public TarefaController(TarefasRepositorio repositorio, FluxoServiceInterface funcoes) {
        this.repositorio = repositorio;
        this.funcoes = funcoes;
    }

    @GetMapping("/tarefa/{id}")
    public String tarefaReferente(@PathVariable long id) {
        return funcoes.mostrarTarefa(id);
    }

    @GetMapping("/pendentes/data")
    public List<Tarefa> ListarPendenteData() {
        return repositorio.tarefaDataPendente();
    }

    @GetMapping("/pendentes/vencimento")
    public List<Tarefa> ListarPendenteVencimento() {
        return repositorio.tarefaVencimentoPendente();
    }

    @GetMapping("/pendentes/prioridade")
    public List<Tarefa> ListarPendentePrioridade() {
        return repositorio.tarefaPrioridadePendente();
    }

    @GetMapping("/concluida/data")
    public List<Tarefa> ListarConcluidaData() {
        return repositorio.tarefaDataConcluidas();
    }

    @GetMapping("/concluida/prioridade")
    public List<Tarefa> ListarConcluidaPrioridade() {
        return repositorio.tarefaPrioridadeConcluidas();
    }

    @PostMapping("/adicionar_tarefa")
    public void criarTarefa(@RequestBody Tarefa tarefa) {
        funcoes.criarTarefas(tarefa);
    }

    @PatchMapping("/prioridade/{id}")
    public Tarefa modificarPrioridade(@PathVariable Long id, @PathVariable Prioridade prioridade) {
        return funcoes.mudancaPrioridade(id, prioridade);
    }

    @PatchMapping("/marca/{id}")
    public void marcaTarefa(@PathVariable Long id) {
        funcoes.marcaTarefa(id);
    }

    @PatchMapping("/modificar_titulo/{id}")
    public Tarefa mudancaTitulo(@PathVariable("id") long id, @RequestBody String titulo) {
        return funcoes.mudancaTitulo(id, titulo);
    }

    @PatchMapping("/modificar_Descricao/{id}")
    public Tarefa mudancaDescricao(@PathVariable("id") long id, @RequestBody String descricao) {
        return funcoes.mudancaDescricao(id, descricao);
    }

    @DeleteMapping("/delete/{id}")
    public void deletarTarefa(@PathVariable("id") long id) {
        funcoes.removendoTarefa(id);
    }
}
