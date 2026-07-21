package com.todoapp.TarefaPendente.model.persistencia;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.todoapp.entities.Tarefa;
import com.todoapp.entities.TarefasRepositorio;
import com.todoapp.model.Enums.Prioridade;
import com.todoapp.model.Enums.Status;
import com.todoapp.model.Interface.EntradaInterface;
import com.todoapp.model.Interface.RepositorioInterface;
import com.todoapp.model.Interface.TarefaFluxoInterface;
import com.todoapp.model.persistencia.Pasta;
import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class PastaTest {
    String userHome = System.getProperty("user.home");
    private TarefasRepositorio tarefasRep;
    private ObjectMapper mapper;
    private RepositorioInterface pasta;
    private EntradaInterface entrada1;
    private EntradaInterface entrada2;
    private TarefaFluxoInterface funcoes1;
    private TarefaFluxoInterface funcoes2;
    private LocalDate date;

    List<Tarefa> listTeste;
    List<Tarefa> segundaList;

    private final ByteArrayOutputStream outPut = new ByteArrayOutputStream();
    private final PrintStream OutPut = new PrintStream(outPut);
    private final PrintStream out = System.out;
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private Tarefa t1;
    private Tarefa t2;
    private Tarefa t3;

    @BeforeEach
    public void setUp() {
        date = LocalDate.now();
        tarefasRep = new TarefasRepositorio(OutPut);
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        pasta = new Pasta(tarefasRep, out, userHome);
        listTeste = new ArrayList<>();
        segundaList = new ArrayList<>();
        t1 = new Tarefa(1L, "Teste1", "Teste descrição", Status.PENDENTE, Prioridade.BAIXA, date, "indeterminado");
        t2 = new Tarefa(2L, "Teste2", "Teste descrição", Status.PENDENTE, Prioridade.BAIXA, date, "indeterminado");
        t3 = new Tarefa(3L, "Teste3", "Teste descrição", Status.PENDENTE, Prioridade.BAIXA, date, "indeterminado");
    }

    @AfterEach
    public void tearDown() {
        System.setOut(out);
        listTeste = new ArrayList<>();
        mapper = new ObjectMapper();
    }

    @Nested
    class CaminhoCerto{
        @Test
        @DisplayName("Verificando se está salvando as tarefas corretamente")
        public void salvandoTarefasTest() {
            List<Tarefa> pendenteSalva = new ArrayList<>();
            List<Tarefa> concluidoSalva = new ArrayList<>();

            segundaList.add(t1);
            segundaList.add(t2);
            listTeste.add(t1);

            pasta.salvandoTarefas(pendenteSalva, concluidoSalva, segundaList, listTeste, mapper);
        }
    }
}
