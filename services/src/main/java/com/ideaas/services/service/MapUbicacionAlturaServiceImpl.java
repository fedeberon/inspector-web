package com.ideaas.services.service;

import com.ideaas.services.dao.MapUbicacionAlturaDao;
import com.ideaas.services.domain.MapUbicacionAltura;
import com.ideaas.services.service.interfaces.MapUbicacionAlturaService;
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
public class MapUbicacionAlturaServiceImpl implements MapUbicacionAlturaService {

    private MapUbicacionAlturaDao dao;

    @Autowired
    public MapUbicacionAlturaServiceImpl(MapUbicacionAlturaDao dao) {
        this.dao = dao;
    }

    @Override
    public MapUbicacionAltura get(Long id) {
        return dao.findById(id).get();
    }

    @Override
    public MapUbicacionAltura save(MapUbicacionAltura mapUbicacionAltura) {
        return dao.save(mapUbicacionAltura);
    }

    @Override
    public List<MapUbicacionAltura> findAll(Integer pageSize, Integer pageNo, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<MapUbicacionAltura> mapUbicacionesAlturas = dao.findAll(paging);

        return mapUbicacionesAlturas.getContent();
    }

    @Override
    public List<MapUbicacionAltura> findAll() {
        Iterable<MapUbicacionAltura> iterator = dao.findAll();

        return  StreamSupport
                .stream(iterator.spliterator(), false)
                .collect(Collectors.toList());
    }
}
