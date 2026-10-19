package com.todoapp.autenticacao;

import org.springframework.security.core.Authentication;
import com.todoapp.service.JwtServico;
import org.springframework.stereotype.Service;

@Service
public class AutenticacaoService {
    private final JwtServico servico;

    public AutenticacaoService(JwtServico servico) {
        this.servico = servico;
    }

    public String autenticacao(Authentication autenticacao) {
        return servico.geraToken(autenticacao);
    }
}
