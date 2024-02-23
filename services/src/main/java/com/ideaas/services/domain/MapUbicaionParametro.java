package com.ideaas.services.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "map_ubicaiones_parametros")
public class MapUbicaionParametro {
    @EmbeddedId
    private MapUbicaionParametroPk id;

    @Column(name = "descripcion")
    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "id_parametro", referencedColumnName = "id_parametro", updatable = false, insertable = false)
    private Parametro parametro;

    @ManyToOne
    @JoinColumn(name = "idUbicacion", referencedColumnName = "idUbicacion", updatable = false, insertable = false)
    private MapUbicacion mapUbicacion;

    public MapUbicaionParametroPk getId() {
        return id;
    }

    public void setId(MapUbicaionParametroPk id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Parametro getParametro() {
        return parametro;
    }

    public void setParametro(Parametro parametro) {
        this.parametro = parametro;
        this.id.setIdParametro(parametro.getIdParametro());
    }

    public MapUbicacion getMapUbicacion() {
        return mapUbicacion;
    }

    public void setMapUbicacion(MapUbicacion mapUbicacion) {
        this.mapUbicacion = mapUbicacion;
        this.id.setIdUbicaion(mapUbicacion.getId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MapUbicaionParametro)) return false;
        MapUbicaionParametro that = (MapUbicaionParametro) o;
        return Objects.equals(getId(), that.getId());
    }

}
