package com.ideaas.services.domain;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "aud_campanias")
@Data

public class AudCampania {

    @Id
    @Column(name = "idCampania")
    private Long id;

    @Column(name = "descripcion")
    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "idProducto")
    private AudProducto audProducto;

    @ManyToOne
    @JoinColumn(name = "idAgencia")
    private AudAgencia audAgencia;

    @Column(name = "observaciones")
    private String observaciones;

    @Column(name = "fecha_Inicio")
    private LocalDateTime fechaInicio;

    public AudCampania() {
    }
}

