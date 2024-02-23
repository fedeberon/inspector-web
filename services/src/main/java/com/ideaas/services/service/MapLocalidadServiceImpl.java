package com.ideaas.services.service;

import com.ideaas.services.dao.MapLocalidadDao;
import com.ideaas.services.domain.MapLocalidad;
import com.ideaas.services.service.interfaces.MapLocalidadService;
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
public class MapLocalidadServiceImpl implements MapLocalidadService {

    private MapLocalidadDao dao;

    private UsuarioService usuarioService;

    @Autowired
    public MapLocalidadServiceImpl(MapLocalidadDao dao, UsuarioService usuarioService) {
        this.dao = dao;
        this.usuarioService = usuarioService;
    }

    @Override
    public MapLocalidad get(Long id) {
        return dao.findById(id).get();
    }

    @Override
    public MapLocalidad save(MapLocalidad mapLocalidad) {
        return dao.save(mapLocalidad);
    }

    @Override
    public List<MapLocalidad> findAll(Integer pageSize, Integer pageNo, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<MapLocalidad> mapLocalidades = dao.findAll(paging);

        return mapLocalidades.getContent();
    }

    @Override
    public List<MapLocalidad> findAll() {
        List<Long> mapCompanyIds = this.usuarioService.getMapCompanyIdOfUserLoggedInIfIsOHHAdminTypeUser().orElse(new ArrayList<>(Arrays.asList(-1l)));
        return this.dao.findAll(mapCompanyIds.stream().map(Long::intValue).collect(Collectors.toList()));
    }

    @Override
    public List<MapLocalidad> findByOrderByDescripcionAsc(){
        List<Long> mapCompanyIds = this.usuarioService.getMapCompanyIdOfUserLoggedInIfIsOHHAdminTypeUser().orElse(new ArrayList<>(Arrays.asList(-1l)));
        return this.dao.findByOrderByDescripcionAsc(mapCompanyIds.stream().map(Long::intValue).collect(Collectors.toList()));
    }
}
