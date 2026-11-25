package com.todoapp.service;

import com.todoapp.autenticacao.userAutentication.AutenticacaoUser;
import com.todoapp.model.Errors.DataBaseException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.stream.Collectors;

@Service
public class JwtServico {
    private final JwtEncoder encoder;

    public JwtServico(JwtEncoder encoder) {
        this.encoder = encoder;
    }

    public String geraToken(Authentication autenticacao) {
        Instant now = Instant.now();
        long expiry = 3600L;

        String user;
        if (autenticacao.getPrincipal() instanceof AutenticacaoUser usuario) {
            user = String.valueOf(usuario.getId());
        } else {
            throw new DataBaseException("Não foi possível localizar o usuário.");
        }

        String scopes = autenticacao.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));
        var claims = JwtClaimsSet.builder()
                .issuer("gerenciador-tarefas")
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiry))
                .subject(user)
                .claim("scope", scopes)
                .build();

        return encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }
}
