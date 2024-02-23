package com.ideaas.services.domain;

import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Data

public class MapBusRecorridoPK implements Serializable {

    @Column(name = "idRecorrido")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "idMapBuses")
    private MapBus mapBus;

    public MapBusRecorridoPK() {
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.id);
        hash = 59 * hash + Objects.hashCode(this.mapBus);
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
        final MapBusRecorridoPK other = (MapBusRecorridoPK) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.mapBus, other.mapBus)) {
            return false;
        }

        return true;
    }
}
