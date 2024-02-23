package com.ideaas.services.dao;

import com.ideaas.services.domain.AudEmpresa;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AudEmpresaDao extends PagingAndSortingRepository<AudEmpresa, Long> {
}
