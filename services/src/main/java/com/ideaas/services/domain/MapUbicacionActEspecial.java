package com.ideaas.services.domain;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "map_ubicaciones_actualizaciones_especiales")
public class MapUbicacionActEspecial {

    @EmbeddedId
    private MapUbicacionActEspecialPK id;

    @Column(name = "Operacion")
    private String operacion;

    @Column(name = "Procesado")
    private Long procesado;

    public MapUbicacionActEspecial() {
    }

    public MapUbicacionActEspecialPK getId() {
        return id;
    }

    public void setId(MapUbicacionActEspecialPK id) {
        this.id = id;
    }

    public String getOperacion() {
        return operacion;
    }

    public void setOperacion(String operacion) {
        this.operacion = operacion;
    }

    public Long getProcesado() {
        return procesado;
    }

    public void setProcesado(Long procesado) {
        this.procesado = procesado;
    }
}
