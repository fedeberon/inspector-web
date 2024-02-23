package com.ideaas.web.controller;

import com.ideaas.services.bean.MyObject;
import com.ideaas.services.bean.Wrapper;
import com.ideaas.services.request.MapUbicacionRequest;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by federicoberon on 08/10/2019.
 */
@Controller
@RequestMapping("maps")
@Secured({"ROLE_ADMINISTRADOR"})
public class MapController {

    @RequestMapping("show")
    public String maps(){
        return "maps";
    }


    @RequestMapping(value = "search", method = RequestMethod.POST)
    public String findAll(@ModelAttribute Wrapper wrapper, Model model){
        model.addAttribute("registros", wrapper.getSelectedElements());
        List<Long> ids = wrapper.getSelectedElements().parallelStream().map(MyObject::getId).collect(Collectors.toList());
        MapUbicacionRequest request = wrapper.getRequest();

        return "ubicacion/map";
    }

}
