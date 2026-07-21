package com.todoapp.model.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.todoapp.model.Enums.Prioridade;
import com.todoapp.model.Enums.Status;
import com.todoapp.model.Errors.TarefaException;
import com.todoapp.entities.TarefasRepositorio;

import com.todoapp.entities.Tarefa;
import com.todoapp.model.Interface.EntradaInterface;
import com.todoapp.model.Interface.RepositorioInterface;
import com.todoapp.model.Interface.TarefaFluxoInterface;

import java.io.PrintStream;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class MenuUtilidades {
    private final TarefaFluxoInterface funcoes;
    private final TarefasRepositorio tarefasRep;
    private final RepositorioInterface pasta;
    private final PrintStream out;
    private final EntradaInterface entrada;
    private final List<Tarefa> pendentes;
    private final List<Tarefa> concluidos;
    private final List<Tarefa> pendenteSalva;
    private final List<Tarefa> concluidoSalva;

    public MenuUtilidades(TarefaFluxoInterface funcoes, TarefasRepositorio tarefasRep, RepositorioInterface pasta, EntradaInterface entrada, List<Tarefa> pendente, List<Tarefa> concluido, List<Tarefa> pendenteSalva, List<Tarefa> concluidoSalva, PrintStream out) {
        this.funcoes = funcoes;
        this.tarefasRep = tarefasRep;
        this.pasta = pasta;
        this.entrada = entrada;
        this.pendentes = pendente;
        this.concluidos = concluido;
        this.pendenteSalva = pendenteSalva;
        this.concluidoSalva = concluidoSalva;
        this.out = out;
    }

    public void addTarefa(Scanner sc) {
        while (true){
            try {
                Tarefa tarefa = new Tarefa();
                LocalDate date = LocalDate.now();
                out.println("TAREFAS");
                while (true) {
                    out.print("Nome da tarefa: ");
                    String titulo = sc.nextLine();
                    if (!titulo.isEmpty()) {
                        tarefa.setTitulo(titulo);
                        break;
                    } else {
                        out.println("Titulo não pode ser vazio");
                    }
                }
                while (true) {
                    out.print("Descrição da tarefa: ");
                    String descricao = sc.nextLine();
                    if (!descricao.isEmpty()) {
                        tarefa.setDescricao(descricao);
                        break;
                    } else {
                        out.println("A descrição não pode ser vazio");
                    }
                }

                funcoes.estaConcluida(tarefa);

                tarefa.setDate(date);

                if (tarefa.getStatus() != Status.CONCLUIDA) {
                    funcoes.nivelPrioridade(tarefa);
                } else {
                    tarefa.setPrioridade(Prioridade.BAIXA);
                    tarefa.setVencimento("Indeterminado.");
                }


                tarefasRep.addId(tarefa);


                if (tarefa.getStatus().equals(Status.PENDENTE)) {
                    tarefasRep.addTarefa(tarefa, pendentes);
                } else {
                    tarefasRep.addTarefa(tarefa, concluidos);
                }

                if (!entrada.ficarTarefa()) {
                    break;
                }
            } catch (TarefaException e) {
                out.println("Error: " + e.getMessage());
            } catch (NullPointerException e) {
                out.println("Error ao procurar tarefa, verifique entrada: " + e.getMessage());
            } catch (Exception ex) {
                out.println("Error inesperado: " + ex.getMessage());
            }
        }
    }

    public void menuMarcaTarefa() {
        while (true) {
            try {
                out.println("MENU MARCA CONCLUSÃO OU PENDENTE: ");
                out.println("1. Listar tarefa pendente.");
                out.println("2. Listar tarefa concluída.");
                out.println("3. Marcar tarefa pendente para concluída.");
                out.println("4. Marcar tarefa concluída para pendente.");
                out.println("5. Marcar tarefa salva pendente para concluída.");
                out.println("6. Marca tarefa salva concluído para pendente.");
                out.println("7. Voltar.");

                byte opcoes = entrada.lerByte();

                switch (opcoes) {
                    case 1:
                        out.println("LISTA DE TAREFAS PENDENTES:");
                        tarefasRep.listarTarefa(pendentes);
                        out.println("////////////////////////////////////");
                        out.println("LISTA DE TAREFAS PENDENTES SALVAS:");
                        tarefasRep.listarTarefa(pendenteSalva);
                        break;
                    case 2:
                        out.println("LISTA DE TAREFAS CONCLUÍDAS:");
                        tarefasRep.listarTarefa(concluidos);
                        out.println("////////////////////////////////////");
                        out.println("LISTA DE TAREFAS CONCLUÍDAS SALVAS:");
                        tarefasRep.listarTarefa(concluidoSalva);
                        break;
                    case 3:
                        funcoes.marcaTarefa(pendentes, concluidos);
                        break;
                    case 4:
                        funcoes.marcaTarefa(concluidos, pendentes);
                        break;
                    case 5:
                        funcoes.marcaTarefa(pendenteSalva, concluidoSalva);
                        break;
                    case 6:
                        funcoes.marcaTarefa(concluidoSalva, pendenteSalva);
                        break;
                    case 7:
                        return;
                    default:
                        out.println("Erro: não foi possível encontrar a opção.");
                }
            } catch (NumberFormatException e){
                out.println("Entrada incorreta, verifique sua entrada: " + e.getMessage());
            } catch (Exception e) {
                out.println("Erro incorreta, verifique sua entrada: " + e.getMessage());
            }
        }
    }

    public void menuModificacao() {
        while (true) {
                long id;
                try {
                    out.println("MENU MODIFICAÇÃO: ");
                    out.println("1. Listar tarefas pendentes.");
                    out.println("2. Listar tarefas pendentes salvas.");
                    out.println("3. Modificar tarefa.");
                    out.println("4. modificar tarefa salva");
                    out.println("5. Voltar.");

                    byte opcoes = entrada.lerByte();

                    switch (opcoes) {
                        case 1:
                            tarefasRep.listarTarefa(pendentes);
                            break;
                        case 2:
                            tarefasRep.listarTarefa(pendenteSalva);
                            break;
                        case 3:
                            out.println("Qual id da tarefa: ");
                            id = entrada.lerLong();
                            tarefasRep.retornarTarefa(pendentes, id)
                                    .ifPresentOrElse(tarefa -> funcoes.modificaTarefa(tarefa),
                                    () -> out.println("Tarefa com Id: '" + id + "' não encontrado"));
                            break;
                        case 4:
                            out.println("Qual id da tarefa: ");
                            id = entrada.lerLong();
                            tarefasRep.retornarTarefa(pendenteSalva, id)
                                    .ifPresentOrElse(tarefa -> funcoes.modificaTarefa(tarefa),
                                            () -> out.println("Tarefa com Id: '" + id + "' não encontrado"));
                            break;
                        case 5:
                            return;
                        default:
                            out.println("Erro: não foi possível encontrar a opção.");
                    }
                }  catch (TarefaException e) {
                    out.println("ERROR: " + e.getMessage());
                } catch (NumberFormatException e){
                    out.println("Entrada incorreta, verifique sua entrada: " + e.getMessage());
                } catch (Exception e) {
                    out.println("ERROR inesperado: " + e.getMessage());
                }
            }

    }

    public void menuRemoverTarefa() {
        while (true) {
            try {
                out.println("MENU REMOVER TAREFA: ");
                out.println("1. Listar tarefas pendentes.");
                out.println("2. Listar tarefas concluídas.");
                out.println("3. Listar tarefas pendentes salvas.");
                out.println("4. Listar tarefas concluídas salvas.");
                out.println("5. Remover tarefa pendentes.");
                out.println("6. Remover tarefa concluída.");
                out.println("7. Remover tarefas pendentes salvas.");
                out.println("8. Remover tarefas concluídas salvas.");
                out.println("9. Voltar.");

                byte opcoes = entrada.lerByte();

                switch (opcoes) {
                    case 1:
                        out.println("TAREFAS PENDENTES: ");
                        tarefasRep.listarTarefa(pendentes);
                        out.println();
                        break;
                    case 2:
                        out.println("TAREFAS CONCLUÍDAS: ");
                        tarefasRep.listarTarefa(concluidos);
                        out.println();
                        break;
                    case 3:
                        out.println("TAREFAS PENDENTES SALVAS: ");
                        tarefasRep.listarTarefa(pendenteSalva);
                        out.println();
                        break;
                    case 4:
                        out.println("TAREFAS CONCLUÍDAS SALVAS: ");
                        tarefasRep.listarTarefa(concluidoSalva);
                        out.println();
                        break;
                    case 5:
                        funcoes.removendoTarefa(pendentes);
                        break;
                    case 6:
                        funcoes.removendoTarefa(concluidos);
                        break;
                    case 7:
                        funcoes.removendoTarefa(pendenteSalva);
                        break;
                    case 8:
                        funcoes.removendoTarefa(concluidoSalva);
                        break;
                    case 9:
                        return;
                    default:
                        out.println("Erro: não foi possível encontrar a opção.");
                }
            } catch (NumberFormatException e) {
                out.println("Entrada incorreta, verifique sua entrada: " + e.getMessage());
            } catch (NullPointerException e) {
                out.println("Entrada não encontrada, verifique novamente: " + e.getMessage());
            } catch (Exception e) {
                out.println("Erro inesperado: " + e.getMessage());
            }
        }
    }

    public Boolean confirmarSalvamento(ObjectMapper mapper) {
        while (true) {
            try {
                out.println("SALVAR TAREFAS: ");
                out.println("1. Salvar tarefas.");
                out.println("2. listar tarefas.");
                out.println("3. Voltar.");

                byte opcoes = entrada.lerByte();

                switch (opcoes) {
                    case 1:
                        pasta.salvandoTarefas(pendenteSalva, concluidoSalva, pendentes, concluidos, mapper);
                        return true;
                    case 2:
                        out.println("TAREFAS PENDENTES: ");
                        if (!pendentes.isEmpty()) {
                            tarefasRep.listarTarefa(pendentes);
                        } else {
                            out.println("Nenhuma tarefa pendentes para salvar.");
                        }
                        out.println();

                        out.println("TAREFAS CONCLUIDOS: ");
                        if (!concluidos.isEmpty()) {
                            tarefasRep.listarTarefa(concluidos);
                        } else {
                            out.println("Nenhuma tarefa concluídas para salvar.");
                        }
                        break;
                    case 3:
                        return false;
                    default:
                        out.println("Erro: não foi possível encontrar a opção.");
                }
            } catch (NumberFormatException e) {
                out.println("Entrada incorreta, verifique sua entrada: " + e.getMessage());
            } catch (Exception e) {
                out.println("Erro inesperado: " + e.getMessage());
            }
        }
    }
}
