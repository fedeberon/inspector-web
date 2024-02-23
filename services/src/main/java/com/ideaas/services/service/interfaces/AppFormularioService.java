package com.ideaas.services.service.interfaces;

import com.ideaas.services.domain.AppFormulario;

import java.util.List;

public interface AppFormularioService {

    AppFormulario get(Long id);

    AppFormulario save(AppFormulario appFormulario);

    List<AppFormulario> findAll();

    List<AppFormulario> findAll(Integer pageSize, Integer pageNo, String sortBy);

    List<AppFormulario> findAllByBajaLogica(Boolean bajaLogica);

}
