package com.ideaas.services.dao;

import com.ideaas.services.domain.MapBusElemento;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MapBusElementoDao extends PagingAndSortingRepository<MapBusElemento, Long> {
}
