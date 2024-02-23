package com.ideaas.web.controller;

import com.ideaas.services.bean.FormRequest;
import com.ideaas.services.domain.AppFormulario;
import com.ideaas.services.domain.AppUbicacionRelevamiento;
import com.ideaas.services.service.interfaces.AppFormularioService;
import com.ideaas.services.service.interfaces.AppUbicacionRelService;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.StringWriter;
import java.util.List;

@Controller
@RequestMapping("formulario")
@Secured({"ROLE_ADMINISTRADOR"})
public class AppFormularioController {

    private AppFormularioService appFormularioService;

    public AppFormularioController(AppFormularioService appFormularioService) {
        this.appFormularioService = appFormularioService;
    }

    private static final Boolean INACTIVE = true;

    @GetMapping("{id}")
    public String show(@PathVariable Long id, Model model){
        AppFormulario formulario = appFormularioService.get(id);

        model.addAttribute("formulario", formulario);

        return "formulario/show";
    }

    @GetMapping("list")
    public String findAll(Model model) {
        List<AppFormulario> formularios = appFormularioService.findAllByBajaLogica(false);
        model.addAttribute("formularios", formularios);

        return "formulario/list";
    }


    @GetMapping("create")
    public String create() {
        return "formulario/create";
    }

    FieldError emptyFormName = new FieldError(
            "appFormulario" , "nombreFormulario" , "Debes completar este campo"
    );
    FieldError emptyForm = new FieldError(
            "appFormulario" , "itemsFormulario" , "Por favor, cree los items del formulario"
    );
    @RequestMapping(value = "save" , method = RequestMethod.POST)
    public String save(@ModelAttribute AppFormulario formulario, BindingResult bindingResult, RedirectAttributes redirectAttributes){

        if(formulario.getNombreFormulario().isEmpty()){
            bindingResult.addError(emptyFormName);
        }
        if(formulario.getFormulario().isEmpty()){
            bindingResult.addError(emptyForm);
        }
        if(bindingResult.hasErrors()) {
            return "formulario/create";
        }

        appFormularioService.save(formulario);
        redirectAttributes.addAttribute("id", formulario.getId());


        return "redirect:/formulario/{id}";

    }

    @RequestMapping("update")
    public String update(@RequestParam Long id, Model model) {
        AppFormulario formulario = appFormularioService.get(id);
        model.addAttribute("updateFormulario", formulario);

        return "formulario/update";
    }

    @RequestMapping(value = "saveUpdate" , method = RequestMethod.POST)
    public String update(@ModelAttribute AppFormulario formulario, RedirectAttributes redirectAttributes){
        appFormularioService.save(formulario);
        redirectAttributes.addAttribute("id", formulario.getId());

        return "redirect:/formulario/{id}";
    }

    @RequestMapping("/bajaLogica")
    public String bajaLogica(@RequestParam Long id){
        AppFormulario appFormulario = appFormularioService.get(id);
        appFormulario.setBajaLogicaFormulario(INACTIVE);
        appFormularioService.save(appFormulario);

        return "redirect:/formulario/list";
    }

    @ModelAttribute("appFormulario")
    public AppFormulario get(){
        return new AppFormulario();
    }

}
