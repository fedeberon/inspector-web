package com.ideaas.services.service;

import com.ideaas.services.dao.MapFormatoDao;
import com.ideaas.services.domain.MapFormato;
import com.ideaas.services.service.interfaces.MapFormatoService;
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
public class MapFormatoServiceImpl implements MapFormatoService {

    private MapFormatoDao dao;

    private UsuarioService usuarioService;

    @Autowired
    public MapFormatoServiceImpl(MapFormatoDao dao, UsuarioService usuarioService) {
        this.dao = dao;
        this.usuarioService = usuarioService;
    }

    @Override
    public MapFormato get(Long id) {
        return dao.findById(id).get();
    }

    @Override
    public MapFormato save(MapFormato mapFormato) {
        return dao.save(mapFormato);
    }

    @Override
    public List<MapFormato> findAll(Integer pageSize, Integer pageNo, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<MapFormato> mapFormato = dao.findAll(paging);

        return mapFormato.getContent();
    }

    @Override
    public List<MapFormato> findAll() {
        List<Long> mapCompanyIds = this.usuarioService.getMapCompanyIdOfUserLoggedInIfIsOHHAdminTypeUser().orElse(new ArrayList<>(Arrays.asList(-1l)));
        return this.dao.findAll(mapCompanyIds.stream().map(Long::intValue).collect(Collectors.toList()));
    }

    @Override
    public List<MapFormato> findByOrderByDescripcionAsc(){
        List<Long> mapCompanyIds = this.usuarioService.getMapCompanyIdOfUserLoggedInIfIsOHHAdminTypeUser().orElse(new ArrayList<>(Arrays.asList(-1l)));
        return this.dao.findByOrderByDescripcionAsc(mapCompanyIds.stream().map(Long::intValue).collect(Collectors.toList()));
    }
}
