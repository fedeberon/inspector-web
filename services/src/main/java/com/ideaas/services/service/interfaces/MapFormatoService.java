package com.ideaas.services.service.interfaces;

import com.ideaas.services.domain.MapFormato;
import java.util.List;

public interface MapFormatoService {

    MapFormato get(Long id);

    MapFormato save(MapFormato mapFormato);

    List<MapFormato> findAll(Integer pageSize, Integer pageNo, String sortBy);

    List<MapFormato> findAll();

    List<MapFormato> findByOrderByDescripcionAsc();

}
