package com.ideaas.services.service.interfaces;

import com.ideaas.services.domain.MapPoi;
import com.ideaas.services.request.MapPoiRequest;

import java.util.List;

public interface MapPoiService {

    MapPoi get(Long id);

    MapPoi save(MapPoi mapPoi);

    List<MapPoi> findAll(Integer pageSize, Integer pageNo, String sortBy);

    List<MapPoi> findAll();

    List<MapPoi> findAll(MapPoiRequest mapPoiRequest);
}
