package com.ideaas.services.service.interfaces;

import com.ideaas.services.domain.Stock;
import com.ideaas.services.exception.StockException;

import java.util.List;

public interface StockService {

    Stock get(Long idStock);

    Stock save(Stock newStock);

    void saveAll(List<Stock> stocks);

    List<Stock> findAll(Integer pageSize, Integer pageNo, String sortBy);

    List<Stock> findAll();

    List<Stock> findAllByMapEmpresa();

    void updateStock(
            Long locationId,
            Long mapCompanyId,
            Long mapElementId,
            Boolean isNewLocation,
            Boolean hasChangedDeleted,
            Boolean hasChangedEmpresa,
            Boolean hasChangedElement,
            Boolean isActiveLocation) throws StockException;

    //Deprecado (?), no parece ser usado en ninguna parte.
    /*void updateStock(Long idUbicaion, Long idMapEmpresa, boolean oldBjaLogica, boolean newBajaLogica);*/
}
