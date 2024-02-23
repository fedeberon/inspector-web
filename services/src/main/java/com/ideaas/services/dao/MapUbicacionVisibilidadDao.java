package com.ideaas.services.dao;

import com.ideaas.services.domain.MapUbicacionVisibilidad;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MapUbicacionVisibilidadDao extends PagingAndSortingRepository<MapUbicacionVisibilidad, Long> {
}
