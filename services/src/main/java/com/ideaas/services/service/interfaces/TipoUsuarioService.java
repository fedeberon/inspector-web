package com.ideaas.services.service.interfaces;

import com.ideaas.services.domain.TipoUsuario;

import java.util.List;

public interface TipoUsuarioService {

    TipoUsuario get(Long id);

    TipoUsuario save(TipoUsuario tipoUsuario);

    List<TipoUsuario> findAll();

    List<TipoUsuario> findAll(Integer pageSize, Integer pageNo, String sortBy);
}
