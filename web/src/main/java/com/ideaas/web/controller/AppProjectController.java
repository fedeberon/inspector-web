package com.ideaas.web.controller;


import com.ideaas.services.domain.AppProject;
import com.ideaas.services.domain.AppRelevamiento;
import com.ideaas.services.domain.AppRespuesta;
import com.ideaas.services.service.interfaces.AppProjectService;
import com.ideaas.services.service.interfaces.AppRelevamientoService;
import com.ideaas.services.service.interfaces.AppRespuestaService;
import com.ideaas.services.service.interfaces.AppUbicacionRelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("proyecto")
@Secured({"ROLE_ADMINISTRADOR"})
public class AppProjectController {

    @Autowired
    AppProjectService appProjectService;

    @Autowired
    AppRelevamientoService appRelevamientoService;

    @Autowired
    AppRespuestaService appRespuestaService;

    @Autowired
    AppUbicacionRelService appUbicacionRelService;
    @GetMapping("list")
    public String listProjects(Model model){
        List<AppProject> projects = appProjectService.findAll();

        model.addAttribute("projects", projects);
        return "project/list";
    }

    @GetMapping("show/{id}")
    public String showProjects(Model model, @PathVariable Long id){
        AppProject project = appProjectService.get(id);

        model.addAttribute("project", project);
        return "project/show";
    }

    @GetMapping("create")
    public String createProject(Model bindingResult){
        bindingResult.addAttribute("appProject", new AppProject());
        return "project/create";
    }

    FieldError emptyProjectName = new FieldError(
            "appProject" , "nombreProyecto" , "Debes completar este campo"
    );
    @RequestMapping(value = "save", method = RequestMethod.POST)
    public String save(@ModelAttribute("appProject") AppProject appProject, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if(appProject.getName() == null){
            bindingResult.addError(emptyProjectName);
        }
        if(bindingResult.hasErrors()) {
            return "project/create";
        }

        appProjectService.save(appProject);
        redirectAttributes.addAttribute("id", appProject.getId());

        return "redirect:/proyecto/show/{id}";
    }

    @RequestMapping("update")
    public String update(@RequestParam Long id, Model model) {
        AppProject project = appProjectService.get(id);
        model.addAttribute("updateProject", project);

        return "project/update";
    }

    @RequestMapping("/delete")
    public String delete(@RequestParam Long id) {
        List<Long> relevamientoIdsToDelete = appRelevamientoService.findAllByProject(id).stream().map(AppRelevamiento::getId).collect(Collectors.toList());

        if(!relevamientoIdsToDelete.isEmpty()){
            appRespuestaService.deleteByRelevamientoIds(relevamientoIdsToDelete);
            appUbicacionRelService.deleteByRelevamientoIds(relevamientoIdsToDelete);
            appRelevamientoService.deleteByIds(relevamientoIdsToDelete);
        }

        appProjectService.delete(id);

        return "redirect:/proyecto/list";
    }

}
