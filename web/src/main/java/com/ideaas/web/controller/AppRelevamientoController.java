package com.ideaas.web.controller;

import com.ideaas.services.bean.Wrapper;
import com.ideaas.services.domain.*;
import com.ideaas.services.request.AppRelevamientoRequest;
import com.ideaas.services.service.interfaces.*;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.Map;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Controller
@RequestMapping("relevamiento")
@Secured({"ROLE_ADMINISTRADOR"})
public class AppRelevamientoController {

    private AppRelevamientoService appRelevamientoService;

    private AppFormularioService appFormularioService;

    private UsuarioService usuarioService;

    private AppEstadoRelService appEstadoRelService;

    private AppRespuestaService appRespuestaService;
    
    private AppUbicacionRelService appUbicacionRelService;

    private AppProjectService appProjectService;

    public AppRelevamientoController(AppRelevamientoService appRelevamientoService, AppFormularioService appFormularioService, UsuarioService usuarioService, AppEstadoRelService appEstadoRelService, AppRespuestaService appRespuestaService, AppUbicacionRelService appUbicacionRelService, AppProjectService appProjectService) {
        this.appRelevamientoService = appRelevamientoService;
        this.appFormularioService = appFormularioService;
        this.usuarioService = usuarioService;
        this.appEstadoRelService = appEstadoRelService;
        this.appRespuestaService = appRespuestaService;
        this.appUbicacionRelService = appUbicacionRelService;
        this.appProjectService = appProjectService;
    }

    private static final Boolean INACTIVE = true;


    @GetMapping("list/{projectId}")
    public String findAllByProject(Model model, @PathVariable Long projectId){
        List<AppRelevamiento> relevamientos = appRelevamientoService.findAllByProject(projectId);
        setCantidadUbicaciones(relevamientos);

        model.addAttribute("projectId", projectId);
        model.addAttribute("relevamientos", relevamientos );

        return "relevamiento/list";
    }

    @Deprecated
    @GetMapping("list")
    public String findAll(Model model){
        List<AppRelevamiento> relevamientos = appRelevamientoService.findAll();
        setCantidadUbicaciones(relevamientos);

        model.addAttribute("relevamientos", relevamientos );

        return "relevamiento/list";
    }

    @RequestMapping(value = "search")
    public String listFiltered(@ModelAttribute("relevamientoRequest") AppRelevamientoRequest relevamientoRequest, Model model){
        model.addAttribute("relevamientos", appRelevamientoService.findAllByEstado(relevamientoRequest.getIdEstado()));

        return "relevamiento/list";
    }

    @GetMapping("create/{id}")
    public String createWithId(@PathVariable Long id, Model model) {

        AppProject project = appProjectService.get(id);

        model.addAttribute("project", project);
        return "relevamiento/create";
    }

    @GetMapping("create")
    public String create() {
        return "relevamiento/create";
    }

    @GetMapping("{id}/{projectId}")
    public String showWithProjectId(@PathVariable Long id, @PathVariable Long projectId, Model model){
        AppRelevamiento relevamiento = appRelevamientoService.get(id);

        model.addAttribute("relevamiento", relevamiento);
        model.addAttribute("projectId", projectId);

        return "relevamiento/show";
    }

    @GetMapping("{id}")
    public String show(@PathVariable Long id, Model model){
        AppRelevamiento relevamiento = appRelevamientoService.get(id);

        model.addAttribute("relevamiento", relevamiento);

        return "relevamiento/show";
    }

    FieldError emptyNombreRelevamiento = new FieldError(
            "appRelevamiento" , "nombreRelevamiento" , "Debes completar este campo"
    );
    FieldError emptyUsuario = new FieldError(
            "appRelevamiento" , "usuario" , "Debes seleccionar un inspector"
    );
    FieldError emptyForm = new FieldError(
            "appRelevamiento" , "formulario" , "Debes seleccionar una formulario"
    );
    FieldError copyLocationsError = new FieldError(
            "appRelevamiento" , "idRelevamientoToCopy" , "Ocurrio un error al intentar copiar las ubicaciones, pero el relevamiento se creo correctamente"
    );

    @RequestMapping(value = "save" , method = RequestMethod.POST)
    public String save(@ModelAttribute AppRelevamiento relevamiento, BindingResult bindingResult, RedirectAttributes redirectAttributes){

        if(relevamiento.getNombreRelevamiento().isEmpty()){
            bindingResult.addError(emptyNombreRelevamiento);
        }
        if(relevamiento.getUsuario().getId() == null){
            bindingResult.addError(emptyUsuario);
        }
        if(relevamiento.getFormulario().getId() == null){
            bindingResult.addError(emptyForm);
        }
        if(bindingResult.hasErrors()) {
            if(relevamiento.getProject() != null){
                return "relevamiento/create/" + relevamiento.getProject().getId();
            }
            return "relevamiento/create";
        }

        appRelevamientoService.save(relevamiento);

        if(relevamiento.getIdRelevamientoToCopy() != -1){
            List<AppRespuesta> respuestas = appRespuestaService.findAllByIdRelevamiento(relevamiento.getIdRelevamientoToCopy());
            List<AppRespuesta> newRespuestas = new ArrayList<>();
            List<AppUbicacionRelevamiento> ubicacionesRelevamiento = appUbicacionRelService.findAllByIdRelevamiento(relevamiento.getIdRelevamientoToCopy());
            List<AppUbicacionRelevamiento> newUbicacionesRelevamiento = new ArrayList<>();
            try {
                for (AppRespuesta respuesta : respuestas){
                    AppUbicacionRelevamiento ubicacion = respuesta.getAppUbicacionRelevamiento();

                    AppRespuesta newRepuesta = new AppRespuesta();
                    newRepuesta.setAppUbicacionRelevamiento(ubicacion);
                    newRepuesta.setAppRelevamiento(relevamiento);
                    newRepuesta.setOneOrdenS(0L);
                    newRepuesta.setEstado(false);

                    newRespuestas.add(newRepuesta);
                }

                for (AppUbicacionRelevamiento ubicacionRelevamiento : ubicacionesRelevamiento){
                    AppUbicacionRelevamiento newUbicacionRel = new AppUbicacionRelevamiento();
                    newUbicacionRel.setAppUbicacion(ubicacionRelevamiento.getAppUbicacion());
                    newUbicacionRel.setAppRelevamiento(relevamiento);
                    newUbicacionRel.setCantidad(ubicacionRelevamiento.getCantidad());
                    newUbicacionRel.setEvp(ubicacionRelevamiento.getEvp());
                    newUbicacionRel.setElemento(ubicacionRelevamiento.getElemento());
                    newUbicacionRel.setAnunciante(ubicacionRelevamiento.getAnunciante());
                    newUbicacionRel.setProducto(ubicacionRelevamiento.getProducto());
                    newUbicacionRel.setReferencias(ubicacionRelevamiento.getReferencias());
                    newUbicacionRel.setOneOrdenS(ubicacionRelevamiento.getOneOrdenS());
                    newUbicacionRel.setOneCircuito(ubicacionRelevamiento.getOneCircuito());
                    
                    newUbicacionesRelevamiento.add(newUbicacionRel);
                }
            }
            catch (Exception e){

                bindingResult.addError(copyLocationsError);
                return "relevamiento/create";
            }

            appRespuestaService.saveAll(newRespuestas);
            appUbicacionRelService.saveAll(newUbicacionesRelevamiento);
        }

        redirectAttributes.addAttribute("id", relevamiento.getId());
        if(relevamiento.getProject() != null){
            redirectAttributes.addAttribute("projectId", relevamiento.getProject().getId());
            return "redirect:/relevamiento/{id}/{projectId}";
        }

        return "redirect:/relevamiento/{id}";
    }

    @RequestMapping("update")
    public String update(@RequestParam Long id, Model model) {
        AppRelevamiento relevamiento = appRelevamientoService.get(id);
        model.addAttribute("projects", this.appProjectService.findAll());
        model.addAttribute("updateRelevamiento", relevamiento);

        return "relevamiento/update";
    }

    @RequestMapping(value = "saveUpdate" , method = RequestMethod.POST)
    public String saveUpdate(@ModelAttribute AppRelevamiento relevamiento, RedirectAttributes redirectAttributes){
        appRelevamientoService.save(relevamiento);
        redirectAttributes.addAttribute("id", relevamiento.getId());

        return "redirect:/relevamiento/{id}";
    }

    @RequestMapping("dropState")
    public String dropState(@RequestParam Long id, RedirectAttributes redirectAttributes){
        AppRelevamiento relevamiento = appRelevamientoService.get(id);
        AppEstadoRelevamiento estado = appEstadoRelService.get(1L); //idEstado = 1 -> No asignado //L is a Long
        relevamiento.setEstado(estado);
        appRelevamientoService.save(relevamiento);
        redirectAttributes.addAttribute("id", relevamiento.getId());

        return "redirect:/relevamiento/{id}";
    }

    @RequestMapping("upState")
    public String upState(@RequestParam Long id, RedirectAttributes redirectAttributes){
        AppRelevamiento relevamiento = appRelevamientoService.get(id);
        AppEstadoRelevamiento estado = appEstadoRelService.get(2L); //idEstado = 2 -> Asignado

        LocalDate date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String dateFormatted = date.format(formatter);

        relevamiento.setEstado(estado);
        relevamiento.setFechaAsignacion(dateFormatted);
        appRelevamientoService.save(relevamiento);
        redirectAttributes.addAttribute("id", relevamiento.getId());

        return "redirect:/relevamiento/{id}";
    }

    @RequestMapping("/delete")
    public String delete(@RequestParam Long id){

        List<Long> respuestasIdsToBeDeleted = appRespuestaService.findAllByIdRelevamiento(id).stream().map(AppRespuesta::getId).collect(Collectors.toList());
        if(!respuestasIdsToBeDeleted.isEmpty()){
            appRespuestaService.deleteAllByIds(respuestasIdsToBeDeleted);
        }
        /*
        List<AppRespuesta> respuestasToBeDeleted = appRespuestaService.findAllByIdRelevamiento(id);
        for (AppRespuesta i: respuestasToBeDeleted) {
            appRespuestaService.delete(i.getId());
        }*/

        List<Long> ubiRelIdsToBeDeleted = appUbicacionRelService.findAllByIdRelevamiento(id).stream().map(AppUbicacionRelevamiento::getId).collect(Collectors.toList());
        if(!ubiRelIdsToBeDeleted.isEmpty()){
            appUbicacionRelService.deleteAllByIds(ubiRelIdsToBeDeleted);
        }
        /*List<AppUbicacionRelevamiento> ubiRelToBeDeleted = appUbicacionRelService.findAllByIdRelevamiento(id);
        for (AppUbicacionRelevamiento i : ubiRelToBeDeleted){
            appUbicacionRelService.delete(i.getId());
        }*/

        appRelevamientoService.delete(id);

        /*AppRelevamiento appRelevamiento = appRelevamientoService.get(id);
        appRelevamiento.setBajaLogica(INACTIVE);
        appRelevamientoService.save(appRelevamiento);*/

        return "redirect:/relevamiento/list";
    }

    @ModelAttribute("appRelevamiento")
    public AppRelevamiento get(){
        return new AppRelevamiento();
    }

    @ModelAttribute("formularios")
    public List<AppFormulario> formularios(){
        return appFormularioService.findAll();
    }

    @ModelAttribute("estados")
    public List<AppEstadoRelevamiento> estados(){
        return appEstadoRelService.findAll();
    }

    @ModelAttribute("inspectores")
    public List<Usuario> inspectores(){
        Long inspectorRol = 5L; //5 -> rol de inspector en tipo_usuarios
        return usuarioService.findAllByTipoUsuario(inspectorRol);
    }

    @ModelAttribute("relevamientosToCopy")
    public List<AppRelevamiento> relevamientos(){
        return appRelevamientoService.findAll();
    }

    @ModelAttribute("relevamientoRequest")
    public AppRelevamientoRequest relevamientoRequest(){
        return new AppRelevamientoRequest();
    }

    private void setCantidadUbicaciones(List<AppRelevamiento> relevamientos){
        Map<Long, Integer> cantidadUbicaciones = appUbicacionRelService.cantidadUbicacionesByRelevamiento();
        for(AppRelevamiento i : relevamientos) {
            Integer cantidad = cantidadUbicaciones.get(i.getId());
            if(cantidad == null)
                cantidad = 0;
            i.setCantidadDeUbicacionesByRelevamiento(cantidad);
        }
    }

    @RequestMapping(value = "/findAllByDates/{startDate}/{endDate}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AppRelevamiento>> findAllByDates(@PathVariable String startDate, @PathVariable String endDate){
        List<AppRelevamiento> relevamientos = appRelevamientoService.findAllByDates(startDate, endDate);

        return ResponseEntity.ok(relevamientos);
    }
}
