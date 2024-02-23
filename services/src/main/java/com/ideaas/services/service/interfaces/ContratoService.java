package com.ideaas.services.service.interfaces;

import com.ideaas.services.domain.Contrato;

import java.util.List;

public interface ContratoService {

    Contrato get(Long id);

    Contrato save(Contrato tipoUsuario);

    List<Contrato> findAll();

    List<Contrato> findAll(Integer pageSize, Integer pageNo, String sortBy);

    List<Contrato> findByEstadoNotAndTipoContratoSorted(String estado , String tipoContrato);
}
