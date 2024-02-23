package com.ideaas.services.dao;

import com.ideaas.services.domain.MapUbicacionActualizacion;
import com.ideaas.services.domain.MapUbicacionActualizacionPK;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MapUbicacionActualizacionDao extends PagingAndSortingRepository<MapUbicacionActualizacion, MapUbicacionActualizacionPK> {
}
