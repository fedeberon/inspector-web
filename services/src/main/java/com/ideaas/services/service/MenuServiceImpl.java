package com.ideaas.services.service;

import com.ideaas.services.dao.MenuDao;
import com.ideaas.services.domain.Menu;
import com.ideaas.services.service.interfaces.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class MenuServiceImpl implements MenuService {

    private MenuDao dao;

    @Autowired
    public MenuServiceImpl(MenuDao dao) {
        this.dao = dao;
    }

    @Override
    public Menu get(Long id) {
        return dao.findById(id).get();
    }

    @Override
    public Menu save(Menu menu) {
        return dao.save(menu);
    }

    @Override
    public List<Menu> findAll(Integer pageSize, Integer pageNo, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<Menu> menus = dao.findAll(paging);

        return menus.getContent();
    }

    @Override
    public List<Menu> findAll() {
        Iterable<Menu> iterator = dao.findAll();

        return  StreamSupport
                .stream(iterator.spliterator(), false)
                .collect(Collectors.toList());
    }
}
