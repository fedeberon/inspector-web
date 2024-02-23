package com.ideaas.services.service;

import com.ideaas.services.dao.AppFormularioDao;
import com.ideaas.services.dao.AppProjectDao;
import com.ideaas.services.dao.AppRelevamientoDao;
import com.ideaas.services.domain.AppProject;
import com.ideaas.services.domain.AppRelevamiento;
import com.ideaas.services.service.interfaces.AppEstadoRelService;
import com.ideaas.services.service.interfaces.AppProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class AppProjectServiceImpl implements AppProjectService {

    private AppProjectDao dao;

    @Autowired
    public AppProjectServiceImpl(AppProjectDao dao) {
        this.dao = dao;
    }

    @Override
    public AppProject get(Long id) {
        return dao.findById(id).get();
    }

    @Override
    public Optional<AppProject> findById(Long id) {
        return dao.findById(id);
    }

    @Override
    public AppProject save(AppProject appProject) {
        return dao.save(appProject);
    }

    @Override
    public void delete(Long id) {
        dao.deleteById(id);
    }

    @Override
    public List<AppProject> findAll() {
        Iterable<AppProject> iterator = dao.findAll();

        return  StreamSupport
                .stream(iterator.spliterator(), false)
                .collect(Collectors.toList());
    }
}
