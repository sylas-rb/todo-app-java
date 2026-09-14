package com.todoapp.model.persistencia;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.todoapp.model.Errors.TarefaException;
import com.todoapp.entities.Tarefa;
import com.todoapp.model.Interface.RepositorioInterface;
import com.todoapp.model.Interface.SaidaInterface;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Pasta implements RepositorioInterface{
    private SaidaInterface out;

    public final String userHome;
    public final String caminhoPastaPendente;
    public final String caminhoPastaConcluido;
    public final String nomeArquivo;
    public final File destinoArquivoPendente;
    public final File destinoArquivoConcluido;

    public Pasta(SaidaInterface out, String userHome) {
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
                out.exibirMensagemLn("Pasta pendente foi criado com sucesso!");
            } else if (pendente.exists()) {
                out.exibirMensagemLn("Pasta pendente já existe.");
            } else {
                out.exibirMensagemLn("Erro ao criar Pasta pendente.");
            }
            if (concluido.mkdirs()) {
                out.exibirMensagemLn("Pasta concluído foi criado com sucesso");
            } else if (concluido.exists()) {
                out.exibirMensagemLn("Pasta concluído já existe.");
            } else {
                out.exibirMensagemLn("Erro ao criar Pasta concluído.");
            }
        } catch (Exception e) {
            out.exibirMensagemLn("Erro ao criar pasta: " + e.getMessage());
        }
    }

    public void salvarTarefaPendente(ObjectMapper mapper, List<Tarefa> tarefasResp) {
        try {
            mapper.writeValue(destinoArquivoPendente, tarefasResp);
            out.exibirMensagemLn("Tarefa pendente salvo com sucesso!");
        } catch(Exception e) {
            out.exibirMensagemLn("Erro ao salvar arquivo com o JSON: " + e.getMessage());
        }
    }

    public void salvarTarefaConcluido(ObjectMapper mapper, List<Tarefa> tarefasResp) {
        try {
            mapper.writeValue(destinoArquivoConcluido, tarefasResp);
            out.exibirMensagemLn("Tarefa concluído salvo com sucesso!");
        } catch (Exception e) {
            out.exibirMensagemLn("Erro ao salvar arquivo com o JSON: " + e.getMessage());
        }
    }

    public List<Tarefa> tarefasSalvasPendente(ObjectMapper mapper) throws IOException {
        if (destinoArquivoPendente.exists() && destinoArquivoPendente.length() > 0) {
            return mapper.readValue(destinoArquivoPendente, new TypeReference<>() {});
        } else {
            out.exibirMensagemLn("tarefa pendente não encontrada.");
            throw new TarefaException("Tarefa não encontrada.");
        }
    }

    public List<Tarefa> tarefasSalvasConcluido(ObjectMapper mapper) throws IOException {
        if (destinoArquivoConcluido.exists() && destinoArquivoConcluido.length() > 0) {
            return mapper.readValue(destinoArquivoConcluido, new TypeReference<>() {});
        } else {
            out.exibirMensagemLn("tarefa pendente não encontrada.");
            throw new TarefaException("Tarefa não encontrada.");
        }
    }

    public void salvandoTarefas(List<Tarefa> pendente, List<Tarefa> concluido, ObjectMapper mapper) {
        if (!pendente.isEmpty()) {
            salvarTarefaPendente(mapper, pendente);
        } else {
            out.exibirMensagemLn("Nenhuma tarefa pendente foi adicionada.");
        }
        if (!concluido.isEmpty()) {
            salvarTarefaConcluido(mapper, concluido);
        } else {
            out.exibirMensagemLn("Nenhuma tarefa concluída foi adicionada.");
        }
    }
}
