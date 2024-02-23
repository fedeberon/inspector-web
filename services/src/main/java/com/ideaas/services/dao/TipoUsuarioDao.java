package com.ideaas.services.dao;

import com.ideaas.services.domain.TipoUsuario;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoUsuarioDao extends PagingAndSortingRepository<TipoUsuario, Long> {
}
