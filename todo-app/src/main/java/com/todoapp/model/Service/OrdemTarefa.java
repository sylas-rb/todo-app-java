package com.todoapp.model.Service;

import com.todoapp.entities.Tarefa;
import com.todoapp.model.Enums.Status;
import com.todoapp.model.Interface.OrdemTarefaInterface;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrdemTarefa implements OrdemTarefaInterface {

    @Override
    public List<Tarefa> ordenarTarefaPrioridade(List<Tarefa> tarefa) {
        return tarefa.stream().sorted(Tarefa.POR_PRIORIDADE).toList();
    }

    public List<Tarefa> ordenarTarefaStatusPendente(List<Tarefa> tarefa) {
        return tarefa.stream().filter(t -> t.getStatus() == Status.PENDENTE).collect(Collectors.toList());
    }

    public List<Tarefa> ordenarTarefaStatusConcluido(List<Tarefa> tarefa) {
        return tarefa.stream().filter(t -> t.getStatus() == Status.CONCLUIDA).collect(Collectors.toList());
    }

    @Override
    public List<Tarefa> ordenarTarefaData(List<Tarefa> tarefa) {
        LocalDate dataAtual = LocalDate.now();
        return tarefa.stream().filter(t -> t.getDate().isEqual(dataAtual)).sorted(Comparator.comparing(Tarefa::getDate)).collect(Collectors.toList());
    }

    @Override
    public List<Tarefa> ordenarTarefaVencimento(List<Tarefa> tarefa) {
        LocalDate dataAtual = LocalDate.now();
        LocalDate vencendo = dataAtual.plusDays(3);
        return tarefa.stream().filter(t -> t.getVencimento() != null).filter(t -> t.getVencimento().isEqual(vencendo)).sorted(Comparator.comparing(Tarefa::getVencimento)).collect(Collectors.toList());
    }
}
