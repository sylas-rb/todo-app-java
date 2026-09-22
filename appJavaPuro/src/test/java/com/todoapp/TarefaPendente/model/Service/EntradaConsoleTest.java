package com.todoapp.TarefaPendente.model.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.todoapp.entities.Tarefa;
import com.todoapp.model.Interface.EntradaInterface;
import com.todoapp.model.Interface.SaidaInterface;
import com.todoapp.model.ordem.EntradaConsole;
import com.todoapp.model.ordem.SaidaConsole;
import org.junit.jupiter.api.*;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class EntradaConsoleTest {
    private ObjectMapper mapper;
    private EntradaInterface entrada1;
    private EntradaInterface entrada2;
    List<Tarefa> listTeste;

    private final PrintStream out = System.out;
    private SaidaInterface Out;
    @BeforeEach
    public void setUp() {
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        Out = new SaidaConsole();
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
            entrada1 = new EntradaConsole(sc, Out);

            byte valor = entrada1.lerByte();

            assertEquals(2, valor);
        }

        @Test
        @DisplayName("Verificando se a função está validando o valor certo em long")
        public void validandoLongTest() {
            Scanner scLong = new Scanner("2");
            entrada1 = new EntradaConsole(scLong, Out);

            long valor = entrada1.lerLong();

            assertEquals(2L, valor);
        }

        @Test
        @DisplayName("Verificando retorno true de adicionar nova tarefa")
        public void ficarTarefaTest(){
            Scanner test = new Scanner("S");
            Scanner test2 = new Scanner(" S ");
            Scanner test3 = new Scanner("s");

            EntradaInterface entradaTest = new EntradaConsole(test, Out);
            EntradaInterface entradaTest2 = new EntradaConsole(test2, Out);
            EntradaInterface entradaTest3 = new EntradaConsole(test3, Out);


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

            EntradaInterface entradaTest = new EntradaConsole(test, Out);
            EntradaInterface entradaTest2 = new EntradaConsole(test2, Out);
            EntradaInterface entradaTest3 = new EntradaConsole(test3, Out);

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

            entrada1 = new EntradaConsole(maiuscula, Out);
            entrada2 = new EntradaConsole(minuscula, Out);

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

            entrada1 = new EntradaConsole(maiuscula, Out);
            entrada2 = new EntradaConsole(minuscula, Out);

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
            entrada1 = new EntradaConsole(sc, Out);

            assertThrows(NumberFormatException.class, ()-> entrada1.lerByte());
        }

        @Test
        @DisplayName("Testando mensagem de retorno de frase se a entrada vier errado.")
        public void ficarTarefaTest(){
            Scanner test = new Scanner("Nao\n sim\n errado\n s\n");
            entrada1 = new EntradaConsole(test, Out);

            boolean teste =  entrada1.ficarTarefa();

            assertTrue(teste, "tem que retornar mensagem de erro antes de retorna true");
        }

        @Test
        @DisplayName("Verificando se a mensagem de erro está sendo mostrado se entrada errada")
        public void confimadoTest () {
            Scanner maiuscula = new Scanner("as\n a\n S");
            entrada1 = new EntradaConsole(maiuscula, Out);

            boolean respostaMai = entrada1.confirmado();

            assertTrue(respostaMai);
        }
    }
}
