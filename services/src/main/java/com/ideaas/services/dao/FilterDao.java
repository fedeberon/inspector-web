package com.ideaas.services.dao;

import com.ideaas.services.domain.*;
import com.ideaas.services.request.MapPoiRequest;
import com.ideaas.services.request.MapUbiActEspecialRequest;
import com.ideaas.services.request.MapUbicacionRequest;
import com.ideaas.services.request.UbicacionActualizacionRequest;

import java.util.List;
import java.util.Map;

public interface FilterDao {

    // List<MapUbicacion> find(MapUbicacionRequest request, Usuario usuario);

    List<MapUbicacion> find(MapUbicacionRequest request);

    List<MapUbicacion> filterSearchUbicacion(Map<String, String> clauses);

    List<MapUbicacionActualizacion> find(UbicacionActualizacionRequest ubicacionActualizacionRequest);

    List<MapUbicacionActEspecial> find(MapUbiActEspecialRequest mapUbiActEspecialRequest);

    List<MapPoi> find(MapPoiRequest mapPoiRequest);
}
