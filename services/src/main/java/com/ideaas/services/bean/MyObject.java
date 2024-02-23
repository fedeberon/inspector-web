package com.ideaas.services.bean;

import com.ideaas.services.domain.AppRelevamiento;
import com.ideaas.services.domain.AppUbicacion;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * Created by federicoberon on 29/10/2019.
 */
@Component
public class MyObject {

    private Long id;
    private Long oneClaveUbi;
    private Boolean checked = false;
    private BigDecimal latitud;
    private BigDecimal longitud;
    private String name;
    private String description;
    private String address;
    private String barrio;
    private String provincia;
    private String localidad;
    private String zona;
    private String polygonCoordinates;
    private Long idEmpresa;
    private Boolean bajaLogica;
    private AppUbicacion appUbicacion;
    private AppRelevamiento appRelevamiento;
    private Long cantidad;
    private String evp;
    private String elemento;
    private String anunciante;
    private String producto;
    private String referencia;
    private Long oneOrdenS;
    private Long oneCircuito;

    public MyObject(){}

    public MyObject(Long id, Long oneClaveUbi, String direccion, String barrio, String localidad, String provincia, String zona, BigDecimal latitud, BigDecimal longitud, Boolean bajaLogica) {
        this.id = id;
        this.oneClaveUbi = oneClaveUbi;
        this.address = direccion;
        this.barrio = barrio;
        this.localidad = localidad;
        this.provincia = provincia;
        this.zona = zona;
        this.latitud = latitud;
        this.longitud = longitud;
        this.bajaLogica = bajaLogica;
    }

    public MyObject(Long id, AppUbicacion appUbicacion, AppRelevamiento appRelevamiento, Long cantidad, String evp, String elemento, String anunciante, String producto, String referencias, Long oneOrdenS, Long oneCircuito, Boolean bajaLogica) {
        this.id = id;
        this.appUbicacion = appUbicacion;
        this.appRelevamiento = appRelevamiento;
        this.cantidad = cantidad;
        this.evp = evp;
        this.elemento = elemento;
        this.anunciante = anunciante;
        this.producto = producto;
        this.referencia = referencias;
        this.oneOrdenS = oneOrdenS;
        this.oneCircuito = oneCircuito;
        this.bajaLogica = bajaLogica;
    }

    public MyObject(Long id, BigDecimal latitud, BigDecimal longitud, String address){
        this.id = id;
        this.latitud = latitud;
        this.longitud = longitud;
        this.address = address;
    }


    public MyObject(Long id, BigDecimal latitud, BigDecimal longitud, String name, String description, String address, String provincia, String localidad, String polygonCoordinates, Long idEmpresa) {
        this.id = id;
        this.latitud = latitud;
        this.longitud = longitud;
        this.name = name;
        this.description = description;
        this.address = address;
        this.provincia = provincia;
        this.localidad = localidad;
        this.polygonCoordinates = polygonCoordinates;
        this.idEmpresa = idEmpresa;
    }

    public String getDescription() {
        return description;
    }

    public String getAddress() {
        return address;
    }

    public Long getId() {
        return id;
    }

    public Boolean getChecked() {
        return checked;
    }

    public BigDecimal getLatitud() {
        return latitud;
    }

    public BigDecimal getLongitud() {
        return longitud;
    }

    public String getName() {
        return name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public void setLatitud(BigDecimal latitud) {
        this.latitud = latitud;
    }

    public void setLongitud(BigDecimal longitud) {
        this.longitud = longitud;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getPolygonCoordinates() {
        return polygonCoordinates;
    }

    public void setPolygonCoordinates(String polygonCoordinates) {
        this.polygonCoordinates = polygonCoordinates;
    }

    public Long getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(Long idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public Boolean getBajaLogica() {
        return bajaLogica;
    }

    public void setBajaLogica(Boolean bajaLogica) {
        this.bajaLogica = bajaLogica;
    }

    public String getBarrio() {
        return barrio;
    }

    public void setBarrio(String barrio) {
        this.barrio = barrio;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    public Long getOneClaveUbi() {
        return oneClaveUbi;
    }

    public void setOneClaveUbi(Long oneClaveUbi) {
        this.oneClaveUbi = oneClaveUbi;
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

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
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
}
