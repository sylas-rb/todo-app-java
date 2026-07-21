package com.todoapp.model.persistencia;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.todoapp.entities.TarefasRepositorio;
import com.todoapp.model.Errors.TarefaException;
import com.todoapp.entities.Tarefa;
import com.todoapp.model.Interface.RepositorioInterface;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

public class Pasta implements RepositorioInterface{
    private TarefasRepositorio tarefaRep;
    private PrintStream out;

    public final String userHome;
    public final String caminhoPastaPendente;
    public final String caminhoPastaConcluido;
    public final String nomeArquivo;
    public final File destinoArquivoPendente;
    public final File destinoArquivoConcluido;

    public Pasta(TarefasRepositorio tarefaRep, PrintStream out, String userHome) {
        this.tarefaRep = tarefaRep;
        this.out = out;
        this.userHome = userHome;
        this.caminhoPastaPendente = userHome + "/TarefaPendente/";
        this.caminhoPastaConcluido = userHome + "/TarefaConcluido/";
        this.nomeArquivo = "tarefa.json";
        this.destinoArquivoPendente = new File(caminhoPastaPendente, nomeArquivo);
        this.destinoArquivoConcluido = new File(caminhoPastaConcluido, nomeArquivo);
    }

    public void criarPasta() {
        try {
            File pendente = new File(caminhoPastaPendente);
            File concluido = new File(caminhoPastaConcluido);

            if (pendente.mkdirs()) {
                out.println("Pasta pendente foi criado com sucesso!");
            } else if (pendente.exists()) {
                out.println("Pasta pendente já existe.");
            } else {
                out.println("Erro ao criar Pasta pendente.");
            }
            if (concluido.mkdirs()) {
                out.println("Pasta concluído foi criado com sucesso");
            } else if (concluido.exists()) {
                out.println("Pasta concluído já existe.");
            } else {
                out.println("Erro ao criar Pasta concluído.");
            }
        } catch (Exception e) {
            out.println("Erro ao criar pasta: " + e.getMessage());
        }
    }

    public void salvarTarefaPendente(ObjectMapper mapper, List<Tarefa> tarefasResp) {
        try {
            mapper.writeValue(destinoArquivoPendente, tarefasResp);
            out.println("Tarefa pendente salvo com sucesso!");
        } catch(Exception e) {
            out.println("Erro ao salvar arquivo com o JSON: " + e.getMessage());
        }
    }

    public void salvarTarefaConcluido(ObjectMapper mapper, List<Tarefa> tarefasResp) {
        try {
            mapper.writeValue(destinoArquivoConcluido, tarefasResp);
            out.println("Tarefa concluído salvo com sucesso!");
        } catch (Exception e) {
            out.println("Erro ao salvar arquivo com o JSON: " + e.getMessage());
        }
    }

    public List<Tarefa> tarefasSalvasPendente(ObjectMapper mapper) throws IOException {
        if (destinoArquivoPendente.exists() && destinoArquivoPendente.length() > 0) {
            return mapper.readValue(destinoArquivoPendente, new TypeReference<>() {});
        } else {
            out.println("tarefa pendente não encontrada.");
            throw new TarefaException("Tarefa não encontrada.");
        }
    }

    public List<Tarefa> tarefasSalvasConcluido(ObjectMapper mapper) throws IOException {
        if (destinoArquivoConcluido.exists() && destinoArquivoConcluido.length() > 0) {
            return mapper.readValue(destinoArquivoConcluido, new TypeReference<>() {});
        } else {
            out.println("tarefa pendente não encontrada.");
            throw new TarefaException("Tarefa não encontrada.");
        }
    }

    public void salvandoTarefas(List<Tarefa> pendenteSalva, List<Tarefa> concluidoSalva, List<Tarefa> pendente, List<Tarefa> concluido, ObjectMapper mapper) {
        if (!pendente.isEmpty()) {
            tarefaRep.adicionarTarefaPasta(pendente, pendenteSalva);
            salvarTarefaPendente(mapper, pendenteSalva);
        } else {
            out.println("Nenhuma tarefa pendente foi adicionada.");
        }
        if (!concluido.isEmpty()) {
            tarefaRep.adicionarTarefaPasta(concluido, concluidoSalva);
            salvarTarefaConcluido(mapper, concluidoSalva);
        } else {
            out.println("Nenhuma tarefa concluída foi adicionada.");
        }
    }
}
