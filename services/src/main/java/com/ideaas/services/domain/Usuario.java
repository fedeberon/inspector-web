package com.ideaas.services.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ideaas.services.enums.TipoUsuarioEnum;
import com.ideaas.services.request.UsuarioMenuRequest;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "usuarios")
// @Data

public class Usuario {

    @Id
    @Column(name = "idUsuario")
    @SequenceGenerator(name = "UsuarioSeqGen", sequenceName = "SEQ_USUARIO", allocationSize = 1)
    @GeneratedValue(generator = "UsuarioSeqGen")
    private Long id;

    @Column(name = "usuario")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "nombreCompleto")
    private String nombreCompleto;

    @Column(name = "eMail")
    private String eMail;

    @Column(name = "telefono")
    private String telefono;

    @Column(name = "estado")
    private String estado;

    @ManyToOne
    @JoinColumn(name = "idTipoUsuario")
    private TipoUsuario tipoUsuario;

    //Cliente depreco esta columna
    @Transient
    private MapAnunciante mapAnunciante;

    //Cliente depreco esta columna
    @Transient
    private Producto producto;

    @Column(name = "cargo")
    private String cargo;

    @ManyToOne
    @JoinColumn(name = "idContratoInv")
    private Contrato contratoInv;

    @ManyToOne
    @JoinColumn(name = "idContratoAud")
    private Contrato contratoAud;

    @ManyToOne
    @JoinColumn(name = "idContratoMap")
    private Contrato contratoMap;

    @Transient
    private UsuarioMenuRequest usuarioMenuRequest;

    @Column(name = "fechaVencimiento")
    private String fechaVencimiento;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "map_empresa_usuario",
            joinColumns = @JoinColumn(name = "idUsuario",
                    referencedColumnName = "idUsuario"),
            inverseJoinColumns = @JoinColumn(name = "idEmpresa",
                    referencedColumnName = "idEmpresa")
    )
    private List<MapEmpresa> empresas;

    public Usuario() { }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public MapAnunciante getMapAnunciante() {
        return mapAnunciante;
    }

    public void setMapAnunciante(MapAnunciante mapAnunciante) {
        this.mapAnunciante = mapAnunciante;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public Contrato getContratoInv() {
        return contratoInv;
    }

    public void setContratoInv(Contrato contratoInv) {
        this.contratoInv = contratoInv;
    }

    public Contrato getContratoAud() {
        return contratoAud;
    }

    public void setContratoAud(Contrato contratoAud) {
        this.contratoAud = contratoAud;
    }

    public Contrato getContratoMap() {
        return contratoMap;
    }

    public void setContratoMap(Contrato contratoMap) {
        this.contratoMap = contratoMap;
    }

    public UsuarioMenuRequest getUsuarioMenuRequest() {
        return usuarioMenuRequest;
    }

    public void setUsuarioMenuRequest(UsuarioMenuRequest usuarioMenuRequest) {
        this.usuarioMenuRequest = usuarioMenuRequest;
    }

    public String getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(String fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public List<MapEmpresa> getEmpresas() {
        return empresas;
    }

    public void setEmpresas(List<MapEmpresa> empresas) {
        this.empresas = empresas;
    }

    public boolean isTipo(TipoUsuarioEnum tipoUsuario) {
        return this.tipoUsuario.getId() == tipoUsuario.getId();
    }
}