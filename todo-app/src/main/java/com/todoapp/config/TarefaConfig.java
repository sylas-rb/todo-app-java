package com.todoapp.config;

import com.todoapp.entities.Tarefa;
import com.todoapp.entities.User;
import com.todoapp.model.Enums.Prioridade;
import com.todoapp.model.Enums.Status;
import com.todoapp.model.Interface.TarefaRepositorio;
import com.todoapp.model.Interface.UserRepositorio;
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

    @Autowired
    private UserRepositorio userRepositorio;

    @Override
    public void run(String... args) throws Exception {
        LocalDate date = LocalDate.now();

        User us1 = new User (null, "Maria", "maria@gmail.com");
        User us2 = new User (null, "Pedro", "pedro@gmail.com");

        userRepositorio.saveAll(Arrays.asList(us1, us2));

        Tarefa t1 = new Tarefa(null, "Teste1", "Teste descrição", Status.CONCLUIDA, Prioridade.BAIXA, date, null, us1);
        Tarefa t2 = new Tarefa(null, "Teste2", "Teste descrição", Status.PENDENTE, Prioridade.MEDIA, date, date.plusDays(Prioridade.MEDIA.getDiasVencimento()), us2);
        Tarefa t3 = new Tarefa( null, "Teste3", "Teste descrição", Status.PENDENTE, Prioridade.ALTA, date, date.plusDays(Prioridade.ALTA.getDiasVencimento()), us1);

        tarefaRepositorio.saveAll(Arrays.asList(t1,t2,t3));

        us1.getTarefas().add(t1);
        us2.getTarefas().add(t2);
        us1.getTarefas().add(t3);
    }
}
