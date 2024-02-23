package com.ideaas.services.service;

import com.ideaas.services.dao.AudUbicacionDao;
import com.ideaas.services.domain.AudUbicacion;
import com.ideaas.services.service.interfaces.AudUbicacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AudUbicacionServiceImpl implements AudUbicacionService {

    private AudUbicacionDao dao;

    @Autowired
    public AudUbicacionServiceImpl(AudUbicacionDao dao) {
        this.dao = dao;
    }

    @Override
    public AudUbicacion get(Long id) {
        return dao.findById(id).get();
    }

    @Override
    public AudUbicacion save(AudUbicacion audUbicacion) {
        return dao.save(audUbicacion);
    }

    @Override
    public List<AudUbicacion> findAll(Integer pageSize, Integer pageNo, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<AudUbicacion> audUbicaciones = dao.findAll(paging);

        return audUbicaciones.getContent();
    }

}
