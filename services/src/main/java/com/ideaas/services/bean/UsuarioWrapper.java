package com.ideaas.services.bean;

import com.ideaas.services.domain.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UsuarioWrapper {

    private Usuario usuario;
    private String empresasIDs;

    public Usuario getUsuario() {
        return usuario;
    }

    public String getEmpresasIDs() {
        return empresasIDs;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void setEmpresasIDs(String empresasIDs) {
        this.empresasIDs = empresasIDs;
    }
}
