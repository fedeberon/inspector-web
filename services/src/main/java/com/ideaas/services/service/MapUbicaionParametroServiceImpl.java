package com.ideaas.services.service;

import com.ideaas.services.dao.MapUbicaionParametroDao;
import com.ideaas.services.domain.MapUbicaionParametro;
import com.ideaas.services.service.interfaces.MapUbicaionParametroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MapUbicaionParametroServiceImpl implements MapUbicaionParametroService {

    @Autowired
    private MapUbicaionParametroDao dao;

    @Override
    public MapUbicaionParametro get(Long id) {
        return this.dao.findById(id).get();
    }

    @Override
    public Optional<MapUbicaionParametro> findById(Long id) {
        return this.dao.findById(id);
    }

    @Override
    public MapUbicaionParametro save(MapUbicaionParametro mapUbicaionParametro) {
        return this.dao.save(mapUbicaionParametro);
    }

    @Override
    public List<MapUbicaionParametro> saveAll(List<MapUbicaionParametro> mapUbicaionParametros) {
        List<MapUbicaionParametro> retorno = new ArrayList<>();
        for (MapUbicaionParametro mapUbicaionParametro : this.dao.saveAll(mapUbicaionParametros) ) {
            retorno.add(mapUbicaionParametro);
        }
        return retorno;
    }

    @Override
    public List<MapUbicaionParametro> findAll(Integer pageSize, Integer pageNo, String sortBy) {
        return this.dao.findAll();
    }

    @Override
    public List<MapUbicaionParametro> findAllByLocationsIDs(List<Long> locationsIDs) {
        return this.dao.findAllByLocationsIDs(locationsIDs);
    }

    @Override
    public void delete(MapUbicaionParametro mapUbicaionParametro) {
        this.dao.delete(mapUbicaionParametro);
    }
}
