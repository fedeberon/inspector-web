package com.ideaas.services.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "app_ubicaciones_relevamiento")
public class AppUbicacionRelevamiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idUbicacionRelevamiento", unique= true, nullable= false)
    @JsonProperty("appUbicacionRelevamiento")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "idUbicacion")
    @JsonProperty("appUbicacion")
    private AppUbicacion appUbicacion;

    @ManyToOne
    @JoinColumn(name = "idRelevamiento")
    @JsonProperty("appRelevamiento")
    private AppRelevamiento appRelevamiento;

    @Column(name = "cantidad")
    @JsonProperty("cantidad")
    private Long cantidad;

    @Column(name = "evp")
    @JsonProperty("evp")
    private String evp;

    @Column(name = "elemento")
    @JsonProperty("elemento")
    private String elemento;

    @Column(name = "anunciante")
    @JsonProperty("anunciante")
    private String anunciante;

    @Column(name = "producto")
    @JsonProperty("producto")
    private String producto;

    @Column(name = "referencias")
    @JsonProperty("referencias")
    private String referencias;

    @Column(name = "one_orden_s")
    @JsonProperty("one_orden_s")
    private Long oneOrdenS;

    @Column(name = "one_circuito")
    @JsonProperty("one_circuito")
    private Long oneCircuito;

    @ColumnDefault("false")
    @Column(name = "bajaLogica")
    private Boolean bajaLogica;

    /**
     * The date and time an application appUbicacionRelevamiento was completed
     */
    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    @Column(name = "fecha_finalizacion")
    private LocalDateTime fechaFinalizacion;

    public AppUbicacionRelevamiento() {
    }

    public AppUbicacionRelevamiento(Long id, AppUbicacion appUbicacion, AppRelevamiento appRelevamiento, Long cantidad, String evp, String elemento, String anunciante, String producto, String referencias, Long oneOrdenS, Long oneCircuito, Boolean bajaLogica) {
        this.id = id;
        this.appUbicacion = appUbicacion;
        this.appRelevamiento = appRelevamiento;
        this.cantidad = cantidad;
        this.evp = evp;
        this.elemento = elemento;
        this.anunciante = anunciante;
        this.producto = producto;
        this.referencias = referencias;
        this.oneOrdenS = oneOrdenS;
        this.oneCircuito = oneCircuito;
        this.bajaLogica = bajaLogica;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AppUbicacion getAppUbicacion() {
        return appUbicacion;
    }

    public void setAppUbicacion(AppUbicacion appUbicacion) {
        this.appUbicacion = appUbicacion;
    }

    public AppRelevamiento getAppRelevamiento() {
        return appRelevamiento;
    }

    public void setAppRelevamiento(AppRelevamiento appRelevamiento) {
        this.appRelevamiento = appRelevamiento;
    }

    public Long getCantidad() {
        return cantidad;
    }

    public void setCantidad(Long cantidad) {
        this.cantidad = cantidad;
    }

    public String getEvp() {
        return evp;
    }

    public void setEvp(String evp) {
        this.evp = evp;
    }

    public String getElemento() {
        return elemento;
    }

    public void setElemento(String elemento) {
        this.elemento = elemento;
    }

    public String getAnunciante() {
        return anunciante;
    }

    public void setAnunciante(String anunciante) {
        this.anunciante = anunciante;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public String getReferencias() {
        return referencias;
    }

    public void setReferencias(String referencias) {
        this.referencias = referencias;
    }

    public Long getOneOrdenS() {
        return oneOrdenS;
    }

    public void setOneOrdenS(Long oneOrdenS) {
        this.oneOrdenS = oneOrdenS;
    }

    public Long getOneCircuito() {
        return oneCircuito;
    }

    public void setOneCircuito(Long oneCircuito) {
        this.oneCircuito = oneCircuito;
    }

    public Boolean getBajaLogica() {
        return bajaLogica;
    }

    public void setBajaLogica(Boolean bajaLogica) {
        this.bajaLogica = bajaLogica;
    }


    /**
     * Gets fecha finalizacion.
     *
     * @return the fecha finalizacion
     */
    public LocalDateTime getFechaFinalizacion() {
        return fechaFinalizacion;
    }

    /**
     * Sets fecha finalizacion.
     *
     * @param fechaFinalizacion the fecha finalizacion
     */
    public void setFechaFinalizacion(LocalDateTime fechaFinalizacion) {
        this.fechaFinalizacion = fechaFinalizacion;
    }

}
