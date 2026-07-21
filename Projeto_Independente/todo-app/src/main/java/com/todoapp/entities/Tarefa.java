package com.todoapp.entities;

import com.todoapp.model.Enums.Prioridade;
import com.todoapp.model.Enums.Status;
import com.todoapp.model.Errors.TarefaException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;

public class Tarefa{
    private static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static final Comparator<Tarefa> POR_PRIORIDADE = Comparator.comparing(Tarefa::getPrioridade);
    private long id;
    private String titulo;
    private String descricao;
    private Status status;
    private Prioridade prioridade;
    private LocalDate date;
    private String vencimento;

    public Tarefa() {}

    public Tarefa(Long id, String titulo, String descricao, Status status,  Prioridade prioridade, LocalDate date, String vencimento) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.status = status;
        this.prioridade = prioridade;
        this.date = date;
        this.vencimento = vencimento;
    }

    public long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Prioridade getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(Prioridade prioridade) {
        this.prioridade = prioridade;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getVencimento() {
        return vencimento;
    }

    public void setVencimento(String vencimento) {
        this.vencimento = vencimento;
    }

    public void tarefaStatus(Status status) {
        if (status == null) {
            throw new TarefaException("Status não pode ser nulo");
        }
        this.status = status;
    }

    public void tarefaPrioridade (Prioridade prioridade) {
        this.setPrioridade(prioridade);
        if ( prioridade.diasVencimento() != 0) {
            this.setVencimento(DTF.format(date.plusDays(prioridade.diasVencimento())));
        } else {
            this.setVencimento("Indeterminado");
        }
    }

    public void concluidaOuPendente() {
        if (this.getStatus() == Status.PENDENTE) {
            this.setStatus(Status.CONCLUIDA);
        } else if (this.getStatus() == Status.CONCLUIDA) {
            this.setStatus(Status.PENDENTE);
        } else {
            throw new TarefaException("Não foi possível mudar status da tarefa, o verifique a tarefa.");
        }
    }

    public String formatoExibicao() {
        return String.format("ID: %d || Titulo: %s (%s - %s)%n" + "Descrição: %s%n" + "Status: %s || Prioridade: %s", getId(), getTitulo(), getDate().format(DTF), getVencimento(), getDescricao(), getStatus(), getPrioridade());
    }
}
