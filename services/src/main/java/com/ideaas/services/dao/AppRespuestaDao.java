package com.ideaas.services.dao;

import com.ideaas.services.domain.AppRespuesta;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface AppRespuestaDao extends PagingAndSortingRepository<AppRespuesta, Long> {

    AppRespuesta findByAppUbicacionRelevamientoIdAndAppRelevamientoId(Long idUbicacion , Long idRelevamiento);

    List<AppRespuesta> findAllByAppUbicacionRelevamiento_Id(Long idUbicacion);

    List<AppRespuesta> findAllByAppRelevamiento_Id(Long idRelevamiento);

    @Transactional
    @Modifying
    @Query("DELETE FROM AppRespuesta ar WHERE ar.id IN :ids")
    void deleteByIds(@Param("ids")List<Long> idsRespuestas);

    @Transactional
    @Modifying
    @Query("DELETE FROM AppRespuesta ar WHERE ar.appRelevamiento.id IN :ids")
    void deleteByRelevamientoIds(@Param("ids")List<Long> idsRelevamiento);
}
