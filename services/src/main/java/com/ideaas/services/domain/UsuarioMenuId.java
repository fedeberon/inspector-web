package com.ideaas.services.domain;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class UsuarioMenuId implements Serializable {

    private Long idUsuario;

    private Long idMenu;

    public UsuarioMenuId() {
    }

    public UsuarioMenuId(Long idUsuario, Long idMenu) {
        this.idUsuario = idUsuario;
        this.idMenu = idMenu;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Long getIdMenu() {
        return idMenu;
    }

    public void setIdMenu(Long idMenu) {
        this.idMenu = idMenu;
    }
}
