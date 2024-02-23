package com.ideaas.web.controller;

import com.ideaas.services.domain.MapElemento;
import com.ideaas.services.domain.MapEmpresa;
import com.ideaas.services.domain.Stock;
import com.ideaas.services.enums.TipoUsuarioEnum;
import com.ideaas.services.exception.StockException;
import com.ideaas.services.request.MapUbicacionRequest;
import com.ideaas.services.service.interfaces.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("stock")
@Secured({"ROLE_ADMINISTRADOR", "ROLE_ADMINISTRADOR_OOH"})
public class StockController {

    @Autowired
    private StockService stockService;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private MapUbicacionService mapUbicacionService;
    @Autowired
    private MapElementoService mapElementoService;
    @Autowired
    private MapEmpresaService mapEmpresaService;

    @GetMapping("{id}")
    public String show(@PathVariable Long id, Model model) {
        Stock stock = this.stockService.get(id);
        model.addAttribute("stock", stock);

        return "stock/show";
    }

    @RequestMapping("list")
    public String list(@ModelAttribute MapUbicacionRequest mapUbicacionRequest, Model model){
        model.addAttribute("stocks", this.stockService.findAllByMapEmpresa());
        return "stock/list";
    }


    @RequestMapping("update")
    public String update(@RequestParam Long id, Model model) {
        Stock stock = this.stockService.get(id);
        int minimumAmountOfLocations = mapUbicacionService.countAllInactiveLocationsOfStock(stock.getMapElemento().getId(), stock.getMapEmpresa().getId());
        model.addAttribute("updateStock", stock);
        model.addAttribute("minimumAmountOfLocations", minimumAmountOfLocations);

        return "stock/update";
    }


    @GetMapping("create")
    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR_OOH')")
    public String create() {
        return "stock/create";
    }

    @RequestMapping(value = "save" , method = RequestMethod.POST)
    public String save(Stock stock, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        System.out.println(stock);
        int inactiveLocationsCount = mapUbicacionService.countAllInactiveLocationsOfStock(stock.getMapElemento().getId(), stock.getMapEmpresa().getId());

        if(usuarioService.getTipoUsurioOfTheLoggedInUser() == TipoUsuarioEnum.ROLE_ADMINISTRADOR_OOH){
            if(stock.getCantDispositivosDeposito()   < 0) { bindingResult.addError(badCantDispositivoDeposito); }
            if(stock.getCantDispositivosReparacion() < 0) { bindingResult.addError(badCantDispositivoReparacion); }
        }

        if((stock.getCantDispositivosDeposito() + stock.getCantDispositivosReparacion()) < inactiveLocationsCount ){
            model.addAttribute("updateStock", stock);
            model.addAttribute("stockErrorCode", StockException.Code.WRONG_MINIMUM_DEVICES_NUMBER_BETWEEN_DEPOSIT_AND_REPAIR.toString());
            model.addAttribute("inactiveLocationsCount", inactiveLocationsCount);
            return "stock/update";
        }

        if(bindingResult.hasErrors()) {
            return "stock/update";
        }

        redirectAttributes.addAttribute("id", this.stockService.save(stock).getIdStock());
        return "redirect:/stock/{id}";
    }

    @RequestMapping("updateUserStock")
    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR_OOH')")
    public String updateUserStock(){
        Optional<List<Long>> empresasIds = usuarioService.getMapCompanyIdOfUserLoggedInIfIsOHHAdminTypeUser();
        List<Stock> stockToBeSaved = new ArrayList<>();
        for (Long empresaId : empresasIds.get()){
            List<MapElemento> elementosSinStock = this.mapElementoService.findByMapEmpresaAndNoInitializedStock(empresaId);
            for(MapElemento e : elementosSinStock){
                Stock newStock = new Stock();
                newStock.setCantDispositivosReparacion(0);
                newStock.setCantDispositivosDeposito(this.mapUbicacionService.countAllInactiveLocationsOfStock(e.getId(), empresaId));
                newStock.setMapElemento(e);
                newStock.setMapEmpresa(mapEmpresaService.get(empresaId));
                stockToBeSaved.add(newStock);
            }
        }
        this.stockService.saveAll(stockToBeSaved);

        return "redirect:/stock/list";
    }

    @ModelAttribute("stock")
    public Stock getStock() {
        Stock stock = new Stock();
        stock.setMapElemento(new MapElemento());
        stock.setMapEmpresa(new MapEmpresa());
        return stock;

    }

    FieldError badCantDispositivoCalle = new FieldError(
            "stock" , "cantDispositivosCalle" , "Debes ingresar un n&uacute;mero positivo"
    );

    FieldError badCantDispositivoDeposito = new FieldError(
            "stock" , "cantDispositivosDeposito" , "Debes ingresar un n&uacute;mero positivo"
    );

    FieldError badCantDispositivoReparacion = new FieldError(
            "stock" , "cantDispositivosReparacion" , "Debes ingresar un n&uacute;mero positivo"
    );

    FieldError badLessThanInactiveLocations = new FieldError(
            "stock", "cantDispositivosDeposito" , "La suma de la cantidad de dispositivos en calle y reparacion debe ser mayor o igual a la cantidad de elementos inactivos."
    );
}
