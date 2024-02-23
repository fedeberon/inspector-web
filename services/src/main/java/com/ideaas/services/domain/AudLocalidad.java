package com.ideaas.services.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "aud_localidades")
@Data

public class AudLocalidad {

    @Id
    @SequenceGenerator(name = "AudLocalidadSeqGen", sequenceName = "SEQ_AUD_LOCALIDADES", allocationSize = 1)
    @GeneratedValue(generator = "AudLocalidadSeqGen")
    @Column(name = "idLocalidad")
    private Long id;

    @Column(name = "descripcion")
    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "idProvincia")
    private AudProvincia audProvincia;

    @Column(name = "bajaLogica")
    private Boolean bajaLogica;

    public AudLocalidad() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public AudProvincia getAudProvincia() {
        return audProvincia;
    }

    public void setAudProvincia(AudProvincia audProvincia) {
        this.audProvincia = audProvincia;
    }

    public void setBajaLogica(Boolean bajaLogica) {
        this.bajaLogica = bajaLogica;
    }
}
