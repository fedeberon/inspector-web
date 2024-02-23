package com.ideaas.services.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
@Table(name = "app_respuesta")
public class AppRespuesta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idRespuesta", unique= true, nullable= false)
    @JsonProperty("idRespuesta")
    private Long id;

    @Column(name = "one_orden_s")
    @JsonProperty("orden")
    private Long oneOrdenS;

    @Column(name = "one_circuito")
    @JsonProperty("circuito")
    private Long oneCircuito;

    @ManyToOne
    @JoinColumn(name = "idUbicacion")
    @JsonProperty("appUbicacionRelevamiento")
    private AppUbicacionRelevamiento appUbicacionRelevamiento;

    @ManyToOne
    @JoinColumn(name = "idRelevamiento")
    @JsonProperty("appRelevamiento")
    private AppRelevamiento appRelevamiento;

    @Column(name = "respuesta")
    @JsonProperty("respuesta")
    private String respuesta;

    @Column(name = "estado" , columnDefinition = "tinyint(1) default 0")
    @JsonProperty("estado")
    private Boolean estado;

    @Column(name = "token")
    @JsonProperty("token")
    private String token;

    public AppRespuesta() {
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public AppUbicacionRelevamiento getAppUbicacionRelevamiento() {
        return appUbicacionRelevamiento;
    }

    public void setAppUbicacionRelevamiento(AppUbicacionRelevamiento appUbicacionRelevamiento) {
        this.appUbicacionRelevamiento = appUbicacionRelevamiento;
    }

    public AppRelevamiento getAppRelevamiento() {
        return appRelevamiento;
    }

    public void setAppRelevamiento(AppRelevamiento appRelevamiento) {
        this.appRelevamiento = appRelevamiento;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
