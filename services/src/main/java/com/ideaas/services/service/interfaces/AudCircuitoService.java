package com.ideaas.services.service.interfaces;

import com.ideaas.services.domain.AudCircuito;

import java.util.List;

public interface AudCircuitoService {

    List<AudCircuito> findAll(Integer pageSize, Integer pageNo, String sortBy);

    AudCircuito get(Long id);

    AudCircuito save(AudCircuito audCircuito);
}
