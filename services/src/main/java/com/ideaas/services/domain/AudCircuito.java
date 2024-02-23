package com.ideaas.services.domain;

import lombok.Data;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "aud_circuitos")
@Data

public class AudCircuito {

    @Id
    @Column(name = "idCircuito")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "idCampania")
    private AudCampania audCampania;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "fecha_inicio")
    private LocalDateTime fechaInicio;

    @Column(name = "fecha_fin")
    private LocalDateTime fechaFin;

    @Column(name = "fecha_inicio_control")
    private LocalDateTime fechaInicioControl;

    @Column(name = "fecha_fin_control")
    private LocalDateTime fechaFinControl;

    @Column(name = "cantidad_Pauta")
    private Long cantidadPauta;

    @ManyToOne
    @JoinColumn(name = "idEmpresa")
    private AudEmpresa audEmpresa;

    @ManyToOne
    @JoinColumn(name = "idElemento")
    private AudElemento audElemento;

    @ManyToOne
    @JoinColumn(name = "idLocalidad")
    private AudLocalidad audLocalidad;

    @ManyToOne
    @JoinColumn(name = "idProvincia")
    private AudProvincia audProvincia;

    @ManyToOne
    @JoinColumn(name = "idFrecuencia")
    private AudFrecuenciaControl audFrecuenciaControl;

    @Column(name = "observaciones")
    private String observaciones;

    @Column(name = "Tipo_Circuito")
    private String tipoCircuito;

    public AudCircuito() {
    }
}
