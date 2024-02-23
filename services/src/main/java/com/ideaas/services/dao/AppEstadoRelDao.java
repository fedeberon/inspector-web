package com.ideaas.services.dao;

import com.ideaas.services.domain.AppEstadoRelevamiento;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppEstadoRelDao extends PagingAndSortingRepository<AppEstadoRelevamiento, Long> {
}
