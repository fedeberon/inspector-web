package com.ideaas.web.controller;

import com.ideaas.services.domain.MapFormato;
import com.ideaas.services.service.interfaces.MapFormatoService;
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
@RequestMapping("formato")
@Secured({"ROLE_ADMINISTRADOR", "ROLE_ADMINISTRADOR_OOH"})
public class FormatoController{

    private MapFormatoService formatoService;

    private static final Boolean INACTIVE = true;

    @Autowired
    public FormatoController(MapFormatoService formatoService) {
        this.formatoService = formatoService;
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR', 'ROLE_ADMINISTRADOR_OOH')")
    public String show(@PathVariable Long id, Model model) {
        MapFormato formato = formatoService.get(id);

        model.addAttribute("formato", formato);

        return "formato/show";
    }

    @GetMapping("list")
    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR', 'ROLE_ADMINISTRADOR_OOH')")
    public String findAll(Model model){
        List<MapFormato> formatos = formatoService.findAll();

        model.addAttribute("formatos", formatos);

        return "formato/list";
    }

    @GetMapping("create")
    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
    public String create() {
        return "formato/create";
    }


    FieldError emptyDescripcionFormato = new FieldError(
            "mapFormato" , "descripcion" , "Debes completar este campo"
    );
    FieldError emptyEvaluaEmpresa = new FieldError(
            "mapFormato" , "evalua" , "Debes completar este campo"
    );

    @RequestMapping(value = "save" , method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
    public String save(@ModelAttribute(name = "mapFormato") MapFormato formato, BindingResult bindingResult, RedirectAttributes redirectAttributes){

        if(formato.getDescripcion().isEmpty()){
            bindingResult.addError(emptyDescripcionFormato);
        }
        if(formato.getEvalua() == null){
            bindingResult.addError(emptyEvaluaEmpresa);
        }

        if(bindingResult.hasErrors()) {
            return "formato/create";
        }

        formato.setFechaAlta(LocalDateTime.now());
        formatoService.save(formato);
        redirectAttributes.addAttribute("id", formato.getId());

        return "redirect:/formato/{id}";
    }

    @RequestMapping(value = "editFormato" , method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
    public String edit(@ModelAttribute MapFormato formato, RedirectAttributes redirectAttributes){
        formatoService.save(formato);
        redirectAttributes.addAttribute("id", formato.getId());

        return "redirect:/formato/{id}";
    }

    @RequestMapping("update")
    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
    public String update(@RequestParam Long id, Model model) {
        MapFormato mapFormato = formatoService.get(id);
        model.addAttribute("updateFormato", mapFormato);
        return "formato/update";
    }

    @RequestMapping("dropBajaLogica")
    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
    public String dropBajaLogica(@RequestParam Long id, RedirectAttributes redirectAttributes){
        MapFormato mapFormato = formatoService.get(id);
        mapFormato.setBajaLogica(INACTIVE);
        formatoService.save(mapFormato);
        redirectAttributes.addAttribute("id", mapFormato.getId());

        return "redirect:/formato/{id}";
    }

    @RequestMapping("upBajaLogica")
    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
    public String updateBajaLogica(@RequestParam Long id, RedirectAttributes redirectAttributes){
        MapFormato mapFormato = formatoService.get(id);
        mapFormato.setBajaLogica(!INACTIVE);
        formatoService.save(mapFormato);
        redirectAttributes.addAttribute("id", mapFormato.getId());

        return "redirect:/formato/{id}";
    }

    @ModelAttribute("mapFormato")
    public MapFormato get(){
        return new MapFormato();
    }



}