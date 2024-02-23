package com.ideaas.services.service;

import com.ideaas.services.dao.MapUbicacionVisibilidadDao;
import com.ideaas.services.domain.MapUbicacionVisibilidad;
import com.ideaas.services.service.interfaces.MapUbicacionVisibilidadService;
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
public class MapUbicacionVisibilidadServiceImpl implements MapUbicacionVisibilidadService {

    private MapUbicacionVisibilidadDao dao;

    @Autowired
    public MapUbicacionVisibilidadServiceImpl(MapUbicacionVisibilidadDao dao) {
        this.dao = dao;
    }

    @Override
    public MapUbicacionVisibilidad get(Long id) {
        return dao.findById(id).get();
    }

    @Override
    public MapUbicacionVisibilidad save(MapUbicacionVisibilidad mapUbicacionVisibilidad) {
        return dao.save(mapUbicacionVisibilidad);
    }

    @Override
    public List<MapUbicacionVisibilidad> findAll(Integer pageSize, Integer pageNo, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<MapUbicacionVisibilidad> mapUbicacionesVisibilidades = dao.findAll(paging);

        return mapUbicacionesVisibilidades.getContent();
    }

    @Override
    public List<MapUbicacionVisibilidad> findAll() {
        Iterable<MapUbicacionVisibilidad> iterator = dao.findAll();

        return  StreamSupport
                .stream(iterator.spliterator(), false)
                .collect(Collectors.toList());
    }
}
