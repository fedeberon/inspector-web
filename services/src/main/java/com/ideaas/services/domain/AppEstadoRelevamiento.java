package com.ideaas.services.domain;

import javax.persistence.*;

@Entity
@Table(name = "app_estado_relevamiento")
public class AppEstadoRelevamiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idEstado", unique= true, nullable= false)
    private Long id;

    @Column(name = "descripcion")
    private String descripcion;

    public AppEstadoRelevamiento() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
