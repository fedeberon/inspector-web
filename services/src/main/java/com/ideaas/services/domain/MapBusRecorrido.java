package com.ideaas.services.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "map_buses_recorridos")
@Data
public class MapBusRecorrido {

    @EmbeddedId
    private MapBusRecorridoPK id;

    @Column(name = "recorrido")
    private String recorrido;

    @Column(name = "duracion_minutos")
    private Long duracionMinutos;

    @Column(name = "unidades_circulacion_01")
    private Long unidadesCirculacion01;

    public MapBusRecorrido() {
    }
}
