package com.ideaas.services.service.interfaces;

import com.ideaas.services.domain.AppRelevamiento;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface AppRelevamientoService {

    AppRelevamiento get(Long id);

    Optional<AppRelevamiento> findById(Long id);

    AppRelevamiento save(AppRelevamiento appRelevamiento);

    void delete(Long id);

    void deleteByIds(List<Long> ids);

    List<AppRelevamiento> findAll();

    List<AppRelevamiento> findAll(Integer pageSize, Integer pageNo, String sortBy);

    List<AppRelevamiento> findAllByUsuario(Long idUsuario);

    List<AppRelevamiento> findAllByUsuarioAndEstadoToShow(Long idUsuario , ArrayList<Long> ids);

    List<AppRelevamiento> findAllByConUbicacionIs(Boolean bool);

    List<AppRelevamiento> findAllByEstado(Long id);

    List<AppRelevamiento> findAllByBajaLogica(Boolean bajaLogica);

    List<AppRelevamiento> findAllByProject(Long id);

    void setStateFinished(Long idRelevamiento);

    List<AppRelevamiento> findAllByDates(String startDate, String endDate);
}
