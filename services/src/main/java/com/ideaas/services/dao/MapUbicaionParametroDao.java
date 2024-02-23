package com.ideaas.services.dao;

import com.ideaas.services.domain.MapUbicaionParametro;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MapUbicaionParametroDao extends PagingAndSortingRepository<MapUbicaionParametro, Long> {

    List<MapUbicaionParametro> findAll();

    @Query("SELECT mup FROM MapUbicaionParametro mup LEFT JOIN FETCH mup.parametro p INNER JOIN mup.mapUbicacion mu where mu.id IN (?1)")
    List<MapUbicaionParametro> findAllByLocationsIDs(List<Long> locationsIDs);
}
