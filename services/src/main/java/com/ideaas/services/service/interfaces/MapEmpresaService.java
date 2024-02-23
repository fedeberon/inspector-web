package com.ideaas.services.service.interfaces;

import com.ideaas.services.domain.MapEmpresa;
import com.ideaas.services.domain.Usuario;

import java.util.List;

public interface MapEmpresaService {

    MapEmpresa get(Long id);

    MapEmpresa save(MapEmpresa mapEmpresa);

    void saveAll(List<MapEmpresa> mapEmpresas);

    List<MapEmpresa> findAll();

    public List<MapEmpresa> findAllByUsuariosContaining(Usuario usuario);

    List<MapEmpresa> findAll(Integer pageSize, Integer pageNo, String sortBy);

    List<MapEmpresa> findByOrderByDescripcionAsc();
}
