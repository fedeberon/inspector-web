package com.ideaas.services.service.interfaces;

import com.ideaas.services.domain.MapUbicaionParametro;

import java.util.List;
import java.util.Optional;

public interface MapUbicaionParametroService {

    MapUbicaionParametro get(Long id);

    MapUbicaionParametro save(MapUbicaionParametro mapUbicaionParametro);

    List<MapUbicaionParametro> saveAll(List<MapUbicaionParametro> mapUbicaionParametros);

    Optional<MapUbicaionParametro> findById(Long id);

    List<MapUbicaionParametro> findAll(Integer pageSize, Integer pageNo, String sortBy);

    List<MapUbicaionParametro> findAllByLocationsIDs(List<Long> locationsIDs);

    void delete(MapUbicaionParametro mapUbicaionParametro);

}
