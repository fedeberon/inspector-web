package com.ideaas.web.controller;

import com.ideaas.services.domain.MaintenanceScreenEnable;
import com.ideaas.services.service.interfaces.MaintenanceScreenEnableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("mantenimiento")
@Secured({"ROLE_ADMINISTRADOR"})
public class MantenimientoController {

    private MaintenanceScreenEnableService mantenimientoService;
    private static final Boolean INACTIVE = false;

    @Autowired
    public MantenimientoController(MaintenanceScreenEnableService mantenimientoService) {
        this.mantenimientoService = mantenimientoService;
    }


    @RequestMapping("list")
    public String findAll(Model model){
        List<MaintenanceScreenEnable> mantenimientos = mantenimientoService.findAll();

        model.addAttribute("mantenimientos", mantenimientos);

        return "mantenimiento/list";
    }

    @RequestMapping("dropStatus")
    public String dropBajaLogica(@RequestParam Long id){
        MaintenanceScreenEnable maintenanceScreenEnable = mantenimientoService.get(id);
        maintenanceScreenEnable.setStatus(INACTIVE);
        mantenimientoService.save(maintenanceScreenEnable);

        return "redirect:/mantenimiento/list";
    }

    @RequestMapping("upStatus")
    public String updateBajaLogica(@RequestParam Long id){
        MaintenanceScreenEnable maintenanceScreenEnable = mantenimientoService.get(id);
        maintenanceScreenEnable.setStatus(!INACTIVE);
        mantenimientoService.save(maintenanceScreenEnable);

        return "redirect:/mantenimiento/list";
    }
}
