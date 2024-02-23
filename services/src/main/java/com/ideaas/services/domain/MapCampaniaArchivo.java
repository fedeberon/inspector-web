package com.ideaas.services.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "map_campanias_archivos")
@Data

public class MapCampaniaArchivo {

    @Id
    @Column(name = "idCampaniaArchivos")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "idCampania")
    private MapCampania mapCampania;

    @Column(name = "nombreArchivo")
    private String nombreArchivo;

    @Column(name = "dataArchivo")
    private String dataArchivo;

    public MapCampaniaArchivo() {
    }
}
