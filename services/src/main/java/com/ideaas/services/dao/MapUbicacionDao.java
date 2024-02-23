package com.ideaas.services.dao;

import com.ideaas.services.bean.MapPlanningDTO;
import com.ideaas.services.bean.MyObject;
import com.ideaas.services.domain.MapEmpresa;
import com.ideaas.services.domain.MapUbicacion;
import com.ideaas.services.domain.Usuario;
import org.hibernate.cache.spi.SecondLevelCacheLogger;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MapUbicacionDao extends PagingAndSortingRepository<MapUbicacion, Long> {

    @Query("select distinct u from MapUbicacion u  JOIN u.mapEmpresa m JOIN m.usuarios us WHERE :usuario MEMBER OF m.usuarios  AND u.id = :idUbicacion")
    Optional<MapUbicacion> findByIdAndUsuarios(@Param("idUbicacion") Long idMapUbicaion, @Param("usuario") Usuario usuario);

    @Query("SELECT new com.ideaas.services.bean.MyObject(u.id, u.latitud, u.longitud, u.mapEmpresa.descripcion, u.mapElemento.descripcion, u.direccion, u.mapProvincia.descripcion, u.mapLocalidad.descripcion, u.polygonLatLong, u.mapEmpresa.id) FROM MapUbicacion u WHERE u.id IN :ids")
    List<MyObject> findAllByIdUbicaciones(@Param("ids") List<Long> ids);

    @Query("SELECT mu.id FROM MapUbicacion  mu INNER JOIN mu.mapEmpresa me WHERE me.id IN ?2 AND mu.id IN ?1")
    List<Long> filterEntitiesIdsThaBelongToAMapCompanies(List<Long> idsToFilter, List<Long> mapCompanyIds);

    @Query("SELECT new com.ideaas.services.bean.MapPlanningDTO(mu.id, mem.descripcion, mu.direccion, mlo.descripcion, mel.descripcion, mpr.descripcion, mu.idReferencia) " +
            "FROM MapUbicacion mu " +
            "INNER JOIN mu.mapEmpresa mem " +
            "INNER JOIN mu.mapLocalidad mlo " +
            "INNER JOIN mu.mapElemento mel " +
            "INNER JOIN mu.mapProvincia mpr " +
            "WHERE mu.id IN (?1)")
    List<MapPlanningDTO> findAllMapPlanningDTOsByIDs(List<Long> locationsIDs);

    @Query("SELECT DISTINCT COUNT(mu) FROM MapUbicacion mu " +
            "INNER JOIN mu.mapElemento mel ON mel.id = :idElemento " +
            "INNER JOIN mu.mapEmpresa mem ON mem.id = :idEmpresa " +
            "WHERE mu.bajaLogica = TRUE")
    int countAllInactiveLocationsOfStock(@Param("idElemento") Long idElemento, @Param("idEmpresa") Long idEmpresa);

    @Query("SELECT DISTINCT COUNT(mu) FROM MapUbicacion mu " +
            "INNER JOIN mu.mapElemento mel ON mel.id = :idElemento " +
            "INNER JOIN mu.mapEmpresa mem ON mem IN (:empresas) " +
            "WHERE mu.bajaLogica = FALSE")
    int countAllActiveLocations(@Param("idElemento") Long idElemento, @Param("empresas") List<Long> empresas);

    @Query("SELECT CASE WHEN (count(mup) > 0) THEN true ELSE false END " +
            "FROM MapUbicacion mup " +
            "INNER JOIN mup.mapEmpresa mem " +
            "WHERE mup.id = ?1 AND mup.bajaLogica <> ?2")
    Boolean locationHasChangedDeleted(Long mapLocationId, Boolean deleted);

    @Query("SELECT CASE WHEN (count(mup) > 0) THEN true ELSE false END " +
            "FROM MapUbicacion mup " +
            "INNER JOIN mup.mapEmpresa mem " +
            "WHERE mup.id = ?1 AND mup.mapEmpresa.id <> ?2")
    Boolean companyHasChanged(Long mapLocationId, Long mapEmpresaId);

    @Query("SELECT CASE WHEN (count(mup) > 0) THEN true ELSE false END " +
            "FROM MapUbicacion mup " +
            "INNER JOIN mup.mapEmpresa mel " +
            "WHERE mup.id = ?1 AND mup.mapElemento.id <> ?2")
    Boolean elementHasChanged(Long mapLocationId, Long mapElementId);
}