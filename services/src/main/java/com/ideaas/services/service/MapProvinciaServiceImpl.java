package com.ideaas.services.service;

import com.ideaas.services.dao.MapProvinciaDao;
import com.ideaas.services.domain.MapProvincia;
import com.ideaas.services.service.interfaces.MapProvinciaService;
import com.ideaas.services.service.interfaces.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class MapProvinciaServiceImpl implements MapProvinciaService {

    private MapProvinciaDao dao;

    private UsuarioService usuarioService;

    @Autowired
    public MapProvinciaServiceImpl(MapProvinciaDao dao, UsuarioService usuarioService) {
        this.dao = dao;
        this.usuarioService = usuarioService;
    }

    @Override
    public MapProvincia get(Long id) {
        return dao.findById(id).get();
    }

    @Override
    public MapProvincia save(MapProvincia mapProvincia) {
        return dao.save(mapProvincia);
    }

    @Override
    public List<MapProvincia> findAll(Integer pageSize, Integer pageNo, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<MapProvincia> mapProvincias = dao.findAll(paging);

        return mapProvincias.getContent();
    }

    @Override
    public List<MapProvincia> findAll() {
        List<Long> mapCompanyIds = this.usuarioService.getMapCompanyIdOfUserLoggedInIfIsOHHAdminTypeUser().orElse(new ArrayList<>(Arrays.asList(-1l)));
        return this.dao.findAll(mapCompanyIds.stream().map(Long::intValue).collect(Collectors.toList()));
    }

    @Override
    public List<MapProvincia> findByOrderByDescripcionAsc(){
        List<Long> mapCompanyIds = this.usuarioService.getMapCompanyIdOfUserLoggedInIfIsOHHAdminTypeUser().orElse(new ArrayList<>(Arrays.asList(-1l)));
        return this.dao.findByOrderByDescripcionAsc(mapCompanyIds.stream().map(Long::intValue).collect(Collectors.toList()));
    }
}
