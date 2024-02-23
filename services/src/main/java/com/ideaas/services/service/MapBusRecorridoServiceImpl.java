package com.ideaas.services.service;

import com.ideaas.services.dao.MapBusRecorridoDao;
import com.ideaas.services.domain.MapBusRecorrido;
import com.ideaas.services.service.interfaces.MapBusRecorridoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MapBusRecorridoServiceImpl implements MapBusRecorridoService {

    private MapBusRecorridoDao dao;

    @Autowired
    public MapBusRecorridoServiceImpl(MapBusRecorridoDao dao) {
        this.dao = dao;
    }

    @Override
    public MapBusRecorrido get(Long id) {
        return dao.findById(id).get();
    }

    @Override
    public MapBusRecorrido save(MapBusRecorrido mapBusRecorrido) {
        return dao.save(mapBusRecorrido);
    }

    @Override
    public List<MapBusRecorrido> findAll(Integer pageSize, Integer pageNo, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<MapBusRecorrido> mapBusesRecorridos = dao.findAll(paging);

        return mapBusesRecorridos.getContent();
    }

}
