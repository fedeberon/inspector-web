package com.ideaas.services.domain;

import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
@Table(name = "app_formulario")
public class AppFormulario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idFormulario", unique= true, nullable= false)
    private Long id;

    @Column(name = "nombreFormulario")
    private String nombreFormulario;

    @Column(name = "formulario")
    private String formulario;

    @ColumnDefault("false")
    @Column(name = "bajaLogicaFormulario")
    private Boolean bajaLogicaFormulario;


    public AppFormulario() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreFormulario() {
        return nombreFormulario;
    }

    public void setNombreFormulario(String nombreFormulario) {
        this.nombreFormulario = nombreFormulario;
    }

    public String getFormulario() {
        return formulario;
    }

    public void setFormulario(String formulario) {
        this.formulario = formulario;
    }

    public Boolean getBajaLogicaFormulario() {
        return bajaLogicaFormulario;
    }

    public void setBajaLogicaFormulario(Boolean bajaLogicaFormulario) {
        this.bajaLogicaFormulario = bajaLogicaFormulario;
    }
}
