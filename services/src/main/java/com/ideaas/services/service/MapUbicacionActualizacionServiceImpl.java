package com.ideaas.services.service;

import com.ideaas.services.dao.FilterDao;
import com.ideaas.services.dao.MapUbicacionActualizacionDao;
import com.ideaas.services.domain.MapUbicacionActualizacion;
import com.ideaas.services.domain.MapUbicacionActualizacionPK;
import com.ideaas.services.request.UbicacionActualizacionRequest;
import com.ideaas.services.service.interfaces.MapUbicacionActualizacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class MapUbicacionActualizacionServiceImpl implements MapUbicacionActualizacionService {

    private MapUbicacionActualizacionDao dao;
    private FilterDao filterDao;

    @Autowired
    public MapUbicacionActualizacionServiceImpl(MapUbicacionActualizacionDao dao, FilterDao filterDao) {
        this.dao = dao;
        this.filterDao = filterDao;
    }

    @Override
    public MapUbicacionActualizacion get(MapUbicacionActualizacionPK id) {
        return dao.findById(id).get();
    }

    @Override
    public MapUbicacionActualizacion save(MapUbicacionActualizacion mapUbicacionActualizacion) {
        return dao.save(mapUbicacionActualizacion);
    }

    @Override
    public List<MapUbicacionActualizacion> findAll(Integer pageSize, Integer pageNo, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());
        Page<MapUbicacionActualizacion> mapUbicacionActualizaciones = dao.findAll(paging);

        return mapUbicacionActualizaciones.getContent();
    }

    @Override
    public List<MapUbicacionActualizacion> findAll() {
        Iterable<MapUbicacionActualizacion> iterator = dao.findAll();

        return  StreamSupport
                .stream(iterator.spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public List<MapUbicacionActualizacion> findAll(UbicacionActualizacionRequest ubicacionActualizacionRequest) {
        return filterDao.find(ubicacionActualizacionRequest);
    }
}
