package com.ideaas.services.service.interfaces;

import com.ideaas.services.bean.MyObject;
import com.ideaas.services.domain.AppUbicacionRelevamiento;

import java.util.Map;
import java.util.List;

public interface AppUbicacionRelService {

    AppUbicacionRelevamiento get(Long id);

    AppUbicacionRelevamiento save(AppUbicacionRelevamiento appUbicacionRelevamiento);

    void saveAll(List<AppUbicacionRelevamiento> appUbicacionRelevamientos);

    void delete(Long id);

    void deleteAllByIds(List<Long> ids);

    void deleteByRelevamientoIds(List<Long> ids);

    List<AppUbicacionRelevamiento> findAll();

    List<AppUbicacionRelevamiento> findAll(Integer pageSize, Integer pageNo, String sortBy);

    List<AppUbicacionRelevamiento> findAllByBajaLogica(Boolean bajaLogica);

    void saveLatLong(MyObject object);

    List<AppUbicacionRelevamiento> findAllByIdRelevamiento(Long idRelevamiento);

    List<AppUbicacionRelevamiento> findAllByRelevamientoAndBajaLogica(Long idRelevamiento);
    
    Map<Long, Integer> cantidadUbicacionesByRelevamiento();

    List<MyObject> findAllByIdIn(List<Long> ids);

    List<MyObject> findAllByIdUbicacionRel(List<Long> ids);

    Long findNextById(Long idUbicacionRelevamiento, Long idRelevamiento);

    Long findPreviousById(Long idUbicacionRelevamiento, Long idRelevamiento);

    List<AppUbicacionRelevamiento> searchByDates(String startDate, String endDate);
}
