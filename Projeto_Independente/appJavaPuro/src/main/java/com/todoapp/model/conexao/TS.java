package com.todoapp.model.conexao;

import com.todoapp.model.Errors.TarefaException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class TS {
    public static String caminhoArquivo(String userHome) {
        Properties props = loadProperties();
        return userHome + props.getProperty("local") + props.getProperty("nome") + "." + props.getProperty("tipo");
    }

    public static Properties loadProperties() {
        try (InputStream fs = TS.class.getResourceAsStream("/TS.properties")) {
            if (fs == null) {
                throw new TarefaException("Arquivo TS.properties não encontrado.");
            }
            Properties props = new Properties();
            props.load(fs);
            return props;
        } catch (IOException e) {
            throw new TarefaException(e.getMessage());
        }
    }
}
