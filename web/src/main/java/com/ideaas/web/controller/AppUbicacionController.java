package com.ideaas.web.controller;

import com.ideaas.services.bean.Wrapper;
import com.ideaas.services.domain.*;
import com.ideaas.services.service.interfaces.*;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("appUbicacion")
@Secured({"ROLE_ADMINISTRADOR"})
public class AppUbicacionController {

    private AppUbicacionService appUbicacionService;
    private AppRelevamientoService appRelevamientoService;
    private AppRespuestaService appRespuestaService;

    public AppUbicacionController(AppUbicacionService appUbicacionService, AppRelevamientoService appRelevamientoService, AppRespuestaService appRespuestaService) {
        this.appUbicacionService = appUbicacionService;
        this.appRelevamientoService = appRelevamientoService;
        this.appRespuestaService = appRespuestaService;
    }

    @GetMapping("list")
    public String list(Model model){
        List<AppUbicacion> ubicaciones = appUbicacionService.findAllByBajaLogica(false);
        model.addAttribute("ubicaciones", ubicaciones );

        return "appUbicacion/list";
    }

    @GetMapping("{id}")
    public String show(@PathVariable Long id, Model model){
        AppUbicacion ubicacion = appUbicacionService.get(id);

        model.addAttribute("appUbicacion", ubicacion);

        return "appUbicacion/show";
    }

    @RequestMapping("update")
    public String update(@RequestParam Long id, Model model) {
        AppUbicacion ubicacion = appUbicacionService.get(id);

        model.addAttribute("updateAppUbicacion", ubicacion);

        return "appUbicacion/update";
    }

    @RequestMapping(value = "saveUpdate" , method = RequestMethod.POST)
    public String saveUpdate(@ModelAttribute AppUbicacion ubicacion, RedirectAttributes redirectAttributes){
        appUbicacionService.save(ubicacion);
        redirectAttributes.addAttribute("id", ubicacion.getId());

        return "redirect:/appUbicacion/{id}";
    }

    @RequestMapping(value = "search", params = "maps" , method = RequestMethod.POST)
    public String findAll(@ModelAttribute("myWrapper") Wrapper ubicaciones, Model model){
        model.addAttribute("registros", this.appUbicacionService.findAllByIdIn(ubicaciones.getIdAppUbicacionesListSelected()));

        return "appUbicacion/map";
    }

    @RequestMapping(value = "deleteAll", params = "delete" , method = RequestMethod.POST)
    public String deleteAll(@ModelAttribute("myWrapper") Wrapper ubicaciones){

        List<AppUbicacion> appUbicaciones = new ArrayList<>();

        this.appUbicacionService.findAllByIdUbicaciones(ubicaciones.getIdAppUbicacionesListSelected()).forEach(
                ubicacion -> {
                    ubicacion.setBajaLogica(true);
                    AppUbicacion ubi = new AppUbicacion(ubicacion.getId(), ubicacion.getOneClaveUbi(), ubicacion.getAddress(), ubicacion.getBarrio(), ubicacion.getLocalidad(), ubicacion.getProvincia(), ubicacion.getZona(), ubicacion.getLatitud(), ubicacion.getLongitud(), ubicacion.getBajaLogica());
                    appUbicaciones.add(ubi);
                }
        );

        appUbicacionService.saveAll(appUbicaciones);

        return "redirect:/appUbicacion/list";
    }

    @ModelAttribute("appUbicacionRelevamiento")
    public AppUbicacionRelevamiento get(){
        return new AppUbicacionRelevamiento();
    }

    @ModelAttribute("relevamientos")
    public List<AppRelevamiento> relevamientos(){
        return appRelevamientoService.findAll();
    }
}
