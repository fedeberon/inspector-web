package com.ideaas.services.dao;

import com.ideaas.services.domain.MapFormato;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MapFormatoDao extends PagingAndSortingRepository<MapFormato, Long> {

    @Query("SELECT DISTINCT mfo " +
            "FROM MapFormato mfo " +
            "WHERE (-1 IN ?1 OR mfo.id IN " +
            "(SELECT DISTINCT mfo2.id " +
            "FROM MapUbicacion mu " +
            "INNER JOIN mu.mapFormato mfo2 " +
            "INNER JOIN mu.mapEmpresa mem " +
            "WHERE mem.id IN ?1))")
    List<MapFormato> findAll(List<Integer> mapCompanyIDs);

    @Query("SELECT DISTINCT mfo " +
            "FROM MapFormato mfo " +
            "WHERE (-1 IN ?1 OR mfo.id IN " +
            "(SELECT DISTINCT mfo2.id " +
            "FROM MapUbicacion mu " +
            "INNER JOIN mu.mapFormato mfo2 " +
            "INNER JOIN mu.mapEmpresa mem " +
            "WHERE mem.id IN ?1)) " +
            "ORDER BY mfo.descripcion")
    List<MapFormato> findByOrderByDescripcionAsc(List<Integer> mapCompanyIDs);
}
