package com.todoapp.autenticacao.userAutentication;

import com.todoapp.entities.User;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class AutenticacaoUser implements UserDetails {
    private final User user;

    public AutenticacaoUser(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(() -> "read");
    }

    @Override
    public @Nullable String getPassword() {
        return user.getSenha();
    }

    @Override
    public String getUsername() {
        return user.getNome();
    }
}
