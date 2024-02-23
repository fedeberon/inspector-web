package com.ideaas.services.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "map_buses")
@Data

public class MapBus {

    @Id
    @Column(name = "idMapBuses")
    private Long id;

    @Column(name = "linea")
    private String linea;

    @Column(name = "color")
    private String color;

    public MapBus() {
    }
}
