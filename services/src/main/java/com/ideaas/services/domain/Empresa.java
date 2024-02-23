package com.ideaas.services.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "empresas")
@Data

public class Empresa {

    @Id
    @Column(name = "idEmpresa")
    private Long id;

    @Column(name = "descripcion")
    private String descripcion;

    public Empresa() {
    }
}