package com.ideaas.services.service;

import com.ideaas.services.dao.AllowedStatusDao;
import com.ideaas.services.service.interfaces.AllowedStatusService;
import org.springframework.stereotype.Service;

@Service
public class AllowedStatusServiceImpl implements AllowedStatusService {

    private AllowedStatusDao dao;

    public AllowedStatusServiceImpl(AllowedStatusDao dao) {
        this.dao = dao;
    }

    @Override
    public Boolean existsByDescripcion(String descripcion){
        return dao.existsByDescripcion(descripcion);
    }
}
