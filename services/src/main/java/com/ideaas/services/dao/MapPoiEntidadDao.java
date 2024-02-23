package com.ideaas.services.dao;

import com.ideaas.services.domain.MapPoiEntidad;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MapPoiEntidadDao extends PagingAndSortingRepository<MapPoiEntidad, Long> {

    List<MapPoiEntidad> findByMapPoiSector_DescripcionIn(List<String> poiSectorDescripciones);
}
