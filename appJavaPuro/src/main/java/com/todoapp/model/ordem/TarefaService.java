package com.todoapp.model.ordem;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.todoapp.service.TarefasRepositorio;
import com.todoapp.model.Interface.EntradaInterface;
import com.todoapp.model.Interface.SaidaInterface;
import com.todoapp.model.Interface.TarefaFluxoInterface;

public class TarefaService {
    private final TarefaFluxoInterface funcao;
    private final MenuUtilidades menu;
    private final EntradaInterface entrada;
    private final SaidaInterface out;

    public TarefaService(TarefaFluxoInterface funcao, MenuUtilidades menu, EntradaInterface entrada, SaidaInterface out) {
        this.funcao = funcao;
        this.menu = menu;
        this.entrada = entrada;
        this.out = out;
    }

    public void iniciar(TarefasRepositorio tarefasRep, ObjectMapper mapper) {
        while (true) {
            try {
                out.exibirMensagemLn("MENU DE TAREFA: ");
                out.exibirMensagemLn("1. Adicionar Tarefa.");
                out.exibirMensagemLn("2. Marca Tarefa como concluída ou pendente.");
                out.exibirMensagemLn("3. Modificar tarefa.");
                out.exibirMensagemLn("4. Remover Tarefa.");
                out.exibirMensagemLn("5. Sair do programa.");
                byte opcao = entrada.lerByte();

                switch (opcao) {
                    case 1:
                        funcao.criarTarefas();
                        break;
                    case 2:
                        menu.menuMarcaTarefa();
                        break;
                    case 3:
                        menu.menuModificacao();
                        break;
                    case 4:
                        menu.menuRemoverTarefa();
                        break;
                    case 5:
                        if (tarefasRep.encontrarTodos() != null) {
                            if (menu.confirmarSalvamento(mapper)) {
                                return;
                            } else {
                                break;
                            }
                        } else {
                            return;
                        }
                    default:
                        out.exibirMensagemLn("Opção não encontrada, escolha entr 1 a 4.");
                }
            } catch (NumberFormatException e) {
                out.exibirMensagemLn("Entrada incorreta, verifique sua entrada: " + e.getMessage());
            } catch (Exception ex) {
                out.exibirMensagemLn("Erro inesperado: " + ex.getMessage());
            }
        }
    }
}
