package com.ideaas.web.controller;

import com.ideaas.services.bean.Wrapper;
import com.ideaas.services.domain.MapPoi;
import com.ideaas.services.domain.MapPoiEntidad;
import com.ideaas.services.domain.MapPoiSector;
import com.ideaas.services.domain.MapProvincia;
import com.ideaas.services.request.MapPoiRequest;
import com.ideaas.services.service.interfaces.MapPoiEntidadService;
import com.ideaas.services.service.interfaces.MapPoiSectorService;
import com.ideaas.services.service.interfaces.MapPoiService;
import com.ideaas.services.service.interfaces.MapProvinciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
@RequestMapping("poi")
@Secured({"ROLE_ADMINISTRADOR"})
public class PoiController {

    private MapPoiService mapPoiService;
    private MapPoiEntidadService mapPoiEntidadService;
    private MapProvinciaService mapProvinciaService;
    private MapPoiSectorService mapPoiSectorService;

    private static final Boolean INACTIVE = true;

    @Autowired
    public PoiController(MapPoiService mapPoiService, MapPoiEntidadService mapPoiEntidadService, MapProvinciaService mapProvinciaService, MapPoiSectorService mapPoiSectorService) {
        this.mapPoiService = mapPoiService;
        this.mapPoiEntidadService = mapPoiEntidadService;
        this.mapProvinciaService = mapProvinciaService;
        this.mapPoiSectorService = mapPoiSectorService;
    }

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.setAutoGrowCollectionLimit(1024);
    }

    @GetMapping("{id}")
    public String show(@PathVariable Long id, Model model) {
        MapPoi poi = mapPoiService.get(id);

        model.addAttribute("poi", poi);

        return "poi/show";
    }

    @GetMapping("list")
    public String findAll(@RequestParam(defaultValue = "10") Integer size,
                          @RequestParam(defaultValue = "0") Integer page, Model model) {
        List<MapPoi> pois = mapPoiService.findAll(size, page, "id");

        model.addAttribute("pois", pois);
        model.addAttribute("page" , page);

        return "poi/list";
    }

    @RequestMapping ("listComplete")
    public String findAll(Model model){
        List<MapPoi> pois = mapPoiService.findAll();
        model.addAttribute("pois", pois);

        return "poi/list";
    }

    @GetMapping("create")
    public String create(Model model) {
        List<MapPoiEntidad> poiEntidades = mapPoiEntidadService.findAll();
        List<MapProvincia> provincias = mapProvinciaService.findAll();
        model.addAttribute("poiEntidades", poiEntidades);
        model.addAttribute("provincias", provincias);

        return "poi/create";
    }

    FieldError emptyDescripcionPoi = new FieldError(
            "mapPois" , "descripcion" , "Debes completar este campo"
    );
    FieldError emptyLatPoi = new FieldError(
            "mapPois" , "geoLatitud" , "Debes completar este campo"
    );
    FieldError emptyLngPoi = new FieldError(
            "mapPois" , "geoLongitud" , "Debes completar este campo"
    );
    FieldError emptyIconoPoi = new FieldError(
            "mapPois" , "icono" , "Debes completar este campo"
    );

    @RequestMapping(value = "save" , method = RequestMethod.POST)
    public String save(@ModelAttribute MapPoi poi, BindingResult bindingResult, RedirectAttributes redirectAttributes){

        if(poi.getDescripcion().isEmpty()){
            bindingResult.addError(emptyDescripcionPoi);
        }
        if(poi.getGeoLatitud() == null){
            bindingResult.addError(emptyLatPoi);
        }
        if(poi.getGeoLongitud() == null){
            bindingResult.addError(emptyLngPoi);
        }
        if(poi.getIcono().isEmpty()){
            bindingResult.addError(emptyIconoPoi);
        }

        if(bindingResult.hasErrors()) {
            return "poi/create";
        }

        mapPoiService.save(poi);
        redirectAttributes.addAttribute("id", poi.getId());
        return "redirect:/poi/{id}";
    }

    @RequestMapping("update")
    public String update(@RequestParam Long id, Model model) {
        MapPoi mapPoi = mapPoiService.get(id);
        model.addAttribute("updatePoi", mapPoi);
        model.addAttribute("poiEntidad", mapPoi.getMapPoiEntidad().getDescripcion());

        return "poi/update";
    }

    @RequestMapping("dropBajaLogica")
    public String dropBajaLogica(@RequestParam Long id, RedirectAttributes redirectAttributes){
        MapPoi mapPoi = mapPoiService.get(id);
        mapPoi.setBajaLogica(INACTIVE);
        mapPoiService.save(mapPoi);
        redirectAttributes.addAttribute("id", mapPoi.getId());

        return "redirect:/poi/{id}";
    }

    @RequestMapping("upBajaLogica")
    public String upBajaLogica(@RequestParam Long id, RedirectAttributes redirectAttributes){
        MapPoi mapPoi = mapPoiService.get(id);
        mapPoi.setBajaLogica(!INACTIVE);
        mapPoiService.save(mapPoi);
        redirectAttributes.addAttribute("id", mapPoi.getId());

        return "redirect:/poi/{id}";
    }

    @RequestMapping(value = "search", params = "maps" , method = RequestMethod.POST)
    public String findAll(@ModelAttribute Wrapper pois, Model model){
        model.addAttribute("registros", pois.getSelectedElements());

        return "ubicacion/map";
    }

    @RequestMapping(value = "search", params = "paginate")
    public String listPaginated(@ModelAttribute("myWrapper") Wrapper wrapper, Model model){
        wrapper.getMapPoiRequest().setPage(wrapper.getPage());
        model.addAttribute("pois", mapPoiService.findAll(wrapper.getMapPoiRequest()));
        model.addAttribute("mapPoiRequest", wrapper.getMapPoiRequest());

        return "poi/list";
    }

    @RequestMapping(value = "findEntidad", method = RequestMethod.POST)
    public ResponseEntity<List<MapPoiEntidad>> findByMapPoiSector(@RequestParam String poiSectorDescripciones, Model model){
        List<String> listPoiSectorDescripciones = Stream.of(poiSectorDescripciones.split(",")).collect(Collectors.toList());

        List<MapPoiEntidad> list = mapPoiEntidadService.findByMapPoiSector_DescripcionIn(listPoiSectorDescripciones);
        model.addAttribute("poiEntidades", list);

        return new ResponseEntity<>( list , HttpStatus.OK);
    }

    @ModelAttribute("mapPoiRequest")
    public MapPoiRequest mapPoiRequest(){
        return new MapPoiRequest();
    }

    @ModelAttribute("poiEntidades")
    public List<MapPoiEntidad> poiEntidades(){
        return mapPoiEntidadService.findAll();
    }

    @ModelAttribute("poiSectores")
    public List<MapPoiSector> poiSectores(){
        return mapPoiSectorService.findAll();
    }

    @ModelAttribute("provincias")
    public List<MapProvincia> provincias(){
        return mapProvinciaService.findAll();
    }

    @ModelAttribute("mapPoi")
    public MapPoi get() {
        return new MapPoi();
    }

    @ModelAttribute("myWrapper")
    public Wrapper wrapper(){
        return new Wrapper();
    }

}