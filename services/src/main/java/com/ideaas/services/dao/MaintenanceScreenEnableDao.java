package com.ideaas.services.dao;

import com.ideaas.services.domain.MaintenanceScreenEnable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaintenanceScreenEnableDao extends PagingAndSortingRepository<MaintenanceScreenEnable, Long> {
}
