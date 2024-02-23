package com.ideaas.services.domain;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "map_procesos")
@Data

public class MapProceso {

//    private List<MapProcesoDetalle> mapProcesoDetalles;

    @Id
    @JoinColumn(name = "ID")
    private Long id;

    @Column(name = "Descripcion")
    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "idUsuario")
    private Usuario usuario;

    @Column(name = "Target_Edad_Desde")
    private Long targetEdadDesde;

    @Column(name = "Target_Edad_Hasta")
    private Long targetEdadHasta;

    @Column(name = "Target_Sexo_Femenino")
    private Long targetSexoFemenino;

    @Column(name = "Target_Sexo_Masculino")
    private Long targetSexoMasculino;

    @Column(name = "Target_NSE_Desde")
    private String targetNSEDesde;

    @Column(name = "Target_NSE_Hasta")
    private String targetNSEHasta;

    @Column(name = "Estado")
    private String estado;

    @Column(name = "Error")
    private String error;

    @Column(name = "Pbr")
    private Double pBR;

    @Column(name = "Contactos")
    private Long contactos;

    @Column(name = "Cobertura")
    private Double cobertura;

    @Column(name = "Cobertura_Porc")
    private Double coberturaPorc;

    @Column(name = "Tasa_Repeticion")
    private Double tasaRepeticion;

    @Column(name = "Inversion")
    private Double inversion;

    @Column(name = "Total_Personas_Universo")
    private Long totalPersonasUniverso;

    @Column(name = "totalPersonasMuestro")
    private Long totalPersonasMuestra;

    @Column(name = "Lunes")
    private Boolean lunes;

    @Column(name = "Martes")
    private Boolean martes;

    @Column(name = "Miercoles")
    private Boolean miercoles;

    @Column(name = "Jueves")
    private Boolean jueves;

    @Column(name = "Viernes")
    private Boolean viernes;

    @Column(name = "Sabado")
    private Boolean sabado;

    @Column(name = "Domingo")
    private Boolean domingo;

    @Column(name = "Tipo_Muestra")
    private String tipoMuestra;

    @Column(name = "Hora_Desde")
    private Long horaDesde;

    @Column(name = "Hora_Hasta")
    private Long horaHasta;

    @Column(name = "fecha_alta")
    private LocalDateTime fechaAltura;

    public MapProceso() {
    }
}
