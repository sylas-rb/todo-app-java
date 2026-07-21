package com.todoapp.model.Interface;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.todoapp.entities.Tarefa;

import java.io.IOException;
import java.util.List;

public interface RepositorioInterface {
    void criarPasta();

    void salvarTarefaPendente(ObjectMapper mapper, List<Tarefa> tarefasRep);

    void salvarTarefaConcluido(ObjectMapper mapper, List<Tarefa> tarefasRep);

    List<Tarefa> tarefasSalvasPendente(ObjectMapper mapper) throws IOException;

    List<Tarefa> tarefasSalvasConcluido(ObjectMapper mapper) throws IOException;

    void salvandoTarefas(List<Tarefa> pendenteSalva, List<Tarefa> concluidoSalva, List<Tarefa> pendente, List<Tarefa> concluido, ObjectMapper mapper);
}
