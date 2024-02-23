package com.ideaas.services.dao;

import com.ideaas.services.bean.MyObject;
import com.ideaas.services.domain.AppUbicacion;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppUbicacionDao extends PagingAndSortingRepository<AppUbicacion, Long> {

    AppUbicacion findByOneClaveUbi(Long oneClaveUbi);

    @Query("SELECT new com.ideaas.services.bean.MyObject(u.id, u.latitud, u.longitud, u.direccion) FROM AppUbicacion u WHERE u.id IN :ids")
    List<MyObject> findAllByIdIn(@Param("ids") List<Long> ids);

    @Query("SELECT new com.ideaas.services.bean.MyObject(u.id, u.oneClaveUbi, u.direccion, u.barrio, u.localidad, u.provincia, u.zona, u.latitud, u.longitud, u.bajaLogica) FROM AppUbicacion u WHERE u.id IN :ids")
    List<MyObject> findAllByIdUbicaciones(@Param("ids") List<Long> ids);

    List<AppUbicacion> findAllByBajaLogica(Boolean BajaLogica);
}
