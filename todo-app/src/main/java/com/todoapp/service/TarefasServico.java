package com.todoapp.service;

import com.todoapp.DTO.request.TarefaResquestDTO;
import com.todoapp.DTO.response.TarefaResponseDTO;
import com.todoapp.entities.Tarefa;
import com.todoapp.entities.User;
import com.todoapp.model.Errors.DataBaseException;
import com.todoapp.model.Errors.RecursoNaoEncontradoExcecao;
import com.todoapp.model.Interface.TarefaRepositorio;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TarefasServico{
    private final TarefaRepositorio tarefaRepositorio;

    public TarefasServico(TarefaRepositorio tarefaRepositorio) {
        this.tarefaRepositorio = tarefaRepositorio;
    }

    public Tarefa recebendoDTO (Long id, TarefaResquestDTO dto, User user) {
        Tarefa tarefa = (id != null) ? encontrarPorId(id) : new Tarefa();

        tarefa.setTitulo(dto.titulo());
        tarefa.setDescricao(dto.descricao());
        tarefa.setStatus(dto.status());
        tarefa.setPrioridade(dto.prioridade());
        tarefa.setDate(dto.date());
        tarefa.setVencimento(tarefa.adicionandoVencimento(tarefa.getDate()));
        tarefa.setUsuario(user);

        return tarefa;
    }

    public TarefaResponseDTO enviandoDTO (Tarefa tarefa) {
        TarefaResponseDTO dto = new TarefaResponseDTO(
                tarefa.getId(),
                tarefa.getTitulo(),
                tarefa.getDescricao(),
                tarefa.getStatus(),
                tarefa.getPrioridade(),
                tarefa.getDate(),
                tarefa.getVencimento()
        );

        return dto;
    }

    public void salvar(Tarefa tarefa){
        tarefaRepositorio.save(tarefa);
    }

    public List<Tarefa> encontrarTudo() {
        return tarefaRepositorio.findAll();

    }

    public Tarefa encontrarPorId (Long id) {
        return tarefaRepositorio.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoExcecao(id));
    }

    public void remover (Long id){
        try {
            Tarefa tarefa = encontrarPorId(id);
            tarefaRepositorio.delete(tarefa);
        } catch (DataIntegrityViolationException e) {
            throw new DataBaseException(e.getMessage());
        }
    }
}
