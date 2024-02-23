package com.ideaas.services.service.interfaces;

import java.util.List;

/**
 * This interface defines a method that allows filtering the ids of the elements that belong to a given group of {@link com.ideaas.services.domain.MapEmpresa
 * OOH Comapies}
 */
public interface MapCompanyFilterIdsService {

    /**
     * This method allows to return the ids of the elements that belong to a {@link com.ideaas.services.domain.MapEmpresa
     * OOH Company} by its ids, if the ids of the elements have any relationship related to the company.
     *
     * @param idsToFilter
     * @param mapCompanyIds
     * @return
     */
    List<Long> filterEntitiesIdsThaBelongToAMapCompanies(List<Long> idsToFilter, List<Long> mapCompanyIds);
}
