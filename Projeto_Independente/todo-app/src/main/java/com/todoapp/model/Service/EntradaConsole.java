package com.todoapp.model.Service;

import com.todoapp.model.Interface.EntradaInterface;

import java.io.PrintStream;
import java.util.Scanner;

public class EntradaConsole implements EntradaInterface {
    private final Scanner sc;
    private final PrintStream out;

    public EntradaConsole(Scanner sc, PrintStream out) {
        this.sc = sc;
        this.out = out;
    }

    public String EntradaUsuario() {
        return sc.nextLine().trim();
    }

    public byte lerByte() {
        return Byte.parseByte(sc.nextLine().trim());
    }

    public long lerLong() {
        return Long.parseLong(sc.nextLine().trim());
    }

    public boolean confirmado () {
        char confirmado = '\u0000';
        String confirmadoAux;

        while (true) {
            out.println("Gostaria de continuar a ação? (s/n)");
            confirmadoAux = sc.nextLine().trim();
            if (confirmadoAux.length() == 1) {
                confirmado = confirmadoAux.charAt(0);
            }
            if (confirmado == 's' || confirmado == 'S') {
                return true;
            } else if (confirmado == 'n' || confirmado == 'N') {
                return false;
            } else {
                out.println("Somente a letra 's' ou 'n'.");
            }
        }
    }

    public boolean ficarTarefa() {
        String str;
        char s = '\u0000';

        while (true) {
            out.println("Adicionar tarefa (s/n):");
            str = sc.nextLine().trim();
            if (str.length() == 1) {
                s = str.charAt(0);
            }
            if (s == 's' || s == 'S') {
                return true;
            } else if (s == 'n' || s == 'N') {
                return false;
            } else {
                out.println("Somente 's' ou 'n'.");
            }
        }
    }
}
