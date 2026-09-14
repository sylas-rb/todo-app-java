package com.todoapp.model.Service;

import com.todoapp.model.Interface.SaidaInterface;
import org.springframework.stereotype.Service;

import java.io.PrintStream;

@Service
public class SaidaConsole implements SaidaInterface {
    private final PrintStream out;

    public SaidaConsole(PrintStream out) {
        this.out = out;
    }
    public void exibirMensagemLn(String mensagem) {
        out.println(mensagem);
    }

    public void exibirMensagem(String mensagem) {
        out.print(mensagem);
    }
}
