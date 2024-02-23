package com.ideaas.web.controller;

import java.util.ArrayList;

import com.ideaas.services.domain.AppFormulario;
import com.ideaas.services.domain.AppRespuesta;
import com.ideaas.services.domain.AppUbicacionRelevamiento;
import com.ideaas.services.service.interfaces.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.ListIterator;

@Controller
@RequestMapping("appUbicacionRevisar")
@Secured({"ROLE_ADMINISTRADOR"})
public class AppUbicacionRevisarController {

    private AppUbicacionRelService appUbicacionRelService;
    private AppRelevamientoService appRelevamientoService;
    private AppRespuestaService appRespuestaService;
    private AppFormularioService appFormularioService;

    private static final Boolean INACTIVE = true;

    @Autowired
    public AppUbicacionRevisarController(AppUbicacionRelService appUbicacionRelService, AppRelevamientoService appRelevamientoService, AppRespuestaService appRespuestaService){
        this.appUbicacionRelService = appUbicacionRelService;
        this.appRelevamientoService = appRelevamientoService;
        this.appRespuestaService = appRespuestaService;
    }

    @GetMapping("list/{idRelevamiento}")
    public String findAllByIdRelevamiento(@PathVariable Long idRelevamiento, Model model){

        List<AppUbicacionRelevamiento> ubicaciones = appUbicacionRelService.findAllByIdRelevamiento(idRelevamiento);
        model.addAttribute("ubicaciones", ubicaciones );
        model.addAttribute("idRelevamiento", idRelevamiento);

        return "appUbicacionRevisar/list";
    }



    @GetMapping("show/{idRelevamiento}/{idUbicacionRel}")
    public String show(@PathVariable Long idRelevamiento, @PathVariable Long idUbicacionRel, Model model){

        AppUbicacionRelevamiento ubicacionRelevamiento = appUbicacionRelService.get(idUbicacionRel);
        AppRespuesta respuesta = new AppRespuesta();
        if(ubicacionRelevamiento.getAppUbicacion() != null){
            respuesta = appRespuestaService.findByUbicacionAndRelevamiento(ubicacionRelevamiento.getId(), idRelevamiento);
            model.addAttribute("respuesta", respuesta);
        }
        Long nextId = appUbicacionRelService.findNextById(idUbicacionRel, idRelevamiento);
        Long previousId = appUbicacionRelService.findPreviousById(idUbicacionRel, idRelevamiento);

        model.addAttribute("ubicacionRelevamiento", ubicacionRelevamiento);
        model.addAttribute("nextId", nextId);
        model.addAttribute("previousId", previousId);
        model.addAttribute("respuesta", respuesta);


        return "appUbicacionRevisar/show";
    }

    @PostMapping("updateRespuesta")
    public ResponseEntity<AppRespuesta> update(@RequestBody AppRespuesta respuestaRequest){
        appRespuestaService.saveRespuestaUpdate(respuestaRequest);
        AppRespuesta respuesta = appRespuestaService.get(respuestaRequest.getId());

        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
}
