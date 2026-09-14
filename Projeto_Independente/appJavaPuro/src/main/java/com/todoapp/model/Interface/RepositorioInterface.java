package com.todoapp.model.Interface;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.todoapp.entities.Tarefa;

import java.io.IOException;
import java.util.List;

public interface RepositorioInterface {
    void criarPasta();

    void salvarTarefa(ObjectMapper mapper, List<Tarefa> tarefasRep);

    List<Tarefa> tarefasSalvas(ObjectMapper mapper) throws IOException;
}
