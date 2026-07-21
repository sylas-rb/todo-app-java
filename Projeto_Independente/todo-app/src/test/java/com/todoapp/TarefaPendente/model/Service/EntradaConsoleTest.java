package com.todoapp.TarefaPendente.model.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.todoapp.entities.Tarefa;
import com.todoapp.entities.TarefasRepositorio;
import com.todoapp.model.Enums.Prioridade;
import com.todoapp.model.Enums.Status;
import com.todoapp.model.Interface.EntradaInterface;
import com.todoapp.model.Interface.RepositorioInterface;
import com.todoapp.model.Interface.TarefaFluxoInterface;
import com.todoapp.model.Service.EntradaConsole;
import com.todoapp.model.persistencia.Pasta;
import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class EntradaConsoleTest {
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
    class CaminhoCerto {
        @Test
        @DisplayName("Verificando se a função está retornando o valor certo em byte")
        public void validandoByteTest() {
            Scanner sc = new Scanner("2");
            entrada1 = new EntradaConsole(sc, OutPut);

            byte valor = entrada1.lerByte();

            assertEquals(2, valor);
        }

        @Test
        @DisplayName("Verificando se a função está validando o valor certo em long")
        public void validandoLongTest() {
            Scanner scLong = new Scanner("2");
            entrada1 = new EntradaConsole(scLong, OutPut);

            long valor = entrada1.lerLong();

            assertEquals(2L, valor);
        }

        @Test
        @DisplayName("Verificando retorno true de adicionar nova tarefa")
        public void ficarTarefaTest(){
            Scanner test = new Scanner("S");
            Scanner test2 = new Scanner(" S ");
            Scanner test3 = new Scanner("s");

            EntradaInterface entradaTest = new EntradaConsole(test, OutPut);
            EntradaInterface entradaTest2 = new EntradaConsole(test2, OutPut);
            EntradaInterface entradaTest3 = new EntradaConsole(test3, OutPut);


            boolean teste = entradaTest.ficarTarefa();
            boolean teste2 =  entradaTest2.ficarTarefa();
            boolean teste3 = entradaTest3.ficarTarefa();

            assertTrue(teste, "Teste tem que retorna verdadeiro com 'S'.");
            assertTrue(teste2, "Teste tem que retorna verdadeiro com 'S' entre espaços.");
            assertTrue(teste3, "Teste tem que retorna verdadeiro com 's'.");
        }

        @Test
        @DisplayName("Verificando retorno false de adicionar nova tarefa.")
        public void ficarTarefaTest2(){
            Scanner test = new Scanner("N");
            Scanner test2 = new Scanner(" N ");
            Scanner test3 = new Scanner("n");

            EntradaInterface entradaTest = new EntradaConsole(test, OutPut);
            EntradaInterface entradaTest2 = new EntradaConsole(test2, OutPut);
            EntradaInterface entradaTest3 = new EntradaConsole(test3, OutPut);

            boolean teste =  entradaTest.ficarTarefa();
            boolean teste2 = entradaTest2.ficarTarefa();
            boolean teste3 = entradaTest3.ficarTarefa();

            assertFalse(teste, "Teste tem que retorna false com 'N'.");
            assertFalse(teste2, "Teste tem que retorna false com 'N' com espaço.");
            assertFalse(teste3, "Teste tem que retorna false com 'n'.");
        }

        @Test
        @DisplayName("verificando o retorno da confirmação com base na resposta, caminho True")
        public void confirmadoTrueTest() {
            Scanner maiuscula = new Scanner("S");
            Scanner minuscula = new Scanner("s");

            entrada1 = new EntradaConsole(maiuscula, OutPut);
            entrada2 = new EntradaConsole(minuscula, OutPut);

            boolean respostaMai = entrada1.confirmado();
            boolean respostaMinus = entrada2.confirmado();

            assertTrue(respostaMai);
            assertTrue(respostaMinus);
        }

        @Test
        @DisplayName("verificando o retorno da confirmação com base na resposta, caminho false")
        public void confirmadoFalseTest() {
            Scanner maiuscula = new Scanner("N");
            Scanner minuscula = new Scanner("n");

            entrada1 = new EntradaConsole(maiuscula, OutPut);
            entrada2 = new EntradaConsole(minuscula, OutPut);

            boolean respostaMai = entrada1.confirmado();
            boolean respostaMinus = entrada2.confirmado();

            assertFalse(respostaMai);
            assertFalse(respostaMinus);
        }
    }

    @Nested
    class CaminhoErrado {
        @Test
        @DisplayName("Testando se a função está retornando o valor certo")
        public void validandoByteTest() {
            Scanner sc = new Scanner("N");
            entrada1 = new EntradaConsole(sc, OutPut);

            byte valor = entrada1.lerByte();

            assertEquals(-1, valor);
        }

        @Test
        @DisplayName("Testando mensagem de retorno de frase se a entrada vier errado.")
        public void ficarTarefaTest(){
            Scanner test = new Scanner("Nao\n sim\n errado\n s\n");
            entrada1 = new EntradaConsole(test, OutPut);

            boolean teste =  entrada1.ficarTarefa();

            assertTrue(teste, "tem que retornar mensagem de erro antes de retorna true");
        }

        @Test
        @DisplayName("Verificando se a mensagem de erro está sendo mostrado se entrada errada")
        public void confimadoTest () {
            Scanner maiuscula = new Scanner("as\n a\n S");
            entrada1 = new EntradaConsole(maiuscula, OutPut);

            boolean respostaMai = entrada1.confirmado();

            assertTrue(outPut.toString().contains("Somente a letra 's' ou 'n'."));
            assertTrue(respostaMai);
        }
    }

    @Nested
    class CaminhoExcecoes {

    }
}
