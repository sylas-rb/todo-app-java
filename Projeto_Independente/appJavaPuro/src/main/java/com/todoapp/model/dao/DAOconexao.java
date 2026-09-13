package com.todoapp.model.dao;

import com.todoapp.Repository.TarefasRepositorio;
import com.todoapp.model.Interface.RepositorioInterface;
import com.todoapp.model.Interface.SaidaInterface;
import com.todoapp.model.Service.SaidaConsole;
import com.todoapp.model.persistencia.Pasta;

public class DAOconexao {
    public static TarefaDAO criarConexao(RepositorioInterface repositorio) {
        return new TarefasRepositorio(repositorio);
    }
}
