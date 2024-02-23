package com.ideaas.web.controller;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("tablas")
@Secured({"ROLE_ADMINISTRADOR", "ROLE_ADMINISTRADOR_OOH"})
public class TablaController{



    @RequestMapping()
    public String tabla(){ return "tabla";
    }

}
