package com.ideaas.services.service;

import com.ideaas.services.dao.MapHeatmapDao;
import com.ideaas.services.domain.MapHeatmap;
import com.ideaas.services.service.interfaces.MapHeatmapService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class MapHeatmapServiceImpl implements MapHeatmapService {

    private MapHeatmapDao dao;

    public MapHeatmapServiceImpl(MapHeatmapDao dao) {
        this.dao = dao;
    }

    @Override
    public MapHeatmap get(Long id) {
        return dao.findById(id).get();
    }

    @Override
    public MapHeatmap save(MapHeatmap mapHeatmap) {
        return dao.save(mapHeatmap);
    }

    @Override
    public List<MapHeatmap> findAll(Integer pageSize, Integer pageNo, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<MapHeatmap> mapHeatmaps = dao.findAll(paging);

        return mapHeatmaps.getContent();
    }

    @Override
    public List<MapHeatmap> findAll() {
        Iterable<MapHeatmap> iterator = dao.findAll();

        return  StreamSupport
                .stream(iterator.spliterator(), false)
                .collect(Collectors.toList());
    }
}
