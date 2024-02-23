package com.ideaas.services.dao;

import com.ideaas.services.domain.MapLocalidad;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MapLocalidadDao extends PagingAndSortingRepository<MapLocalidad, Long> {

    @Query("SELECT DISTINCT mlo " +
            "FROM MapLocalidad mlo " +
            "WHERE (-1 IN ?1 OR mlo.id IN " +
            "(SELECT DISTINCT mlo2.id " +
            "FROM MapUbicacion mu " +
            "INNER JOIN mu.mapLocalidad mlo2 " +
            "INNER JOIN mu.mapEmpresa mem  " +
            "WHERE mem.id IN ?1))")
    List<MapLocalidad> findAll(List<Integer> mapCompanyIDs);

    @Query("SELECT DISTINCT mlo " +
            "FROM MapLocalidad mlo " +
            "WHERE (-1 IN ?1 OR mlo.id IN " +
            "(SELECT DISTINCT mlo2.id " +
            "FROM MapUbicacion mu " +
            "INNER JOIN mu.mapLocalidad mlo2 " +
            "INNER JOIN mu.mapEmpresa mem  " +
            "WHERE mem.id IN ?1)) " +
            "ORDER BY mlo.descripcion")
    List<MapLocalidad> findByOrderByDescripcionAsc(List<Integer> mapCompanyIDs);
}
