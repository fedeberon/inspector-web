package com.ideaas.services.dao;

import com.ideaas.services.domain.MapBusRecorrido;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MapBusRecorridoDao extends PagingAndSortingRepository<MapBusRecorrido, Long> {
}
