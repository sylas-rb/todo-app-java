package com.todoapp.model.Interface;

import com.todoapp.entities.Tarefa;

import java.util.List;

public interface TarefaFluxoInterface {
    void estaConcluida(Tarefa tarefa);
    void nivelPrioridade(Tarefa tarefa);
    boolean confirmacaoTarefa(List<Tarefa> tarefa, long id);
    void marcaTarefa(List<Tarefa> remetente, List<Tarefa> destinatario);
    boolean confirmarModificacao(String mudanca, byte n, Tarefa tarefa);
    void modificaTarefa(Tarefa tarefa);
    void removendoTarefa(List<Tarefa> tarefa);
}
