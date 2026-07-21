package com.todoapp.model.Service;

import com.todoapp.entities.Tarefa;
import com.todoapp.entities.TarefasRepositorio;
import com.todoapp.model.Enums.Prioridade;
import com.todoapp.model.Enums.Status;
import com.todoapp.model.Errors.TarefaException;
import com.todoapp.model.Interface.EntradaInterface;
import com.todoapp.model.Interface.TarefaFluxoInterface;

import java.io.PrintStream;
import java.util.InputMismatchException;
import java.util.List;

public class TarefaFluxo implements TarefaFluxoInterface {
    private final EntradaInterface entrada;
    private final TarefasRepositorio tarefasRep;
    private PrintStream out;

    public TarefaFluxo(EntradaInterface entrada, TarefasRepositorio tarefasRep, PrintStream out) {
        this.entrada = entrada;
        this.tarefasRep = tarefasRep;
        this.out = out;
    }

    public void estaConcluida(Tarefa tarefa) {
        char concluida;
        String str;

        while (true) {
            out.println("tarefa está concluída (s/n): ");
            str = entrada.EntradaUsuario();
            if (str.length() == 1) {
                concluida = str.charAt(0);

                if (concluida == 's' || concluida == 'S') {
                    tarefa.tarefaStatus(Status.CONCLUIDA);
                } else if (concluida == 'n' || concluida == 'N') {
                    tarefa.tarefaStatus(Status.PENDENTE);
                } else {
                    out.println("so pode escolher entre 's' ou 'n'.");
                }
            } else {
                out.println("Somente 's' ou 'n'.");
            }
        }
    }

    public void nivelPrioridade(Tarefa tarefa) {
        while (true) {
            try {
                out.println("Qual é o nível de prioridade entre 1 a 3: ");
                out.println("1 - nível alto (7 (sete dias) para concluir)");
                out.println("2 - nível medio (15 (quinze dias) para concluir)");
                out.println("3 - nível baixo (indeterminado)");

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
                    out.println("so pode escolher entre números entre '1' a '3'.");
                    continue;
                }
                break;
            } catch (Exception ex) {
                out.println("Erro inesperado: " + ex.getMessage());
            }
        }
    }

    public boolean confirmacaoTarefa(List<Tarefa> tarefa, long id) {
        Tarefa tarefaAlvo;

        if (!tarefa.isEmpty()) {
            tarefaAlvo = tarefasRep.retornarTarefa(tarefa, id).orElseThrow(() -> new TarefaException("Tarefa não encontrada"));
        } else {
            throw new TarefaException("Tarefa não pode ser encontrada.");
        }

        out.println(tarefaAlvo);
        out.println();
        return entrada.confirmado();
    }

    public void marcaTarefa(List<Tarefa> remetente, List<Tarefa> destinatario) {
        long id;
        while (true) {
            try {
                out.print("Qual id da tarefa? ");
                id = entrada.lerLong();

                if (confirmacaoTarefa(remetente, id)) {

                    Tarefa tarefaAlvo = tarefasRep.retornarTarefa(remetente, id).orElseThrow(() -> new TarefaException("Tarefa não encontrada"));
                    boolean conclusao = tarefasRep.moverATarefa(tarefaAlvo, remetente, destinatario);
                    if (!conclusao) {
                        throw new TarefaException("Erro para concluir a marcação.");
                    }
                    break;
                }
                break;
            } catch (TarefaException ex) {
                out.println(ex.getMessage());
            }catch (NumberFormatException e){
            out.println("Entrada incorreta, verifique sua entrada: " + e.getMessage());
            }
        }
    }

    public boolean confirmarModificacao(String mudanca, byte n, Tarefa tarefa) {
        while (true) {
            if (n == 1) {
                if (entrada.confirmado()) {
                    tarefa.setTitulo(mudanca);
                    out.println("Titulo de tarefa modificada com sucesso.");
                    return true;
                } else {
                    out.println("Modificação cancelada.");
                    return false;
                }
            } else if (n == 2) {
                if (entrada.confirmado()) {
                    tarefa.setDescricao(mudanca);
                    out.println("Descrição de tarefa modificada com sucesso.");
                    return true;
                } else {
                    out.println("Modificação cancelada.");
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
                out.println("Qual modificação que fazer: ");
                out.println("1. Alterar titulo.");
                out.println("2. Alterar descrição.");
                out.println("3. Alterar prioridade.");
                out.println("4. Voltar.");

                byte n = entrada.lerByte();

                if (n == -1 ) {
                    continue;
                }

                switch (n) {
                    case 1:
                        decisao = 1;
                        out.println("Qual seria o novo titulo? ");
                        titulo = entrada.EntradaUsuario();
                        out.println(tarefa.getTitulo() + " -> " + titulo);

                        if (confirmarModificacao(titulo, decisao, tarefa)) {
                            break;
                        }
                        break;
                    case 2:
                        decisao = 2;
                        out.println("Qual seria a nova descrição? ");
                        descricao = entrada.EntradaUsuario();
                        out.println(tarefa.getDescricao() + " -> " + descricao);

                        if (confirmarModificacao(descricao, decisao, tarefa)) {
                            break;
                        }
                        break;
                    case 3:
                        out.println(tarefa.toString());
                        out.println();

                        if (entrada.confirmado()) {
                            nivelPrioridade(tarefa);
                            out.println("Propriedade da tarefa modificada com sucesso.");
                            break;
                        } else {
                            break;
                        }
                    case 4:
                        return;
                    default:
                        out.println("Não foi possível encontrar a modificação, verifique a opção selecionada.");
                }
            } catch (InputMismatchException e) {
                out.println("Entrada incorreta.");
                break;
            } catch (NullPointerException e) {
                out.println("A tarefa não está de acordo para modificação, verifique a tarefa.");
            } catch (Exception e) {
                out.println("verifique sua entrada: " + e.getMessage());
                break;
            }
        }
    }

    public void removendoTarefa(List<Tarefa> tarefa) {
        long id;
        while (true) {
            out.print("Qual id da tarefa? ");

            id = entrada.lerLong();

            if (id == -1) {
                continue;
            }

            if (confirmacaoTarefa(tarefa, id)) {
                try {
                    final long idFinal = id;
                    if (tarefa.removeIf(t -> t.getId() == idFinal)) {
                        out.println("Tarefa removida com sucesso.");
                        break;
                    } else {
                        throw new TarefaException("Erro ao remove tarefa, verifique o ID digitado.");
                    }
                } catch (TarefaException ex) {
                    out.println(ex.getMessage());
                } catch (NullPointerException ex) {
                    out.println("Erro ao procurar tarefa, verifique o Id digitado.");
                } catch (Exception ex) {
                    out.println("Erro inesperado, verifique o ID digitado.");
                }
            }
        }
    }
}
