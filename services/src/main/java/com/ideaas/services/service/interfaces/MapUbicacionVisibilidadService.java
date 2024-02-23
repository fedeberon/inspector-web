package com.ideaas.services.service.interfaces;

import com.ideaas.services.domain.MapUbicacionVisibilidad;

import java.util.List;

public interface MapUbicacionVisibilidadService {

    MapUbicacionVisibilidad get(Long id);

    MapUbicacionVisibilidad save(MapUbicacionVisibilidad mapUbicacionVisibilidad);

    List<MapUbicacionVisibilidad> findAll(Integer pageSize, Integer pageNo, String sortBy);

    List<MapUbicacionVisibilidad> findAll();
}
