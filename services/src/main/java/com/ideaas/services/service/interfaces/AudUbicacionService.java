package com.ideaas.services.service.interfaces;

import com.ideaas.services.domain.AudUbicacion;

import java.util.List;

public interface AudUbicacionService {

    AudUbicacion get(Long id);

    AudUbicacion save(AudUbicacion audUbicacion);

    List<AudUbicacion> findAll(Integer pageSize, Integer pageNo, String sortBy);
}
