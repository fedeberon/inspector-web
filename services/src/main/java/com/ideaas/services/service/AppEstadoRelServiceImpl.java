package com.ideaas.services.service;

import com.ideaas.services.dao.AppEstadoRelDao;
import com.ideaas.services.domain.AppEstadoRelevamiento;
import com.ideaas.services.service.interfaces.AppEstadoRelService;
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
public class AppEstadoRelServiceImpl implements AppEstadoRelService {

    private AppEstadoRelDao dao;

    @Autowired
    public AppEstadoRelServiceImpl(AppEstadoRelDao dao) {
        this.dao = dao;
    }

    @Override
    public AppEstadoRelevamiento get(Long id) {
        return dao.findById(id).get();
    }

    @Override
    public AppEstadoRelevamiento save(AppEstadoRelevamiento appEstadoRelevamiento) {
        return dao.save(appEstadoRelevamiento);
    }

    @Override
    public List<AppEstadoRelevamiento> findAll() {
        Iterable<AppEstadoRelevamiento> iterator = dao.findAll();

        return  StreamSupport
                .stream(iterator.spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public List<AppEstadoRelevamiento> findAll(Integer pageSize, Integer pageNo, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<AppEstadoRelevamiento> appEstadosRelevamiento = dao.findAll(paging);

        return appEstadosRelevamiento.getContent();
    }

}
