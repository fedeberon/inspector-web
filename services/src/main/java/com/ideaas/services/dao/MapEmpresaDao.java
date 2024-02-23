package com.ideaas.services.dao;

import com.ideaas.services.domain.MapEmpresa;
import com.ideaas.services.domain.Usuario;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MapEmpresaDao extends PagingAndSortingRepository<MapEmpresa, Long> {

    List<MapEmpresa> findByUsuariosContainingOrderByDescripcionAsc(Usuario usuario);

    List<MapEmpresa> findByOrderByDescripcionAsc();

    List<MapEmpresa> findAllByUsuariosContaining(Usuario usuario);

    List<MapEmpresa> findAllByIdIn(List<Long> empresasIDs);
}
