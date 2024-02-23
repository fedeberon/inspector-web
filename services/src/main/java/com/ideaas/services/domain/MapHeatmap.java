package com.ideaas.services.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "map_heatMap")
public class MapHeatmap {

    @Id
    @Column(name = "idLocacion")
    private Long id;

    @Column(name = "fechaRecepcion")
    private String fechaRecepcion;

    @Column(name = "idUsuario")
    private Long usuario;

    @Column(name = "item")
    private Long item;

    @Column(name = "latitud")
    private BigDecimal latitud;

    @Column(name = "longitud")
    private BigDecimal longitud;

    @Column(name = "altura")
    private Long altura;

    @Column(name = "velocidad")
    private Long velocidad;

    @Column(name = "exactitud")
    private BigDecimal exactitud;

    @Column(name = "proveedor")
    private String proveedor;

    @Column(name = "orientacion")
    private Long orientacion;

    @Column(name = "fechaSincronizacion")
    private String fechaSincronizacion;

    public Long getLocacion() {
        return id;
    }

    public void setLocacion(Long locacion) {
        this.id = locacion;
    }

    public String getFechaRecepcion() {
        return fechaRecepcion;
    }

    public void setFechaRecepcion(String fechaRecepcion) {
        this.fechaRecepcion = fechaRecepcion;
    }

    public Long getUsuario() {
        return usuario;
    }

    public void setUsuario(Long usuario) {
        this.usuario = usuario;
    }

    public Long getItem() {
        return item;
    }

    public void setItem(Long item) {
        this.item = item;
    }

    public BigDecimal getLatitud() {
        return latitud;
    }

    public void setLatitud(BigDecimal latitud) {
        this.latitud = latitud;
    }

    public BigDecimal getLongitud() {
        return longitud;
    }

    public void setLongitud(BigDecimal longitud) {
        this.longitud = longitud;
    }

    public Long getAltura() {
        return altura;
    }

    public void setAltura(Long altura) {
        this.altura = altura;
    }

    public Long getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(Long velocidad) {
        this.velocidad = velocidad;
    }

    public BigDecimal getExactitud() {
        return exactitud;
    }

    public void setExactitud(BigDecimal exactitud) {
        this.exactitud = exactitud;
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    public Long getOrientacion() {
        return orientacion;
    }

    public void setOrientacion(Long orientacion) {
        this.orientacion = orientacion;
    }

    public String getFechaSincronizacion() {
        return fechaSincronizacion;
    }

    public void setFechaSincronizacion(String fechaSincronizacion) {
        this.fechaSincronizacion = fechaSincronizacion;
    }
}
