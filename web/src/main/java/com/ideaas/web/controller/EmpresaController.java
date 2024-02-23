package com.ideaas.web.controller;


import com.ideaas.services.domain.MapEmpresa;
import com.ideaas.services.service.interfaces.MapEmpresaService;
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
@RequestMapping("empresa")
@Secured({"ROLE_ADMINISTRADOR"})
public class EmpresaController {

    private static final Boolean INACTIVE = true;

    private MapEmpresaService empresaService;

    @Autowired
    public EmpresaController(MapEmpresaService empresaService) {
        this.empresaService = empresaService;
    }

    @GetMapping("{id}")
    public String show(@PathVariable Long id, Model model) {
        MapEmpresa empresa = empresaService.get(id);

        model.addAttribute("empresa", empresa);

        return "empresa/show";
    }

    @GetMapping("list")
    public String findAll(Model model){
        List<MapEmpresa> empresas = empresaService.findAll();
        model.addAttribute("empresas", empresas);

        return "empresa/list";
    }

    @GetMapping("create")
    public String create() {
        return "empresa/create";
    }


    FieldError emptyDescripcionEmpresa = new FieldError(
            "mapEmpresa" , "descripcion" , "Debes completar este campo"
    );

    @RequestMapping(value = "save" , method = RequestMethod.POST)
    public String save(@ModelAttribute MapEmpresa empresa, BindingResult bindingResult, RedirectAttributes redirectAttributes){

        if(empresa.getDescripcion().isEmpty()){
            bindingResult.addError(emptyDescripcionEmpresa);

        }

        if(bindingResult.hasErrors()) {
            return "empresa/create";
        }

        empresaService.save(empresa);
        redirectAttributes.addAttribute("id", empresa.getId());

        return "redirect:/empresa/{id}";
    }

    @PutMapping("editEmpresa")
    public String edit(@ModelAttribute MapEmpresa empresa, RedirectAttributes redirectAttributes){
        empresaService.save(empresa);
        redirectAttributes.addAttribute("id", empresa.getId());

        return "redirect:/{id}";
    }

    @RequestMapping("update")
    public String update(@RequestParam Long id, Model model) {
        MapEmpresa mapEmpresa = empresaService.get(id);
        model.addAttribute("updateEmpresa", mapEmpresa);
        return "empresa/update";
    }

    @RequestMapping("dropBajaLogica")
    public String dropBajaLogica(@RequestParam Long id, Model model, RedirectAttributes redirectAttributes){
        MapEmpresa empresa = empresaService.get(id);
        empresa.setBajaLogica(INACTIVE);
        empresaService.save(empresa);
        redirectAttributes.addAttribute("id", empresa.getId());

        return "redirect:/empresa/{id}";
    }

    @RequestMapping("upBajaLogica")
    public String upBajaLogica(@RequestParam Long id, Model model, RedirectAttributes redirectAttributes){
        MapEmpresa empresa = empresaService.get(id);
        empresa.setBajaLogica(!INACTIVE);
        empresaService.save(empresa);
        redirectAttributes.addAttribute("id", empresa.getId());

        return "redirect:/empresa/{id}";
    }

    @ModelAttribute("mapEmpresa")
    public MapEmpresa get(){
        return new MapEmpresa();
    }

}
