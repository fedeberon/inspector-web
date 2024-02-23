package com.ideaas.services.dao;

import com.ideaas.services.domain.MapPoiSector;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MapPoiSectorDao extends PagingAndSortingRepository<MapPoiSector, Long> {
}
