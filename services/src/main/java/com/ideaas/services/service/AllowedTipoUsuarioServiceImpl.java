package com.ideaas.services.service;

import com.ideaas.services.dao.AllowedTipoUsuarioDao;
import com.ideaas.services.service.interfaces.AllowedTipoUsuarioService;
import org.springframework.stereotype.Service;

@Service
public class AllowedTipoUsuarioServiceImpl implements AllowedTipoUsuarioService {

    private AllowedTipoUsuarioDao dao;

    public AllowedTipoUsuarioServiceImpl(AllowedTipoUsuarioDao dao) {
        this.dao = dao;
    }

    @Override
    public Boolean existsByTipoUsuario_Id(Long id){
        return dao.existsByTipoUsuario_Id(id);
    }
}
