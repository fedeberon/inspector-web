package com.ideaas.services.dao;

import com.ideaas.services.domain.AllowedTipoUsuario;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AllowedTipoUsuarioDao extends PagingAndSortingRepository<AllowedTipoUsuario, Long> {
    Boolean existsByTipoUsuario_Id(Long id);
}
