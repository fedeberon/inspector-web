package com.ideaas.services.service.interfaces;

import com.ideaas.services.domain.AppProject;

import java.util.List;
import java.util.Optional;

public interface AppProjectService {

    AppProject get(Long id);

    Optional<AppProject> findById(Long id);

    AppProject save(AppProject appProject);

    void delete(Long id);

    List<AppProject> findAll();
}
