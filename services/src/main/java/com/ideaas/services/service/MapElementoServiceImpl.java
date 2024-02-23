package com.ideaas.services.service;

import com.ideaas.services.dao.MapElementoDao;
import com.ideaas.services.domain.MapElemento;
import com.ideaas.services.service.interfaces.MapElementoService;
import com.ideaas.services.service.interfaces.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class MapElementoServiceImpl implements MapElementoService {

    private MapElementoDao dao;

    private UsuarioService usuarioService;

    @Autowired
    public MapElementoServiceImpl(MapElementoDao dao, UsuarioService usuarioService) {
        this.dao = dao;
        this.usuarioService = usuarioService;
    }

    @Override
    public MapElemento get(Long id) {
        return dao.findById(id).get();
    }

    @Override
    public MapElemento save(MapElemento mapElemento) {
        return dao.save(mapElemento);
    }

    @Override
    public List<MapElemento> findAll(Integer pageSize, Integer pageNo, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<MapElemento> mapElemento = dao.findAll(paging);

        return mapElemento.getContent();
    }

    @Override
    public List<MapElemento> findAll() throws EntityNotFoundException {
        List<Long> mapCompanyIds = this.usuarioService.getMapCompanyIdOfUserLoggedInIfIsOHHAdminTypeUser().orElse(new ArrayList<>(Arrays.asList(-1l)));
        return  this.dao.findAll(mapCompanyIds.stream().map(Long::intValue).collect(Collectors.toList()));
    }

    @Override
    public List<MapElemento> findByOrderByDescripcionAsc(){
        List<Long> mapCompanyIds = this.usuarioService.getMapCompanyIdOfUserLoggedInIfIsOHHAdminTypeUser().orElse(new ArrayList<>(Arrays.asList(-1l)));
        return  this.dao.findByOrderByDescripcionAsc(mapCompanyIds.stream().map(Long::intValue).collect(Collectors.toList()));
    }

    @Override
    public List<MapElemento> findByMapEmpresaAndNoInitializedStock(Long mapCompanyId) {
        return this.dao.findByMapEmpresaAndNoInitializedStock(mapCompanyId);
    }
}
