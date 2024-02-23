package com.ideaas.api.restController;

import com.ideaas.services.domain.AppRelevamiento;
import com.ideaas.services.domain.AppRespuesta;
import com.ideaas.services.domain.AppUbicacion;
import com.ideaas.services.domain.AppUbicacionRelevamiento;
import com.ideaas.services.service.interfaces.AppRelevamientoService;
import com.ideaas.services.service.interfaces.AppRespuestaService;
import com.ideaas.services.service.interfaces.AppUbicacionRelService;
import org.springframework.beans.factory.annotation.Autowired;
import com.ideaas.services.service.interfaces.AppUbicacionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/appUbicacion")
public class AppUbicacionRestController {

    private AppUbicacionRelService appUbicacionRelService;
    private AppRespuestaService appRespuestaService;
    private AppRelevamientoService appRelevamientoService;
    private AppUbicacionService appUbicacionService;

    @Autowired
    public AppUbicacionRestController(AppUbicacionRelService appUbicacionRelService, AppRespuestaService appRespuestaService, AppRelevamientoService appRelevamientoService, AppUbicacionService appUbicacionService) {
        this.appUbicacionRelService = appUbicacionRelService;
        this.appRespuestaService = appRespuestaService;
        this.appRelevamientoService = appRelevamientoService;
        this.appUbicacionService = appUbicacionService;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public AppUbicacionRelevamiento get(@PathVariable Long id){
        return appUbicacionRelService.get(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/list/{pageSize}/{pageNo}/{sortBy}")
    public List<AppUbicacionRelevamiento> findAll(@PathVariable Integer pageSize, @PathVariable Integer pageNo, @PathVariable String sortBy){
        return appUbicacionRelService.findAll(pageSize, pageNo, sortBy);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/list/{pageSize}/{pageNo}")
    public List<AppUbicacionRelevamiento> findAll(@PathVariable Integer pageSize, @PathVariable Integer pageNo){
        return appUbicacionRelService.findAll(pageSize, pageNo, "id");
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/list/{pageSize}")
    public List<AppUbicacionRelevamiento> findAll(@PathVariable Integer pageSize){
        return appUbicacionRelService.findAll(pageSize, 0, "id");
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/findAllByRelevamiento/{idRelevamiento}")
    public List<AppRespuesta> findAllByIdRelevamiento(@PathVariable Long idRelevamiento){
        List<AppRespuesta> appRespuestas = appRespuestaService.findAllByIdRelevamiento(idRelevamiento);

        return appRespuestas;
    }

    // @RequestMapping(value = "/save", method = RequestMethod.POST)
    // public ResponseEntity<AppUbicacionRelevamiento> save(@RequestBody AppUbicacionRelevamiento newAppUbicacion){
    //     boolean hasError = false;

    //     if(newAppUbicacion.getAppRelevamiento().getId() == null){
    //         hasError = true;
    //     }else if(!appRelevamientoService.findById(newAppUbicacion.getAppRelevamiento().getId()).isPresent()){
    //         hasError = true;
    //     }
    //     if(newAppUbicacion.getAppUbicacion().getDireccion().isEmpty()){
    //         hasError = true;
    //     }

    //     if(hasError) {
    //         return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    //     } else {
    //         AppUbicacionRelevamiento appUbicacion = appUbicacionRelService.save(newAppUbicacion);

    //         AppRespuesta newRepuesta = new AppRespuesta();
    //         newRepuesta.setAppUbicacionRelevamiento(appUbicacion);
    //         newRepuesta.setAppRelevamiento(appUbicacion.getAppRelevamiento());
    //         newRepuesta.setOneOrdenS(0L);
    //         newRepuesta.setEstado(false);

    //         appRespuestaService.save(newRepuesta);

    //         return new ResponseEntity<>(appUbicacion, HttpStatus.CREATED);
    //     }
    // }
    @RequestMapping(value = "/save", method = RequestMethod.POST)
        public ResponseEntity<AppRespuesta> save(@RequestBody AppUbicacion newAppUbicacion){

            if(newAppUbicacion.getIdRelevamiento() != null){

                AppUbicacion appUbicacion =  new AppUbicacion();

                // Crear la nueva AppUbicacion
                appUbicacion.setDireccion(newAppUbicacion.getDireccion());
                appUbicacion.setLatitud(newAppUbicacion.getLatitud());
                appUbicacion.setLongitud(newAppUbicacion.getLongitud());


                appUbicacionService.save(appUbicacion);

                // Encontrar el relevamiento a lque va a pertenecer la nueva ubicacion
                Long appRelevamientoId = newAppUbicacion.getIdRelevamiento();
                AppRelevamiento appRelevamiento = appRelevamientoService.get(appRelevamientoId);


                // Crear una instancia de AppUbicacionRelevamiento
                AppUbicacionRelevamiento newAppUbicacionRelevamiento = new AppUbicacionRelevamiento();

                // Asignar la AppUbicacion y AppRelevamiento recibida a la nueva instancia de AppUbicacionRelevamiento
                newAppUbicacionRelevamiento.setAppUbicacion(appUbicacion);
                newAppUbicacionRelevamiento.setAppRelevamiento(appRelevamiento);
                newAppUbicacionRelevamiento.setEvp("");
                newAppUbicacionRelevamiento.setElemento("");
                newAppUbicacionRelevamiento.setAnunciante("");
                newAppUbicacionRelevamiento.setProducto("");
                newAppUbicacionRelevamiento.setReferencias("");

                appUbicacionRelService.save(newAppUbicacionRelevamiento);


                // Crea una AppRespuesta, que es lo que utiliza luego para devolver el AppUbicacionRelevamiento en la ruta "/findAllByRelevamiento/{idRelevamiento}"
                AppRespuesta newRepuesta = new AppRespuesta();
                newRepuesta.setAppUbicacionRelevamiento(newAppUbicacionRelevamiento);
                newRepuesta.setAppRelevamiento(appRelevamiento);
                newRepuesta.setOneOrdenS(0L);
                newRepuesta.setEstado(false);

                appRespuestaService.save(newRepuesta);

                // Devolver una respuesta con el objeto guardado y un código de estado HTTP
                return new ResponseEntity<>(newRepuesta, HttpStatus.CREATED);

            } else {

                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

            }
        }
}
