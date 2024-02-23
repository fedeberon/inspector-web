package com.ideaas.services.service;

import com.ideaas.services.dao.FilterDao;
import com.ideaas.services.domain.*;
import com.ideaas.services.request.MapUbicacionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;

/**
 * Created by federicoberon on 21/11/2019.
 */

@Service
public class FilterSearchUbicacionServiceImpl implements Function<MapUbicacionRequest, Map> {


    private FilterDao dao;

    @Autowired
    public FilterSearchUbicacionServiceImpl(FilterDao dao) {
        this.dao = dao;
    }

    @Override
    public Map apply(MapUbicacionRequest request){
        Map clauses = new HashMap();
        clauses.put("mapEmpresa.descripcion", request.getMapEmpresa());
        clauses.put("mapElemento.descripcion", request.getMapElemento());
        clauses.put("mapProvincia.descripcion", request.getMapProvincia());
        clauses.put("mapFormato.descripcion", request.getMapFormato());
        clauses.put("audLocalidad.descripcion", request.getMapLocalidad());
        clauses.put("mapMedio.descripcion", request.getMapMedio());
        List<MapUbicacion> ubicaciones = dao.filterSearchUbicacion(clauses);

        Set<MapEmpresa> empresas = new HashSet();
        ubicaciones.forEach(line -> empresas.add(line.getMapEmpresa()));

        Set<MapElemento> elementos = new HashSet();
        ubicaciones.forEach(line -> elementos.add(line.getMapElemento()));

        Set<MapProvincia> provincias = new HashSet();
        ubicaciones.forEach(line -> provincias.add(line.getMapProvincia()));

        Set<MapFormato> formato = new HashSet<>();
        ubicaciones.forEach(line -> formato.add(line.getMapFormato()));

        Set<MapLocalidad> localidad = new HashSet<>();
        ubicaciones.forEach(line -> localidad.add(line.getMapLocalidad()));

        Set<MapMedio> medios = new HashSet<>();
        ubicaciones.stream().filter(line -> medios.add(line.getMapMedio()));

        Map<String, Set> results = new HashMap<>();
        results.put("empresas", empresas);
        results.put("elementos", elementos);
        results.put("provincias", provincias);
        results.put("formatos", formato);
        results.put("localidades", localidad);
        results.put("medios", medios);

        return results;
    }

}
