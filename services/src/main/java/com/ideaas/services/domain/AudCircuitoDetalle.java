package com.ideaas.services.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "aud_circuitos_detalle")
@Data

public class AudCircuitoDetalle {

    @EmbeddedId
    private AudCircuitoDetallePK id;

    @Column(name = "Exhibe")
    private String exhibe;

    @Column(name = "Observaciones")
    private String observaciones;

    @ManyToOne
    @JoinColumn(name = "idUbicacion")
    private AudUbicacion audUbicacion;

    @Column(name = "archivo_foto")
    private String archivoFoto;

    @Column(name = "cantidad_BE")
    private Long cantidadBE;

    @Column(name = "cantidad_CD")
    private Long cantidadCD;

    @Column(name = "cantidad_SA")
    private Long cantidadSA;

    @Column(name = "estado_BE")
    private Long estadoBE;

    @Column(name = "estado_CD")
    private Long estadoCD;

    @Column(name = "estado_SA")
    private Long estadoSA;

    public AudCircuitoDetalle() {
    }
}
