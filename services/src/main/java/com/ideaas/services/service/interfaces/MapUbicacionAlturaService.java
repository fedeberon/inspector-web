package com.ideaas.services.service.interfaces;

import com.ideaas.services.domain.MapUbicacionAltura;

import java.util.List;

public interface MapUbicacionAlturaService {

    MapUbicacionAltura get(Long id);

    MapUbicacionAltura save(MapUbicacionAltura mapUbicacionAltura);

    List<MapUbicacionAltura> findAll(Integer pageSize, Integer pageNo, String sortBy);

    List<MapUbicacionAltura> findAll();
}
