package com.todoapp.model.persistencia;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.todoapp.model.Errors.TarefaException;
import com.todoapp.entities.Tarefa;
import com.todoapp.model.Interface.RepositorioInterface;
import com.todoapp.model.Interface.SaidaInterface;
import com.todoapp.model.conexao.TS;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Pasta implements RepositorioInterface{
    private SaidaInterface out;
    private File destinoArquivo;
    private String userHome;

    public Pasta(SaidaInterface out, String userHome) {
        this.out = out;
        this.userHome = userHome;
        this.destinoArquivo = new File(TS.caminhoArquivo(userHome));
    }

    public Pasta(SaidaInterface out) {
        this.out = out;
    }

    public void criarPasta() {
        File pasta = destinoArquivo.getParentFile();
        try {
            if (pasta.mkdirs()) {
                out.exibirMensagemLn("Pasta foi criado com sucesso!");
            } else if (pasta.exists()) {
                out.exibirMensagemLn("Pasta já existe.");
            } else {
                out.exibirMensagemLn("Erro ao criar Pasta.");
            }
        } catch (Exception e) {
            out.exibirMensagemLn("Erro ao criar pasta: " + e.getMessage());
        }
    }

    public void salvarTarefa(ObjectMapper mapper, List<Tarefa> tarefasResp) {
        try {
            mapper.writeValue(destinoArquivo, tarefasResp);
            out.exibirMensagemLn("Tarefas salvo com sucesso!");
        } catch(Exception e) {
            out.exibirMensagemLn("Erro ao salvar arquivo com o JSON: " + e.getMessage());
        }
    }

    public List<Tarefa> tarefasSalvas(ObjectMapper mapper) throws IOException {
        if (destinoArquivo.exists() && destinoArquivo.length() > 0) {
            return mapper.readValue(destinoArquivo, new TypeReference<>() {});
        } else if (destinoArquivo.length() == 0) {
            out.exibirMensagemLn("Nenhuma tarefa encontrada.");
            return new ArrayList<>();
        } else {
            throw new TarefaException("Erro ao ler arquivo.");
        }
    }
}
