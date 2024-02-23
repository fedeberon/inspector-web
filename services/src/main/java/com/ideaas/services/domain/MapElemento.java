package com.ideaas.services.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "map_elementos")
@Data

public class MapElemento {

    @Id
    @Column(name = "idElemento")
    @SequenceGenerator(name = "MapElementoSeqGen", sequenceName = "SEQ_MAP_ELEMENTOS", allocationSize = 1)
    @GeneratedValue(generator = "MapElementoSeqGen")
    private Long id;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "panos")
    private Long panos;

    @ManyToOne
    @JoinColumn(name = "idMedio")
    private MapMedio mapMedio;

    @ManyToOne
    @JoinColumn(name = "idFormato")
    private MapFormato mapFormato;

    @Column(name = "acumulaDatos")
    private Long acumulaDatos;

    @Column(name = "evalua")
    private Long evalua;

    @Column(name = "metrosContacto")
    private Long metrosContacto;

    @Column(name = "coeficiente")
    private BigDecimal coeficiente;

    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    @Column(name = "fecha_alta")
    private LocalDateTime fechaAlta;

    @Column(name = "bajaLogica")
    private Boolean bajaLogica;

    public MapElemento() {
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

    public Long getPanos() {
        return panos;
    }

    public void setPanos(Long panos) {
        this.panos = panos;
    }

    public MapMedio getMapMedio() {
        return mapMedio;
    }

    public void setMapMedio(MapMedio mapMedio) {
        this.mapMedio = mapMedio;
    }

    public MapFormato getMapFormato() {
        return mapFormato;
    }

    public void setMapFormato(MapFormato mapFormato) {
        this.mapFormato = mapFormato;
    }

    public Long getAcumulaDatos() {
        return acumulaDatos;
    }

    public void setAcumulaDatos(Long acumulaDatos) {
        this.acumulaDatos = acumulaDatos;
    }

    public Long getEvalua() {
        return evalua;
    }

    public void setEvalua(Long evalua) {
        this.evalua = evalua;
    }

    public Long getMetrosContacto() {
        return metrosContacto;
    }

    public void setMetrosContacto(Long metrosContacto) {
        this.metrosContacto = metrosContacto;
    }

    public BigDecimal getCoeficiente() {
        return coeficiente;
    }

    public void setCoeficiente(BigDecimal coeficiente) {
        this.coeficiente = coeficiente;
    }

    public LocalDateTime getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(LocalDateTime fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public Boolean getBajaLogica() {
        return bajaLogica;
    }

    public void setBajaLogica(Boolean bajaLogica) {
        this.bajaLogica = bajaLogica;
    }
}
