package com.ideaas.services.dao;


import com.ideaas.services.domain.MapElemento;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MapElementoDao extends PagingAndSortingRepository<MapElemento, Long> {

    @Query("SELECT DISTINCT mel " +
            "FROM MapElemento mel " +
            "WHERE (-1 IN ?1 OR mel.id IN " +
            "(SELECT DISTINCT mel2.id " +
            "FROM MapUbicacion mu " +
            "INNER JOIN mu.mapElemento mel2 " +
            "INNER JOIN mu.mapEmpresa mem " +
            "WHERE mem.id IN ?1))")
    List<MapElemento> findAll(List<Integer> mapCompanyIDs);

    @Query("SELECT DISTINCT mel " +
            "FROM MapElemento mel " +
            "WHERE (-1 IN ?1 OR mel.id IN " +
            "(SELECT DISTINCT mel2.id " +
            "FROM MapUbicacion mu " +
            "INNER JOIN mu.mapElemento mel2 " +
            "INNER JOIN mu.mapEmpresa mem " +
            "WHERE mem.id IN ?1)) " +
            "ORDER BY mel.descripcion")
    List<MapElemento> findByOrderByDescripcionAsc(List<Integer> mapCompanyIDs);

    @Query("SELECT DISTINCT mel " +
            "FROM MapUbicacion mup " +
            "INNER JOIN mup.mapEmpresa mem " +
            "INNER JOIN mup.mapElemento mel " +
            "WHERE mem.id = :mapCompanyId " +
            "AND mup.mapElemento.id NOT IN " +
            "   (SELECT s.mapElemento.id " +
            "   FROM Stock s " +
            "   WHERE s.mapEmpresa.id = :mapCompanyId)")
    List<MapElemento> findByMapEmpresaAndNoInitializedStock(@Param("mapCompanyId") Long mapCompanyIds);
}
