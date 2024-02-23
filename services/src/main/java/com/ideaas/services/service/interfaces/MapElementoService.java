package com.ideaas.services.service.interfaces;


import com.ideaas.services.domain.MapElemento;

import java.util.List;

public interface MapElementoService {

    MapElemento get(Long id);

    MapElemento save(MapElemento mapElemento);

    List<MapElemento> findAll(Integer pageSize, Integer pageNo, String sortBy);

    List<MapElemento> findAll();

    List<MapElemento> findByOrderByDescripcionAsc();

    List<MapElemento> findByMapEmpresaAndNoInitializedStock(Long mapCompanyId);
}
