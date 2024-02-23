package com.ideaas.services.dao;

import com.ideaas.services.domain.AppRelevamiento;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public interface AppRelevamientoDao extends PagingAndSortingRepository<AppRelevamiento, Long> {

    List<AppRelevamiento> findAllByUsuario_Id(Long idUsuario);

    List<AppRelevamiento> findAllByConUbicacionesPreAsignadasIs(Boolean bool);

    List<AppRelevamiento> findAllByUsuario_IdAndEstado_IdIn(Long idUsuario , ArrayList<Long> ids);

    List<AppRelevamiento> findAllByEstado_Id(Long id);

    List<AppRelevamiento> findAllByBajaLogica(Boolean bajaLogica);

    List<AppRelevamiento> findAllByProject_Id(Long id);

    @Transactional
    @Modifying
    @Query("DELETE FROM AppRelevamiento rel WHERE rel.id IN :ids")
    void deleteByIds(List<Long> ids);

    List<AppRelevamiento> findAllByFechaAsignacionIsBetween(String startDate, String endDate);
}
