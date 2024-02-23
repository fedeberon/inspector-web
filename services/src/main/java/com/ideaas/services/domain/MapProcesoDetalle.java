package com.ideaas.services.domain;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "map_procesos_detalle")
@Data

public class MapProcesoDetalle {

    @EmbeddedId
    private MapProcesoDetallePK id;

    @Column(name = "Pbr")
    private Double pBR;

    @Column(name = "Contactos")
    private Long contactos;

    @Column(name = "Cobertura")
    private  Long cobertura;

    @Column(name = "Cobertura_Porc")
    private Double coberturaPorc;

    @Column (name = "Tasa_Repeticion")
    private Double tasaRepeticion;

    @Column(name = "Impactos")
    private Long impactos;

    @Column(name = "Coeficiente")
    private BigDecimal coeficiente;

    public MapProcesoDetalle() {
    }

    //    private MapProceso proceso;
}
