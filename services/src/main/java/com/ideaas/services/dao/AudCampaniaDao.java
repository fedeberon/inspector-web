package com.ideaas.services.dao;

import com.ideaas.services.domain.AudCampania;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AudCampaniaDao extends PagingAndSortingRepository<AudCampania, Long> {
}
