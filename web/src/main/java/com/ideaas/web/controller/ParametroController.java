package com.ideaas.web.controller;

import com.ideaas.services.domain.Parametro;
import com.ideaas.services.service.interfaces.ParametroService;
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
@RequestMapping("parametro")
@Secured({"ROLE_ADMINISTRADOR", "ROLE_ADMINISTRADOR_OOH"})
public class ParametroController {

    private ParametroService parametroService;

    @Autowired
    public ParametroController(ParametroService parametroService) {
        this.parametroService = parametroService;
    }

    @GetMapping("{id}")
    public String show(@PathVariable Long id, Model model) {
        Parametro parametro = this.parametroService.get(id);
        model.addAttribute("parametro", parametro);

        return "parametro/show";
    }

    @GetMapping("list")
    public String findAll(Model model) {
        List<Parametro> parametros =  this.parametroService.findAll();
        model.addAttribute("parametros", parametros);

        return "parametro/list";
    }

    @GetMapping("create")
    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR_OOH')")
    public String create() {
        return "parametro/create";
    }


    FieldError emptyNombreParametro = new FieldError(
            "parametro" , "nombre" , "Debes completar este campo"
    );

    @RequestMapping(value = "save" , method = RequestMethod.POST)
    public String save(@ModelAttribute Parametro parametro, BindingResult bindingResult, RedirectAttributes redirectAttributes){

        if(parametro.getNombre().isEmpty()){
            bindingResult.addError(emptyNombreParametro);
        }

        if(bindingResult.hasErrors()) {
            return "parametro/create";
        }

        parametroService.save(parametro);
        redirectAttributes.addAttribute("id", parametro.getIdParametro());

        return "redirect:/parametro/{id}";
    }

    @PutMapping("editParametro")
    public String edit(@ModelAttribute Parametro parametro, RedirectAttributes redirectAttributes){
        parametroService.save(parametro);
        redirectAttributes.addAttribute("id", parametro.getIdParametro());

        return "redirect:/{id}";
    }

    @RequestMapping("update")
    public String update(@RequestParam Long id, Model model) {
        Parametro parametro = this.parametroService.get(id);

        model.addAttribute("updateParametro", parametro);
        return "parametro/update";
    }

    @RequestMapping("delete")
    public String delete(@RequestParam Long id, Model model) {
        this.parametroService.delete(id);

        return "redirect:/parametro/list";
    }

    @ModelAttribute("parametro")
    public Parametro get(){
        return new Parametro();
    }

}
