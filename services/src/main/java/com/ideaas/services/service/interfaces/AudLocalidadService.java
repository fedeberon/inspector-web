package com.ideaas.services.service.interfaces;

import com.ideaas.services.domain.AudLocalidad;

import java.util.List;

public interface AudLocalidadService {

    AudLocalidad get(Long id);

    AudLocalidad save(AudLocalidad audLocalidad);

    List<AudLocalidad> findAll(Integer pageSize, Integer pageNo, String sortBy);

    List<AudLocalidad> findAll();
}
