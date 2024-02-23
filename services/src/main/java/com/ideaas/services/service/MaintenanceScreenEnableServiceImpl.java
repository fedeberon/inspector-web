package com.ideaas.services.service;

import com.ideaas.services.dao.MaintenanceScreenEnableDao;
import com.ideaas.services.domain.MaintenanceScreenEnable;
import com.ideaas.services.service.interfaces.MaintenanceScreenEnableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class MaintenanceScreenEnableServiceImpl implements MaintenanceScreenEnableService {

    private MaintenanceScreenEnableDao dao;

    @Autowired
    public MaintenanceScreenEnableServiceImpl(MaintenanceScreenEnableDao dao) {
        this.dao = dao;
    }

    @Override
    public MaintenanceScreenEnable get(Long id) {
        return dao.findById(id).get();
    }

    @Override
    public MaintenanceScreenEnable save(MaintenanceScreenEnable maintenanceScreenEnable) {
        return dao.save(maintenanceScreenEnable);
    }

    @Override
    public List<MaintenanceScreenEnable> findAll() {
        Iterable<MaintenanceScreenEnable> iterator = dao.findAll();

        return  StreamSupport
                .stream(iterator.spliterator(), false)
                .collect(Collectors.toList());
    }
}
