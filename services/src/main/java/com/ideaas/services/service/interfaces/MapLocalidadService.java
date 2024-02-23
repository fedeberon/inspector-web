package com.ideaas.services.service.interfaces;

import com.ideaas.services.domain.MapLocalidad;

import java.util.List;

public interface MapLocalidadService {

    MapLocalidad get(Long id);

    MapLocalidad save(MapLocalidad mapLocalidad);

    List<MapLocalidad> findAll(Integer pageSize, Integer pageNo, String sortBy);

    List<MapLocalidad> findAll();

    List<MapLocalidad> findByOrderByDescripcionAsc();
}
