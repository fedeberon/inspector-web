package com.ideaas.services.service;

import com.ideaas.services.dao.StockDao;
import com.ideaas.services.domain.*;
import com.ideaas.services.enums.TipoUsuarioEnum;
import com.ideaas.services.exception.StockException;
import com.ideaas.services.service.interfaces.MapEmpresaService;
import com.ideaas.services.service.interfaces.StockService;
import com.ideaas.services.service.interfaces.UsuarioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class StockServiceImpl implements StockService {

    @Autowired
    private StockDao dao;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private MapEmpresaService mapEmpresaService;

    @Override
    public Stock get(Long idStock) {
        Stock stock =  null;
        /*
        switch (TipoUsuarioEnum.tipoUsuarioOf(usuario.getTipoUsuario().getId())) {
            case ROLE_ADMINISTRADOR:
                Optional<Stock> optianal =  dao.findById(idStock);
                stock = optianal.isPresent() ? optianal.get() : null;
                break;
            case ROLE_ADMINISTRADOR_OOH:
                stock = dao.findAByIdAndMapEmpresa(usuario.getEmpresas().get(0).getId(), idStock);
                break;
        }
        if(stock == null) { throw new StockException("StockNotInitilized"); }
        */
        List<Long> idMapEmpresas =  this.usuarioService.getMapCompanyIdOfUserLoggedInIfIsOHHAdminTypeUser().orElse(new ArrayList<>(Arrays.asList(-1l)));
        stock = this.dao.findAByIdAndMapEmpresa(idStock, idMapEmpresas);
        if(stock == null){
            throw new StockException("StockNotInitilized");
        }
        return stock;
    }

    @Override
    public List<Stock> findAll() {
        Usuario usuario = this.usuarioService.getUsuarioLogeado();
        List<Stock> stocks =  new ArrayList<>();
        switch (TipoUsuarioEnum.tipoUsuarioOf(usuario.getTipoUsuario().getId())) {
            case ROLE_ADMINISTRADOR:
                stocks.addAll(this.dao.findAll());
                break;
            case ROLE_ADMINISTRADOR_OOH:
                List<MapEmpresa> empresas = mapEmpresaService.findAll();
                if(!empresas.isEmpty()) {
                    stocks = empresas.stream().filter(Objects::nonNull).map(MapEmpresa::getStock).flatMap(Collection::stream).distinct().filter(Objects::nonNull).collect(Collectors.toList());
                }
                break;
        }
        return stocks;
    }

    @Override
    public List<Stock> findAllByMapEmpresa(){
        List<Long> mapCompanyIds =  this.usuarioService.getMapCompanyIdOfUserLoggedInIfIsOHHAdminTypeUser().orElse(new ArrayList<>(Collections.singletonList(-1L)));
        return this.dao.findAllByMapEmpresa(mapCompanyIds);
    }

    @Override
    public List<Stock> findAll(Integer pageSize, Integer pageNo, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<Stock> mapBusesRecorridos = dao.findAll(paging);

        return mapBusesRecorridos.getContent();
    }

    @Override
    public Stock save(Stock newStock) {
        return this.dao.save(newStock);
    }

    private void verifyStock(Stock stock){
        if(this.usuarioService.getTipoUsurioOfTheLoggedInUser() == TipoUsuarioEnum.ROLE_ADMINISTRADOR_OOH){{
            /*if(newStock.getCantDispositivosCalle() < 0) {
                    throw new StockException(StockException.Code.WRONG_CANT_ELEMENT_STREET);
            }*/
            if(stock.getCantDispositivosDeposito() < 0) {
                throw new StockException(StockException.Code.WRONG_CANT_ELEMENT_DEPOSIT);
            } else if(stock.getCantDispositivosReparacion() < 0) {
                throw new StockException(StockException.Code.WRONG_CANT_ELEMENT_REPAIR);
            }
        }}
    }

    @Override
    public void saveAll(List<Stock> stocks) {
        this.dao.saveAll(stocks);
    }

    @Override
    public void updateStock (
            Long locationId,
            Long mapCompanyId,
            Long mapElementId,
            Boolean isNewLocation,
            Boolean hasChangedDeleted,
            Boolean companyHasChanged,
            Boolean hasChangedElement,
            Boolean isActiveLocation) throws StockException {
        if (this.usuarioService.getTipoUsurioOfTheLoggedInUser() == TipoUsuarioEnum.ROLE_ADMINISTRADOR) return;

        log.info("1. ¿Es una nueva ubicacion?: {}", isNewLocation);
        if (isNewLocation) { // 1. Si es una nueva ubicación se resta un elemento de deposito
            Stock newStock = this.dao.findAllByMapEmpresaIdAndMapElementoId(mapCompanyId, mapElementId);
            log.info(String.valueOf(newStock));
            if(Objects.nonNull(newStock)) {
                log.info("2. Dispositivos antes de restarle: {}", newStock.getCantDispositivosDeposito());
                newStock.setCantDispositivosDeposito(newStock.getCantDispositivosDeposito() - 1);
                log.info("3. Dispositivos despues de restarle: {}", newStock.getCantDispositivosDeposito());
                this.verifyStock(newStock);
            }
        } else { // En caso contrario, significa que ya existe un stock
            Stock oldStock = this.dao.findByLocationId(locationId);
            if (Objects.nonNull(oldStock)) {
                log.info("4. ¿Cambio el elemento?: {}", hasChangedElement);
                // Si el viejo y el nuevo id del elemento cambiaron, significa que hay que actualizar dos stocks distintos
                if (hasChangedElement || companyHasChanged) {
                    log.info("5. ¿No cambio el deleted y esta activa?: {}", !hasChangedDeleted&&isActiveLocation);
                    if (!hasChangedDeleted&&isActiveLocation) { // Si no se cambió la baja lógic y estaba activo, entonces no hay que sumar uno al sotck viejo
                        log.info("6. Cant. deposito antes del cambio: {}", oldStock.getCantDispositivosDeposito());
                        oldStock.setCantDispositivosDeposito(oldStock.getCantDispositivosDeposito() + 1);
                        log.info("7. Cant. deposito despues del cambio: {}", oldStock.getCantDispositivosDeposito());
                    }
                    Stock newStock = this.dao.findAllByMapEmpresaIdAndMapElementoId(mapCompanyId, mapElementId);
                    log.info("8. ¿El newStock no es null?: {}", Objects.nonNull(newStock));
                    if(Objects.nonNull(newStock)&&isActiveLocation) { // restar unoal stock del nuevo elemento si existe
                        log.info("9. Cant. deposito antes del cambio: {}", oldStock.getCantDispositivosDeposito());
                        newStock.setCantDispositivosDeposito(newStock.getCantDispositivosDeposito() - 1);
                        log.info("10. Cant. deposito despues del cambio: {}", oldStock.getCantDispositivosDeposito());
                        log.info("11. NewStock id: {}", newStock.getIdStock());
                        this.verifyStock(newStock);
                        this.verifyStock(oldStock);
                        return;
                    }
                } else {
                    log.info("12. ¿Cambio el deleted?: {}", hasChangedDeleted);
                    if (hasChangedDeleted) { // Si no se cambió la baja lógica, entonces no hay que hacer cambios
                        log.info("13. Cant. deposito antes del cambio: {}", oldStock.getCantDispositivosDeposito());
                        oldStock.setCantDispositivosDeposito(oldStock.getCantDispositivosDeposito() + (!isActiveLocation ? 1 : -1));
                        log.info("14. Cant. deposito despues del cambio: {}", oldStock.getCantDispositivosDeposito());
                    }
                }
                log.info("15. OldStock id: {}", oldStock.getIdStock());
                this.verifyStock(oldStock);
            }
        }
    }
}
