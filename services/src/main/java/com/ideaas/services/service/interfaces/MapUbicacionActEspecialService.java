package com.ideaas.services.service.interfaces;

import com.ideaas.services.domain.MapUbicacionActEspecial;
import com.ideaas.services.domain.MapUbicacionActEspecialPK;
import com.ideaas.services.request.MapUbiActEspecialRequest;

import java.util.List;

public interface MapUbicacionActEspecialService {

    MapUbicacionActEspecial get(MapUbicacionActEspecialPK id);

    MapUbicacionActEspecial save(MapUbicacionActEspecial mapUbicacionActEspecial);

    List<MapUbicacionActEspecial> findAll(Integer pageSize, Integer pageNo, String sortBy);

    List<MapUbicacionActEspecial> findAll();

    List<MapUbicacionActEspecial> findAll(MapUbiActEspecialRequest mapUbiActEspecialRequest);
}
