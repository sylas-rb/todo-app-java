package com.todoapp.model.dao;

import com.todoapp.service.TarefasRepositorio;
import com.todoapp.model.Interface.RepositorioInterface;

public class DAOconexao {
    public static TarefaDAO criarConexao(RepositorioInterface repositorio) {
        return new TarefasRepositorio(repositorio);
    }
}
