package com.ideaas.services.dao;

import com.ideaas.services.domain.UsuarioMenu;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioMenuDao extends PagingAndSortingRepository<UsuarioMenu, Long> {

    List<UsuarioMenu> findByUsuarioMenuId_IdUsuario(Long idUsuario);
}
