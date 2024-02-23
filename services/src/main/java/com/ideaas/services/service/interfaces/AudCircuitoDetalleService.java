package com.ideaas.services.service.interfaces;

import com.ideaas.services.domain.AudCircuitoDetalle;

import java.util.List;

public interface AudCircuitoDetalleService {

    AudCircuitoDetalle get(Long id);

    AudCircuitoDetalle save(AudCircuitoDetalle audCircuitoDetalle);

    List<AudCircuitoDetalle> findAll(Integer pageSize, Integer pageNo, String sortBy);
}
