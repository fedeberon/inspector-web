package com.ideaas.web.controller;

import com.google.gson.Gson;
import com.ideaas.services.bean.MapReservationDTO;
import com.ideaas.services.domain.AppRelevamiento;
import com.ideaas.services.domain.AppUbicacionRelevamiento;
import com.ideaas.services.domain.MapElemento;
import com.ideaas.services.domain.MapEmpresa;
import com.ideaas.services.domain.MapFormato;
import com.ideaas.services.domain.MapLocalidad;
import com.ideaas.services.domain.MapMedio;
import com.ideaas.services.domain.MapProvincia;
import com.ideaas.services.domain.MapReservacion;
import com.ideaas.services.service.interfaces.AppRelevamientoService;
import com.ideaas.services.service.interfaces.AppUbicacionRelService;
import com.ideaas.services.service.interfaces.MapElementoService;
import com.ideaas.services.service.interfaces.MapEmpresaService;
import com.ideaas.services.service.interfaces.MapFormatoService;
import com.ideaas.services.service.interfaces.MapLocalidadService;
import com.ideaas.services.service.interfaces.MapMedioService;
import com.ideaas.services.service.interfaces.MapProvinciaService;
import com.ideaas.services.service.interfaces.MapReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("api/relevamiento")
public class AppRelevamientoRestController {

    private AppRelevamientoService appRelevamientoService;

    private MapReservationService mapReservationService;

    private MapLocalidadService mapLocalidadService;

    private MapElementoService mapElementoService;

    private MapEmpresaService mapEmpresaService;

    private MapFormatoService mapFormatoService;

    private MapProvinciaService mapProvinciaService;

    private MapMedioService mapMedioService;

    private AppUbicacionRelService appUbicacionRelService;

    @Autowired
    public AppRelevamientoRestController(final AppRelevamientoService appRelevamientoService,
                                         final MapReservationService mapReservationService,
                                         final MapLocalidadService mapLocalidadService,
                                         final MapElementoService mapElementoService,
                                         final MapEmpresaService mapEmpresaService,
                                         final MapFormatoService mapFormatoService,
                                         final MapProvinciaService mapProvinciaService,
                                         final MapMedioService mapMedioService,
                                         final AppUbicacionRelService appUbicacionRelService) {
        this.appRelevamientoService = appRelevamientoService;
        this.mapReservationService = mapReservationService;
        this.mapLocalidadService = mapLocalidadService;
        this.mapElementoService = mapElementoService;
        this.mapEmpresaService = mapEmpresaService;
        this.mapFormatoService = mapFormatoService;
        this.mapProvinciaService = mapProvinciaService;
        this.mapMedioService = mapMedioService;
        this.appUbicacionRelService = appUbicacionRelService;
    }

    @RequestMapping(value = "/findAllByDates/{startDate}/{endDate}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AppRelevamiento>> searchRelevamientos(@PathVariable String startDate,
                                                                     @PathVariable String endDate){
        List<AppRelevamiento> relevamientos = appRelevamientoService.findAllByDates(startDate, endDate);

        return ResponseEntity.ok(relevamientos);
    }

    @RequestMapping(value = "/ubicacionRelevamiento/findAllByDates/{startDate}/{endDate}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AppUbicacionRelevamiento>> searchUbicacionRelevamientos(@PathVariable String startDate,
                                                                                       @PathVariable String endDate){
        List<AppUbicacionRelevamiento> relevamientos = appUbicacionRelService.searchByDates(startDate, endDate);

        return ResponseEntity.ok(relevamientos);
    }

    @RequestMapping(value = "/reservaciones/updateOrderNumber", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MapReservationDTO> updateOrderNumber(@RequestBody MapReservationDTO reservationDTO) {
        MapReservacion reservacion = mapReservationService.get(reservationDTO.getId());
        reservacion.setOrdenNumber(reservationDTO.getOrdenNumber());
        mapReservationService.save(reservacion);

        return ResponseEntity.status(HttpStatus.OK).body(reservationDTO);
    }

    @RequestMapping(value = "/localidades", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<MapLocalidad>> getLocalidades(){
        List<MapLocalidad> localidades =  mapLocalidadService.findByOrderByDescripcionAsc();
        return ResponseEntity.status(HttpStatus.OK).body(localidades);
    }

    @RequestMapping(value = "/elementos", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<MapElemento>> getElementos(){
        List<MapElemento> elementos =  mapElementoService.findByOrderByDescripcionAsc();
        return ResponseEntity.status(HttpStatus.OK).body(elementos);
    }

    @RequestMapping(value = "/empresas", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getEmpresas(){
        List<MapEmpresa> elementos =  mapEmpresaService.findByOrderByDescripcionAsc();
        List<Map<String, Object>> elementosArray = elementos.stream()
                .map(elemento -> {
                    Map<String, Object> elementoObjeto = new HashMap<>();
                    elementoObjeto.put("id", elemento.getId());
                    elementoObjeto.put("descripcion", elemento.getDescripcion());
                    return elementoObjeto;
                })
                .collect(Collectors.toList());

        String elementosJson = new Gson().toJson(elementosArray);
        return ResponseEntity.status(HttpStatus.OK).body(elementosJson);
    }


    @RequestMapping(value = "/formatos", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getFormatos(){
        List<MapFormato> formatos = mapFormatoService.findByOrderByDescripcionAsc();
        List<Map<String, Object>> elementosArray = formatos.stream()
                .map(elemento -> {
                    Map<String, Object> elementoObjeto = new HashMap<>();
                    elementoObjeto.put("id", elemento.getId());
                    elementoObjeto.put("descripcion", elemento.getDescripcion());
                    return elementoObjeto;
                })
                .collect(Collectors.toList());

        String elementosJson = new Gson().toJson(elementosArray);

        return ResponseEntity.status(HttpStatus.OK).body(elementosJson);
    }


    @RequestMapping(value = "/provincias", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getProvincias(){
        List<MapProvincia> provincias =  mapProvinciaService.findByOrderByDescripcionAsc();
        List<Map<String, Object>> elementosArray = provincias.stream()
                .map(elemento -> {
                    Map<String, Object> elementoObjeto = new HashMap<>();
                    elementoObjeto.put("id", elemento.getId());
                    elementoObjeto.put("descripcion", elemento.getDescripcion());
                    return elementoObjeto;
                })
                .collect(Collectors.toList());

        String elementosJson = new Gson().toJson(elementosArray);

        return ResponseEntity.status(HttpStatus.OK).body(elementosJson);
    }

    @RequestMapping(value = "/medios", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getMedios(){
        List<MapMedio> medios = mapMedioService.findByOrderByDescripcionAsc();
        List<Map<String, Object>> elementosArray = medios.stream()
                .map(elemento -> {
                    Map<String, Object> elementoObjeto = new HashMap<>();
                    elementoObjeto.put("id", elemento.getId());
                    elementoObjeto.put("descripcion", elemento.getDescripcion());
                    return elementoObjeto;
                })
                .collect(Collectors.toList());

        String elementosJson = new Gson().toJson(elementosArray);
        return ResponseEntity.status(HttpStatus.OK).body(elementosJson);
    }

}
