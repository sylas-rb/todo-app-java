package com.todoapp.model.ordem;

import com.todoapp.model.Interface.SaidaInterface;
import org.springframework.stereotype.Service;

@Service
public class SaidaConsole implements SaidaInterface {
    public void exibirMensagemLn(String mensagem) {
        System.out.println(mensagem);
    }

    public void exibirMensagem(String mensagem) {
        System.out.print(mensagem);
    }
}
