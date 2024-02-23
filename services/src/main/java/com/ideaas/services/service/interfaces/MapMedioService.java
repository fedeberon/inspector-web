package com.ideaas.services.service.interfaces;

import com.ideaas.services.domain.MapMedio;

import java.util.List;

public interface MapMedioService {

    MapMedio get(Long id);

    MapMedio save(MapMedio mapMedio);

    List<MapMedio> findAll(Integer pageSize, Integer pageNo, String sortBy);

    List<MapMedio> findAll();

    List<MapMedio> findByOrderByDescripcionAsc();
}
