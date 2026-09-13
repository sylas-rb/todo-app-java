package com.todoapp.Repository;

import com.todoapp.entities.Tarefa;
import com.todoapp.model.Enums.Prioridade;
import com.todoapp.model.Enums.Status;
import com.todoapp.model.Errors.TarefaException;
import com.todoapp.model.Interface.OrdemTarefaInterface;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class TarefasRepositorio {
    private final List<Tarefa> pendente = new ArrayList<>();
    private final List<Tarefa> concluida = new ArrayList<>();
    private final OrdemTarefaInterface ordem;

    private long incrementoId = 0;

    public TarefasRepositorio(List<Tarefa> tarefasPendente, List<Tarefa> tarefasConcluida, OrdemTarefaInterface ordem) {
        if (tarefasPendente != null) {
            this.pendente.addAll(tarefasPendente);
        }
        if (tarefasConcluida != null) {
            this.concluida.addAll(tarefasConcluida);
        }

        this.ordem = ordem;
    }

    public List<Tarefa> getTarefaPendentes() {
        return pendente;
    }

    public List<Tarefa> getTarefaConcluidas() {
        return concluida;
    }

    public List<Tarefa> tarefaDataPendente() {
        return ordem.ordenarTarefaData(pendente);
    }

    public List<Tarefa> tarefaDataConcluidas() {
        return ordem.ordenarTarefaData(concluida);
    }

    public List<Tarefa> tarefaVencimentoPendente() {
        return ordem.ordenarTarefaVencimento(pendente);
    }

    public List<Tarefa> tarefaPrioridadePendente() {
        return ordem.ordenarTarefaPrioridade(pendente);
    }

    public List<Tarefa> tarefaPrioridadeConcluidas() {
        return ordem.ordenarTarefaPrioridade(concluida);
    }

    public boolean removeTarefa(long id) {
        Optional<Tarefa> t = retornarTarefa(id);
        if (t.isPresent()){
            if (pendente.remove(t.get())) return true;
            if (concluida.remove(t.get())) return true;
        }
        return false;
    }

    public void addId(Tarefa t) {
        if (t == null) {
            throw new TarefaException("Não foi possível encontrar tarefa concluído.");
        }
        t.setId(this.incrementoId++);
    }

    public long maxIdTarefa() {
        return Math.max(pendente.stream().mapToLong(Tarefa :: getId).max().orElse(0L), concluida.stream().mapToLong(Tarefa :: getId).max().orElse(0L));
    }

    public void addTarefa(Tarefa tarefa) {
        if (tarefa == null) {
            throw new TarefaException("Não foi possível adicionar tarefa para tarefa concluída, tarefa vazia.");
        }
        if (tarefa.getStatus() == Status.CONCLUIDA) {
            concluida.add(tarefa);
        } else {
            pendente.add(tarefa);
        }
    }

    public Optional<Tarefa> retornarTarefa(long id) {
            Optional<Tarefa> t = pendente.stream().filter(tarefa -> tarefa.getId() == id).findFirst();
            if (t.isPresent()) return t;
            return concluida.stream().filter(tarefa -> tarefa.getId() == id).findFirst();
    }

    public boolean moverATarefa(Tarefa tarefaAlvo) {
        if (pendente.remove(tarefaAlvo)) {
            tarefaAlvo.concluidaOuPendente();
            tarefaAlvo.setVencimento(null);
            concluida.add(tarefaAlvo);
            return true;
        } else if (concluida.remove(tarefaAlvo)) {
            tarefaAlvo.concluidaOuPendente();
            tarefaAlvo.incrementandoVencimento();
            pendente.add(tarefaAlvo);
            return true;
        }
        return false;
    }
}
