package com.ideaas.services.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "app_ubicacion")
public class AppUbicacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idUbicacion", unique= true, nullable= false)
    @JsonProperty("idUbicacion")
    private Long id;

    @Column(name = "one_claveUbi")
    @JsonProperty("one_claveUbi")
    private Long oneClaveUbi;

    @Column(name = "direccion")
    @JsonProperty("direccion")
    private String direccion;

    @Column(name = "barrio")
    @JsonProperty("barrio")
    private String barrio;

    @Column(name = "localidad")
    @JsonProperty("localidad")
    private String localidad;

    @Column(name = "provincia")
    @JsonProperty("provincia")
    private String provincia;

    @Column(name = "zona")
    @JsonProperty("zona")
    private String zona;

    @Column(name = "geo_latitud")
    @JsonProperty("geo_latitud")
    private BigDecimal latitud;

    @Column(name = "geo_longitud")
    @JsonProperty("geo_longitud")
    private BigDecimal longitud;

    @ColumnDefault("false")
    @Column(name = "bajaLogica")
    private Boolean bajaLogica;

    @Transient
    private Long idRelevamiento;

    public AppUbicacion() {
    }

    public AppUbicacion(Long id, Long oneClaveUbi, String direccion, String barrio, String localidad, String provincia, String zona, BigDecimal latitud, BigDecimal longitud, Boolean bajaLogica) {
        this.id = id;
        this.oneClaveUbi = oneClaveUbi;
        this.direccion = direccion;
        this.barrio = barrio;
        this.localidad = localidad;
        this.provincia = provincia;
        this.zona = zona;
        this.latitud = latitud;
        this.longitud = longitud;
        this.bajaLogica = bajaLogica;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOneClaveUbi() {
        return oneClaveUbi;
    }

    public void setOneClaveUbi(Long oneClaveUbi) {
        this.oneClaveUbi = oneClaveUbi;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getBarrio() {
        return barrio;
    }

    public void setBarrio(String barrio) {
        this.barrio = barrio;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
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

    public Boolean getBajaLogica() {
        return bajaLogica;
    }

    public void setBajaLogica(Boolean bajaLogica) {
        this.bajaLogica = bajaLogica;
    }

    public Long getIdRelevamiento() {
        return idRelevamiento;
    }
}
