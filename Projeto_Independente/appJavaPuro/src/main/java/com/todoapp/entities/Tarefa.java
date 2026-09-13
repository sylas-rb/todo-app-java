package com.todoapp.entities;

import com.todoapp.model.Enums.Prioridade;
import com.todoapp.model.Enums.Status;
import com.todoapp.model.Errors.TarefaException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;

public class Tarefa{
    private static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static final Comparator<Tarefa> POR_PRIORIDADE = Comparator.comparing(Tarefa::getPrioridade);
    private Long id;
    private String titulo;
    private String descricao;
    private Status status;
    private Prioridade prioridade;
    private LocalDate date;
    private LocalDate vencimento;

    public Tarefa() {}

    public Tarefa(String titulo, String descricao, Status status,  Prioridade prioridade, LocalDate date, LocalDate vencimento) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.status = status;
        this.prioridade = prioridade;
        this.date = date;
        this.vencimento = vencimento;
    }

    public Long getId() {
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

    public LocalDate getVencimento() {
        return vencimento;
    }

    public void setVencimento(LocalDate vencimento) {
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
            this.setVencimento(date.plusDays(prioridade.diasVencimento()));
        } else {
            this.setVencimento(null);
        }
    }

    public void concluidaOuPendente() {
        if (this.getStatus() == Status.PENDENTE) {
            this.tarefaStatus(Status.CONCLUIDA);
        } else if (this.getStatus() == Status.CONCLUIDA) {
            this.tarefaStatus(Status.PENDENTE);
        } else {
            throw new TarefaException("Não foi possível mudar status da tarefa, o verifique a tarefa.");
        }
    }

    public void incrementandoVencimento() {
        LocalDate now = LocalDate.now();
        long diasPassado = ChronoUnit.DAYS.between(this.getDate(), now);
        if (diasPassado < 7 && this.getPrioridade().equals(Prioridade.ALTA)) {
            this.setVencimento(this.getVencimento().plusDays(7));
        } else if (diasPassado < 15 && this.getPrioridade().equals(Prioridade.ALTA) || this.getPrioridade().equals(Prioridade.MEDIA)) {
            this.setVencimento(this.getVencimento().plusDays(15));
        } else {
            this.setVencimento(null);
        }
    }

    public String formatoExibicao() {
        if (this.vencimento != null) {
            return String.format("ID: %d || Titulo: %s (%s - %s)%n" + "Descrição: %s%n" + "Status: %s || Prioridade: %s", getId(), getTitulo(), getDate().format(DTF), getVencimento().format(DTF), getDescricao(), getStatus(), getPrioridade());
        }
        return String.format("ID: %d || Titulo: %s (%s - Indeterminado)%n" + "Descrição: %s%n" + "Status: %s || Prioridade: %s", getId(), getTitulo(), getDate().format(DTF), getDescricao(), getStatus(), getPrioridade());
    }
}
