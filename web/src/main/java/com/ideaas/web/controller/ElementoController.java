package com.ideaas.web.controller;

import com.ideaas.services.domain.MapElemento;
import com.ideaas.services.domain.MapFormato;
import com.ideaas.services.domain.MapMedio;
import com.ideaas.services.service.interfaces.MapElementoService;
import com.ideaas.services.service.interfaces.MapFormatoService;
import com.ideaas.services.service.interfaces.MapMedioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("elemento")
@Secured({"ROLE_ADMINISTRADOR", "ROLE_ADMINISTRADOR_OOH"})
public class ElementoController {

    private MapElementoService elementoService;
    private MapMedioService mapMedioService;
    private MapFormatoService mapFormatoService;

    private static final Boolean INACTIVE = true;


    @Autowired
    public ElementoController(MapElementoService elementoService, MapMedioService mapMedioService, MapFormatoService mapFormatoService) {

        this.elementoService = elementoService;
        this.mapMedioService = mapMedioService;
        this.mapFormatoService = mapFormatoService;
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR', 'ROLE_ADMINISTRADOR_OOH')")
    public String show(@PathVariable Long id, Model model) {
        MapElemento elemento = elementoService.get(id);

        model.addAttribute("elemento", elemento);

        return "elemento/show";
    }

    @GetMapping ("list")
    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR', 'ROLE_ADMINISTRADOR_OOH')")
    public String findAll(Model model){
        List<MapElemento> elementos = elementoService.findAll();
        model.addAttribute("elementos", elementos);

        return "elemento/list";
    }

    @GetMapping("create")
    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
    public String create(Model model) {
        List<MapMedio> medios = mapMedioService.findAll();
        List<MapFormato> formatos = mapFormatoService.findAll();
        model.addAttribute("formatos", formatos);
        model.addAttribute("medios", medios);

        return "elemento/create";
    }

    FieldError emptyDescripcionElemento = new FieldError(
            "mapElemento" , "descripcion" , "Debes completar este campo"
    );
    FieldError emptyAcumulaDatosElemento = new FieldError(
            "mapElemento" , "acumulaDatos" , "Debes completar este campo"
    );
    FieldError emptyEvaluaElemento = new FieldError(
            "mapElemento" , "evalua" , "Debes completar este campo"
    );

    @RequestMapping(value = "save" , method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
    public String save(@ModelAttribute (name = "mapElemento") MapElemento elemento, BindingResult bindingResult, RedirectAttributes redirectAttributes){

        if(elemento.getDescripcion().isEmpty()){
            bindingResult.addError(emptyDescripcionElemento);

        }
        if(elemento.getAcumulaDatos() == null){
            bindingResult.addError(emptyAcumulaDatosElemento);

        }
        if(elemento.getEvalua() == null){
            bindingResult.addError(emptyEvaluaElemento);

        }

        if(bindingResult.hasErrors()) {
            return "elemento/create";
        }

        elemento.setFechaAlta(LocalDateTime.now());
        elementoService.save(elemento);
        redirectAttributes.addAttribute("id", elemento.getId());

        return "redirect:/elemento/{id}";
    }

    @RequestMapping(value = "editElemento" , method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
    public String edit(@ModelAttribute MapElemento elemento, RedirectAttributes redirectAttributes){
        elementoService.save(elemento);
        redirectAttributes.addAttribute("id", elemento.getId());

        return "redirect:/elemento/{id}";
    }

    @RequestMapping("update")
    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
    public String update(@RequestParam Long id, Model model) {
        MapElemento mapElemento = elementoService.get(id);
        model.addAttribute("updateElemento", mapElemento);
        return "elemento/update";
    }

    @RequestMapping("dropBajaLogica")
    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
    public String dropBajaLogica(@RequestParam Long id, Model model, RedirectAttributes redirectAttributes){
        MapElemento mapElemento = elementoService.get(id);
        mapElemento.setBajaLogica(INACTIVE);
        elementoService.save(mapElemento);
        redirectAttributes.addAttribute("id", mapElemento.getId());

        return "redirect:/elemento/{id}";
    }

    @RequestMapping("upBajaLogica")
    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
    public String upBajaLogica(@RequestParam Long id, Model model, RedirectAttributes redirectAttributes){
        MapElemento mapElemento = elementoService.get(id);
        mapElemento.setBajaLogica(!INACTIVE);
        elementoService.save(mapElemento);
        redirectAttributes.addAttribute("id", mapElemento.getId());

        return "redirect:/elemento/{id}";
    }

    @ModelAttribute("mapElemento")
    public MapElemento get(){
        return new MapElemento();
    }

    @ModelAttribute("medios")
    public List<MapMedio> medios(){
        return mapMedioService.findAll();
    }

    @ModelAttribute("formatos")
    public List<MapFormato> formatos(){
        return mapFormatoService.findAll();
    }

}