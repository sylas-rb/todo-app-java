package com.todoapp.TarefaPendente.entities;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.todoapp.entities.Tarefa;
import com.todoapp.service.TarefasRepositorio;
import com.todoapp.model.Enums.Prioridade;
import com.todoapp.model.Enums.Status;
import com.todoapp.model.Errors.TarefaException;
import com.todoapp.model.Interface.RepositorioInterface;
import com.todoapp.model.Interface.SaidaInterface;
import com.todoapp.model.ordem.SaidaConsole;
import com.todoapp.model.persistencia.Pasta;
import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class TarefasRepositorioTest {
    private TarefasRepositorio tarefasRep;
    private ObjectMapper mapper;
    private LocalDate date;
    private RepositorioInterface pasta;
    private SaidaInterface saida;

    private final ByteArrayOutputStream outPut = new ByteArrayOutputStream();
    private final PrintStream OutPut = new PrintStream(outPut);
    private final PrintStream out = System.out;
    private Tarefa t1;
    private Tarefa t2;
    private Tarefa t3;

    @BeforeEach
    public void setUp() {
        saida = new SaidaConsole();
        pasta = new Pasta(saida);
        date = LocalDate.now();
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        tarefasRep = new TarefasRepositorio(pasta);
        t1 = new Tarefa(1L, "Teste1", "Teste descrição", Status.PENDENTE, Prioridade.BAIXA, date, null);
        t2 = new Tarefa(2L, "Teste2", "Teste descrição", Status.PENDENTE, Prioridade.BAIXA, date, null);
        t3 = new Tarefa(3L, "Teste3", "Teste descrição", Status.PENDENTE, Prioridade.BAIXA, date, null);
    }

    @AfterEach
    public void tearDown() {
        System.setOut(out);
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
        @DisplayName("Verificando se insere tarefas novas")
        public void inserirTarefaTest() {
            tarefasRep.inserir(t1);
            assertEquals(t1, tarefasRep.encontrarPorId(1L));
        }

        @Test
        @DisplayName("Verificando se a tarefa foi adicionada")
        public void addTarefaTest() {
            tarefasRep.inserir(t1);
            tarefasRep.inserir(t2);

            assertEquals(2, tarefasRep.encontrarTodos().size());
        }

        @Test
        @DisplayName("Verificando se retorna o id maior")
        public void retornoIdTest() {
            tarefasRep.inserir(t1);
            tarefasRep.inserir(t2);

            assertEquals(t2.getId(), tarefasRep.retornoId());
        }

        @Test
        @DisplayName("Verificando se retorna a tarefa correta")
        public void retornarTarefaTest() {
            t1.setId(1L);
            t2.setId(2L);
            t3.setId(3L);

            tarefasRep.inserir(t1);
            tarefasRep.inserir(t2);
            tarefasRep.inserir(t3);

            assertEquals(t2,tarefasRep.encontrarPorId(2L));
        }

        @Test
        @DisplayName("verificando se esta adicionando corretamente as tarefas")
        public void adicionaTarefaPastaTest() {
            tarefasRep.inserir(t1);
            tarefasRep.inserir(t2);
            tarefasRep.inserir(t3);

            tarefasRep.salvarTarefa(mapper);

            assertEquals(3, tarefasRep.encontrarTodos().size());
        }
    }

    @Nested
    class CaminhoErrado {
        @Test
        @DisplayName("mostrando mensagem caso a lista esteja vazia")
        public void listarTarefaTest() {
            assertNull(tarefasRep.encontrarTodos());
        }

        @Test
        @DisplayName("Verificando se retorna do erro se lista vazia, retorno null")
        public void retornarTarefaTest() {
            long id2 = 1;

            assertNull(tarefasRep.encontrarPorId(id2));
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
        @DisplayName("verificando se esta adicionando corretamente as tarefas")
        public void adicionaTarefaPastaTest() {
            assertThrows(TarefaException.class, ()-> tarefasRep.inserir(null));
        }

        @Test
        @DisplayName("verificando se retorna erro se não encontrar o id para deletar")
        public void deletarTarefaTest() {
            assertThrows(TarefaException.class, ()-> tarefasRep.deletePorId(1L));
        }
    }
}
