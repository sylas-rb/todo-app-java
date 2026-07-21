package com.todoapp.model.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.todoapp.entities.TarefasRepositorio;
import com.todoapp.model.Interface.EntradaInterface;

import java.util.Scanner;

public class TarefaService {
    private final MenuUtilidades menu;
    private final EntradaInterface entrada;

    public TarefaService(MenuUtilidades menu, EntradaInterface entrada) {
        this.menu = menu;
        this.entrada = entrada;
    }

    public void iniciar(Scanner sc, TarefasRepositorio tarefasRep, ObjectMapper mapper) {
        while (true) {
            try {
                System.out.println("MENU DE TAREFA: ");
                System.out.println("1. Adicionar Tarefa.");
                System.out.println("2. Marca Tarefa como concluída ou pendente.");
                System.out.println("3. Modificar tarefa.");
                System.out.println("4. Remover Tarefa.");
                System.out.println("5. Sair do programa.");
                byte opcao = entrada.lerByte();

                switch (opcao) {
                    case 1:
                        menu.addTarefa(sc);
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
                        if (!tarefasRep.getTarefaPendentes().isEmpty() || !tarefasRep.getTarefaConcluidas().isEmpty()) {
                            if (menu.confirmarSalvamento(mapper)) {
                                return;
                            } else {
                                break;
                            }
                        } else {
                            return;
                        }
                    default:
                        System.out.println("Opção não encontrada, escolha entr 1 a 4.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada incorreta, verifique sua entrada: " + e.getMessage());
            } catch (Exception ex) {
                System.out.println("Erro inesperado: " + ex.getMessage());
            }
        }
    }
}
