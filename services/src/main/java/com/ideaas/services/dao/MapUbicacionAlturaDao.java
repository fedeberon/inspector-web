package com.ideaas.services.dao;

import com.ideaas.services.domain.MapUbicacionAltura;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MapUbicacionAlturaDao extends PagingAndSortingRepository<MapUbicacionAltura, Long> {
}
