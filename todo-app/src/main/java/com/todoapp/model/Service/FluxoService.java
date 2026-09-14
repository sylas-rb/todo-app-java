package com.todoapp.model.Service;

import com.todoapp.entities.Tarefa;
import com.todoapp.Repository.TarefasRepositorio;
import com.todoapp.model.Enums.Prioridade;
import com.todoapp.model.Errors.TarefaException;
import com.todoapp.model.Interface.EntradaInterface;
import com.todoapp.model.Interface.SaidaInterface;
import com.todoapp.model.Interface.FluxoServiceInterface;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class FluxoService implements FluxoServiceInterface {
    private final EntradaInterface entrada;
    private final TarefasRepositorio tarefasRep;
    private final SaidaInterface out;

    public FluxoService(EntradaInterface entrada, TarefasRepositorio tarefasRep, SaidaInterface out) {
        this.entrada = entrada;
        this.tarefasRep = tarefasRep;
        this.out = out;
    }

    public void criarTarefas(Tarefa tarefa) {
        LocalDate date = LocalDate.now();

        if (tarefa.getTitulo().isEmpty()) {
            throw new TarefaException("Titulo não pode ser vazio.");
        }

        if (tarefa.getDescricao().isEmpty()) {
            throw new TarefaException("A descrição não pode ser vazio");
        }

        tarefa.setDate(date);

        tarefa.tarefaPrioridade(tarefa.getPrioridade());


        tarefasRep.addId(tarefa);

        tarefasRep.addTarefa(tarefa);
    }

    public String mostrarTarefa(long id) {
        Tarefa tarefa = tarefasRep.retornarTarefa(id).orElseThrow(() -> new TarefaException("Não foi possível encontrar a tarefa com id: " + id));
        return tarefa.formatoExibicao();
    }

    public Tarefa mudancaPrioridade(long id, Prioridade prioridade) {
        Tarefa tarefa = tarefasRep.retornarTarefa(id).orElseThrow(() -> new TarefaException("Não foi possível encontrar a tarefa com id: " + id));

        tarefa.tarefaPrioridade(prioridade);

        return tarefa;
    }

    public Tarefa mudancaTitulo(long id, String titulo) {
        Tarefa tarefa = tarefasRep.retornarTarefa(id).orElseThrow(() -> new TarefaException("Não foi possível encontrar a tarefa com id: " + id));

        tarefa.setTitulo(titulo);

        return tarefa;
    }

    public Tarefa mudancaDescricao(long id, String descricao) {
        Tarefa tarefa = tarefasRep.retornarTarefa(id).orElseThrow(() -> new TarefaException("Não foi possível encontrar a tarefa com id: " + id));

        tarefa.setDescricao(descricao);

        return tarefa;
    }

    public void marcaTarefa(long id) {
        Tarefa tarefaAlvo = tarefasRep.retornarTarefa(id).orElseThrow(() -> new TarefaException("Tarefa não encontrada"));
        boolean conclusao = tarefasRep.moverATarefa(tarefaAlvo);
        if (!conclusao) {
            throw new TarefaException("Erro para concluir a marcação.");
        }
    }

    public String removendoTarefa(long id) {
        if (tarefasRep.removeTarefa(id)) {
            return "Tarefa removida com sucesso.";
        } else {
            throw new TarefaException("Tarefa id " + id + " não pode ser encontrada.");
        }
    }
}
