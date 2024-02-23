package com.ideaas.services.service;

import com.ideaas.services.dao.FilterDao;
import com.ideaas.services.dao.MapUbicacionActEspecialDao;
import com.ideaas.services.domain.MapUbicacionActEspecial;
import com.ideaas.services.domain.MapUbicacionActEspecialPK;
import com.ideaas.services.request.MapUbiActEspecialRequest;
import com.ideaas.services.service.interfaces.MapUbicacionActEspecialService;
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
public class MapUbicacionActEspecialServiceImpl implements MapUbicacionActEspecialService {

    private MapUbicacionActEspecialDao dao;

    private FilterDao filterDao;

    @Autowired
    public MapUbicacionActEspecialServiceImpl(MapUbicacionActEspecialDao dao, FilterDao filterDao) {
        this.dao = dao;
        this.filterDao = filterDao;
    }

    @Override
    public MapUbicacionActEspecial get(MapUbicacionActEspecialPK id) {
        return dao.findById(id).get();
    }

    @Override
    public MapUbicacionActEspecial save(MapUbicacionActEspecial mapUbicacionActEspecial) {
        return dao.save(mapUbicacionActEspecial);
    }

    @Override
    public List<MapUbicacionActEspecial> findAll(Integer pageSize, Integer pageNo, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());
        Page<MapUbicacionActEspecial> mapUbicacionActEspeciales = dao.findAll(paging);

        return mapUbicacionActEspeciales.getContent();
    }

    @Override
    public List<MapUbicacionActEspecial> findAll() {
        Iterable<MapUbicacionActEspecial> iterator = dao.findAll();

        return  StreamSupport
                .stream(iterator.spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public List<MapUbicacionActEspecial> findAll(MapUbiActEspecialRequest mapUbiActEspecialRequest) {
        return filterDao.find(mapUbiActEspecialRequest);
    }
}
