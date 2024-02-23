package com.ideaas.services.dao;

import com.ideaas.services.domain.MapHeatmap;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MapHeatmapDao extends PagingAndSortingRepository<MapHeatmap, Long> {
}
