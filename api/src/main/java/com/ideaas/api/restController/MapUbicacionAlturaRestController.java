package com.ideaas.api.restController;

import com.ideaas.services.domain.MapUbicacionAltura;
import com.ideaas.services.service.interfaces.MapUbicacionAlturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/mapUbicacionAltura")
public class MapUbicacionAlturaRestController {

    private MapUbicacionAlturaService mapUbicacionAlturaService;

    @Autowired
    public MapUbicacionAlturaRestController(MapUbicacionAlturaService mapUbicacionAlturaService) {
        this.mapUbicacionAlturaService = mapUbicacionAlturaService;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public MapUbicacionAltura get(@PathVariable Long id){
        return mapUbicacionAlturaService.get(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/list/{pageSize}/{pageNo}/{sortBy}")
    public List<MapUbicacionAltura> findAll(@PathVariable Integer pageSize, @PathVariable Integer pageNo, @PathVariable String sortBy){
        return mapUbicacionAlturaService.findAll(pageSize, pageNo, sortBy);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/list/{pageSize}/{pageNo}")
    public List<MapUbicacionAltura> findAll(@PathVariable Integer pageSize, @PathVariable Integer pageNo){
        return mapUbicacionAlturaService.findAll(pageSize, pageNo, "id");
    }


    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/list/{pageSize}")
    public List<MapUbicacionAltura> findAll(@PathVariable Integer pageSize){
        return mapUbicacionAlturaService.findAll(pageSize, 0, "id");
    }

    @PostMapping
    public ResponseEntity<MapUbicacionAltura> save(@RequestBody MapUbicacionAltura newMapUbicacionAltura){
        MapUbicacionAltura mapUbicacionAltura = mapUbicacionAlturaService.save(newMapUbicacionAltura);

        return new ResponseEntity(mapUbicacionAltura, HttpStatus.CREATED);
    }

}
