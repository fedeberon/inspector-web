package com.ideaas.services.service;

import com.ideaas.services.dao.MapBusElementoDao;
import com.ideaas.services.domain.MapBusElemento;
import com.ideaas.services.service.interfaces.MapBusElementoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MapBusElementoServiceImpl implements MapBusElementoService {

    private MapBusElementoDao dao;

    @Autowired
    public MapBusElementoServiceImpl(MapBusElementoDao dao) {
        this.dao = dao;
    }

    @Override
    public MapBusElemento get(Long id) {
        return dao.findById(id).get();
    }

    @Override
    public MapBusElemento save(MapBusElemento mapBusElemento) {
        return dao.save(mapBusElemento);
    }

    @Override
    public List<MapBusElemento> findAll(Integer pageSize, Integer pageNo, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<MapBusElemento> mapBusElementos = dao.findAll(paging);

        return mapBusElementos.getContent();
    }
}
