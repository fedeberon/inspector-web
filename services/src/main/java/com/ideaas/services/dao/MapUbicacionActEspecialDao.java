package com.ideaas.services.dao;

import com.ideaas.services.domain.MapUbicacionActEspecial;
import com.ideaas.services.domain.MapUbicacionActEspecialPK;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MapUbicacionActEspecialDao extends PagingAndSortingRepository<MapUbicacionActEspecial, MapUbicacionActEspecialPK> {
}
