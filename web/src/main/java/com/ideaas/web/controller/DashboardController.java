package com.ideaas.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by federicoberon on 08/10/2019.
 */
@Controller
@RequestMapping("dashboard")
public class DashboardController {


    @RequestMapping("panel")
    public String panel(){
        return "panel";
    }

}
