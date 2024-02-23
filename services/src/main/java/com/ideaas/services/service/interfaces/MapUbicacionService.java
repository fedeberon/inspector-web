package com.ideaas.services.service.interfaces;

import com.ideaas.services.bean.MapPlanningDTO;
import com.ideaas.services.bean.MyObject;
import com.ideaas.services.domain.MapUbicacion;
import com.ideaas.services.exception.StockException;
import com.ideaas.services.request.MapUbicacionRequest;

import java.util.List;
import java.util.Optional;

public interface MapUbicacionService extends MapCompanyFilterIdsService {

    MapUbicacion get(Long id);

    MapUbicacion save(MapUbicacion mapUbicacion);

    Optional<MapUbicacion> findById(Long id);

    List<MapUbicacion> findAll(Integer pageSize, Integer pageNo, String sortBy);

    List<MapUbicacion> findAll(MapUbicacionRequest mapUbicacionRequest);

    List<MapUbicacion> saveList(MapUbicacionRequest mapUbicacionRequest) throws StockException;

    void saveLatLong(MapUbicacionRequest request);

    void savePolygon(MapUbicacionRequest request);

    MapUbicacion deletePolygonLatLong(Long idUbicacion);

    List<MyObject> findAllByIdIn(List<Long> idMapUbicacionesListSelected);

    List<MapPlanningDTO> findAllMapPlanningDTOsByLocationsIDs(MapUbicacionRequest mapUbicacionRequest);

    int countAllInactiveLocationsOfStock(Long idElemento, Long idEmpresa);

    int countAllActiveLocations(Long idElemento);
}
