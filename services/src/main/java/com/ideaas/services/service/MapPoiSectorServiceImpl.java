package com.ideaas.services.service;

import com.ideaas.services.dao.MapPoiSectorDao;
import com.ideaas.services.domain.MapPoiSector;
import com.ideaas.services.service.interfaces.MapPoiSectorService;
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
public class MapPoiSectorServiceImpl implements MapPoiSectorService {

    private MapPoiSectorDao dao;

    @Autowired
    public MapPoiSectorServiceImpl(MapPoiSectorDao dao) {
        this.dao = dao;
    }

    @Override
    public MapPoiSector get(Long id) {
        return dao.findById(id).get();
    }

    @Override
    public MapPoiSector save(MapPoiSector mapPoiSector) {
        return dao.save(mapPoiSector);
    }

    @Override
    public List<MapPoiSector> findAll(Integer pageSize, Integer pageNo, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<MapPoiSector> mapPoiSectores = dao.findAll(paging);

        return mapPoiSectores.getContent();
    }

    @Override
    public List<MapPoiSector> findAll() {
        Iterable<MapPoiSector> iterator = dao.findAll();
        return  StreamSupport
                .stream(iterator.spliterator(), false)
                .collect(Collectors.toList());
    }

}
