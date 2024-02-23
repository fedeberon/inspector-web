package com.ideaas.web.controller;

import com.ideaas.services.bean.Wrapper;
import com.ideaas.services.domain.MapUbicacionActEspecial;
import com.ideaas.services.domain.MapUbicacionActEspecialPK;
import com.ideaas.services.request.MapUbiActEspecialRequest;
import com.ideaas.services.service.interfaces.MapUbicacionActEspecialService;
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
import java.util.List;

@Controller
@RequestMapping("ubicacionActEspecial")
@Secured({"ROLE_ADMINISTRADOR"})
public class UbicacionActEspecialController {

    private MapUbicacionActEspecialService mapUbicacionActEspecialService;
    private MapUbicacionService mapUbicacionService;
    DateTimeFormatter FOMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    @Autowired
    public UbicacionActEspecialController(MapUbicacionActEspecialService mapUbicacionActEspecialService, MapUbicacionService mapUbicacionService) {
        this.mapUbicacionActEspecialService = mapUbicacionActEspecialService;
        this.mapUbicacionService = mapUbicacionService;
    }

    @RequestMapping("show")
    public String show(@RequestParam Long id, @RequestParam String fechaAlta, Model model) {

        LocalDateTime dateTime = LocalDateTime.parse(fechaAlta, FOMATTER);

        MapUbicacionActEspecial mapUbicacionActEspecial = mapUbicacionActEspecialService.get(new MapUbicacionActEspecialPK(id,dateTime));

        model.addAttribute("mapUbicacionActEspecial", mapUbicacionActEspecial);

        return "ubicacionActEspecial/show";
    }

    @RequestMapping("list")
    public String findAll(@RequestParam(defaultValue = "10") Integer size,
                          @RequestParam(defaultValue = "0") Integer page, Model model) {
        List<MapUbicacionActEspecial> ubicacionActEspeciales = mapUbicacionActEspecialService.findAll(size, page, "id.mapUbicacionId");

        model.addAttribute("ubicacionActEspeciales", ubicacionActEspeciales);
        model.addAttribute("page" , page);

        return "ubicacionActEspecial/list";
    }


    @RequestMapping("create")
    public String create() {
        return "ubicacionActEspecial/create";
    }

    FieldError idUbicacionNotExist = new FieldError(
            "mapUbicacionActEspecial" , "id.mapUbicacionId" , "El IdUbicacion introducido no existe"
    );

    @RequestMapping(value = "save" , method = RequestMethod.POST)
    public String save(@ModelAttribute MapUbicacionActEspecial mapUbicacionActEspecial, BindingResult bindingResult, RedirectAttributes redirectAttributes){

        if(!mapUbicacionService.findById(mapUbicacionActEspecial.getId().getMapUbicacionId()).isPresent()){
            bindingResult.addError(idUbicacionNotExist);
        }
        if(bindingResult.hasErrors()) {
            return "ubicacionActEspecial/create";
        }

        LocalDateTime now = LocalDateTime.now();
        now = now.truncatedTo(ChronoUnit.SECONDS);

        MapUbicacionActEspecialPK id = new MapUbicacionActEspecialPK( mapUbicacionActEspecial.getId().getMapUbicacionId() , now);
        mapUbicacionActEspecial.setId(id);
        mapUbicacionActEspecialService.save(mapUbicacionActEspecial);

        redirectAttributes.addAttribute("id", mapUbicacionActEspecial.getId().getMapUbicacionId());

        String fechaAlta = FOMATTER.format(now);
        redirectAttributes.addAttribute("fechaAlta", fechaAlta);

        return "redirect:/ubicacionActEspecial/show";
    }

    @RequestMapping("update")
    public String update(@RequestParam Long id,@RequestParam String fechaAlta, Model model) {

        LocalDateTime dateTime = LocalDateTime.parse(fechaAlta, FOMATTER);

        MapUbicacionActEspecial mapUbicacionActEspecial = mapUbicacionActEspecialService.get(new MapUbicacionActEspecialPK( id , dateTime));

        model.addAttribute("updateUbicacionActEspecial", mapUbicacionActEspecial);
        return "ubicacionActEspecial/update";
    }

    @RequestMapping("dropState")
    public String dropState(@RequestParam Long id, @RequestParam String fechaAlta, RedirectAttributes redirectAttributes){

        LocalDateTime dateTime = LocalDateTime.parse(fechaAlta, FOMATTER);

        MapUbicacionActEspecial mapUbicacionActEspecial = mapUbicacionActEspecialService.get(new MapUbicacionActEspecialPK( id , dateTime));
        mapUbicacionActEspecial.setOperacion("B");
        mapUbicacionActEspecialService.save(mapUbicacionActEspecial);

        redirectAttributes.addAttribute("id", mapUbicacionActEspecial.getId().getMapUbicacionId());
        redirectAttributes.addAttribute("fechaAlta", fechaAlta);

        return "redirect:/ubicacionActEspecial/show";
    }

    @RequestMapping("upState")
    public String upState(@RequestParam Long id, @RequestParam String fechaAlta, RedirectAttributes redirectAttributes){

        LocalDateTime dateTime = LocalDateTime.parse(fechaAlta, FOMATTER);

        MapUbicacionActEspecial mapUbicacionActEspecial = mapUbicacionActEspecialService.get(new MapUbicacionActEspecialPK( id , dateTime));
        mapUbicacionActEspecial.setOperacion("A");
        mapUbicacionActEspecialService.save(mapUbicacionActEspecial);

        redirectAttributes.addAttribute("id", mapUbicacionActEspecial.getId().getMapUbicacionId());
        redirectAttributes.addAttribute("fechaAlta", fechaAlta);

        return "redirect:/ubicacionActEspecial/show";
    }

    @RequestMapping(value = "editUbicacionActEspecial" , method = RequestMethod.POST)
    public String edit(@ModelAttribute MapUbicacionActEspecial mapUbicacionActEspecial, RedirectAttributes redirectAttributes){

        mapUbicacionActEspecialService.save(mapUbicacionActEspecial);

        redirectAttributes.addAttribute("idUbicacion", mapUbicacionActEspecial.getId().getMapUbicacionId());

        String fechaAlta = FOMATTER.format(mapUbicacionActEspecial.getId().getFechaAlta());
        redirectAttributes.addAttribute("fechaAlta", fechaAlta);

        return "redirect:/ubicacionActEspecial/show?id={idUbicacion}&fechaAlta={fechaAlta}";
    }

    @RequestMapping(value = "search", params = "paginate")
    public String listPaginated(@ModelAttribute("myWrapper") Wrapper wrapper, Model model){
        wrapper.getMapUbiActEspecialRequest().setPage(wrapper.getPage());
        model.addAttribute("ubicacionActEspeciales", mapUbicacionActEspecialService.findAll(wrapper.getMapUbiActEspecialRequest()));
        model.addAttribute("mapUbiActEspecialRequest", wrapper.getMapUbiActEspecialRequest());

        return "ubicacionActEspecial/list";
    }

    @ModelAttribute("mapUbicacionActEspecial")
    public MapUbicacionActEspecial get(){return new MapUbicacionActEspecial();}

    @ModelAttribute("mapUbiActEspecialRequest")
    public MapUbiActEspecialRequest mapUbiActEspecialRequest(){
        return new MapUbiActEspecialRequest();
    }

    @ModelAttribute("myWrapper")
    public Wrapper wrapper(){
        return new Wrapper();
    }

}
