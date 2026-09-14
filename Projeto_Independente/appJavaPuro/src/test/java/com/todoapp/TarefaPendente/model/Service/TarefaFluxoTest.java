package com.todoapp.TarefaPendente.model.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.todoapp.entities.Tarefa;
import com.todoapp.Repository.TarefasRepositorio;
import com.todoapp.model.Enums.Prioridade;
import com.todoapp.model.Enums.Status;
import com.todoapp.model.Errors.TarefaException;
import com.todoapp.model.Interface.EntradaInterface;
import com.todoapp.model.Interface.RepositorioInterface;
import com.todoapp.model.Interface.SaidaInterface;
import com.todoapp.model.Interface.TarefaFluxoInterface;
import com.todoapp.model.Service.EntradaConsole;
import com.todoapp.model.Service.SaidaConsole;
import com.todoapp.model.Service.TarefaFluxo;
import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class TarefaFluxoTest {
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
    private SaidaInterface OutPut;
    private Tarefa t1;
    private Tarefa t2;
    private Tarefa t3;

    @BeforeEach
    public void setUp() {
        date = LocalDate.now();
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        OutPut = new SaidaConsole();
        tarefasRep = new TarefasRepositorio(listTeste, pasta);
        listTeste = new ArrayList<>();
        segundaList = new ArrayList<>();
        t1 = new Tarefa(1L, "Teste1", "Teste descrição", Status.PENDENTE, Prioridade.BAIXA, date, null);
        t2 = new Tarefa(2L, "Teste2", "Teste descrição", Status.PENDENTE, Prioridade.BAIXA, date, null);
        t3 = new Tarefa(3L, "Teste3", "Teste descrição", Status.PENDENTE, Prioridade.BAIXA, date, null);
    }

    @AfterEach
    public void tearDown() {
        listTeste = new ArrayList<>();
        mapper = new ObjectMapper();
    }

    @Nested
    class CaminhoCorreto {
        @Test
        @DisplayName("Verificando retorno true para concluir tarefa")
        public void estaConcluidaTest(){
            Scanner test = new Scanner("S");
            Scanner test2 = new Scanner("s");

            entrada1 = new EntradaConsole(test, OutPut);
            entrada2 = new EntradaConsole(test2, OutPut);

            funcoes1 = new TarefaFluxo(entrada1, tarefasRep, OutPut);
            funcoes2 = new TarefaFluxo(entrada2, tarefasRep, OutPut);

            funcoes1.estaConcluida(t1);
            funcoes2.estaConcluida(t2);

            assertEquals(Status.CONCLUIDA, t1.getStatus());
            assertEquals(Status.CONCLUIDA, t2.getStatus());
        }

        @Test
        @DisplayName("Verificando retorno false para não concluir tarefa")
        public void estaConcluidaTest2(){
            Tarefa t1 = new Tarefa();
            Tarefa t2 = new Tarefa();

            Scanner test = new Scanner("N");
            Scanner test2 = new Scanner("n");

            entrada1 = new EntradaConsole(test, OutPut);
            entrada2 = new EntradaConsole(test2, OutPut);

            funcoes1 = new TarefaFluxo(entrada1, tarefasRep, OutPut);
            funcoes2 = new TarefaFluxo(entrada2, tarefasRep, OutPut);

            funcoes1.estaConcluida(t1);
            funcoes2.estaConcluida(t2);

            assertEquals(Status.PENDENTE, t1.getStatus());
            assertEquals(Status.PENDENTE, t2.getStatus());
        }

        @Test
        @DisplayName("verificando o retorno correto de cada nível de prioridade")
        public void nivelPrioridadeTest() {
            LocalDate data = LocalDate.now();
            LocalDate dataAlta = data.plusDays(7);
            LocalDate dataMedia = data.plusDays(15);

            Scanner alta = new Scanner("1");
            Scanner media = new Scanner("2");
            Scanner baixa = new Scanner("3");

            EntradaInterface entradaAlta = new EntradaConsole(alta, OutPut);
            EntradaInterface entradaMedia = new EntradaConsole(media, OutPut);
            EntradaInterface entradaBaixa = new EntradaConsole(baixa, OutPut);

            TarefaFluxoInterface funcoesAlta = new TarefaFluxo(entradaAlta, tarefasRep, OutPut);
            TarefaFluxoInterface funcoesMedia = new  TarefaFluxo(entradaMedia, tarefasRep, OutPut);
            TarefaFluxoInterface funcoesBaixa = new TarefaFluxo(entradaBaixa, tarefasRep, OutPut);

            funcoesAlta.nivelPrioridade(t1);
            funcoesMedia.nivelPrioridade(t2);
            funcoesBaixa.nivelPrioridade(t3);

            assertEquals(Prioridade.ALTA, t1.getPrioridade());
            assertEquals(Prioridade.MEDIA, t2.getPrioridade());
            assertEquals(Prioridade.BAIXA, t3.getPrioridade());

            assertEquals(dataAlta, t1.getVencimento());
            assertEquals(dataMedia, t2.getVencimento());
            assertNull(t3.getVencimento());
        }

        @Test
        @DisplayName("Verificando se esta voltando a decisão certa, retorno true")
        public void confirmacaoTarefaTest() {
            Scanner scRespostaMaiuscula = new Scanner("S");
            Scanner scRespostaMinuscula = new Scanner("s");

            entrada1 = new EntradaConsole(scRespostaMaiuscula, OutPut);
            entrada2 = new EntradaConsole(scRespostaMinuscula, OutPut);

            funcoes1 = new TarefaFluxo(entrada1, tarefasRep, OutPut);
            funcoes2 = new TarefaFluxo(entrada2, tarefasRep, OutPut);

            long id1 = 0;
            long id2 = 2;

            Tarefa primeira = new Tarefa(id1, "Teste1", "Teste descrição", Status.PENDENTE, Prioridade.BAIXA, date, null);
            Tarefa segundo = new Tarefa(id2, "Teste1", "Teste descrição", Status.PENDENTE, Prioridade.BAIXA, date, null);

            tarefasRep.inserir(primeira);
            tarefasRep.inserir(segundo);

            boolean verdadeiroM = funcoes1.confirmacaoTarefa(id1);
            boolean verdadeirom = funcoes2.confirmacaoTarefa(id2);

            assertTrue(verdadeiroM);
            assertTrue(verdadeirom);
        }

        @Test
        @DisplayName("Verificando se a função retorna boolean se a confirmação for negativo")
        public void confirmacaoTarefaTestFalse() {
            Scanner scRespostaMaiuscula = new Scanner("N");
            Scanner scRespostaMinuscula = new Scanner("n");

            entrada1 = new EntradaConsole(scRespostaMaiuscula, OutPut);
            entrada2 = new EntradaConsole(scRespostaMinuscula, OutPut);

            funcoes1 = new TarefaFluxo(entrada1, tarefasRep, OutPut);
            funcoes2 = new TarefaFluxo(entrada2, tarefasRep, OutPut);

            long id1 = 0;
            long id2 = 2;

            Tarefa primeira = new Tarefa(id1, "Teste1", "Teste descrição", Status.PENDENTE, Prioridade.BAIXA, date, null);
            Tarefa segundo = new Tarefa(id2, "Teste1", "Teste descrição", Status.PENDENTE, Prioridade.BAIXA, date, null);

            tarefasRep.inserir(primeira);
            tarefasRep.inserir(segundo);

            boolean verdadeiroM = funcoes1.confirmacaoTarefa(id1);
            boolean verdadeirom = funcoes2.confirmacaoTarefa(id2);

            assertFalse(verdadeiroM);
            assertFalse(verdadeirom);
        }

        @Test
        @DisplayName("Verificando se a mudança está acontecendo")
        public void confirmarModificacaoTest() {
            Scanner confirmacao =  new Scanner("s\n s");
            entrada1 = new EntradaConsole(confirmacao, OutPut);
            funcoes1 = new TarefaFluxo(entrada1, tarefasRep, OutPut);

            String titulo = "testado titulo";
            String descricao = "testado descrição";
            byte ntitulo = 1;
            byte ndescricao = 2;

            boolean tituloModificado = funcoes1.confirmarModificacao(titulo, ntitulo, t1);
            boolean descricaoModificado = funcoes1.confirmarModificacao(descricao, ndescricao, t2);

            assertTrue(tituloModificado);
            assertTrue(descricaoModificado);
            assertEquals(titulo, t1.getTitulo());
            assertEquals(descricao, t2.getDescricao());
        }

        @Test
        @DisplayName("Verificando se não houve mudança e retorno false")
        public void confirmarModificacaoFalseTest() {
            Scanner confirmacao =  new Scanner("n\n n");
            entrada1 = new EntradaConsole(confirmacao, OutPut);
            funcoes1 = new TarefaFluxo(entrada1, tarefasRep, OutPut);

            String titulo = "testado titulo";
            String descricao = "testado descrição";
            byte ntitulo = 1;
            byte ndescricao = 2;

            boolean tituloModificado = funcoes1.confirmarModificacao(titulo, ntitulo, t1);
            boolean descricaoModificado = funcoes1.confirmarModificacao(descricao, ndescricao, t2);

            assertFalse(tituloModificado);
            assertFalse(descricaoModificado);
            assertNotEquals(titulo, t1.getTitulo());
            assertNotEquals(descricao, t2.getDescricao());
        }

        @Test
        @DisplayName("Verificando se as funções estão sendo chamada e as modificações feitas")
        public void modificaTarefaTest() {
            LocalDate data = LocalDate.now();
            LocalDate dataMedia = data.plusDays(15);

            Scanner caminhoTitulo = new Scanner("1\n Teste titulo\n s\n 4");
            Scanner caminhoDescricao = new Scanner("2\n Teste descrição\n s 4");
            Scanner caminhoPrioridade = new Scanner("3\n s\n 2\n 4");

            entrada1 = new EntradaConsole(caminhoTitulo, OutPut);
            entrada2 = new EntradaConsole(caminhoDescricao, OutPut);
            EntradaInterface entrada3= new EntradaConsole(caminhoPrioridade, OutPut);

            funcoes1 = new TarefaFluxo(entrada1, tarefasRep, OutPut);
            funcoes2 = new TarefaFluxo(entrada2, tarefasRep, OutPut);
            TarefaFluxoInterface funcoes3 = new TarefaFluxo(entrada3, tarefasRep, OutPut);

            funcoes1.modificaTarefa(t1);
            funcoes2.modificaTarefa(t2);
            funcoes3.modificaTarefa(t3);

            assertEquals("Teste titulo",  t1.getTitulo());
            assertEquals("Teste descrição", t2.getDescricao());
            assertEquals(Prioridade.MEDIA, t3.getPrioridade());
            assertEquals(dataMedia, t3.getVencimento());
        }

        @Test
        @DisplayName("Verificando se a função está excluindo a tarefa corretamente")
        public void removendoTarefaTest() {
            Scanner caminhoRemocao = new Scanner("1\n s\n");
            entrada1 = new EntradaConsole(caminhoRemocao, OutPut);

            funcoes1 = new TarefaFluxo(entrada1, tarefasRep, OutPut);

            tarefasRep.inserir(t1);
            tarefasRep.inserir(t2);

            funcoes1.removendoTarefa();

            assertEquals(1, tarefasRep.encontrarTodos().size());
            assertTrue(tarefasRep.encontrarTodos().contains(t2));
            assertFalse(tarefasRep.encontrarTodos().contains(t1));
        }
    }

    @Nested
    class CaminhoErrado{
        @Test
        @DisplayName("Testando retorno da mensagem dependendo da entrada")
        public void estaConcluidoTest() {
            Scanner test = new Scanner ("Nao\n sim\n errado\n e\n s \n");
            entrada1 = new EntradaConsole(test, OutPut);
            funcoes1 = new TarefaFluxo(entrada1, tarefasRep, OutPut);

            funcoes1.estaConcluida(t1);

            assertEquals(Status.CONCLUIDA, t1.getStatus());
        }

        @Test
        @DisplayName("Testando os retornos de mensagens de erros em entradas invalidas.")
        public void nivelPrioridadetest() {
            LocalDate data = LocalDate.now();
            LocalDate dataAlta = data.plusDays(7);

            Scanner alta = new Scanner("1j1j\n 4\n 1 ");
            entrada1 = new EntradaConsole(alta, OutPut);
            funcoes1 = new TarefaFluxo(entrada1, tarefasRep, OutPut);

            funcoes1.nivelPrioridade(t1);

            assertEquals(Prioridade.ALTA, t1.getPrioridade());

            assertEquals(dataAlta, t1.getVencimento());
        }

        @Test
        @DisplayName("Testando se a função retorna false se a lista for vazia")
        public void confirmacaoTarefaNullTest() {
            Scanner scRespostaMinuscula = new Scanner("s");
            entrada1 = new EntradaConsole(scRespostaMinuscula, OutPut);
            funcoes1 = new TarefaFluxo(entrada1, tarefasRep, OutPut);

            long id2 = 2;

            assertFalse(funcoes1.confirmacaoTarefa(id2));
        }

        @Test
        @DisplayName("Verificando se esta voltando a decisão certa, retorno true")
        public void confirmacaoTarefaTest() {
            Scanner scRespostaMaiuscula = new Scanner("lkadjsflskaj\n a\n S");
            entrada1 = new EntradaConsole(scRespostaMaiuscula, OutPut);
            funcoes1 =  new TarefaFluxo(entrada1, tarefasRep, OutPut);

            long id1 = 0;

            Tarefa primeira = new Tarefa(id1, "Teste1", "Teste descrição", Status.PENDENTE, Prioridade.BAIXA, date, null);

            tarefasRep.inserir(primeira);

            boolean verdadeiroM = funcoes1.confirmacaoTarefa(id1);

            assertTrue(verdadeiroM);
        }
    }
}
