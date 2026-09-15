package com.todoapp.controller;

import com.todoapp.entities.Tarefa;
import com.todoapp.model.Enums.Prioridade;
import com.todoapp.model.Enums.Status;
import com.todoapp.model.Interface.FluxoServiceInterface;
import com.todoapp.model.Interface.OrdemTarefaInterface;
import com.todoapp.model.Interface.TarefaDAO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tarefas")
public class TarefaController {

    private final TarefaDAO repositorio;
    private final FluxoServiceInterface funcoes;
    private final OrdemTarefaInterface ordemTarefa;

    public TarefaController(TarefaDAO repositorio, FluxoServiceInterface funcoes, OrdemTarefaInterface ordemTarefa) {
        this.repositorio = repositorio;
        this.funcoes = funcoes;
        this.ordemTarefa = ordemTarefa;
    }

    @GetMapping("/{id}")
    public Tarefa tarefaReferente(@PathVariable long id) {
        return repositorio.encontrarPorId(id);
    }

    @GetMapping("/pendente")
    public List<Tarefa> pendente() {
        return ordemTarefa.ordenarTarefaStatusPendente(repositorio.encontrarTodos());
    }

    @GetMapping("/concluido")
    public List<Tarefa> concluido() {
        return ordemTarefa.ordenarTarefaStatusConcluido(repositorio.encontrarTodos());
    }

    @GetMapping("/data")
    public List<Tarefa> ListarData() {
        return ordemTarefa.ordenarTarefaData(repositorio.encontrarTodos());
    }

    @GetMapping("/vencimento")
    public List<Tarefa> ListarPendenteVencimento() {
        return ordemTarefa.ordenarTarefaVencimento(repositorio.encontrarTodos());
    }

    @GetMapping("/prioridade")
    public List<Tarefa> ListarPendentePrioridade() {
        return ordemTarefa.ordenarTarefaPrioridade(repositorio.encontrarTodos());
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
    public void marcaTarefa(@PathVariable Long id, @PathVariable Status status) {
        funcoes.marcaTarefa(id, status);
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
        repositorio.deletePorId(id);
    }
}
