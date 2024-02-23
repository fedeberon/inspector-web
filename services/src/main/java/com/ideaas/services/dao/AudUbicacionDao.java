package com.ideaas.services.dao;

import com.ideaas.services.domain.AudUbicacion;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AudUbicacionDao extends PagingAndSortingRepository<AudUbicacion, Long> {
}
