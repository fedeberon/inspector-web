package com.ideaas.api.restController;

import com.ideaas.services.domain.AppRelevamiento;
import com.ideaas.services.service.interfaces.AppRelevamientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("api/appRelevamiento")
@CrossOrigin(origins = "http://localhost:8080")
public class AppRelevamientoRestController {

    private AppRelevamientoService appRelevamientoService;

    @Autowired
    public AppRelevamientoRestController(AppRelevamientoService appRelevamientoService){
        this.appRelevamientoService = appRelevamientoService;
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/findAllByUser/{idUsuario}" , method = RequestMethod.GET)
    public List<AppRelevamiento> findAllByIdUsuario(@PathVariable Long idUsuario){
        return appRelevamientoService.findAllByUsuario(idUsuario);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/findAllByDates/{startDate}/{endDate}" , method = RequestMethod.GET)
    public ResponseEntity<List<AppRelevamiento>> findAllByDates(@PathVariable String startDate, @PathVariable String endDate){
        List<AppRelevamiento> relevamientos = appRelevamientoService.findAllByDates(startDate, endDate);

         return ResponseEntity.ok(relevamientos);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/findAllToShow/{idUsuario}" , method = RequestMethod.GET)
    public List<AppRelevamiento> findAllToShow(@PathVariable Long idUsuario){
        ArrayList<Long> estados = new ArrayList<>(Arrays.asList(2L, 3L)); //app_estado_relevamiento, idEstado = 2 es para "Asignado"
                                                                          //y idEstado = 3 es para "Finalizado"

        return appRelevamientoService.findAllByUsuarioAndEstadoToShow(idUsuario , estados);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/findById/{idRelevamiento}", method = RequestMethod.GET)
    public ResponseEntity<AppRelevamiento> findById(@PathVariable Long idRelevamiento) {
        AppRelevamiento appRelevamiento = appRelevamientoService.get(idRelevamiento);

        if (appRelevamiento != null) {
            return ResponseEntity.ok(appRelevamiento);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/updateGeocodingRouteId/{idRelevamiento}/{newIdGeocodingRuta}", method = RequestMethod.PUT)
    public ResponseEntity<AppRelevamiento> updateGeocodingRouteId(
            @PathVariable Long idRelevamiento,
            @PathVariable String newIdGeocodingRuta) {

        AppRelevamiento appRelevamiento = appRelevamientoService.get(idRelevamiento);

        if (appRelevamiento != null) {
            appRelevamiento.setId_geocoding_ruta_relevamiento(newIdGeocodingRuta);
            appRelevamientoService.save(appRelevamiento);

            return ResponseEntity.ok(appRelevamiento);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
