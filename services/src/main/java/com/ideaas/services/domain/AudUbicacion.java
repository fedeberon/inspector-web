package com.ideaas.services.domain;

import lombok.Data;
import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "aud_ubicaciones")
@Data

public class AudUbicacion {

    @Id
    @Column(name = "idUbicacion")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "idEmpresa")
    private AudEmpresa audEmpresa;

    @Column(name = "direccion")
    private String direccion;

    @Column(name = "ubicacion")
    private String ubicacion;

    @ManyToOne
    @JoinColumn(name = "idProvincia")
    private AudProvincia audProvincia;

    @ManyToOne
    @JoinColumn(name = "idLocalidad")
    private AudLocalidad audLocalidad;

    @Column(name = "cantidad")
    private Long cantidad;

    @ManyToOne
    @JoinColumn(name = "idElemento")
    private AudElemento audElemento;

    @Column(name = "geo_latitud")
    private BigDecimal latitud;

    @Column(name = "geo_longitud")
    private BigDecimal longitud;

    @Column(name = "metroscontacto")
    private Long metrosContacto;

    @Column(name = "coeficiente")
    private BigDecimal coeficiente;

    public AudUbicacion() {
    }
}
