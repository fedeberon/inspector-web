package com.ideaas.services.service.interfaces;

import com.ideaas.services.domain.MapBus;

import java.util.List;

public interface MapBusService {

    MapBus get(Long id);

    MapBus save(MapBus mapBus);

    List<MapBus> findAll(Integer pageSize, Integer pageNo, String sortBy);

    List<MapBus> findAll();
}
