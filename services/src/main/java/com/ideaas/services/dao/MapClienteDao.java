package com.ideaas.services.dao;

import com.ideaas.services.domain.MapCliente;
import com.ideaas.services.domain.Parametro;
import com.ideaas.services.domain.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MapClienteDao extends PagingAndSortingRepository<MapCliente, Long> {

    @Query("SELECT DISTINCT mc FROM MapCliente mc JOIN mc.mapEmpresas e WHERE :usuario MEMBER OF e.usuarios AND mc.id = :idMapCliente")
    Optional<MapCliente> getByUsuarioAndIdMapCliente(@Param("idMapCliente") Long id, @Param("usuario") Usuario usuario);

    /**
     * Find all the {@link MapCliente clients} that belong to a group of {@link com.ideaas.services.domain.MapEmpresa companies}
     * by its ids. If instead of company IDs, if a list with a number equal to -1 is given, return all clients
     *
     * @param mapCompanyIds
     * @return
     */
    @Query("SELECT DISTINCT mc FROM MapCliente mc INNER JOIN mc.mapEmpresas me WHERE (-1 IN ?1 OR me.id IN ?1)")
    List<MapCliente> findAll(List<Integer> mapCompanyIds);

    @Query("SELECT mc.id FROM MapCliente mc INNER JOIN mc.mapEmpresas me WHERE me.id IN ?2 AND mc.id IN ?1")
    List<Long> filterEntitiesIdsThaBelongToAMapCompanies(List<Long> idsToFilter, List<Long> mapCompanyIds);
}
