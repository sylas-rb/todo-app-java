package com.todoapp.service;

import com.todoapp.DTO.request.TarefaResquestDTO;
import com.todoapp.DTO.response.TarefaResponseDTO;
import com.todoapp.entities.Tarefa;
import com.todoapp.entities.User;
import com.todoapp.model.Errors.DataBaseException;
import com.todoapp.model.Errors.RecursoNaoEncontradoExcecao;
import com.todoapp.model.Interface.TarefaRepositorio;
import com.todoapp.model.Interface.UserRepositorio;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TarefasServico{
    private final TarefaRepositorio tarefaRepositorio;
    private final UserRepositorio userRepositorio;

    public TarefasServico(TarefaRepositorio tarefaRepositorio, UserRepositorio userRepositorio) {
        this.tarefaRepositorio = tarefaRepositorio;
        this.userRepositorio = userRepositorio;
    }

    public Tarefa recebendoDTO (Long idTarefa, TarefaResquestDTO dto, Long idUser) {
        Tarefa tarefa = (idTarefa != null) ? encontrarPorId(idTarefa, idUser) : new Tarefa();
        User user = userRepositorio.findById(idUser).orElseThrow(() -> new RecursoNaoEncontradoExcecao(idUser));

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

    public List<Tarefa> encontrarTudo(Long id) {
        return tarefaRepositorio.findByUsuarioId(id);
    }

    public Tarefa encontrarPorId (Long idTarefa, Long idUser) {
        return tarefaRepositorio.findByIdAndUsuarioId(idTarefa, idUser)
                .orElseThrow(() -> new RecursoNaoEncontradoExcecao(idTarefa));
    }

    public void remover (Long idTarefa, Long idUser){
        try {
            Tarefa tarefa = encontrarPorId(idTarefa, idUser);
            tarefaRepositorio.delete(tarefa);
        } catch (DataIntegrityViolationException e) {
            throw new DataBaseException(e.getMessage());
        }
    }
}
