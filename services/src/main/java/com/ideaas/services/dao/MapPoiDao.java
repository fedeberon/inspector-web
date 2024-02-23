package com.ideaas.services.dao;

import com.ideaas.services.domain.MapPoi;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MapPoiDao extends PagingAndSortingRepository<MapPoi, Long> {
}
