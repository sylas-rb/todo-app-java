package com.todoapp.autenticacao;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AutenticacaoController {
    private final AutenticacaoService Service;

    public AutenticacaoController(AutenticacaoService service) {
        Service = service;
    }

    @PostMapping("/autenticacao")
    public String autenticacao(Authentication autenticacao) {
        return Service.autenticacao(autenticacao);
    }
}
