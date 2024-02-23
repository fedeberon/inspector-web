package com.ideaas.services.dao;

import com.ideaas.services.domain.Contrato;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContratoDao extends PagingAndSortingRepository<Contrato, Long> {

    List<Contrato> findByEstadoNotAndTipoContratoOrderByDescripcionAsc(String estado , String tipoContrato);
}
