package com.ideaas.services.dao;

import com.ideaas.services.domain.AppFormulario;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppFormularioDao extends PagingAndSortingRepository<AppFormulario, Long> {

    List<AppFormulario> findAllByBajaLogicaFormulario(Boolean bajaLogica);
}
