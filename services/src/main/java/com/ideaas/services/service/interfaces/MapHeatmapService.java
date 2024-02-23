package com.ideaas.services.service.interfaces;

import com.ideaas.services.domain.MapHeatmap;

import java.util.List;

public interface MapHeatmapService {

    MapHeatmap get(Long id);

    MapHeatmap save(MapHeatmap mapHeatMap);

    List<MapHeatmap> findAll(Integer pageSize, Integer pageNo, String sortBy);

    List<MapHeatmap> findAll();
}
