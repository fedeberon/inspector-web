package com.ideaas.services.service.interfaces;

import com.ideaas.services.domain.AppRespuesta;

import java.util.List;

public interface AppRespuestaService {

    AppRespuesta get(Long id);

    AppRespuesta save(AppRespuesta appRespuesta);

    void saveRespuestaUpdate(AppRespuesta appRespuesta);

    void saveUpdate(AppRespuesta appRespuesta);

    void saveAll(List<AppRespuesta> AppRespuestas);

    void delete(Long id);

    void deleteAllByIds(List<Long> ids);

    void deleteByRelevamientoIds(List<Long> ids);

    List<AppRespuesta> findAll();

    List<AppRespuesta> findAll(Integer pageSize, Integer pageNo, String sortBy);

    AppRespuesta findByUbicacionAndRelevamiento(Long idUbicacion , Long idRelevamiento);

    List<AppRespuesta> findAllByIdUbicacion(Long idUbicacion);

    List<AppRespuesta> findAllByIdRelevamiento(Long idRelevamiento);
}
