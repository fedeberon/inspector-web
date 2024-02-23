package com.ideaas.services.service;

import com.ideaas.services.dao.FilterDao;
import com.ideaas.services.dao.MapPoiDao;
import com.ideaas.services.domain.MapPoi;
import com.ideaas.services.request.MapPoiRequest;
import com.ideaas.services.service.interfaces.MapPoiService;
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
public class MapPoiServiceImpl implements MapPoiService {

    private MapPoiDao dao;

    private FilterDao filterDao;

    @Autowired
    public MapPoiServiceImpl(MapPoiDao dao, FilterDao filterDao) {
        this.dao = dao;
        this.filterDao = filterDao;
    }

    @Override
    public MapPoi get(Long id) {
        return dao.findById(id).get();
    }

    @Override
    public MapPoi save(MapPoi mapPoi) {
        return dao.save(mapPoi);
    }

    @Override
    public List<MapPoi> findAll(Integer pageSize, Integer pageNo, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<MapPoi> mapPois = dao.findAll(paging);

        return mapPois.getContent();
    }

    @Override
    public List<MapPoi> findAll() {
        Iterable<MapPoi> iterator = dao.findAll();

        return  StreamSupport
                .stream(iterator.spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public List<MapPoi> findAll(MapPoiRequest mapPoiRequest) {
        return filterDao.find(mapPoiRequest);
    }
}
