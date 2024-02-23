package com.ideaas.services.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "aud_productos")
@Data

public class AudProducto {

    @Id
    @Column(name = "idProducto")
    private Long id;

    @Column(name = "descripcion")
    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "idAnunciante")
    private AudAnunciante audAnunciante;

    public AudProducto() {
    }
}
