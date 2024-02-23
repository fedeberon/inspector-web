package com.ideaas.services.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "parametros")
public class Parametro {
    @Id

    @SequenceGenerator(name = "ParametroSeqGen", sequenceName = "SEQ_PARAMETRO", allocationSize = 0)
    @GeneratedValue(generator = "ParametroSeqGen")
    @Column(name="id_parametro", nullable= false)
    private Long idParametro;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "map_empresas_parametros",
            joinColumns        = @JoinColumn(name = "id_parametro"   , referencedColumnName = "id_parametro"),
            inverseJoinColumns = @JoinColumn(name = "idEmpresa", referencedColumnName = "idEmpresa")
    )
    private List<MapEmpresa> empresas;

    @Column(name="nombre", nullable= false)
    private String nombre;

    public Parametro() {}

    public Long getIdParametro() {
        return idParametro;
    }

    public void setIdParametro(Long idParametro) {
        this.idParametro = idParametro;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<MapEmpresa> getEmpresas() {
        return empresas;
    }

    public void setEmpresas(List<MapEmpresa> empresas) {
        this.empresas = empresas;
    }
}


