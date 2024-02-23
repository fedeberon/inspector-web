package com.ideaas.services.service.interfaces;

import com.ideaas.services.domain.MapBusRecorrido;

import java.util.List;

public interface MapBusRecorridoService {

    MapBusRecorrido get(Long id);

    MapBusRecorrido save(MapBusRecorrido mapBusRecorrido);

    List<MapBusRecorrido> findAll(Integer pageSize, Integer pageNo, String sortBy);
}
