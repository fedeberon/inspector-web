package com.ideaas.services.dao;

import com.ideaas.services.bean.MyObject;
import com.ideaas.services.domain.AppRelevamiento;
import com.ideaas.services.domain.AppRespuesta;
import com.ideaas.services.domain.AppUbicacion;
import com.ideaas.services.domain.AppUbicacionRelevamiento;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface AppUbicacionRelDao extends PagingAndSortingRepository<AppUbicacionRelevamiento, Long> {

    List<AppUbicacionRelevamiento> findAllByAppRelevamiento_Id(Long idRelevamiento);

    List<AppUbicacionRelevamiento> findAllByBajaLogica(Boolean BajaLogica);


    @Query(value="SELECT * FROM app_ubicaciones_relevamiento WHERE idRelevamiento=?1 AND bajaLogica=false", nativeQuery = true)
    List<AppUbicacionRelevamiento> findAllByRelevamientoAndBajaLogica(Long idRelevamient);
    
    @Query(value="SELECT idRelevamiento, COUNT(*) AS cantidad_ubicaciones FROM app_ubicaciones_relevamiento aup GROUP BY aup.idRelevamiento ORDER BY aup.idRelevamiento", nativeQuery = true)
    List<Object []> cantidadUbicacionesByRelevamiento();

    @Query("SELECT new com.ideaas.services.bean.MyObject(u.appUbicacion.id, u.appUbicacion.latitud, u.appUbicacion.longitud, u.appUbicacion.direccion) FROM AppUbicacionRelevamiento u JOIN u.appUbicacion au WHERE u.id IN :ids")
    List<MyObject> findAllByIdIn(List<Long> ids);

    @Query("SELECT new com.ideaas.services.bean.MyObject(u.id, u.appUbicacion, u.appRelevamiento, u.cantidad, u.evp, u.elemento, u.anunciante, u.producto, u.referencias, u.oneOrdenS, u.oneCircuito, u.bajaLogica) FROM AppUbicacionRelevamiento u WHERE u.id IN :ids")
    List<MyObject> findAllByIdUbicacionRel(@Param("ids") List<Long> ids);

    @Query(value="SELECT idUbicacionRelevamiento FROM app_ubicaciones_relevamiento ubirel WHERE ubirel.idRelevamiento = :idRelevamiento AND ubirel.idUbicacionRelevamiento > :idUbicacionRelevamiento ORDER BY ubirel.idUbicacionRelevamiento LIMIT 1", nativeQuery = true)
    Long findNextById(Long idUbicacionRelevamiento, Long idRelevamiento);

    @Query(value="SELECT idUbicacionRelevamiento FROM app_ubicaciones_relevamiento ubirel WHERE ubirel.idRelevamiento = :idRelevamiento AND ubirel.idUbicacionRelevamiento < :idUbicacionRelevamiento ORDER BY ubirel.idUbicacionRelevamiento DESC LIMIT 1", nativeQuery = true)
    Long findPreviousById(Long idUbicacionRelevamiento, Long idRelevamiento);

    @Transactional
    @Modifying
    @Query("DELETE FROM AppUbicacionRelevamiento ubirel WHERE ubirel.id IN :ids")
    void deleteByIds(@Param("ids")List<Long> idsUbirel);

    @Transactional
    @Modifying
    @Query("DELETE FROM AppUbicacionRelevamiento ubirel WHERE ubirel.appRelevamiento.id IN :ids")
    void deleteByRelevamientoIds(@Param("ids")List<Long> idsRelevamiento);


    List<AppUbicacionRelevamiento> findAllByAppRelevamientoFechaAsignacionIsBetween(String startDate, String endDate);

}
