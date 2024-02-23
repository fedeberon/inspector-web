package com.ideaas.services.service.interfaces;

import com.ideaas.services.domain.AudEmpresa;

import java.util.List;

public interface AudEmpresaService {

    AudEmpresa get(Long id);

    AudEmpresa save(AudEmpresa audEmpresa);

    List<AudEmpresa> findAll(Integer pageSize, Integer pageNo, String sortBy);

    List<AudEmpresa> findAll();

}
