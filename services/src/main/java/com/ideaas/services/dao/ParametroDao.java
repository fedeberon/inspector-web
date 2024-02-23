package com.ideaas.services.dao;

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
public interface ParametroDao  extends PagingAndSortingRepository<Parametro, Long> {

    @Query("SELECT DISTINCT p FROM Parametro p INNER JOIN p.empresas me WHERE (-1 IN ?1 OR me.id IN ?1)")
    List<Parametro> findAll(List<Integer> mapCompanyIDs);

    @Query("SELECT DISTINCT p.idParametro FROM Parametro p INNER JOIN p.empresas me WHERE me.id IN ?2 AND p.idParametro IN ?1")
    List<Long> filterEntitiesIdsThaBelongToAMapCompanies(List<Long> idsToFilter, List<Long> mapCompanyIds);
}

