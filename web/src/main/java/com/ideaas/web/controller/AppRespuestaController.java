package com.ideaas.web.controller;

import com.ideaas.services.domain.AppRespuesta;
import com.ideaas.services.service.interfaces.AppRespuestaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("appRespuesta")
@Secured({"ROLE_ADMINISTRADOR"})
public class AppRespuestaController {

    private AppRespuestaService appRespuestaService;

    public AppRespuestaController(AppRespuestaService appRespuestaService) {
        this.appRespuestaService = appRespuestaService;

    }

    @RequestMapping(value = "getAnswer/{idUbicacion}/{idRelevamiento}" , method = RequestMethod.GET)
    public ResponseEntity<AppRespuesta> getAllLocationOfRelevamiento(@PathVariable Long idUbicacion, @PathVariable Long idRelevamiento){
        AppRespuesta respuesta = appRespuestaService.findByUbicacionAndRelevamiento(idUbicacion , idRelevamiento);

        if(respuesta == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else{
            return new ResponseEntity<>(respuesta , HttpStatus.OK);
        }
    }

    @RequestMapping(value = "createReport/{idRelevamiento}" , method = RequestMethod.GET)
    public ResponseEntity<List<AppRespuesta>> getAllLocationOfRelevamiento(@PathVariable Long idRelevamiento){
        List<AppRespuesta> respuestas = appRespuestaService.findAllByIdRelevamiento(idRelevamiento);

        return new ResponseEntity<>(respuestas , HttpStatus.OK);
    }

    @RequestMapping(value = "delete/{idRespuesta}" , method = RequestMethod.DELETE)
    public ResponseEntity<AppRespuesta> deleteAppRespuesat(@PathVariable Long idRespuesta){
        AppRespuesta respuesta = appRespuestaService.get(idRespuesta);
        appRespuestaService.delete(idRespuesta);

        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping("updateRespuesta")
    public ResponseEntity<AppRespuesta> update(@RequestBody AppRespuesta respuestaRequest){
        appRespuestaService.saveUpdate(respuestaRequest);
        AppRespuesta respuesta = appRespuestaService.get(respuestaRequest.getId());

        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
}
