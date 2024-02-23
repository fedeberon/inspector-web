package com.ideaas.web.controller;


import com.ideaas.services.domain.MapMedio;
import com.ideaas.services.domain.MapProvincia;
import com.ideaas.services.service.interfaces.MapMedioService;
import com.ideaas.services.service.interfaces.MapProvinciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("provincia")
@Secured({"ROLE_ADMINISTRADOR", "ROLE_ADMINISTRADOR_OOH"})
public class ProvinciaController {

    private MapProvinciaService provinciaService;

    private static final Boolean INACTIVE = true;

    @Autowired
    public ProvinciaController(MapProvinciaService provinciaService) {
        this.provinciaService = provinciaService;
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR', 'ROLE_ADMINISTRADOR_OOH')")
    public String show(@PathVariable Long id, Model model) {
        MapProvincia provincia = provinciaService.get(id);

        model.addAttribute("provincia", provincia);

        return "provincia/show";
    }

    @GetMapping("list")
    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR', 'ROLE_ADMINISTRADOR_OOH')")
    public String findAll(Model model){
        List<MapProvincia> provincias = provinciaService.findAll();

        model.addAttribute("provincias", provincias);

        return "provincia/list";
    }

    @GetMapping("create")
    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
    public String create() {
        return "provincia/create";
    }

    FieldError emptyEvaluaProvincia = new FieldError(
            "mapProvincia" , "evalua" , "Debes completar este campo"
    );
    @RequestMapping(value = "save" , method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
    public String save(@ModelAttribute MapProvincia provincia, BindingResult bindingResult, RedirectAttributes redirectAttributes){

        if(provincia.getEvalua() == null){
            bindingResult.addError(emptyEvaluaProvincia);
        }

        if(bindingResult.hasErrors()) {
            return "provincia/create";
        }

        provinciaService.save(provincia);
        redirectAttributes.addAttribute("id", provincia.getId());

        return "redirect:/provincia/{id}";
    }

    @RequestMapping("update")
    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
    public String update(@RequestParam Long id, Model model) {
        MapProvincia mapProvincia = provinciaService.get(id);
        model.addAttribute("updateProvincia", mapProvincia);
        return "provincia/update";
    }

    @RequestMapping("dropBajaLogica")
    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
    public String dropBajaLogica(@RequestParam Long id, RedirectAttributes redirectAttributes){
        MapProvincia mapProvincia = provinciaService.get(id);
        mapProvincia.setBajaLogica(INACTIVE);
        provinciaService.save(mapProvincia);
        redirectAttributes.addAttribute("id", mapProvincia.getId());

        return "redirect:/provincia/{id}";
    }

    @RequestMapping("upBajaLogica")
    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
    public String upBajaLogica(@RequestParam Long id, RedirectAttributes redirectAttributes){
        MapProvincia mapProvincia = provinciaService.get(id);
        mapProvincia.setBajaLogica(!INACTIVE);
        provinciaService.save(mapProvincia);
        redirectAttributes.addAttribute("id", mapProvincia.getId());

        return "redirect:/provincia/{id}";
    }

    @ModelAttribute ("mapProvincia")
    public MapProvincia get() {return new MapProvincia();}

}