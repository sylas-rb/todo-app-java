package com.todoapp.entities;

import com.todoapp.model.Enums.Prioridade;
import com.todoapp.model.Enums.Status;
import com.todoapp.model.Errors.TarefaException;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;

@Entity
@Table(name = "tb_tarefa")
public class Tarefa{
    private static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static final Comparator<Tarefa> POR_PRIORIDADE = Comparator.comparing(Tarefa::getPrioridade);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String descricao;

    @Enumerated(EnumType.STRING)
    private Status status;
    @Enumerated(EnumType.STRING)
    private Prioridade prioridade;

    private LocalDate date;
    private LocalDate vencimento;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User usuario;

    public Tarefa() {}

    public Tarefa(Long id, String titulo, String descricao, Status status,  Prioridade prioridade, LocalDate date, LocalDate vencimento, User usuario) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.status = status;
        this.prioridade = prioridade;
        this.date = date;
        this.vencimento = vencimento;
        this.usuario = usuario;
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

    public User getUsuario() {
        return usuario;
    }

    public void tarefaStatus(Status status) {
        if (status == null) {
            throw new TarefaException("Status não pode ser nulo");
        }
        this.status = status;
    }

    public void setUsuario(User usuario) {
        this.usuario = usuario;
    }

    public void atualizar (Tarefa tarefa) {
        this.titulo = tarefa.getTitulo();
        this.descricao = tarefa.getDescricao();
        this.status = tarefa.getStatus();
        this.prioridade = tarefa.getPrioridade();
        this.date = tarefa.getDate();
        this.vencimento = tarefa.getVencimento();
        this.usuario = tarefa.getUsuario();
    }

    public String formatoExibicao() {
        if (this.vencimento != null) {
            return String.format("ID: %d || Titulo: %s (%s - %s)%n" + "Descrição: %s%n" + "Status: %s || Prioridade: %s", getId(), getTitulo(), getDate().format(DTF), getVencimento().format(DTF), getDescricao(), getStatus(), getPrioridade());
        }
        return String.format("ID: %d || Titulo: %s (%s - Indeterminado)%n" + "Descrição: %s%n" + "Status: %s || Prioridade: %s", getId(), getTitulo(), getDate().format(DTF), getDescricao(), getStatus(), getPrioridade());
    }
}
