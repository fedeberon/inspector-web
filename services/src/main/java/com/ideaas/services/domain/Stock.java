package com.ideaas.services.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "stock")
public class Stock {

    @Id
    @SequenceGenerator(name = "StockSeqGen", sequenceName = "SEQ_STOCK", allocationSize = 0)
    @GeneratedValue(generator = "StockSeqGen")
    @Column(name="id_stock", nullable= false)
    private Long idStock;

    @Column(name = "cant_dispositivos_deposito")
    private int cantDispositivosDeposito;

    @Column(name = "cant_dispositivos_reparacion")
    private int cantDispositivosReparacion;

    @ManyToOne
    @JoinColumn(name = "idElemento")
    @JsonProperty("mapElemento")
    private MapElemento mapElemento;

    @ManyToOne
    @JoinColumn(name = "idEmpresa")
    private MapEmpresa mapEmpresa;

    @Formula("(SELECT " +
            "            COUNT(*)" +
            "        FROM" +
            "            map_ubicaciones AS mu" +
            "                INNER JOIN" +
            "            map_empresas mem ON mem.idEmpresa = mu.idEmpresa" +
            "                INNER JOIN" +
            "            map_elementos mel ON mel.idElemento = mu.idElemento" +
            "                INNER JOIN" +
            "            stock AS s ON (mel.idElemento = s.idElemento AND mem.idEmpresa = s.idEmpresa)" +
            "        WHERE" +
            "            mu.bajaLogica = 0 AND s.id_stock = id_stock)")
    private int cantDispositivosCalle;

    public Long getIdStock() {
        return idStock;
    }

    public int getCantDispositivosDeposito() {
        return cantDispositivosDeposito;
    }

    public void setCantDispositivosDeposito(int cantDispositivosDeposito) {
        this.cantDispositivosDeposito = cantDispositivosDeposito;
    }

    public int getCantDispositivosReparacion() {
        return cantDispositivosReparacion;
    }

    public void setCantDispositivosReparacion(int cantDispositivosRparacion) {
        this.cantDispositivosReparacion = cantDispositivosRparacion;
    }

    public MapElemento getMapElemento() {
        return mapElemento;
    }

    public void setMapElemento(MapElemento mapElemento){
        this.mapElemento = mapElemento;
    }

    public MapEmpresa getMapEmpresa() {
        return mapEmpresa;
    }

    public void setMapEmpresa(MapEmpresa mapEmpresa){
        this.mapEmpresa = mapEmpresa;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Stock)) return false;
        Stock stock = (Stock) o;
        return Objects.equals(getIdStock(), stock.getIdStock());
    }

}
