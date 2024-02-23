package com.ideaas.services.service.interfaces;

import com.ideaas.services.domain.Menu;

import java.util.List;

public interface MenuService {

    Menu get(Long id);

    Menu save(Menu menu);

    List<Menu> findAll(Integer pageSize, Integer pageNo, String sortBy);

    List<Menu> findAll();
}
