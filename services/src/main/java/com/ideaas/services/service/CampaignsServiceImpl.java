package com.ideaas.services.service;

import com.ideaas.services.dao.MapCampaignDao;
import com.ideaas.services.domain.*;
import com.ideaas.services.bean.MapCampaignDTO;
import com.ideaas.services.service.interfaces.MapCampaignsService;
import com.ideaas.services.service.interfaces.MapClienteService;
import com.ideaas.services.service.interfaces.MapReservationService;
import com.ideaas.services.service.interfaces.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CampaignsServiceImpl implements MapCampaignsService {

    //#region properties and constructor
    private MapCampaignDao dao;

    private MapClienteService mapClienteService;

    private MapReservationService mapReservationService;

    private UsuarioService usuarioService;

    @Autowired
    public CampaignsServiceImpl(MapCampaignDao dao, MapClienteService mapClienteService, MapReservationService mapReservationService, UsuarioService usuarioService) {
        this.dao = dao;
        this.mapClienteService = mapClienteService;
        this.mapReservationService = mapReservationService;
        this.usuarioService = usuarioService;
    }
    //#endregion

    //#region commons methods

    @Override
    public MapCampaign get(Long id) {
        return this.dao.findById(id).get();
    }

    @Override
    public MapCampaign save(MapCampaign mapCampaing) {
        Optional<Optional<Long>> idValidated = this.usuarioService.filterIdBelongToUserLoggedIn(mapCampaing.getMapClient().getId(), this.mapClienteService);
        if(idValidated.isPresent()) if(!idValidated.get().isPresent()) return null;
        return this.dao.save(mapCampaing);
    }

    @Override
    public List<MapCampaign> findAll() {
        return this.dao.findAll();
    }

    @Override
    public List<MapCampaignDTO> findAllMapCampaignDTO() {
        List<Long> mapCompanyIds = this.usuarioService.getMapCompanyIdOfUserLoggedInIfIsOHHAdminTypeUser().orElse(new ArrayList<>(Arrays.asList(-1l)));
        return this.dao.findAllCampaigns(mapCompanyIds.stream().map(Long::intValue).collect(Collectors.toList()));
    }
    
    @Override
    public MapCampaignDTO getCampaignDTOByCampaignId(Long id) {
        
        Optional<Optional<Long>> idValidated = this.usuarioService.filterIdBelongToUserLoggedIn(id, this);

        if(idValidated.isPresent()) if(!idValidated.get().isPresent()) return null;

        return this.dao.findCampaignById(id);
    }

    @Override
    public void delete(MapCampaign mapCampaing) {
        Optional<Optional<Long>> idValidated = this.usuarioService.filterIdBelongToUserLoggedIn(mapCampaing.getId(), this);
        if(idValidated.isPresent()) if(!idValidated.get().isPresent()) return;
        this.mapReservationService.deleteReservationsByCampaignId(mapCampaing.getId());
        this.dao.delete(mapCampaing);
    }

    //#endregion

    @Override
    public void deleteById(Long idCampaign) {
        Optional<Optional<Long>> idValidated = this.usuarioService.filterIdBelongToUserLoggedIn(idCampaign, this);
        if(idValidated.isPresent()) if(!idValidated.get().isPresent()) return;
        this.dao.deleteById(idCampaign);
    }

    @Override
    public MapCampaign clone(MapCampaign newMapCampaing) {
        Optional<Optional<Long>> idValidated = this.usuarioService.filterIdBelongToUserLoggedIn(newMapCampaing.getId(), this);
        if(idValidated.isPresent()) if(!idValidated.get().isPresent()) return null;


        return new MapCampaign();
    }

    //#region validations

    @Override
    public List<Long> filterEntitiesIdsThaBelongToAMapCompanies(List<Long> idsToFilter, List<Long> mapCompanyIds) {
        return this.dao.filterEntitiesIdsThaBelongToAMapCompanies(idsToFilter, mapCompanyIds);
    }

    //#endregion
}
