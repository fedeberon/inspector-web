package com.ideaas.services.domain;

import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.time.LocalDateTime;

@Embeddable
public class MapUbicacionActEspecialPK implements Serializable {

    @Column(name = "idUbicacion")
    private Long mapUbicacionId;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "Fecha_Alta")
    private LocalDateTime fechaAlta;

    public MapUbicacionActEspecialPK() {
    }

    public MapUbicacionActEspecialPK(Long mapUbicacionId, LocalDateTime fechaAlta) {
        this.mapUbicacionId = mapUbicacionId;
        this.fechaAlta = fechaAlta;
    }

    public Long getMapUbicacionId() {
        return mapUbicacionId;
    }

    public void setMapUbicacionId(Long mapUbicacionId) {
        this.mapUbicacionId = mapUbicacionId;
    }

    public LocalDateTime getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(LocalDateTime fechaAlta) {
        this.fechaAlta = fechaAlta;
    }
}
