package com.ideaas.services.domain;

import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Data

public class MapProcesoDetallePK  implements Serializable {

    @Column(name = "ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "idMapBuses")
    private MapBus mapBus;

    @ManyToOne
    @JoinColumn(name = "idElemento")
    private MapElemento mapElemento;

    @ManyToOne
    @JoinColumn(name = "idUbicacion")
    private MapUbicacion mapUbicacion;

    @Transient
    @Column(name = "idCircuito")
    private Long auditoriaCircuito;

    public MapProcesoDetallePK() {
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.id);
        hash = 59 * hash + Objects.hashCode(this.mapBus);
        hash = 59 * hash + Objects.hashCode(this.mapElemento);
        hash = 59 * hash + Objects.hashCode(this.mapUbicacion);
        hash = 59 * hash + Objects.hashCode(this.auditoriaCircuito);
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
        final MapProcesoDetallePK other = (MapProcesoDetallePK) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.mapBus, other.mapBus)) {
            return false;
        }
        if (!Objects.equals(this.mapElemento, other.mapElemento)) {
            return false;
        }
        if (!Objects.equals(this.mapUbicacion, other.mapUbicacion)) {
            return false;
        }
        if (!Objects.equals(this.auditoriaCircuito, other.auditoriaCircuito)) {
            return false;
        }
        return true;
    }
}
