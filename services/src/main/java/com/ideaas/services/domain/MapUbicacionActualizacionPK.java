package com.ideaas.services.domain;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Objects;

@Embeddable
public class MapUbicacionActualizacionPK implements Serializable{

    @Column(name = "idUbicacion")
    private Long mapUbicacionId;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "Fecha_Operacion")
    private LocalDateTime fechaOperacion;

    public MapUbicacionActualizacionPK() {
    }

    public MapUbicacionActualizacionPK(Long mapUbicacionId, LocalDateTime fechaOperacion) {
        this.mapUbicacionId = mapUbicacionId;
        this.fechaOperacion = fechaOperacion;
    }

    public Long getMapUbicacionId() {
        return mapUbicacionId;
    }

    public void setMapUbicacionId(Long mapUbicacionId) {
        this.mapUbicacionId = mapUbicacionId;
    }

    public LocalDateTime getFechaOperacion() {
        return fechaOperacion;
    }

    public void setFechaOperacion(LocalDateTime fechaOperacion) {
        this.fechaOperacion = fechaOperacion;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.mapUbicacionId);
        hash = 59 * hash + Objects.hashCode(this.fechaOperacion);

        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MapUbicacionActualizacionPK other = (MapUbicacionActualizacionPK) obj;

        if (!Objects.equals(this.mapUbicacionId, other.mapUbicacionId)) {
            return false;
        }

        if (!Objects.equals(this.fechaOperacion, other.fechaOperacion)) {
            return false;
        }

        return true;
    }
}
