package com.ideaas.services.dao;

import com.ideaas.services.domain.MapProvincia;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MapProvinciaDao extends PagingAndSortingRepository<MapProvincia, Long> {

    @Query("SELECT DISTINCT mpr " +
            "FROM MapProvincia mpr " +
            "WHERE (-1 IN ?1 OR mpr.id IN " +
            "(SELECT DISTINCT mpr2.id " +
            "FROM MapUbicacion mu " +
            "INNER JOIN mu.mapProvincia mpr2 " +
            "INNER JOIN mu.mapEmpresa mem " +
            "WHERE mem.id IN ?1))")
    List<MapProvincia> findAll(List<Integer> mapCompanyIDs);

    @Query("SELECT DISTINCT mpr " +
            "FROM MapProvincia mpr " +
            "WHERE (-1 IN ?1 OR mpr.id IN " +
            "(SELECT DISTINCT mpr2.id " +
            "FROM MapUbicacion mu " +
            "INNER JOIN mu.mapProvincia mpr2 " +
            "INNER JOIN mu.mapEmpresa mem " +
            "WHERE mem.id IN ?1)) " +
            "ORDER BY mpr.descripcion")
    List<MapProvincia> findByOrderByDescripcionAsc(List<Integer> mapCompanyIDs);
}
