package com.ideaas.services.domain;

import lombok.Data;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Data

public class MapBusElementoPK implements Serializable {

    @ManyToOne
    @JoinColumn(name = "idMapBuses")
    private MapBus mapBus;

    @ManyToOne
    @JoinColumn(name = "idEmpresa")
    private MapEmpresa mapEmpresa;

    @ManyToOne
    @JoinColumn(name = "idElemento")
    private MapElemento mapElemento;

    public MapBusElementoPK() {
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.mapBus);
        hash = 59 * hash + Objects.hashCode(this.mapElemento);
        hash = 59 * hash + Objects.hashCode(this.mapEmpresa);
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
        final MapBusElementoPK other = (MapBusElementoPK) obj;
        if (!Objects.equals(this.mapElemento, other.mapElemento)) {
            return false;
        }
        if (!Objects.equals(this.mapBus, other.mapBus)) {
            return false;
        }
        if (!Objects.equals(this.mapElemento, other.mapElemento)) {
            return false;
        }
        if (!Objects.equals(this.mapEmpresa, other.mapEmpresa)) {
            return false;
        }
        return true;
    }


}
