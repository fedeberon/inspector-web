package com.ideaas.services.service.interfaces;

import com.ideaas.services.domain.Parametro;
import com.ideaas.services.domain.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ParametroService extends MapCompanyFilterIdsService  {

    Parametro get(Long id);

    Parametro save(Parametro parametro);

    List<Parametro> findAll();

    /**
     * Find all belong to a {@link com.ideaas.services.domain.MapEmpresa
     * OOH Company} by its ids
     *
     * @return
     */
    List<Parametro> findAllByMapCompanyIds(List<Long> mapCompanyIds);

    void delete(Long id);
}
