package com.ideaas.services.domain;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "map_ubicaciones_actualizaciones")
public class MapUbicacionActualizacion {

    @EmbeddedId
    private MapUbicacionActualizacionPK id;

    @Column(name = "Operacion")
    private String operacion;

    @Column(name = "Procesado")
    private Long procesado;

    @Column(name = "Evalua")
    private Long evalua;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "Fecha_Alta")
    private LocalDateTime fechaAlta;

    public MapUbicacionActualizacion() {
    }

    public MapUbicacionActualizacionPK getId() {
        return id;
    }

    public void setId(MapUbicacionActualizacionPK id) {
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

    public Long getEvalua() {
        return evalua;
    }

    public void setEvalua(Long evalua) {
        this.evalua = evalua;
    }

    public LocalDateTime getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(LocalDateTime fechaAlta) {
        this.fechaAlta = fechaAlta;
    }
}
