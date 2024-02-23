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
@RequestMapping("poiSector")
@Secured({"ROLE_ADMINISTRADOR"})
public class PoiSectorController{

    private MapPoiSectorService poiSectorService;

    private static final Boolean INACTIVE = true;

    @Autowired
    public PoiSectorController(MapPoiSectorService poiSectorService) {
        this.poiSectorService = poiSectorService;
    }

    @GetMapping("{id}")
    public String show(@PathVariable Long id, Model model) {
        MapPoiSector poiSector = poiSectorService.get(id);

        model.addAttribute("poiSector", poiSector);

        return "poiSector/show";
    }

    @GetMapping("list")
    public String findAll(Model model){
        List<MapPoiSector> poiSectores = poiSectorService.findAll();

        model.addAttribute("poiSectores", poiSectores);

        return "poiSector/list";
    }

    @GetMapping("create")
    public String create() {
        return "poiSector/create";
    }

    FieldError emptyDescripcionPoiSector = new FieldError(
            "mapPoiSector" , "descripcion" , "Debes completar este campo"
    );

    @RequestMapping(value = "save" , method = RequestMethod.POST)
    public String save(@ModelAttribute MapPoiSector poiSector, BindingResult bindingResult, RedirectAttributes redirectAttributes){

        if(poiSector.getDescripcion().isEmpty()){
            bindingResult.addError(emptyDescripcionPoiSector);
        }

        if(bindingResult.hasErrors()) {
            return "poiSector/create";
        }

        poiSectorService.save(poiSector);
        redirectAttributes.addAttribute("id", poiSector.getId());
        return "redirect:/poiSector/{id}";
    }

    @RequestMapping("update")
    public String update(@RequestParam Long id, Model model) {
       MapPoiSector mapPoiSector = poiSectorService.get(id);
        model.addAttribute("updatePoiSector", mapPoiSector);
        return "poiSector/update";
    }

    @RequestMapping("dropBajaLogica")
    public String dropBajaLogica(@RequestParam Long id, RedirectAttributes redirectAttributes){
        MapPoiSector mapPoiSector = poiSectorService.get(id);
        mapPoiSector.setBajaLogica(INACTIVE);
        poiSectorService.save(mapPoiSector);
        redirectAttributes.addAttribute("id", mapPoiSector.getId());

        return "redirect:/poiSector/{id}";
    }

    @RequestMapping("upBajaLogica")
    public String upBajaLogica(@RequestParam Long id, RedirectAttributes redirectAttributes){
        MapPoiSector mapPoiSector = poiSectorService.get(id);
        mapPoiSector.setBajaLogica(!INACTIVE);
        poiSectorService.save(mapPoiSector);
        redirectAttributes.addAttribute("id", mapPoiSector.getId());

        return "redirect:/poiSector/{id}";
    }

    @ModelAttribute("mapPoiSector")
    public MapPoiSector get(){
        return new MapPoiSector();
    }

}