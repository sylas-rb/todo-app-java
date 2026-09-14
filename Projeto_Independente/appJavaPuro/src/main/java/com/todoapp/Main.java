package com.todoapp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.todoapp.entities.Tarefa;
import com.todoapp.Repository.TarefasRepositorio;
import com.todoapp.model.Errors.TarefaException;
import com.todoapp.model.Interface.*;
import com.todoapp.model.Service.*;
import com.todoapp.model.dao.DAOconexao;
import com.todoapp.model.persistencia.Pasta;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        try (Scanner sc = new Scanner(System.in)) {
            String userHome = System.getProperty("user.home");
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            OrdemTarefaInterface ordem = new OrdemTarefa();
            SaidaInterface out = new SaidaConsole();

            RepositorioInterface repositorio = new Pasta(out, userHome);
            TarefasRepositorio tarefasRep = (TarefasRepositorio) DAOconexao.criarConexao(repositorio);
            tarefasRep.carregarTarefa(mapper);

            EntradaInterface entrada = new EntradaConsole(sc, out);
            TarefaFluxoInterface funcoes = new TarefaFluxo(entrada, tarefasRep, out);

            MenuUtilidades menu = new MenuUtilidades(funcoes, tarefasRep, entrada, out, ordem);
            TarefaService tarefaSer = new TarefaService(funcoes,menu, entrada, out);



            repositorio.criarPasta();
            tarefaSer.iniciar(tarefasRep, mapper);

            out.exibirMensagemLn("TAREFAS: ");
            List<Tarefa> tarefas = ordem.ordenarTarefaVencimento(tarefasRep.encontrarTodos());
            if (tarefas.isEmpty()) {
                out.exibirMensagemLn("Nenhuma tarefa encontrada.");
            } else {
                tarefas.forEach(t -> out.exibirMensagemLn(t.formatoExibicao()));
            }
        } catch (TarefaException e) {
            System.err.println(e.getMessage());
        }catch (Exception e) {
            System.err.println("Erro inesperado: " + e.getMessage());
        }
    }
}
