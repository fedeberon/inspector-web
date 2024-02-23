package com.ideaas.services.service.interfaces;

import com.ideaas.services.domain.MapPoiSector;

import java.util.List;

public interface MapPoiSectorService {

    MapPoiSector get(Long id);

    MapPoiSector save(MapPoiSector mapPoiSector);

    List<MapPoiSector> findAll(Integer pageSize, Integer pageNo, String sortBy);

    List<MapPoiSector> findAll();
}
