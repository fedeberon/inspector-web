package com.ideaas.web.controller;

import com.ideaas.services.bean.Wrapper;
import com.ideaas.services.domain.AppRelevamiento;
import com.ideaas.services.domain.AppRespuesta;
import com.ideaas.services.domain.AppUbicacion;
import com.ideaas.services.domain.AppUbicacionRelevamiento;
import com.ideaas.services.service.interfaces.AppRelevamientoService;
import com.ideaas.services.service.interfaces.AppRespuestaService;
import com.ideaas.services.service.interfaces.AppUbicacionRelService;
import com.ideaas.services.service.interfaces.AppUbicacionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("appUbicacionRelevamiento")
public class AppUbicacionRelevamientoController {

    private AppUbicacionRelService appUbicacionRelService;
    private AppRelevamientoService appRelevamientoService;
    private AppRespuestaService appRespuestaService;
    private AppUbicacionService appUbicacionService;

    public AppUbicacionRelevamientoController(AppUbicacionRelService appUbicacionRelService, AppRelevamientoService appRelevamientoService, AppRespuestaService appRespuestaService, AppUbicacionService appUbicacionService) {
        this.appUbicacionRelService = appUbicacionRelService;
        this.appRelevamientoService = appRelevamientoService;
        this.appRespuestaService = appRespuestaService;
        this.appUbicacionService = appUbicacionService;
    }
    @Deprecated
    @GetMapping("list")
    public String list(Model model){
        List<AppUbicacionRelevamiento> appUbicacionRelevamientos = appUbicacionRelService.findAllByBajaLogica(false);
        model.addAttribute("ubicaciones", appUbicacionRelevamientos );

        return "appUbicacionRelevamiento/list";
    }

    @GetMapping("list/{idRelevamiento}")
    public String findAllByIdRelevamiento(@PathVariable Long idRelevamiento, Model model){

        List<AppUbicacionRelevamiento> ubicaciones = appUbicacionRelService.findAllByIdRelevamiento(idRelevamiento);
        model.addAttribute("ubicaciones", ubicaciones );

        return "appUbicacionRelevamiento/list";
    }

    @GetMapping("{id}")
    public String show(@PathVariable Long id, Model model){
        AppUbicacionRelevamiento appUbicacionRelevamiento = appUbicacionRelService.get(id);
        List<AppRespuesta> respuestas = appRespuestaService.findAllByIdUbicacion(id);

        model.addAttribute("appUbicacionRelevamiento", appUbicacionRelevamiento);
        model.addAttribute("relevamientosShow", respuestas);

        return "appUbicacionRelevamiento/show";
    }

    @GetMapping("create")
    public String create() {
        return "appUbicacionRelevamiento/create";
    }

    FieldError emptyNombreRelevamiento = new FieldError(
            "appUbicacionRelevamiento" , "appRelevamiento" , "Debes completar este campo"
    );
    FieldError emptyDireccion = new FieldError(
            "appUbicacionRelevamiento" , "direccion" , "Debes completar este campo"
    );

    @RequestMapping(value = "save" , method = RequestMethod.POST)
    public String save(@ModelAttribute AppUbicacionRelevamiento ubicacionRelevamiento, BindingResult bindingResult, RedirectAttributes redirectAttributes){

        if(ubicacionRelevamiento.getAppRelevamiento().getId() == null){
            bindingResult.addError(emptyNombreRelevamiento);
        }
        if(ubicacionRelevamiento.getAppUbicacion().getId() == null){
            bindingResult.addError(emptyDireccion);
        }

        if(bindingResult.hasErrors()) {
            return "appUbicacionRelevamiento/create";
        }

        appUbicacionRelService.save(ubicacionRelevamiento);
        redirectAttributes.addAttribute("id", ubicacionRelevamiento.getId());

        AppRespuesta newRepuesta = new AppRespuesta();

        if(ubicacionRelevamiento.getOneOrdenS() != null){
            newRepuesta.setOneOrdenS(ubicacionRelevamiento.getOneOrdenS());
        }
        if(ubicacionRelevamiento.getOneCircuito() != null){
            newRepuesta.setOneCircuito(ubicacionRelevamiento.getOneCircuito());
        }
        newRepuesta.setAppUbicacionRelevamiento(ubicacionRelevamiento);
        newRepuesta.setAppRelevamiento(ubicacionRelevamiento.getAppRelevamiento());
        newRepuesta.setEstado(false);
        appRespuestaService.save(newRepuesta);

        return "redirect:/appUbicacionRelevamiento/{id}";
    }

    @RequestMapping(value="saveAll", method= RequestMethod.POST)
    public ResponseEntity<Object> saveAll(@RequestBody ArrayList<AppUbicacionRelevamiento> ubicaciones){

        try {
            List<AppRespuesta> correctRespuestas = new ArrayList<>();
            List<AppUbicacionRelevamiento> correctUbicaciones = new ArrayList<>();
            List<AppUbicacionRelevamiento> incorrectUbicaciones = new ArrayList<>();
            List<AppUbicacionRelevamiento> alreadyExitsUbicaciones = new ArrayList<>();
            boolean hasError;

            for(AppUbicacionRelevamiento appUbicacionRelevamiento : ubicaciones) {
                hasError = false;

                if (appUbicacionRelevamiento.getAppRelevamiento().getId() == null) {
                    hasError = true;
                } else if (!appRelevamientoService.findById(appUbicacionRelevamiento.getAppRelevamiento().getId()).isPresent()){
                    hasError = true;
                }

                if(appUbicacionRelevamiento.getOneOrdenS() == null){
                    hasError = true;
                }

                if (appUbicacionRelevamiento.getAppUbicacion().getDireccion() == null) {
                    hasError = true;
                }

                if (hasError) {
                    incorrectUbicaciones.add(appUbicacionRelevamiento);

                } else if (appUbicacionRelevamiento.getAppUbicacion().getOneClaveUbi() == null || appUbicacionService.findByOneClaveUbi(appUbicacionRelevamiento.getAppUbicacion().getOneClaveUbi()) == null){
                        AppUbicacion appUbicacion = appUbicacionService.save(appUbicacionRelevamiento.getAppUbicacion());
                        appUbicacionRelevamiento.setAppUbicacion(appUbicacion);
                } else {
                    AppUbicacion appUbicacion = appUbicacionService.findByOneClaveUbi(appUbicacionRelevamiento.getAppUbicacion().getOneClaveUbi());
                    appUbicacionRelevamiento.setAppUbicacion(appUbicacion);
                }

                AppRespuesta newAppRespuesta = new AppRespuesta();
                newAppRespuesta.setOneOrdenS(appUbicacionRelevamiento.getOneOrdenS());
                newAppRespuesta.setOneCircuito(appUbicacionRelevamiento.getOneCircuito());
                newAppRespuesta.setAppUbicacionRelevamiento(appUbicacionRelevamiento);
                newAppRespuesta.setAppRelevamiento(appUbicacionRelevamiento.getAppRelevamiento());
                newAppRespuesta.setEstado(false);

                correctRespuestas.add(newAppRespuesta);
                correctUbicaciones.add(appUbicacionRelevamiento);
            }

            if(incorrectUbicaciones.isEmpty()){

                if(correctUbicaciones.isEmpty()){
                    return new ResponseEntity<>(alreadyExitsUbicaciones, HttpStatus.CONFLICT);
                }else {
                    appUbicacionRelService.saveAll(correctUbicaciones);
                    appRespuestaService.saveAll(correctRespuestas);

                    return new ResponseEntity<>(new Object[]{correctUbicaciones, alreadyExitsUbicaciones}, HttpStatus.OK);
                }

            }else{
                return new ResponseEntity<>(incorrectUbicaciones , HttpStatus.BAD_REQUEST);
            }

        } catch (Exception e) {
            return new ResponseEntity("Internal Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping("update")
    public String update(@RequestParam Long id, Model model) {
        AppUbicacionRelevamiento ubicacionRelevamiento = appUbicacionRelService.get(id);
        List<AppRespuesta> respuestas = appRespuestaService.findAllByIdUbicacion(id);

        model.addAttribute("appUbicacionRelevamiento", ubicacionRelevamiento);
        model.addAttribute("updateRespuestas", respuestas);

        return "appUbicacionRelevamiento/update";
    }

    @RequestMapping(value = "saveUpdate" , method = RequestMethod.POST)
    public String update(@ModelAttribute AppUbicacionRelevamiento ubicacion, RedirectAttributes redirectAttributes){
        appUbicacionRelService.save(ubicacion);

        if(ubicacion.getAppRelevamiento().getId() != null && ubicacion.getAppRelevamiento().getId() != -1){

            if(ubicacion.getOneOrdenS() == null){
                redirectAttributes.addAttribute("id", ubicacion.getId());
                return "redirect:/appUbicacionRelevamiento/update?id={id}";
            }

            AppRespuesta newRepuesta = new AppRespuesta();
            newRepuesta.setAppUbicacionRelevamiento(ubicacion);
            newRepuesta.setAppRelevamiento(ubicacion.getAppRelevamiento());
            newRepuesta.setOneOrdenS(ubicacion.getOneOrdenS());
            newRepuesta.setOneCircuito(ubicacion.getOneCircuito());
            newRepuesta.setEstado(false);
            appRespuestaService.save(newRepuesta);

        }

        redirectAttributes.addAttribute("id", ubicacion.getId());

        return "redirect:/appUbicacionRelevamiento/{id}";
    }

    @RequestMapping(value ={"search", "list/search"} , params = "maps" , method = RequestMethod.POST)
    public String findAll(@ModelAttribute("myWrapper") Wrapper ubicaciones, Model model){
        model.addAttribute("registros", this.appUbicacionRelService.findAllByIdIn(ubicaciones.getIdAppUbicacionesListSelected()));

        return "appUbicacionRelevamiento/map";
    }

    @RequestMapping(value = {"deleteAll", "list/deleteAll"} , params = "delete" , method = RequestMethod.POST)
    public String deleteAll(@ModelAttribute("myWrapper") Wrapper ubicaciones){
        this.appUbicacionRelService.findAllByIdUbicacionRel(ubicaciones.getIdAppUbicacionesListSelected()).forEach(
                ubicacion -> {
                    AppRespuesta respuesta = this.appRespuestaService.findByUbicacionAndRelevamiento(ubicacion.getId(), ubicacion.getAppRelevamiento().getId());
                    if (respuesta != null){
                        this.appRespuestaService.delete(respuesta.getId());
                    }
                    this.appUbicacionRelService.delete(ubicacion.getId());
                }
        );
        return "redirect:/appUbicacionRelevamiento/list";
    }

    @ModelAttribute("appUbicacionRelevamiento")
    public AppUbicacionRelevamiento get(){
        return new AppUbicacionRelevamiento();
    }

    @ModelAttribute("relevamientos")
    public List<AppRelevamiento> relevamientos(){
        return appRelevamientoService.findAll();
    }

    @ModelAttribute("ubicaciones")
    public List<AppUbicacion> ubicaciones(){
        return appUbicacionService.findAll();
    }
}
