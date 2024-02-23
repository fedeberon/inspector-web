package com.ideaas.services.service.interfaces;

import com.ideaas.services.domain.MapPoiEntidad;

import java.util.List;

public interface MapPoiEntidadService {

    MapPoiEntidad get(Long id);

    MapPoiEntidad save(MapPoiEntidad mapPoiEntidad);

    List<MapPoiEntidad> findAll(Integer pageSize, Integer pageNo, String sortBy);

    List<MapPoiEntidad> findAll();

    List<MapPoiEntidad> findByMapPoiSector_DescripcionIn(List<String> poiSectorDescripciones);
}
