package com.ideaas.services.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "aud_empresas")
@Data

public class AudEmpresa {

    @Id
    @Column(name = "idEmpresa")
    @SequenceGenerator(name = "AudEmpresaSeqGen", sequenceName = "SEQ_AUD_EMPRESA", allocationSize = 1)
    @GeneratedValue(generator = "AudEmpresaSeqGen")
    private Long id;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "bajaLogica")
    private Boolean BajaLogica;

    public AudEmpresa() {
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

    public Boolean getBajaLogica() {
        return BajaLogica;
    }

    public void setBajaLogica(Boolean bajaLogica) {
        BajaLogica = bajaLogica;
    }
}
