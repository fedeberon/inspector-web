package com.ideaas.web.controller;

import com.ideaas.services.domain.MapCliente;
import com.ideaas.services.service.interfaces.MapClienteService;
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
import java.util.regex.Pattern;

@Controller
@RequestMapping("cliente")
@Secured({"ROLE_ADMINISTRADOR", "ROLE_ADMINISTRADOR_OOH"})
public class ClienteController {

    private MapClienteService mapClienteService;

    @Autowired
    public ClienteController(MapClienteService mapClienteService) {
        this.mapClienteService = mapClienteService;
    }

    @GetMapping("{id}")
    public String show(@PathVariable Long id, Model model) {
        MapCliente mapCliente = this.mapClienteService.get(id);
        model.addAttribute("cliente", mapCliente);

        return "cliente/show";
    }

    @GetMapping("list")
    public String findAll(Model model) {
        List<MapCliente> mapClientes =  this.mapClienteService.findAll();
        model.addAttribute("clientes", mapClientes);

        return "cliente/list";
    }

    @GetMapping("create")
    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR_OOH')")
    public String create() {
        return "cliente/create";
    }


    FieldError emptyNombreCliente = new FieldError(
            "cliente" , "nombre" , "Debes completar este campo"
    );

    FieldError phoneNumberNotContainNumbers
            = new FieldError(
            "MapCliente" , "telefono" , "El número de teléfono solo puede contener números"
    );

    @RequestMapping(value = "save" , method = RequestMethod.POST)
    public String save(@ModelAttribute MapCliente mapCliente, Model model, BindingResult bindingResult, RedirectAttributes redirectAttributes){

        if(mapCliente.getNombre().isEmpty()){
            bindingResult.addError(emptyNombreCliente);
        }

        if(!mapCliente.getTelefono().isEmpty()&&!Pattern.compile("^\\d*$").matcher(mapCliente.getTelefono()).find()){
            bindingResult.addError(phoneNumberNotContainNumbers);
        }

        if(bindingResult.hasErrors()) {
            model.addAttribute("cliente", mapCliente);
            return "cliente/create";
        }

        mapClienteService.save(mapCliente);
        redirectAttributes.addAttribute("id", mapCliente.getId());

        return "redirect:/cliente/{id}";
    }

    @PutMapping("editCliente")
    public String edit(@ModelAttribute MapCliente mapCliente, Model model, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if(mapCliente.getNombre().isEmpty()){
            bindingResult.addError(emptyNombreCliente);
        }

        if(!mapCliente.getTelefono().isEmpty()&&!Pattern.compile("^\\d*$").matcher(mapCliente.getTelefono()).find()){
            bindingResult.addError(phoneNumberNotContainNumbers);
        }

        if(bindingResult.hasErrors()) {
            model.addAttribute("updateCliente", mapCliente);
            return "cliente/update";
        }
        mapClienteService.save(mapCliente);
        redirectAttributes.addAttribute("id", mapCliente.getId());

        return "redirect:/{id}";
    }

    @RequestMapping("update")
    public String update(@RequestParam Long id, Model model) {
        MapCliente mapCliente = this.mapClienteService.get(id);

        model.addAttribute("updateCliente", mapCliente);
        return "cliente/update";
    }

    @RequestMapping("delete")
    public String delete(@RequestParam Long id) {
        this.mapClienteService.delete(id);

        return "redirect:/cliente/list";
    }

    @ModelAttribute("cliente")
    public MapCliente get(){
            return new MapCliente();
        }

    }

