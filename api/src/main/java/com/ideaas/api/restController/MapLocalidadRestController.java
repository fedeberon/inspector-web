package com.ideaas.api.restController;

import com.ideaas.services.domain.MapLocalidad;
import com.ideaas.services.service.interfaces.MapLocalidadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/mapLocalidad")
public class MapLocalidadRestController {

    private MapLocalidadService mapLocalidadService;

    @Autowired
    public MapLocalidadRestController(MapLocalidadService mapLocalidadService) {
        this.mapLocalidadService = mapLocalidadService;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public MapLocalidad get(@PathVariable Long id){
        return mapLocalidadService.get(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/list/{pageSize}/{pageNo}/{sortBy}")
    public List<MapLocalidad> findAll(@PathVariable Integer pageSize, @PathVariable Integer pageNo, @PathVariable String sortBy){
        return mapLocalidadService.findAll(pageSize, pageNo, sortBy);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/list/{pageSize}/{pageNo}")
    public List<MapLocalidad> findAll(@PathVariable Integer pageSize, @PathVariable Integer pageNo){
        return mapLocalidadService.findAll(pageSize, pageNo, "id");
    }


    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/list/{pageSize}")
    public List<MapLocalidad> findAll(@PathVariable Integer pageSize){
        return mapLocalidadService.findAll(pageSize, 0, "id");
    }

    @PostMapping
    public ResponseEntity<MapLocalidad> save(@RequestBody MapLocalidad newMapLocalidad){
        MapLocalidad mapLocalidad = mapLocalidadService.save(newMapLocalidad);

        return new ResponseEntity(mapLocalidad, HttpStatus.CREATED);
    }
}
