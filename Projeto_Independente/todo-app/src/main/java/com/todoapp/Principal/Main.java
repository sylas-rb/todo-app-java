package com.todoapp.Principal;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.todoapp.entities.Tarefa;
import com.todoapp.entities.TarefasRepositorio;
import com.todoapp.model.Errors.TarefaException;
import com.todoapp.model.Interface.EntradaInterface;
import com.todoapp.model.Interface.RepositorioInterface;
import com.todoapp.model.Interface.TarefaFluxoInterface;
import com.todoapp.model.Service.*;
import com.todoapp.model.persistencia.Pasta;

import java.io.IOException;
import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        try (Scanner sc = new Scanner(System.in)) {
            String userHome = System.getProperty("user.home");
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            PrintStream out = System.out;
            TarefasRepositorio tarefasRep = new TarefasRepositorio(out);
            RepositorioInterface repositorio = new Pasta(tarefasRep, out, userHome);

            List<Tarefa> pendente = tarefasRep.getTarefaPendentes();
            List<Tarefa> concluida = tarefasRep.getTarefaConcluidas();
            List<Tarefa> pendenteSalva = repositorio.tarefasSalvasPendente(mapper);
            List<Tarefa> concluidaSalva = repositorio.tarefasSalvasConcluido(mapper);

            tarefasRep.setIncrementoId(Math.max(tarefasRep.maxIdTarefa(pendenteSalva), tarefasRep.maxIdTarefa(concluida)) + 1);
            EntradaInterface entrada = new EntradaConsole(sc, out);
            TarefaFluxoInterface funcoes = new TarefaFluxo(entrada, tarefasRep, out);

            MenuUtilidades menu = new MenuUtilidades(funcoes, tarefasRep, repositorio, entrada, pendente, concluida, pendenteSalva, concluidaSalva, out);
            TarefaService tarefaSer = new TarefaService(menu, entrada);



            repositorio.criarPasta();
            tarefaSer.iniciar(sc, tarefasRep, mapper);
            System.out.println();

            System.out.println("TAREFAS PENDENTES: ");
            tarefasRep.listarTarefa(pendente);
            System.out.println();

            System.out.println("TAREFAS CONCLUÍDAS: ");
            tarefasRep.listarTarefa(concluida);
            System.out.println();
        } catch (TarefaException e) {
            System.err.println(e.getMessage());
        } catch (IOException e) {
            System.err.println("Erro ao ler arquivo: " + e.getMessage());
        }catch (Exception e) {
            System.err.println("Erro inesperado: " + e.getMessage());
        }
    }
}
