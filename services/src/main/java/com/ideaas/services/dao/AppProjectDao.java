package com.ideaas.services.dao;

import com.ideaas.services.domain.AppProject;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AppProjectDao extends PagingAndSortingRepository<AppProject, Long> {
}
