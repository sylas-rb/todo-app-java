package com.todoapp.model.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.todoapp.model.Enums.Status;
import com.todoapp.model.Errors.TarefaException;
import com.todoapp.Repository.TarefasRepositorio;

import com.todoapp.entities.Tarefa;
import com.todoapp.model.Interface.*;

import java.util.List;

public class MenuUtilidades {
    private final TarefaFluxoInterface funcoes;
    private final TarefasRepositorio tarefasRep;
    private final SaidaInterface out;
    private final EntradaInterface entrada;
    private final OrdemTarefaInterface ordem;

    public MenuUtilidades(TarefaFluxoInterface funcoes, TarefasRepositorio tarefasRep, EntradaInterface entrada, SaidaInterface out, OrdemTarefaInterface ordem) {
        this.funcoes = funcoes;
        this.tarefasRep = tarefasRep;
        this.entrada = entrada;
        this.out = out;
        this.ordem = ordem;
    }

    public void menuMarcaTarefa() {
        List<Tarefa> tarefaPendentes;
        List<Tarefa> tarefaConcluidos;
        long id;
        Tarefa tarefa;

        while (true) {
            try {
                out.exibirMensagemLn("MENU MARCA CONCLUSÃO OU PENDENTE: ");
                out.exibirMensagemLn("1. Listar tarefa pendente.");
                out.exibirMensagemLn("2. Listar tarefa concluída.");
                out.exibirMensagemLn("3. Marcar tarefa pendente para concluída.");
                out.exibirMensagemLn("4. Marcar tarefa concluída para pendente.");
                out.exibirMensagemLn("5. Voltar.");

                byte opcoes = entrada.lerByte();

                switch (opcoes) {
                    case 1:
                        out.exibirMensagemLn("LISTA DE TAREFAS PENDENTES:");
                        tarefaPendentes = ordem.ordenarTarefaStatusPendente(tarefasRep.encontrarTodos());
                        if (tarefaPendentes.isEmpty()) {
                            out.exibirMensagemLn("Nenhuma tarefa encontrada.");
                        } else {
                            tarefaPendentes.forEach(t -> out.exibirMensagemLn(t.formatoExibicao()));
                        }
                        break;
                    case 2:
                        out.exibirMensagemLn("LISTA DE TAREFAS CONCLUÍDAS:");
                        tarefaConcluidos = ordem.ordenarTarefaStatusConcluido(tarefasRep.encontrarTodos());
                        if (tarefaConcluidos.isEmpty()) {
                            out.exibirMensagemLn("Nenhuma tarefa encontrada.");
                        } else {
                            tarefaConcluidos.forEach(t -> out.exibirMensagemLn(t.formatoExibicao()));
                        }
                        break;
                    case 3:
                        out.exibirMensagemLn("qual id da tarefa para concluir?");
                        id = entrada.lerLong();
                        tarefa = tarefasRep.encontrarPorId(id);
                        tarefa.setStatus(Status.CONCLUIDA);
                        out.exibirMensagemLn("tarefa concluída com sucesso.");
                        break;
                    case 4:
                        out.exibirMensagemLn("qual id da tarefa para status pendente?");
                        id = entrada.lerLong();
                        tarefa = tarefasRep.encontrarPorId(id);
                        tarefa.setStatus(Status.PENDENTE);
                        out.exibirMensagemLn("tarefa retornou a ter status pendente.");
                        break;
                    case 5:
                        return;
                    default:
                        out.exibirMensagemLn("Erro: não foi possível encontrar a opção.");
                }
            } catch (NumberFormatException e){
                out.exibirMensagemLn("Entrada incorreta, verifique sua entrada: " + e.getMessage());
            } catch (Exception e) {
                out.exibirMensagemLn("Erro incorreta, verifique sua entrada: " + e.getMessage());
            }
        }
    }

    public void menuModificacao() {
        List<Tarefa> tarefaPendentes;
        Tarefa tarefa;
        while (true) {
                long id;
                try {
                    out.exibirMensagemLn("MENU MODIFICAÇÃO: ");
                    out.exibirMensagemLn("1. Listar tarefas pendentes.");
                    out.exibirMensagemLn("2. Modificar tarefa.");
                    out.exibirMensagemLn("3. Voltar.");

                    byte opcoes = entrada.lerByte();

                    switch (opcoes) {
                        case 1:
                            tarefaPendentes = ordem.ordenarTarefaStatusPendente(tarefasRep.encontrarTodos());
                            if (tarefaPendentes.isEmpty()) {
                                out.exibirMensagemLn("Nenhuma tarefa encontrada.");
                            } else {
                                tarefaPendentes.forEach(t -> out.exibirMensagemLn(t.formatoExibicao()));
                            }
                            break;
                        case 2:
                            out.exibirMensagemLn("Qual id da tarefa? ");
                            id = entrada.lerLong();
                            tarefa = tarefasRep.encontrarPorId(id);
                            funcoes.modificaTarefa(tarefa);
                            break;
                        case 3:
                            return;
                        default:
                            out.exibirMensagemLn("Erro: não foi possível encontrar a opção.");
                    }
                }  catch (TarefaException e) {
                    out.exibirMensagemLn("ERROR: " + e.getMessage());
                } catch (NumberFormatException e){
                    out.exibirMensagemLn("Entrada incorreta, verifique sua entrada: " + e.getMessage());
                } catch (Exception e) {
                    out.exibirMensagemLn("ERROR inesperado: " + e.getMessage());
                }
            }

    }

    public void menuRemoverTarefa() {
        List<Tarefa> tarefaPendentes;
        List<Tarefa> tarefaConcluidos;
        while (true) {
            try {
                out.exibirMensagemLn("MENU REMOVER TAREFA: ");
                out.exibirMensagemLn("1. Listar tarefas pendentes.");
                out.exibirMensagemLn("2. Listar tarefas concluídas.");
                out.exibirMensagemLn("3. Remover tarefa.");
                out.exibirMensagemLn("4. Voltar.");

                byte opcoes = entrada.lerByte();

                switch (opcoes) {
                    case 1:
                        out.exibirMensagemLn("TAREFAS PENDENTES: ");
                        tarefaPendentes = ordem.ordenarTarefaPrioridade(tarefasRep.encontrarTodos());
                        if (tarefaPendentes.isEmpty()) {
                            out.exibirMensagemLn("Nenhuma tarefa encontrada.");
                        } else {
                            tarefaPendentes.forEach(t -> out.exibirMensagemLn(t.formatoExibicao()));
                        }
                        break;
                    case 2:
                        out.exibirMensagemLn("TAREFAS CONCLUÍDAS: ");
                        tarefaConcluidos = ordem.ordenarTarefaPrioridade(tarefasRep.encontrarTodos());
                        if (tarefaConcluidos.isEmpty()) {
                            out.exibirMensagemLn("Nenhuma tarefa encontrada.");
                        } else {
                            tarefaConcluidos.forEach(t -> out.exibirMensagemLn(t.formatoExibicao()));
                        }
                        break;
                    case 3:
                        funcoes.removendoTarefa();
                        break;
                    case 4:
                        return;
                    default:
                        out.exibirMensagemLn("Erro: não foi possível encontrar a opção.");
                }
            } catch (NumberFormatException e) {
                out.exibirMensagemLn("Entrada incorreta, verifique sua entrada: " + e.getMessage());
            } catch (NullPointerException e) {
                out.exibirMensagemLn("Entrada não encontrada, verifique novamente: " + e.getMessage());
            } catch (Exception e) {
                out.exibirMensagemLn("Erro inesperado: " + e.getMessage());
            }
        }
    }

    public Boolean confirmarSalvamento(ObjectMapper mapper) {
        List<Tarefa> tarefaPendentes;
        List<Tarefa> tarefaConcluidos;
        while (true) {
            try {
                out.exibirMensagemLn("SALVAR TAREFAS: ");
                out.exibirMensagemLn("1. Salvar tarefas.");
                out.exibirMensagemLn("2. listar tarefas.");
                out.exibirMensagemLn("3. Voltar.");

                byte opcoes = entrada.lerByte();

                switch (opcoes) {
                    case 1:
                        tarefasRep.salvarTarefa(mapper);
                        return true;
                    case 2:
                        out.exibirMensagemLn("TAREFAS PENDENTES: ");
                        tarefaPendentes = ordem.ordenarTarefaData(tarefasRep.encontrarTodos());
                        if (tarefaPendentes.isEmpty()) {
                            out.exibirMensagemLn("Nenhuma tarefa encontrada.");
                        } else {
                            tarefaPendentes.forEach(t -> out.exibirMensagemLn(t.formatoExibicao()));
                        }

                        out.exibirMensagemLn("TAREFAS CONCLUIDOS: ");
                        tarefaConcluidos = ordem.ordenarTarefaData(tarefasRep.encontrarTodos());
                        if (tarefaConcluidos.isEmpty()) {
                            out.exibirMensagemLn("Nenhuma tarefa encontrada.");
                        } else {
                            tarefaConcluidos.forEach(t -> out.exibirMensagemLn(t.formatoExibicao()));
                        }
                        break;
                    case 3:
                        return false;
                    default:
                        out.exibirMensagemLn("Erro: não foi possível encontrar a opção.");
                }
            } catch (NumberFormatException e) {
                out.exibirMensagemLn("Entrada incorreta, verifique sua entrada: " + e.getMessage());
            } catch (Exception e) {
                out.exibirMensagemLn("Erro inesperado: " + e.getMessage());
            }
        }
    }
}
