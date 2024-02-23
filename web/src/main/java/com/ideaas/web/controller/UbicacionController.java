package com.ideaas.web.controller;

import com.ideaas.services.bean.MyObject;
import com.ideaas.services.bean.Wrapper;
import com.ideaas.services.domain.*;
import com.ideaas.services.exception.StockException;
import com.ideaas.services.request.MapUbicacionRequest;
import com.ideaas.services.service.interfaces.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by federicoberon on 08/10/2019.
 */

@Controller
@Secured({"ROLE_ADMINISTRADOR", "ROLE_ADMINISTRADOR_OOH"})
@RequestMapping("ubicacion")
public class UbicacionController {


    private MapUbicacionService mapUbicacionService;

    private MapEmpresaService mapEmpresaService;

    private MapElementoService mapElementoService;

    private MapFormatoService mapFormatoService;

    private MapMedioService mapMedioService;

    private MapLocalidadService mapLocalidadService;

    private MapProvinciaService mapProvinciaService;

    private MapBusService mapBusService;

    private MapUbicacionAlturaService mapUbicacionAlturaService;

    private MapUbicacionVisibilidadService mapUbicacionVisibilidadService;

    private ParametroService parametroService;

    @Autowired
    public UbicacionController(MapUbicacionService mapUbicacionService, MapEmpresaService mapEmpresaService, MapElementoService mapElementoService, MapFormatoService mapFormatoService, MapMedioService mapMedioService, MapLocalidadService mapLocalidadService, MapProvinciaService mapProvinciaService, MapBusService mapBusService, MapUbicacionAlturaService mapUbicacionAlturaService, MapUbicacionVisibilidadService mapUbicacionVisibilidadService, ParametroService parametroService) {
        this.mapUbicacionService = mapUbicacionService;
        this.mapEmpresaService = mapEmpresaService;
        this.mapElementoService = mapElementoService;
        this.mapFormatoService = mapFormatoService;
        this.mapMedioService = mapMedioService;
        this.mapLocalidadService = mapLocalidadService;
        this.mapProvinciaService = mapProvinciaService;
        this.mapBusService = mapBusService;
        this.mapUbicacionAlturaService = mapUbicacionAlturaService;
        this.mapUbicacionVisibilidadService = mapUbicacionVisibilidadService;
        this.parametroService = parametroService;
    }

    @RequestMapping("list")
    public String list(@ModelAttribute MapUbicacionRequest mapUbicacionRequest, Model model){
        model.addAttribute("ubicaciones", mapUbicacionService.findAll(mapUbicacionRequest));
        model.addAttribute("ubicacionRequest", mapUbicacionRequest);

        return "ubicacion/list";
    }

    @RequestMapping(value = "search", params = "paginate")
    public String listPaginated(@ModelAttribute("myWrapper") Wrapper wrapper, Model model){
        wrapper.getRequest().setPage(wrapper.getPage());
        model.addAttribute("ubicaciones", mapUbicacionService.findAll(wrapper.getRequest()));
        model.addAttribute("ubicacionRequest", wrapper.getRequest());

        return "ubicacion/list";
    }

    @RequestMapping(value = "search", params = "saveList")
    public String saveList(@ModelAttribute Wrapper wrapper, Model model){
        List<Long> ids = wrapper.getIdAppUbicacionesListSelected();
        MapUbicacionRequest request = wrapper.getRequest();
        request.setIdsSearch(ids.stream().map(String::valueOf).reduce((p, n) -> p+","+n).get());
        model.addAttribute("ubicacionRequest", request);

        try {
            model.addAttribute("ubicaciones", mapUbicacionService.saveList(request));
        } catch (StockException e) {
            int minimumAmountOfLocations = mapUbicacionService.countAllInactiveLocationsOfStock(
                    wrapper.getRequest().getIdElemento(),
                    wrapper.getRequest().getIdEmpresa());
            model.addAttribute("stockErrorCode", e.code());
            model.addAttribute("inactiveLocationsCount", minimumAmountOfLocations);
            model.addAttribute("ubicaciones", mapUbicacionService.findAll(wrapper.getRequest()));
            model.addAttribute("ubicacionRequest", wrapper.getRequest());

            return "ubicacion/list";
        }

        return "ubicacion/list";
    }

    @RequestMapping(value = "search", params = "maps" , method = RequestMethod.POST)
    public String findAll(@ModelAttribute("myWrapper") Wrapper ubicaciones, Model model){
        model.addAttribute("registros",this.mapUbicacionService.findAllByIdIn(ubicaciones.getIdAppUbicacionesListSelected()));
        model.addAttribute("request", ubicaciones.getRequest());

        return "ubicacion/map";
    }

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.setAutoGrowCollectionLimit(1024);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        dateFormat.setLenient(false);
        webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    @GetMapping("create")
    public String create(@RequestParam(required = false) Long locationId, Model model) {
        if(Objects.nonNull(locationId)){
            model.addAttribute("mapUbicacion", mapUbicacionService.get(locationId));
        }
        return "ubicacion/create";
    }

    FieldError emptyMapEmpresa = new FieldError(
            "mapUbicacion" , "mapEmpresa" , "Debes seleccionar una opcion"
    );
    FieldError emptyMapElemento = new FieldError(
            "mapUbicacion" , "mapElemento" , "Debes seleccionar una opcion"
    );
    FieldError emptyMapFormato = new FieldError(
            "mapUbicacion" , "mapFormato" , "Debes seleccionar una opcion"
    );
    FieldError emptyMapMedio = new FieldError(
            "mapUbicacion" , "mapMedio" , "Debes seleccionar una opcion"
    );
    FieldError emptyMapProvincia = new FieldError(
            "mapUbicacion" , "mapProvincia" , "Debes seleccionar una opcion"
    );
    FieldError emptyMapLocalidad = new FieldError(
            "mapUbicacion" , "mapLocalidad" , "Debes seleccionar una opcion"
    );
    FieldError emptyCoeficiente = new FieldError(
            "mapUbicacion" , "coeficiente" , "Debes completar este campo"
    );
    FieldError emptyMetrosContacto = new FieldError(
            "mapUbicacion" , "metrosContacto" , "Debes completar este campo"
    );

    @RequestMapping(value = "save" , method = RequestMethod.POST)
    public String save(@ModelAttribute (name = "mapUbicacion") MapUbicacion mapUbicacion,  Model model, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if(mapUbicacion.getMapEmpresa().getId() == null){
            bindingResult.addError(emptyMapEmpresa);

        }
        if(mapUbicacion.getMapElemento().getId() == null){
            bindingResult.addError(emptyMapElemento);

        }
        if(mapUbicacion.getMapFormato().getId() == null){
            bindingResult.addError(emptyMapFormato);

        }
        if(mapUbicacion.getMapMedio().getId() == null){
            bindingResult.addError(emptyMapMedio);

        }
        if(mapUbicacion.getMapProvincia().getId() == null){
            bindingResult.addError(emptyMapProvincia);

        }
        if(mapUbicacion.getMapLocalidad().getId() == null){
            bindingResult.addError(emptyMapLocalidad);

        }
        if(mapUbicacion.getCoeficiente() == null ){
            mapUbicacion.setCoeficiente(BigDecimal.valueOf(0l));
        }
        if(mapUbicacion.getMetrosContacto() == null){
            mapUbicacion.setMetrosContacto(0l);
        }
        if(mapUbicacion.getMapUbicacionAltura() == null){
            mapUbicacion.setMapUbicacionAltura(this.mapUbicacionAlturaService.get(0l));
        }
        if(mapUbicacion.getMapUbicacionVisibilidad() == null){
            mapUbicacion.setMapUbicacionVisibilidad(this.mapUbicacionVisibilidadService.get(0l));
        }
        if(mapUbicacion.getMapBuses() == null){
            mapUbicacion.setMapBuses(this.mapBusService.get(0l));
        }

        if(bindingResult.hasErrors()) {
            return "ubicacion/create";
        }

        mapUbicacion.setFechaAlta(LocalDateTime.now());
        try {
            mapUbicacionService.save(mapUbicacion);
        } catch (StockException e) {
            int minimumAmountOfLocations = mapUbicacionService.countAllInactiveLocationsOfStock(
                    mapUbicacion.getMapElemento().getId(),
                    mapUbicacion.getMapEmpresa().getId());
            model.addAttribute("stockErrorCode", e.code());
            model.addAttribute("inactiveLocationsCount", minimumAmountOfLocations);
            return "ubicacion/create";
        }

        redirectAttributes.addAttribute("id", mapUbicacion.getId());

        return "redirect:/ubicacion/{id}?isAfterCreate=true";

    }

    @GetMapping("{id}")
    public String show(@PathVariable Long id, @RequestParam(defaultValue = "false") Boolean isAfterCreate, Model model) {
        MapUbicacion ubicacion = mapUbicacionService.get(id);
        if(Objects.nonNull(isAfterCreate)&isAfterCreate){
            model.addAttribute("isAfterCreate", isAfterCreate);
        }
        model.addAttribute("ubicacion", ubicacion);

        return "ubicacion/show";
    }

    @RequestMapping(value = "search" , params="editUbicacion", method = RequestMethod.POST)
    public String edit(@ModelAttribute MapUbicacion updatedUbicacion, @ModelAttribute("myWrapper") Wrapper filtro, Model model){
        try {
            mapUbicacionService.save(updatedUbicacion);
        } catch (StockException e) {
            int minimumAmountOfLocations = mapUbicacionService.countAllInactiveLocationsOfStock(
                    updatedUbicacion.getMapElemento().getId(),
                    updatedUbicacion.getMapEmpresa().getId());
            model.addAttribute("stockErrorCode", e.code());
            model.addAttribute("inactiveLocationsCount", minimumAmountOfLocations);
            model.addAttribute("updateUbicacion", updatedUbicacion);
            model.addAttribute("request", null);
            return "ubicacion/update";
        }
        MapUbicacion ubicacion = mapUbicacionService.get(updatedUbicacion.getId());
        model.addAttribute("ubicacion", ubicacion);
        model.addAttribute("request", filtro.getRequest());

        return "ubicacion/show";
    }

    @RequestMapping(value = "search", params = {"editar"})
    public String update(@ModelAttribute("myWrapper") Wrapper filtro, Model model) {
        MapUbicacion mapUbicacion = mapUbicacionService.get(filtro.getId());
        model.addAttribute("updateUbicacion", mapUbicacion);
        model.addAttribute("request", filtro.getRequest());

        return "ubicacion/update";
    }

    @ModelAttribute("mapUbicacion")
    public MapUbicacion get(){
        return new MapUbicacion();
    }

    @ModelAttribute("empresas")
    public List<MapEmpresa> empresas(){
        return mapEmpresaService.findByOrderByDescripcionAsc();
    }

    @ModelAttribute("parametros")
    public List<Parametro> parametros(){
        return this.parametroService.findAll();
    }

    @ModelAttribute("elementos")
    public List<MapElemento> elementos(){
        return mapElementoService.findByOrderByDescripcionAsc();
    }

    @ModelAttribute("formatos")
    public List<MapFormato> formatos(){
        return mapFormatoService.findByOrderByDescripcionAsc();
    }

    @ModelAttribute("medios")
    public List<MapMedio> medios(){
        return mapMedioService.findByOrderByDescripcionAsc();
    }

    @ModelAttribute("localidades")
    public List<MapLocalidad> localidades(){
        return mapLocalidadService.findByOrderByDescripcionAsc();
    }

    @ModelAttribute("provincias")
    public List<MapProvincia> provincias(){
        return mapProvinciaService.findByOrderByDescripcionAsc();
    }

    @ModelAttribute("buses")
    public List<MapBus> buses(){
        return mapBusService.findAll();
    }

    @ModelAttribute("alturas")
    public List<MapUbicacionAltura> alturas(){
        return mapUbicacionAlturaService.findAll();
    }

    @ModelAttribute("visibilidades")
    public List<MapUbicacionVisibilidad> visibilidades(){
        return mapUbicacionVisibilidadService.findAll();
    }

    @ModelAttribute("mapUbicacionRequest")
    public MapUbicacionRequest mapUbicacionRequest(){
        return new MapUbicacionRequest();
    }

    @ModelAttribute("myWrapper")
    public Wrapper wrapper(){
        return new Wrapper();
    }

}