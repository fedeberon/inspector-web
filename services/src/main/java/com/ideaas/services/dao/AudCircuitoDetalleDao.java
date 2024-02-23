package com.ideaas.services.dao;

import com.ideaas.services.domain.AudCircuitoDetalle;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AudCircuitoDetalleDao extends PagingAndSortingRepository<AudCircuitoDetalle, Long> {
}
