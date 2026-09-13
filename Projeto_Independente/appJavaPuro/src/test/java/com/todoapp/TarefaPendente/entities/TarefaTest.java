package com.todoapp.TarefaPendente.entities;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.todoapp.entities.Tarefa;
import com.todoapp.Repository.TarefasRepositorio;
import com.todoapp.model.Enums.Prioridade;
import com.todoapp.model.Enums.Status;
import com.todoapp.model.Errors.TarefaException;
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

import static org.junit.jupiter.api.Assertions.*;

public class TarefaTest {
    String userHome = System.getProperty("user.home");
    private ObjectMapper mapper;
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
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
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
    class CaminhoCerto {
        @Test
        @DisplayName("Verificando se a função troca de status da tarefa")
        public void concluidaOuPendenteTest() {
            t1.setStatus(Status.PENDENTE);
            t2.setStatus(Status.CONCLUIDA);

            t1.concluidaOuPendente();
            t2.concluidaOuPendente();

            assertEquals(Status.CONCLUIDA, t1.getStatus());
            assertEquals(Status.PENDENTE, t2.getStatus());
        }

        @Test
        @DisplayName("verificando se a mundança de status")
        public void tarefaStatusTest() {
            t1.setStatus(Status.CONCLUIDA);

            assertEquals(Status.CONCLUIDA, t1.getStatus());
        }

        @Test
        @DisplayName("verificar se a prioridade e o vencimento está voltando certo")
        public void tarefaPrioridadeTest() {
            LocalDate date = LocalDate.now();
            LocalDate dateAlta = date.plusDays(7);
            LocalDate dateMedia = date.plusDays(15);

            t1.tarefaPrioridade(Prioridade.ALTA);
            t2.tarefaPrioridade(Prioridade.MEDIA);
            t3.tarefaPrioridade(Prioridade.BAIXA);

            assertEquals(Prioridade.ALTA, t1.getPrioridade());
            assertEquals(dateAlta.format(dtf), t1.getVencimento());
            assertEquals(Prioridade.MEDIA, t2.getPrioridade());
            assertEquals(dateMedia.format(dtf), t2.getVencimento());
            assertEquals(Prioridade.BAIXA, t3.getPrioridade());
            assertEquals("Indeterminado", t3.getVencimento());
        }
    }

    @Nested
    class CaminhoExcecoes {
        @Test
        @DisplayName("Teste para ter a troca de status da tarefa")
        public void concluidaOuPendenteTest() {
            t1 = new Tarefa(1L, "Teste1", "Teste descrição", null, Prioridade.BAIXA, date, "indeterminado");

            assertThrows(TarefaException.class, () -> t1.concluidaOuPendente());
            assertNull(t1.getStatus());
        }

        @Test
        @DisplayName("verificando se a mundança de status")
        public void tarefaStatusTest() {
            assertThrows(TarefaException.class, ()-> t1.tarefaStatus(null));
        }
    }
}