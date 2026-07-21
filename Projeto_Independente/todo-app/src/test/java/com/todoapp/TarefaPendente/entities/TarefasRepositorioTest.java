package com.todoapp.TarefaPendente.entities;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.todoapp.entities.Tarefa;
import com.todoapp.entities.TarefasRepositorio;
import com.todoapp.model.Enums.Prioridade;
import com.todoapp.model.Enums.Status;
import com.todoapp.model.Errors.TarefaException;
import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TarefasRepositorioTest {
    private TarefasRepositorio tarefasRep;
    private ObjectMapper mapper;
    private LocalDate date;

    List<Tarefa> listTeste;
    List<Tarefa> segundaList;

    private final ByteArrayOutputStream outPut = new ByteArrayOutputStream();
    private final PrintStream OutPut = new PrintStream(outPut);
    private final PrintStream out = System.out;
    private Tarefa t1;
    private Tarefa t2;
    private Tarefa t3;

    @BeforeEach
    public void setUp() {
        date = LocalDate.now();
        tarefasRep = new TarefasRepositorio(OutPut);
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
        @DisplayName("Verificando se está adicionando o id correto")
        public void addIdTest() {
            tarefasRep.addId(t1);
            tarefasRep.addId(t2);
            tarefasRep.addId(t3);

            assertEquals(0L, t1.getId());
            assertEquals(1L, t2.getId());
            assertEquals(2L, t3.getId());
        }

        @Test
        @DisplayName("Verificando se a tarefa foi adicionada")
        public void addTarefaTest() {
            tarefasRep.addTarefa(t1, listTeste);
            tarefasRep.addTarefa(t2, listTeste);

            assertEquals(2, listTeste.size());
        }

        @Test
        @DisplayName("Verificando se esta mostrando a lista de tarefas")
        public void listarTarefaTest() {
            long teste1 = 1;
            Tarefa primeiroLista = new Tarefa(teste1, "Teste1", "Teste descrição", Status.PENDENTE, Prioridade.BAIXA, date, "25/08/2026");

            listTeste.add(primeiroLista);

            tarefasRep.listarTarefa(listTeste);

            assertEquals(primeiroLista.formatoExibicao(), outPut.toString().trim());
        }

        @Test
        @DisplayName("Verificando maior ID")
        public void maiorIdTest() {
            long primeiro = 0;
            long segundo = 1;

            t1.setId(primeiro);
            t2.setId(segundo);

            listTeste.add(t1);
            listTeste.add(t2);

            long idMaior = tarefasRep.maxIdTarefa(listTeste);

            assertEquals(segundo, idMaior);
        }

        @Test
        @DisplayName("Verificando se retorna a tarefa correta")
        public void retornarTarefaTest() {
            long id1 = 0;
            long id2 = 1;
            long id3 = 2;

            t1.setId(id1);
            t2.setId(id2);
            t3.setId(id3);

            listTeste.add(t1);
            listTeste.add(t2);
            listTeste.add(t3);

            assertEquals(t2,tarefasRep.retornarTarefa(listTeste, id2).orElseThrow());
        }

        @Test
        @DisplayName("Verificando se a tarefa foi movida com as modificações necessaria")
        public void moverATarefaTest() {
            t1.setStatus(Status.CONCLUIDA);
            t2.setStatus(Status.PENDENTE);
            t3.setStatus(Status.CONCLUIDA);

            t1.setId(1L);
            t2.setId(1L);
            t3.setId(2L);

            listTeste.add(t1);
            segundaList.add(t2);
            listTeste.add(t3);

            tarefasRep.moverATarefa(t2, segundaList, listTeste);

            assertEquals(Status.CONCLUIDA, t2.getStatus());
            assertEquals(1L, t2.getId());
            assertEquals(3, listTeste.size());
        }

        @Test
        @DisplayName("verificando se esta adicionando corretamente as tarefas")
        public void adicionaTarefaPastaTest() {
            listTeste.add(t1);
            segundaList.add(t2);
            segundaList.add(t3);

            tarefasRep.adicionarTarefaPasta(segundaList, listTeste);

            assertEquals(3, listTeste.size());
            assertTrue(listTeste.contains(t1));
            assertTrue(listTeste.contains(t2));
            assertTrue(listTeste.contains(t3));
        }
    }

    @Nested
    class CaminhoErrado {
        @Test
        @DisplayName("mostrando mensagem caso a lista esteja vazia")
        public void listarTarefaTest() {
            String messagem = "Nenhuma tarefa encontrada.";

            tarefasRep.listarTarefa(listTeste);

            assertEquals(messagem, outPut.toString().trim());
        }

        @Test
        @DisplayName("Verificando se a função retorna mensagem de erro se status for null")
        public void moverATarefaTest() {
            t1.setId(1L);
            listTeste.add(t1);

            tarefasRep.moverATarefa(t1, segundaList, listTeste);

            assertEquals("Não foi possível mover a tarefa, por favor tente novamente.", outPut.toString().trim());
        }
    }

    @Nested
    class CaminhoExcecoes {
        @Test
        @DisplayName("Exceções que podem acontecer caso Tarefa não encontrada")
        public void addIdTestNull() {
            assertThrows(TarefaException.class, () -> tarefasRep.addId(null));
        }

        @Test
        @DisplayName("Verificando se retorna do erro se lista vazia, retorno null")
        public void retornarTarefaTest() {
            long id2 = 1;

            assertThrows(TarefaException.class, () -> tarefasRep.retornarTarefa(listTeste, id2));
        }

        @Test
        @DisplayName("verificando se esta adicionando corretamente as tarefas")
        public void adicionaTarefaPastaTest() {
            segundaList.add(t2);
            segundaList.add(t3);

            tarefasRep.adicionarTarefaPasta(segundaList, null);

            assertTrue(outPut.toString().contains("Erro ao adicionar tarefas: "));
        }

        @Test
        @DisplayName("Exceções caso a lista tarefa não seja encontrada")
        public void addTarefaTest() {
            assertThrows(TarefaException.class, () -> tarefasRep.addTarefa(null, listTeste));
        }
    }
}
