package com.ideaas.services.service.interfaces;

import com.ideaas.services.domain.MapCliente;

import java.util.List;

public interface MapClienteService extends MapCompanyFilterIdsService {

    MapCliente get(Long id);

    MapCliente save(MapCliente parametro);

    List<MapCliente> findAll();

    void delete(Long id);
}
