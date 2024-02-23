package com.ideaas.web.controller;

import com.ideaas.services.domain.MapPoi;
import com.ideaas.services.domain.MapPoiEntidad;
import com.ideaas.services.domain.MapPoiSector;
import com.ideaas.services.service.interfaces.MapPoiEntidadService;
import com.ideaas.services.service.interfaces.MapPoiSectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("poiEntidad")
@Secured({"ROLE_ADMINISTRADOR"})
public class PoiEntidadController{

    private MapPoiEntidadService poiEntidadService;
    private MapPoiSectorService poiSectorService;

    private static final Boolean INACTIVE = true;


    @Autowired
    public PoiEntidadController(MapPoiEntidadService poiEntidadService, MapPoiSectorService poiSectorService) {
        this.poiEntidadService = poiEntidadService;
        this.poiSectorService = poiSectorService;
    }

    @GetMapping("{id}")
    public String show(@PathVariable Long id, Model model) {
        MapPoiEntidad poiEntidades = poiEntidadService.get(id);

        model.addAttribute("poiEntidades", poiEntidades);

        return "poiEntidad/show";
    }

    @GetMapping("list")
    public String findAll(Model model){
        List<MapPoiEntidad> poiEntidades = poiEntidadService.findAll();

        model.addAttribute("poiEntidades", poiEntidades);

        return "poiEntidad/list";
    }

    @GetMapping("create")
    public String create(Model model) {
        List<MapPoiSector> poiSectores = poiSectorService.findAll();
        model.addAttribute("poiSectores", poiSectores);
        return "poiEntidad/create";
    }

    FieldError emptyDescripcionPoiEntidad = new FieldError(
            "mapPoiEntidad" , "descripcion" , "Debes completar este campo"
    );

    @RequestMapping(value = "save" , method = RequestMethod.POST)
    public String save(@ModelAttribute MapPoiEntidad poiEntidad, BindingResult bindingResult, RedirectAttributes redirectAttributes){

        if(poiEntidad.getDescripcion().isEmpty()){
            bindingResult.addError(emptyDescripcionPoiEntidad);
        }

        if(bindingResult.hasErrors()) {
            return "poiEntidad/create";
        }

        poiEntidadService.save(poiEntidad);
        redirectAttributes.addAttribute("id", poiEntidad.getId());
        return "redirect:/poiEntidad/{id}";
    }

    @RequestMapping("update")
    public String update(@RequestParam Long id, Model model) {
        MapPoiEntidad mapPoiEntidad = poiEntidadService.get(id);
        model.addAttribute("updatePoiEntidad", mapPoiEntidad);
        return "poiEntidad/update";
    }

    @RequestMapping("dropBajaLogica")
    public String dropBajaLogica(@RequestParam Long id, RedirectAttributes redirectAttributes){
        MapPoiEntidad mapPoiEntidad = poiEntidadService.get(id);
        mapPoiEntidad.setBajaLogica(INACTIVE);
        poiEntidadService.save(mapPoiEntidad);
        redirectAttributes.addAttribute("id", mapPoiEntidad.getId());

        return "redirect:/poiEntidad/{id}";
    }

    @RequestMapping("upBajaLogica")
    public String upBajaLogica(@RequestParam Long id, RedirectAttributes redirectAttributes){
        MapPoiEntidad mapPoiEntidad = poiEntidadService.get(id);
        mapPoiEntidad.setBajaLogica(!INACTIVE);
        poiEntidadService.save(mapPoiEntidad);
        redirectAttributes.addAttribute("id", mapPoiEntidad.getId());

        return "redirect:/poiEntidad/{id}";
    }

    @ModelAttribute("poiSectores")
    public List<MapPoiSector> poiSectores(){
        return poiSectorService.findAll();
    }

    @ModelAttribute("mapPoiEntidad")
    public MapPoiEntidad get(){
        return new MapPoiEntidad();
    }


}