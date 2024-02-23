package com.ideaas.services.dao;

import com.ideaas.services.domain.AudLocalidad;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AudLocalidadDao extends PagingAndSortingRepository<AudLocalidad, Long> {
}
