package com.todoapp.autenticacao.userAutentication;

import com.todoapp.model.Interface.AutenticacaoRepositorio;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final AutenticacaoRepositorio repositorio;

    public UserDetailsServiceImpl(AutenticacaoRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repositorio.findByNome(username).map(AutenticacaoUser::new).orElseThrow(() -> new UsernameNotFoundException("User não encontrado"));
    }
}
