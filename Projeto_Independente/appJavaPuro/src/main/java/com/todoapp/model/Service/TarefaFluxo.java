package com.todoapp.model.Service;

import com.todoapp.entities.Tarefa;
import com.todoapp.Repository.TarefasRepositorio;
import com.todoapp.model.Enums.Prioridade;
import com.todoapp.model.Enums.Status;
import com.todoapp.model.Errors.TarefaException;
import com.todoapp.model.Interface.EntradaInterface;
import com.todoapp.model.Interface.SaidaInterface;
import com.todoapp.model.Interface.TarefaFluxoInterface;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.InputMismatchException;

@Service
public class TarefaFluxo implements TarefaFluxoInterface {
    private final EntradaInterface entrada;
    private final TarefasRepositorio tarefasRep;
    private final SaidaInterface out;

    public TarefaFluxo(EntradaInterface entrada, TarefasRepositorio tarefasRep, SaidaInterface out) {
        this.entrada = entrada;
        this.tarefasRep = tarefasRep;
        this.out = out;
    }

    public void criarTarefas() {
        while (true){
            try {
                Tarefa tarefa = new Tarefa();
                LocalDate date = LocalDate.now();
                out.exibirMensagemLn("TAREFAS");
                while (true) {
                    out.exibirMensagem("Nome da tarefa: ");
                    String titulo = entrada.EntradaUsuario();
                    if (!titulo.isEmpty()) {
                        tarefa.setTitulo(titulo);
                        break;
                    } else {
                        out.exibirMensagemLn("Titulo não pode ser vazio");
                    }
                }
                while (true) {
                    out.exibirMensagem("Descrição da tarefa: ");
                    String descricao = entrada.EntradaUsuario();
                    if (!descricao.isEmpty()) {
                        tarefa.setDescricao(descricao);
                        break;
                    } else {
                        out.exibirMensagemLn("A descrição não pode ser vazio");
                    }
                }

                estaConcluida(tarefa);

                tarefa.setDate(date);

                if (tarefa.getStatus() != Status.CONCLUIDA) {
                    nivelPrioridade(tarefa);
                } else {
                    tarefa.setPrioridade(Prioridade.BAIXA);
                    tarefa.setVencimento(null);
                }


                tarefasRep.addId(tarefa);

                tarefasRep.inserir(tarefa);

                if (!entrada.ficarTarefa()) {
                    break;
                }
            } catch (TarefaException e) {
                out.exibirMensagemLn("Error: " + e.getMessage());
            } catch (NullPointerException e) {
                out.exibirMensagemLn("Error ao procurar tarefa, verifique entrada: " + e.getMessage());
            } catch (Exception ex) {
                out.exibirMensagemLn("Error inesperado: " + ex.getMessage());
            }
        }
    }

    public void estaConcluida(Tarefa tarefa) {
        char concluida;
        String str;

        while (true) {
            out.exibirMensagemLn("tarefa está concluída (s/n): ");
            str = entrada.EntradaUsuario();
            if (str.length() == 1) {
                concluida = str.charAt(0);

                if (concluida == 's' || concluida == 'S') {
                    tarefa.tarefaStatus(Status.CONCLUIDA);
                    break;
                } else if (concluida == 'n' || concluida == 'N') {
                    tarefa.tarefaStatus(Status.PENDENTE);
                    break;
                } else {
                    out.exibirMensagemLn("so pode escolher entre 's' ou 'n'.");
                }
            } else {
                out.exibirMensagemLn("Somente 's' ou 'n'.");
            }
        }
    }

    public void nivelPrioridade(Tarefa tarefa) {
        while (true) {
            try {
                out.exibirMensagemLn("Qual é o nível de prioridade entre 1 a 3: ");
                out.exibirMensagemLn("1 - nível alto (7 (sete dias) para concluir)");
                out.exibirMensagemLn("2 - nível medio (15 (quinze dias) para concluir)");
                out.exibirMensagemLn("3 - nível baixo (indeterminado)");

                byte prioridade = entrada.lerByte();

                if (prioridade == -1 ) {
                    continue;
                }

                if (prioridade == 1) {
                    tarefa.tarefaPrioridade(Prioridade.ALTA);
                } else if (prioridade == 2) {
                    tarefa.tarefaPrioridade(Prioridade.MEDIA);
                } else if (prioridade == 3) {
                    tarefa.tarefaPrioridade(Prioridade.BAIXA);
                    break;
                } else {
                    out.exibirMensagemLn("so pode escolher entre números entre '1' a '3'.");
                    continue;
                }
                break;
            } catch (Exception ex) {
                out.exibirMensagemLn("Erro inesperado: " + ex.getMessage());
            }
        }
    }

    public boolean confirmacaoTarefa(long id) {
        Tarefa tarefaAlvo = tarefasRep.encontrarPorId(id);

        out.exibirMensagemLn(tarefaAlvo.formatoExibicao());
        return entrada.confirmado();
    }

    public boolean confirmarModificacao(String mudanca, byte n, Tarefa tarefa) {
        while (true) {
            if (n == 1) {
                if (entrada.confirmado()) {
                    tarefa.setTitulo(mudanca);
                    out.exibirMensagemLn("Titulo de tarefa modificada com sucesso.");
                    return true;
                } else {
                    out.exibirMensagemLn("Modificação cancelada.");
                    return false;
                }
            } else if (n == 2) {
                if (entrada.confirmado()) {
                    tarefa.setDescricao(mudanca);
                    out.exibirMensagemLn("Descrição de tarefa modificada com sucesso.");
                    return true;
                } else {
                    out.exibirMensagemLn("Modificação cancelada.");
                    return false;
                }
            }
        }
    }

    public void modificaTarefa(Tarefa tarefa) {
        String titulo, descricao;
        byte decisao;

        while (true) {
            try {
                out.exibirMensagemLn("Qual modificação que fazer: ");
                out.exibirMensagemLn("1. Alterar titulo.");
                out.exibirMensagemLn("2. Alterar descrição.");
                out.exibirMensagemLn("3. Alterar prioridade.");
                out.exibirMensagemLn("4. Voltar.");

                byte n = entrada.lerByte();

                if (n == -1 ) {
                    continue;
                }

                switch (n) {
                    case 1:
                        decisao = 1;
                        out.exibirMensagemLn("Qual seria o novo titulo? ");
                        titulo = entrada.EntradaUsuario();
                        out.exibirMensagemLn(tarefa.getTitulo() + " -> " + titulo);

                        if (confirmarModificacao(titulo, decisao, tarefa)) {
                            break;
                        }
                        break;
                    case 2:
                        decisao = 2;
                        out.exibirMensagemLn("Qual seria a nova descrição? ");
                        descricao = entrada.EntradaUsuario();
                        out.exibirMensagemLn(tarefa.getDescricao() + " -> " + descricao);

                        if (confirmarModificacao(descricao, decisao, tarefa)) {
                            break;
                        }
                        break;
                    case 3:
                        out.exibirMensagemLn(tarefa.toString());

                        if (entrada.confirmado()) {
                            nivelPrioridade(tarefa);
                            out.exibirMensagemLn("Propriedade da tarefa modificada com sucesso.");
                            break;
                        }

                        break;
                    case 4:
                        return;
                    default:
                        out.exibirMensagemLn("Não foi possível encontrar a modificação, verifique a opção selecionada.");
                }
            } catch (InputMismatchException e) {
                out.exibirMensagemLn("Entrada incorreta.");
                break;
            } catch (NullPointerException e) {
                out.exibirMensagemLn("A tarefa não está de acordo para modificação, verifique a tarefa.");
            } catch (Exception e) {
                out.exibirMensagemLn("verifique sua entrada: " + e.getMessage());
                break;
            }
        }
    }

    public void removendoTarefa() {
        long id;
        while (true) {
            out.exibirMensagem("Qual id da tarefa? ");

            id = entrada.lerLong();

            if (id == -1) {
                continue;
            }

            if (confirmacaoTarefa(id)) {
                try {
                    tarefasRep.deletePorId(id);
                } catch (TarefaException ex) {
                    out.exibirMensagemLn(ex.getMessage());
                } catch (NullPointerException ex) {
                    out.exibirMensagemLn("Erro ao procurar tarefa, verifique o Id digitado.");
                } catch (Exception ex) {
                    out.exibirMensagemLn("Erro inesperado, verifique o ID digitado.");
                }
            }
        }
    }
}
