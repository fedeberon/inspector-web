package com.ideaas.api.restController;

import com.ideaas.services.domain.AppEstadoRelevamiento;
import com.ideaas.services.domain.AppRespuesta;
import com.ideaas.services.service.interfaces.AppRelevamientoService;
import com.ideaas.services.service.interfaces.AppRespuestaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/appRespuesta")
public class AppRespuestaRestController {

    private AppRespuestaService appRespuestaService;

    private AppRelevamientoService appRelevamientoService;

    @Autowired
    public AppRespuestaRestController(AppRespuestaService appRespuestaService , AppRelevamientoService appRelevamientoService) {
        this.appRespuestaService = appRespuestaService;
        this.appRelevamientoService = appRelevamientoService;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public AppRespuesta get(@PathVariable Long id){
        return appRespuestaService.get(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/list/{pageSize}/{pageNo}/{sortBy}")
    public List<AppRespuesta> findAll(@PathVariable Integer pageSize, @PathVariable Integer pageNo, @PathVariable String sortBy){
        return appRespuestaService.findAll(pageSize, pageNo, sortBy);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/list/{pageSize}/{pageNo}")
    public List<AppRespuesta> findAll(@PathVariable Integer pageSize, @PathVariable Integer pageNo){
        return appRespuestaService.findAll(pageSize, pageNo, "id");
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/list/{pageSize}")
    public List<AppRespuesta> findAll(@PathVariable Integer pageSize){
        return appRespuestaService.findAll(pageSize, 0, "id");
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<AppRespuesta> save(@RequestBody AppRespuesta newAppUbicacion){
        AppRespuesta appRespuesta = appRespuestaService.save(newAppUbicacion);

        return new ResponseEntity(appRespuesta, HttpStatus.CREATED);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/getStatus/{idUbicacion}/{idRelevamiento}")
    public ResponseEntity<AppRespuesta> getStatus(@PathVariable Long idUbicacion, @PathVariable Long idRelevamiento){
        AppRespuesta respuesta = appRespuestaService.findByUbicacionAndRelevamiento(idUbicacion, idRelevamiento);

        return new ResponseEntity(respuesta.getEstado(), HttpStatus.OK);
    }


    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/setStatusFinished" , method = RequestMethod.POST)
    public void setStatusFinished(@RequestBody AppRespuesta respuestaForUpdated){
        AppRespuesta respuesta = appRespuestaService.findByUbicacionAndRelevamiento(respuestaForUpdated.getAppUbicacionRelevamiento().getId() , respuestaForUpdated.getAppRelevamiento().getId());
        respuesta.setEstado(true);
        respuesta.getAppUbicacionRelevamiento().setFechaFinalizacion(LocalDateTime.now().atZone(ZoneId.of("America/Argentina/Buenos_Aires")).toLocalDateTime());
        appRespuestaService.save(respuesta);

        List<AppRespuesta> respuestasDelRelevamiento = appRespuestaService.findAllByIdRelevamiento(respuestaForUpdated.getAppRelevamiento().getId());
        List<AppRespuesta> respuestasSinTerminar = new ArrayList<>();

        for(AppRespuesta respuestaDelRelevamiento : respuestasDelRelevamiento){
            if(!respuestaDelRelevamiento.getEstado()){
                respuestasSinTerminar.add(respuestaDelRelevamiento);
            }
        }

        if(respuestasSinTerminar.size() == 0){
            appRelevamientoService.setStateFinished(respuestaForUpdated.getAppRelevamiento().getId());
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/setStatusUnfinished" , method = RequestMethod.POST)
    public void setStatusUnfinished(@RequestBody AppRespuesta respuestaForUpdated){
        AppRespuesta respuesta = appRespuestaService.findByUbicacionAndRelevamiento(respuestaForUpdated.getAppUbicacionRelevamiento().getId() , respuestaForUpdated.getAppRelevamiento().getId());
        respuesta.setEstado(false);
        appRespuestaService.save(respuesta);
    }

    @CrossOrigin("*")
    @RequestMapping(value = "/saveResponseForm", method = RequestMethod.POST)
    public ResponseEntity<AppRespuesta> saveResponseForm(@RequestBody AppRespuesta respuestaToupdated){

        try {
            AppRespuesta respuesta = appRespuestaService.get(respuestaToupdated.getId());

            if(respuestaToupdated.getToken().equals(respuesta.getToken()) || true){
                respuesta.setToken(null);
                respuesta.setRespuesta(respuestaToupdated.getRespuesta());
                appRespuestaService.save(respuesta);

                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
