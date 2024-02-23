package com.ideaas.services.service.interfaces;

import com.ideaas.services.domain.MapUbicacionActualizacion;
import com.ideaas.services.domain.MapUbicacionActualizacionPK;
import com.ideaas.services.request.UbicacionActualizacionRequest;

import java.util.List;

public interface MapUbicacionActualizacionService {

    MapUbicacionActualizacion get(MapUbicacionActualizacionPK id);

    MapUbicacionActualizacion save(MapUbicacionActualizacion mapUbicacionActualizacion);

    List<MapUbicacionActualizacion> findAll(Integer pageSize, Integer pageNo, String sortBy);

    List<MapUbicacionActualizacion> findAll();

    List<MapUbicacionActualizacion> findAll(UbicacionActualizacionRequest ubicacionActualizacionRequest);

}
