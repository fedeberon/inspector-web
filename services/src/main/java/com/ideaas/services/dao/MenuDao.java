package com.ideaas.services.dao;

import com.ideaas.services.domain.Menu;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuDao extends PagingAndSortingRepository<Menu, Long> {
}
