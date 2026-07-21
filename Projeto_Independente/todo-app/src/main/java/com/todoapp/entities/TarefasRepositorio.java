package com.todoapp.entities;

import com.todoapp.model.Errors.TarefaException;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TarefasRepositorio {
    private final List<Tarefa> pendente = new ArrayList<>();
    private final List<Tarefa> concluida = new ArrayList<>();

    private final PrintStream out;

    private long incrementoId = 0;

    public TarefasRepositorio(PrintStream out) {
        this.out = out;
    }

    public List<Tarefa> getTarefaPendentes() {
        return pendente;
    }

    public List<Tarefa> getTarefaConcluidas() {
        return concluida;
    }

    public void setIncrementoId(long incrementoId) {
        this.incrementoId = incrementoId;
    }

    public void addId(Tarefa t) {
        if (t == null) {
            throw new TarefaException("Não foi possível encontrar tarefa concluído.");
        }
        t.setId(this.incrementoId++);
    }

    public long maxIdTarefa(List<Tarefa> tarefas) {
        return tarefas.stream().mapToLong(Tarefa::getId).max().orElse(0L);
    }

    public void addTarefa(Tarefa tarefa, List<Tarefa> tarefas) {
        if (tarefa == null) {
            throw new TarefaException("Não foi possível adicionar tarefa para tarefa concluída, tarefa vazia.");
        }
        tarefas.add(tarefa);
    }

    public void listarTarefa(List<Tarefa> tarefas) {
        List<Tarefa> tarefasOrdenadas = tarefas.stream().sorted(Tarefa.POR_PRIORIDADE).toList();
        if (tarefasOrdenadas.isEmpty()) {
            out.println("Nenhuma tarefa encontrada.");
        } else {
            tarefasOrdenadas.forEach(t -> out.println(t.formatoExibicao()));
        }
    }

    public Optional<Tarefa> retornarTarefa(List<Tarefa> tarefas, long id) {
        if (!tarefas.isEmpty()) {
            return tarefas.stream().filter(tarefa -> tarefa.getId() == id).findFirst();
        } else {
            throw new TarefaException("Não foi possível encontrar tarefa.");
        }
    }

    public boolean moverATarefa(Tarefa tarefaAlvo, List<Tarefa> remetente, List<Tarefa> destinatario) {
        if (remetente.remove(tarefaAlvo)) {
            tarefaAlvo.concluidaOuPendente();
            addTarefa(tarefaAlvo, destinatario);
            return true;
        }
        out.println("Não foi possível mover a tarefa, por favor tente novamente.");
        return false;
    }

    public void adicionarTarefaPasta(List<Tarefa> remetente, List<Tarefa> destinatario) {
        try {
            destinatario.addAll(remetente);
        } catch (Exception e) {
            out.println("Erro ao adicionar tarefas: " + e.getMessage());
        }
    }
}
