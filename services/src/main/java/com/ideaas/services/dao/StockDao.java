package com.ideaas.services.dao;

import com.ideaas.services.domain.MapEmpresa;
import com.ideaas.services.domain.Stock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface StockDao extends PagingAndSortingRepository<Stock, Long> {

    // @Query("SELECT DISTINCT s FROM Stock s JOIN s.empresas MapEmpresa WHERE  :empresa MEMBER OF s.empresas AND s.idStock = :idStock")
    @Query("SELECT DISTINCT s FROM Stock s WHERE s.idStock  = :idStock AND (-1L IN :idMapEmpresas OR s.mapEmpresa.id IN :idMapEmpresas)")
    Stock findAByIdAndMapEmpresa(@Param("idStock") Long idStock, @Param("idMapEmpresas") List<Long> idMapEmpresas);


    @Query("SELECT  s FROM Stock s")
    List<Stock> findAll();

    @Query("SELECT DISTINCT s FROM Stock s INNER JOIN s.mapEmpresa mem WHERE (-1L IN :idEmpresas OR mem.id IN :idEmpresas)")
    List<Stock> findAllByMapEmpresa(@Param("idEmpresas") List<Long> idEmpresas);

    Stock findAllByMapEmpresaIdAndMapElementoId(Long mapEmpresaId, Long MapElementoId);

    @Query("SELECT s " +
            "FROM MapUbicacion  mup " +
            "INNER JOIN mup.mapEmpresa mem " +
            "INNER JOIN mup.mapElemento mel, " +
            "Stock s " +
            "WHERE  " +
            "    s.mapElemento.id = mel.id AND " +
            "    s.mapEmpresa.id = mem.id AND" +
            "    mup.id = ?1")
    Stock findByLocationId(Long locaitonId);
}
