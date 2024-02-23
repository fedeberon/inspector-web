package com.ideaas.services.dao;

import com.ideaas.services.domain.MapBus;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MapBusDao extends PagingAndSortingRepository<MapBus, Long> {
}
