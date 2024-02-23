package com.ideaas.services.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.List;

public class UserDetailImpL extends User {

    private final Long idUser;

    private final List<Long> idMapEmpresas;

    public UserDetailImpL(String username,
                          String password,
                          Long idUsuario,
                          boolean enabled,
                          boolean accountNonExpired,
                          boolean credentialsNonExpired,
                          boolean accountNonLocked,
                          List<Long> idMapEmpresas,
                          Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.idUser = idUsuario;
        this.idMapEmpresas = idMapEmpresas;
    }

    public Long getIdUser() {
        return idUser;
    }

    public List<Long> getIdMapEmpresas() {
        return idMapEmpresas;
    }
}
