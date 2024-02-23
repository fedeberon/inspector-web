package com.ideaas.services.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.ColumnDefault;
import javax.persistence.*;

@Entity
@Table(name = "app_relevamiento")
public class AppRelevamiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("idRelevamiento")
    @Column(name = "idRelevamiento", unique= true, nullable= false)
    private Long id;

    @Column(name = "nombreRelevamiento")
    private String nombreRelevamiento;

    @ManyToOne
    @JoinColumn(name = "idUsuario")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "idFormulario")
    private AppFormulario formulario;

    @ManyToOne
    @JoinColumn(name = "idEstado")
    private AppEstadoRelevamiento estado;

    @Column(name = "con_ubicaciones_preasignadas")
    private Boolean conUbicacionesPreAsignadas;

    @Column(name = "fecha_asignacion")
    private String fechaAsignacion;
    
    @Transient
    private Integer cantidadDeUbicacionesByRelevamiento;

    @Transient
    private Long idRelevamientoToCopy;

    @Deprecated
    @ColumnDefault("false")
    @Column(name = "bajaLogica")
    private Boolean bajaLogica;

    @ManyToOne
    @JoinColumn(name = "id_app_proyecto")
    private AppProject project;

    @JsonProperty("id_geocoding_ruta_relevamiento")
    @Column(name = "id_geocoding_ruta_relevamiento")
    private String id_geocoding_ruta_relevamiento;

    public AppRelevamiento() {
    }

    public AppRelevamiento(Long id) {
        super();
        this.id = id;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreRelevamiento() {
        return nombreRelevamiento;
    }

    public void setNombreRelevamiento(String nombreRelevamiento) {
        this.nombreRelevamiento = nombreRelevamiento;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public AppFormulario getFormulario() {
        return formulario;
    }

    public void setFormulario(AppFormulario formulario) {
        this.formulario = formulario;
    }

    public AppEstadoRelevamiento getEstado() {
        return estado;
    }

    public void setEstado(AppEstadoRelevamiento estado) {
        this.estado = estado;
    }

    public Boolean getConUbicacionesPreAsignadas() {
        return conUbicacionesPreAsignadas;
    }

    public void setConUbicacionesPreAsignadas(Boolean conUbicacionesPreAsignadas) {
        this.conUbicacionesPreAsignadas = conUbicacionesPreAsignadas;
    }

    public String getFechaAsignacion() {
        return fechaAsignacion;
    }

    public void setFechaAsignacion(String fechaAsignacion) {
        this.fechaAsignacion = fechaAsignacion;
    }

    public Long getIdRelevamientoToCopy() {
        return idRelevamientoToCopy;
    }

    public void setIdRelevamientoToCopy(Long idRelevamientoToCopy) {
        this.idRelevamientoToCopy = idRelevamientoToCopy;
    }

    public Boolean getBajaLogica() {
        return bajaLogica;
    }

    public void setBajaLogica(Boolean bajaLogicaFormulario) {
        this.bajaLogica = bajaLogicaFormulario;
    }
    
    public Integer getCantidadDeUbicacionesByRelevamiento() {
        return cantidadDeUbicacionesByRelevamiento;
    }

	public void setCantidadDeUbicacionesByRelevamiento(Integer cantidadDeUbicacionesByRelevamiento) {
	        this.cantidadDeUbicacionesByRelevamiento = cantidadDeUbicacionesByRelevamiento;
	}

    public AppProject getProject() {
        return project;
    }

    public void setProject(AppProject project) {
        this.project = project;
    }

    public String getId_geocoding_ruta_relevamiento() {
        return id_geocoding_ruta_relevamiento;
    }

    public void setId_geocoding_ruta_relevamiento(String id_geocoding_ruta_relevamiento) {
        this.id_geocoding_ruta_relevamiento = id_geocoding_ruta_relevamiento;
    }

}
