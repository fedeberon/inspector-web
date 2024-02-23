package com.ideaas.services.service;

import com.ideaas.services.dao.MapClienteDao;
import com.ideaas.services.domain.MapCliente;
import com.ideaas.services.domain.MapEmpresa;
import com.ideaas.services.domain.Usuario;
import com.ideaas.services.enums.TipoUsuarioEnum;
import com.ideaas.services.service.interfaces.MapClienteService;
import com.ideaas.services.service.interfaces.MapEmpresaService;
import com.ideaas.services.service.interfaces.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MapClienteServiceImpl implements MapClienteService {

    @Autowired
    private MapClienteDao dao;

    @Autowired
    private UsuarioService usuarioService;

    @Override
    public MapCliente get(Long id) {
        Optional<Optional<Long>> idValidated = this.usuarioService.filterIdBelongToUserLoggedIn(id, this);
        if(idValidated.isPresent()) if(!idValidated.get().isPresent()) return null;
        return this.dao.findById(id).get();
    }

    @Override
    public MapCliente save(MapCliente mapCliente) {
        mapCliente.setMapEmpresas(new ArrayList<>(this.usuarioService.getUsuarioLogeado().getEmpresas()));
        return this.dao.save(mapCliente);
    }

    @Override
    public List<MapCliente> findAll() {
        List<Long> mapCompanyIds = this.usuarioService.getMapCompanyIdOfUserLoggedInIfIsOHHAdminTypeUser().orElse(new ArrayList<>(Arrays.asList(-1l)));
        return this.dao.findAll(mapCompanyIds.stream().map(Long::intValue).collect(Collectors.toList()));
    }

    @Override
    public void delete(Long id) {
        Optional<Optional<Long>> idValidated = this.usuarioService.filterIdBelongToUserLoggedIn(id, this);
        if(idValidated.isPresent()) if(!idValidated.get().isPresent()) return;
        this.dao.delete(get(id));
    }

    //#region validations

    @Override
    public List<Long> filterEntitiesIdsThaBelongToAMapCompanies(List<Long> idsToFilter, List<Long> mapCompanyIds) {
        return this.dao.filterEntitiesIdsThaBelongToAMapCompanies(idsToFilter, mapCompanyIds);
    }

    //#endregion
}