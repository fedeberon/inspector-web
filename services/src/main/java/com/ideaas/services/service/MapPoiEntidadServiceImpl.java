package com.ideaas.services.service;

import com.ideaas.services.dao.MapPoiEntidadDao;
import com.ideaas.services.domain.MapPoiEntidad;
import com.ideaas.services.domain.MapPoiSector;
import com.ideaas.services.service.interfaces.MapPoiEntidadService;
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
public class MapPoiEntidadServiceImpl implements MapPoiEntidadService {

    private MapPoiEntidadDao dao;

    @Autowired
    public MapPoiEntidadServiceImpl(MapPoiEntidadDao dao) {
        this.dao = dao;
    }

    @Override
    public MapPoiEntidad get(Long id) {
        return dao.findById(id).get();
    }

    @Override
    public MapPoiEntidad save(MapPoiEntidad mapPoiEntidad) {
        return dao.save(mapPoiEntidad);
    }

    @Override
    public List<MapPoiEntidad> findAll(Integer pageSize, Integer pageNo, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<MapPoiEntidad> mapPoisEntidades = dao.findAll(paging);

        return mapPoisEntidades.getContent();
    }

    @Override
    public List<MapPoiEntidad> findAll() {
        Iterable<MapPoiEntidad> iterator = dao.findAll();
        return  StreamSupport
                .stream(iterator.spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public List<MapPoiEntidad> findByMapPoiSector_DescripcionIn(List<String> poiSectorDescripciones) {
        Iterable<MapPoiEntidad> iterator = dao.findByMapPoiSector_DescripcionIn(poiSectorDescripciones);
        return  StreamSupport
                .stream(iterator.spliterator(), false)
                .collect(Collectors.toList());
    }
}
