package com.ideaas.services.dao;

import com.ideaas.services.domain.MapMedio;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface MapMedioDao extends PagingAndSortingRepository<MapMedio, Long> {
    @Query("SELECT DISTINCT mdi " +
            "FROM MapMedio mdi " +
            "WHERE (-1 IN ?1 OR mdi.id IN " +
            "(SELECT DISTINCT mdi2.id " +
            "FROM MapUbicacion mu " +
            "INNER JOIN mu.mapMedio mdi2 " +
            "INNER JOIN mu.mapEmpresa mem " +
            "WHERE mem.id IN ?1)) " +
            "ORDER BY mdi.descripcion")
    List<MapMedio> findByOrderByDescripcionAsc(List<Integer> mapCompanyIDs);
}
