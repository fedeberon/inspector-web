package com.ideaas.services.service.interfaces;

import com.ideaas.services.domain.MapProvincia;

import java.util.List;

public interface MapProvinciaService {

    MapProvincia get(Long id);

    MapProvincia save(MapProvincia mapProvincia);

    List<MapProvincia> findAll(Integer pageSize, Integer pageNo, String sortBy);

    List<MapProvincia> findAll();

    List<MapProvincia> findByOrderByDescripcionAsc();
}
