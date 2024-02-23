package com.ideaas.web.controller;

import com.ideaas.services.bean.Wrapper;
import com.ideaas.services.bean.WrapperRequest;
import com.ideaas.services.domain.MapUbicacionActualizacion;
import com.ideaas.services.domain.MapUbicacionActualizacionPK;
import com.ideaas.services.request.UbicacionActualizacionRequest;
import com.ideaas.services.service.interfaces.MapUbicacionActualizacionService;
import com.ideaas.services.service.interfaces.MapUbicacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("ubicacionActualizacion")
@Secured({"ROLE_ADMINISTRADOR"})
public class UbicacionActualizacionController {

    private MapUbicacionActualizacionService mapUbicacionActualizacionService;
    private MapUbicacionService mapUbicacionService;
    DateTimeFormatter FOMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    @Autowired
    public UbicacionActualizacionController(MapUbicacionActualizacionService mapUbicacionActualizacionService, MapUbicacionService mapUbicacionService) {
        this.mapUbicacionActualizacionService = mapUbicacionActualizacionService;
        this.mapUbicacionService = mapUbicacionService;
    }

    @RequestMapping("show")
    public String show(@RequestParam Long id, @RequestParam String fechaOperacion, Model model) {

        LocalDateTime dateTime = LocalDateTime.parse(fechaOperacion, FOMATTER);

        MapUbicacionActualizacion mapUbicacionActualizacion = mapUbicacionActualizacionService.get(new MapUbicacionActualizacionPK(id,dateTime));

        model.addAttribute("mapUbicacionActualizacion", mapUbicacionActualizacion);

        return "ubicacionActualizacion/show";
    }

    @RequestMapping("listComplete")
    public String findAll(Model model){
        List<MapUbicacionActualizacion> ubicacionActualizaciones = mapUbicacionActualizacionService.findAll();

        model.addAttribute("ubicacionActualizaciones", ubicacionActualizaciones);

        return "ubicacionActualizacion/list";
    }

    @RequestMapping("list")
    public String findAll(@RequestParam(defaultValue = "10") Integer size,
                          @RequestParam(defaultValue = "0") Integer page, Model model) {
        List<MapUbicacionActualizacion> ubicacionActualizaciones = mapUbicacionActualizacionService.findAll(size, page, "id.mapUbicacionId");

        model.addAttribute("ubicacionActualizaciones", ubicacionActualizaciones);
        model.addAttribute("page" , page);

        return "ubicacionActualizacion/list";
    }


    @RequestMapping("create")
    public String create() {
        return "ubicacionActualizacion/create";
    }

    FieldError idUbicacionNotExist = new FieldError(
            "mapUbicacionActualizacion" , "id.mapUbicacionId" , "El idUbicacion ingresado no existe"
    );

    FieldError emptyIdUbicacion = new FieldError(
            "mapUbicacionActualizacion" , "id.mapUbicacionId" , "Debes completar este campo"
    );

    @RequestMapping(value = "save" , method = RequestMethod.POST)
    public String save(@ModelAttribute MapUbicacionActualizacion mapUbicacionActualizacion, BindingResult bindingResult, RedirectAttributes redirectAttributes){

        if(!mapUbicacionService.findById(mapUbicacionActualizacion.getId().getMapUbicacionId()).isPresent()){
            bindingResult.addError(idUbicacionNotExist);
        }
        if(bindingResult.hasErrors()) {
            return "ubicacionActualizacion/create";
        }

        LocalDateTime now = LocalDateTime.now();
        now = now.truncatedTo(ChronoUnit.SECONDS);

        MapUbicacionActualizacionPK id = new MapUbicacionActualizacionPK( mapUbicacionActualizacion.getId().getMapUbicacionId() , now);
        mapUbicacionActualizacion.setId(id);
        mapUbicacionActualizacion.setFechaAlta(now);
        mapUbicacionActualizacionService.save(mapUbicacionActualizacion);

        redirectAttributes.addAttribute("id", mapUbicacionActualizacion.getId().getMapUbicacionId());
        String fechaOperacion = FOMATTER.format(now);
        redirectAttributes.addAttribute("fechaOperacion", fechaOperacion);

        return "redirect:/ubicacionActualizacion/show";
    }

    @RequestMapping("update")
    public String update(@RequestParam Long id,@RequestParam String fechaOperacion, Model model) {

        LocalDateTime dateTime = LocalDateTime.parse(fechaOperacion, FOMATTER);

        MapUbicacionActualizacion mapUbicacionActualizacion = mapUbicacionActualizacionService.get(new MapUbicacionActualizacionPK( id , dateTime));

        model.addAttribute("updateUbicacionActualizacion", mapUbicacionActualizacion);
        return "ubicacionActualizacion/update";
    }

    @SuppressWarnings("Duplicates")
    @RequestMapping("dropState")
    public String dropState(@RequestParam Long id, @RequestParam String fechaOperacion, RedirectAttributes redirectAttributes){

        LocalDateTime dateTime = LocalDateTime.parse(fechaOperacion, FOMATTER);

        MapUbicacionActualizacion mapUbicacionActualizacion = mapUbicacionActualizacionService.get(new MapUbicacionActualizacionPK( id , dateTime));
        mapUbicacionActualizacion.setOperacion("B");
        mapUbicacionActualizacionService.save(mapUbicacionActualizacion);

        redirectAttributes.addAttribute("id", mapUbicacionActualizacion.getId().getMapUbicacionId());
        redirectAttributes.addAttribute("fechaOperacion", fechaOperacion);

        return "redirect:/ubicacionActualizacion/show";
    }

    @SuppressWarnings("Duplicates")
    @RequestMapping("upState")
    public String upState(@RequestParam Long id, @RequestParam String fechaOperacion, RedirectAttributes redirectAttributes){

        LocalDateTime dateTime = LocalDateTime.parse(fechaOperacion, FOMATTER);

        MapUbicacionActualizacion mapUbicacionActualizacion = mapUbicacionActualizacionService.get(new MapUbicacionActualizacionPK( id , dateTime));
        mapUbicacionActualizacion.setOperacion("A");
        mapUbicacionActualizacionService.save(mapUbicacionActualizacion);

        redirectAttributes.addAttribute("id", mapUbicacionActualizacion.getId().getMapUbicacionId());
        redirectAttributes.addAttribute("fechaOperacion", fechaOperacion);

        return "redirect:/ubicacionActualizacion/show";
    }

    @RequestMapping(value = "editUbicacionActualizacion" , method = RequestMethod.POST)
    public String edit(@ModelAttribute MapUbicacionActualizacion ubicacionActualizacion, RedirectAttributes redirectAttributes){

        mapUbicacionActualizacionService.save(ubicacionActualizacion);

        redirectAttributes.addAttribute("id", ubicacionActualizacion.getId().getMapUbicacionId());

        String fechaOperacion = FOMATTER.format(ubicacionActualizacion.getId().getFechaOperacion());
        redirectAttributes.addAttribute("fechaOperacion", fechaOperacion);

        return "redirect:/ubicacionActualizacion/show";
    }

    @RequestMapping(value = "search", params = "paginate")
    public String listPaginated(@ModelAttribute("myWrapper") Wrapper wrapper, Model model){
        wrapper.getUbicacionActualizacionRequest().setPage(wrapper.getPage());
        model.addAttribute("ubicacionActualizaciones", mapUbicacionActualizacionService.findAll(wrapper.getUbicacionActualizacionRequest()));
        model.addAttribute("ubicacionActualizacionRequest", wrapper.getUbicacionActualizacionRequest());

        return "ubicacionActualizacion/list";
    }


    @ModelAttribute("mapUbicacionActualizacion")
    public MapUbicacionActualizacion get(){return new MapUbicacionActualizacion();}

    @ModelAttribute("ubicacionActualizacionRequest")
    public UbicacionActualizacionRequest ubicacionActualizacionRequest(){
        return new UbicacionActualizacionRequest();
    }

    @ModelAttribute("myWrapper")
    public Wrapper wrapper(){
        return new Wrapper();
    }
}
