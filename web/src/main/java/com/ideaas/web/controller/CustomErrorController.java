package com.ideaas.web.controller;

import com.ideaas.services.enums.TipoUsuarioEnum;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        String errorMessage = (String) request.getAttribute("javax.servlet.error.message");
        Exception exception = (Exception) request.getAttribute("javax.servlet.error.exception");
        model.addAttribute("statusCode", statusCode);
        model.addAttribute("exception", exception);
        model.addAttribute("errorMessage", errorMessage);
        model.addAttribute("message", (exception != null) ? exception.getMessage() : "N/A");

        return "error";
    }
    @RequestMapping("/accessDenied")
    public String handleAccessDenied(HttpServletRequest request, Model model) {

        return "access_denied";
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
