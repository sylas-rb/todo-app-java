package com.todoapp.config;

import com.todoapp.entities.Tarefa;
import com.todoapp.model.Enums.Prioridade;
import com.todoapp.model.Enums.Status;
import com.todoapp.model.Interface.TarefaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.time.LocalDate;
import java.util.Arrays;

@Configuration
@Profile("tarefa-test")
public class TarefaConfig implements CommandLineRunner {
    @Autowired
    private TarefaRepositorio tarefaRepositorio;

    @Override
    public void run(String... args) throws Exception {
        LocalDate date = LocalDate.now();
        Tarefa t1 = new Tarefa(null, "Teste1", "Teste descrição", Status.CONCLUIDA, Prioridade.BAIXA, date, null);
        Tarefa t2 = new Tarefa(null, "Teste2", "Teste descrição", Status.PENDENTE, Prioridade.MEDIA, date, date.plusDays(15L));
        Tarefa t3 = new Tarefa( null, "Teste3", "Teste descrição", Status.PENDENTE, Prioridade.ALTA, date, date.plusDays(5L));

        tarefaRepositorio.saveAll(Arrays.asList(t1,t2,t3));
    }
}
