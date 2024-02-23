package com.ideaas.services.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "productos")
@Data

public class Producto {

    @Id
    @Column(name = "idProducto")
    private Long id;

    @Column(name = "descripcion")
    private String descripcion;

    public Producto() {
    }
}
