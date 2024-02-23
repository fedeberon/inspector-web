package com.ideaas.services.service;

import com.ideaas.services.dao.AudLocalidadDao;
import com.ideaas.services.domain.AudLocalidad;
import com.ideaas.services.service.interfaces.AudLocalidadService;
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
public class AudLocalidadServiceImpl implements AudLocalidadService {

    private AudLocalidadDao dao;

    @Autowired
    public AudLocalidadServiceImpl(AudLocalidadDao dao) {
        this.dao = dao;
    }

    @Override
    public AudLocalidad get(Long id) {
        return dao.findById(id).get();
    }

    @Override
    public AudLocalidad save(AudLocalidad audLocalidad) {
        return dao.save(audLocalidad);
    }

    @Override
    public List<AudLocalidad> findAll(Integer pageSize, Integer pageNo, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<AudLocalidad> audLocalidades = dao.findAll(paging);

        return audLocalidades.getContent();
    }

    @Override
    public List<AudLocalidad> findAll() {
        Iterable<AudLocalidad> iterator = dao.findAll();

        return  StreamSupport
                .stream(iterator.spliterator(), false)
                .collect(Collectors.toList());
    }
}
