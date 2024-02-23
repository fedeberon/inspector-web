package com.ideaas.services.domain;

import lombok.Data;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "map_buses_elementos")
@Data

public class MapBusElemento {

    @EmbeddedId
    private MapBusElementoPK id;

    public MapBusElemento() {
    }
}
