package com.ideaas.api.restController;

import com.ideaas.services.domain.MapPoiEntidad;
import com.ideaas.services.service.interfaces.MapPoiEntidadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/mapPoiEntidad")
public class MapPoiEntidadRestController {

    private MapPoiEntidadService mapPoiEntidadService;

    @Autowired
    public MapPoiEntidadRestController(MapPoiEntidadService mapPoiEntidadService) {
        this.mapPoiEntidadService = mapPoiEntidadService;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public MapPoiEntidad get(@PathVariable Long id){
        return mapPoiEntidadService.get(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/list/{pageSize}/{pageNo}/{sortBy}")
    public List<MapPoiEntidad> findAll(@PathVariable Integer pageSize, @PathVariable Integer pageNo, @PathVariable String sortBy){
        return mapPoiEntidadService.findAll(pageSize, pageNo, sortBy);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/list/{pageSize}/{pageNo}")
    public List<MapPoiEntidad> findAll(@PathVariable Integer pageSize, @PathVariable Integer pageNo){
        return mapPoiEntidadService.findAll(pageSize, pageNo, "id");
    }


    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/list/{pageSize}")
    public List<MapPoiEntidad> findAll(@PathVariable Integer pageSize){
        return mapPoiEntidadService.findAll(pageSize, 0, "id");
    }

    @PostMapping
    public ResponseEntity<MapPoiEntidad> save(@RequestBody MapPoiEntidad newMapPoiEntidad){
        MapPoiEntidad mapPoiEntidad = mapPoiEntidadService.save(newMapPoiEntidad);

        return new ResponseEntity(mapPoiEntidad, HttpStatus.CREATED);
    }
}
