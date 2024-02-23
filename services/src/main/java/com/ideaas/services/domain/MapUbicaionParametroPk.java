package com.ideaas.services.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Data
@Embeddable
public class MapUbicaionParametroPk implements Serializable {

    @Column(name = "id_parametro")
    private Long idParametro;

    @Column(name = "idUbicacion")
    private Long idUbicaion;

    public MapUbicaionParametroPk() {}

    public MapUbicaionParametroPk(Long idParametro, Long idUbicaion) {
        this.idParametro = idParametro;
        this.idUbicaion = idUbicaion;
    }

    public Long getIdParametro() {
        return idParametro;
    }

    public void setIdParametro(Long idParametro) {
        this.idParametro = idParametro;
    }

    public Long getIdUbicaion() {
        return idUbicaion;
    }

    public void setIdUbicaion(Long idUbicaion) {
        this.idUbicaion = idUbicaion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MapUbicaionParametroPk)) return false;
        MapUbicaionParametroPk that = (MapUbicaionParametroPk) o;
        return getIdParametro().equals(that.getIdParametro()) && getIdUbicaion().equals(that.getIdUbicaion());
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.idParametro);
        hash = 59 * hash + Objects.hashCode(this.idUbicaion);
        return hash;
    }
}
