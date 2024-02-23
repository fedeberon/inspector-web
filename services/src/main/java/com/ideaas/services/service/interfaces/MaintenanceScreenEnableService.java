package com.ideaas.services.service.interfaces;

import com.ideaas.services.domain.MaintenanceScreenEnable;

import java.util.List;

public interface MaintenanceScreenEnableService {

    MaintenanceScreenEnable get(Long id);

    MaintenanceScreenEnable save(MaintenanceScreenEnable maintenanceScreenEnable);

    List<MaintenanceScreenEnable> findAll();
}
