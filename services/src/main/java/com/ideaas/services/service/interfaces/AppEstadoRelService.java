package com.ideaas.services.service.interfaces;

import com.ideaas.services.domain.AppEstadoRelevamiento;

import java.util.List;

public interface AppEstadoRelService {

    AppEstadoRelevamiento get(Long id);

    AppEstadoRelevamiento save(AppEstadoRelevamiento appEstadoRelevamiento);

    List<AppEstadoRelevamiento> findAll();

    List<AppEstadoRelevamiento> findAll(Integer pageSize, Integer pageNo, String sortBy);
}
