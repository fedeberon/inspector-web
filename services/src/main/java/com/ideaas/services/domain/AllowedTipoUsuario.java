package com.ideaas.services.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "manager_allowed_tipo_usuario")
@Data
public class AllowedTipoUsuario {

    @Id
    @Column(name = "MAT_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "MAT_TIPO_USUARIO")
    private TipoUsuario tipoUsuario;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }
}
