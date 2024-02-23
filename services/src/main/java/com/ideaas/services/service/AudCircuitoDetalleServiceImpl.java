package com.ideaas.services.service;

import com.ideaas.services.dao.AudCircuitoDetalleDao;
import com.ideaas.services.domain.AudCircuitoDetalle;
import com.ideaas.services.service.interfaces.AudCircuitoDetalleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AudCircuitoDetalleServiceImpl implements AudCircuitoDetalleService {

    private AudCircuitoDetalleDao dao;

    @Autowired
    public AudCircuitoDetalleServiceImpl(AudCircuitoDetalleDao dao) {
        this.dao = dao;
    }

    @Override
    public AudCircuitoDetalle get(Long id) {
        return dao.findById(id).get();
    }

    @Override
    public AudCircuitoDetalle save(AudCircuitoDetalle audCircuitoDetalle) {
        return dao.save(audCircuitoDetalle);
    }

    @Override
    public List<AudCircuitoDetalle> findAll(Integer pageSize, Integer pageNo, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<AudCircuitoDetalle> audCircuitoDetalles = dao.findAll(paging);

        return audCircuitoDetalles.getContent();
    }
}
