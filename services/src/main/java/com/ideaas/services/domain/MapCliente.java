package com.ideaas.services.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * <p>The "MapCliente" entity is a type of company. They are different from the companies
 * that are stored in the "MapEmpresa" entity. This entity saves the companies that want to rent
 * for a range of time, in order to put their advertising, the locations of a company out of home
 * company, which are represented by "MapEmpresa" companies
 *
 * @See MapEmpresa
 */
@Entity
@Table(name = "map_cliente")
public class MapCliente {
    /**
     * The cliente id.
     */
    @Id
    @SequenceGenerator(name = "MapClienteSeqGen", sequenceName = "SEQ_MAP_CLIENTE", allocationSize = 1)
    @GeneratedValue(generator = "MapClienteSeqGen")
    @Column(name = "id_map_cliente")
    private Long id;

    /**
     * The cliente name.
     */
    @Column(nullable = false)
    private String nombre;

    /**
     * The cliente agency.
     */
    @Column
    private String agencia;

    /**
     * The cliente email.
     */
    @Column
    private String email;

    /**
     * The cliente phone number.
     */
    @Column
    private String telefono;

    /**
     * The cliente address.
     */
    @Column
    private String direccion;

    /**
     * The cliente Account Executive. This attribute is only useful for
     * OOH users to make reports.
     */
    @Column(name = "ejecutivo_de_cuenta")
    private String ejecutivoDeCuenta;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "map_empresa_map_cliente",
            joinColumns        = @JoinColumn(name = "id_map_cliente"   , referencedColumnName = "id_map_cliente"),
            inverseJoinColumns = @JoinColumn(name = "idEmpresa", referencedColumnName = "idEmpresa")
    )
    private List<MapEmpresa> mapEmpresas;

    /**
     * Instantiates a new Map cliente.
     */
    public MapCliente() {}

    /**
     * Instantiates a new Map cliente.
     */
    public MapCliente(String id) { this.id = Long.valueOf(id); }

    /**
     * Gets id.
     *
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets nombre.
     *
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Sets nombre.
     *
     * @param nombre the nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Gets agencia.
     *
     * @return the agencia
     */
    public String getAgencia() {
        return agencia;
    }


    /**
     * Gets email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets email.
     *
     * @param email the email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Sets agencia.
     *
     * @param agencia the agencia
     */
    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }

    /**
     * Gets telefono.
     *
     * @return the telefono
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Sets telefono.
     *
     * @param telefono the telefono
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * Gets direccion.
     *
     * @return the direccion
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * Sets direccion.
     *
     * @param direccion the direccion
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * Gets ejecutivo de cuenta.
     *
     * @return the ejecutivo de cuenta
     */
    public String getEjecutivoDeCuenta() {
        return ejecutivoDeCuenta;
    }

    /**
     * Sets ejecutivo de cuenta.
     *
     * @param ejecutivoDeCuenta the ejecutivo de cuenta
     */
    public void setEjecutivoDeCuenta(String ejecutivoDeCuenta) {
        this.ejecutivoDeCuenta = ejecutivoDeCuenta;
    }

    /**
     * Gets map empresas.
     *
     * @return the map empresas
     */
    public List<MapEmpresa> getMapEmpresas() {
        return mapEmpresas;
    }

    /**
     * Sets map empresas.
     *
     * @param mapEmpresas the map empresas
     */
    public void setMapEmpresas(List<MapEmpresa> mapEmpresas) {
        this.mapEmpresas = mapEmpresas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MapCliente)) return false;
        MapCliente that = (MapCliente) o;
        return Objects.equals(getId(), that.getId());
    }
}
