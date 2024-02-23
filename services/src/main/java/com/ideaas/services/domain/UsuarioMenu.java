package com.ideaas.services.domain;

import javax.persistence.*;

@Entity
@Table(name = "usuarios_menu")
public class UsuarioMenu {

    @EmbeddedId
    private UsuarioMenuId usuarioMenuId;

    public UsuarioMenu() {
    }

    public UsuarioMenu(UsuarioMenuId usuarioMenuId) {
        this.usuarioMenuId = usuarioMenuId;
    }

    public UsuarioMenuId getUsuarioMenuId() {
        return usuarioMenuId;
    }

    public void setUsuarioMenuId(UsuarioMenuId usuarioMenuId) {
        this.usuarioMenuId = usuarioMenuId;
    }
}
