package com.ideaas.api.restController;


import com.ideaas.services.domain.MapUbicacion;
import com.ideaas.services.request.MapUbicacionRequest;
import com.ideaas.services.service.interfaces.MapUbicacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/mapUbicacion")
public class MapUbicacionRestController {

    private MapUbicacionService mapUbicacionService;

    @Autowired
    public MapUbicacionRestController(MapUbicacionService mapUbicacionService) {
        this.mapUbicacionService = mapUbicacionService;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public MapUbicacion get(@PathVariable Long id){
         return mapUbicacionService.get(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/list/{pageSize}/{pageNo}/{sortBy}")
    public List<MapUbicacion> findAll(@PathVariable Integer pageSize, @PathVariable Integer pageNo, @PathVariable String sortBy){
        return mapUbicacionService.findAll(pageSize, pageNo, sortBy);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/list/{pageSize}/{pageNo}")
    public List<MapUbicacion> findAll(@PathVariable Integer pageSize, @PathVariable Integer pageNo){
        return mapUbicacionService.findAll(pageSize, pageNo, "id");
    }


    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/list/{pageSize}")
    public List<MapUbicacion> findAll(@PathVariable Integer pageSize){
        return mapUbicacionService.findAll(pageSize, 0, "id");
    }

    @PostMapping
    public ResponseEntity<MapUbicacion> save(@RequestBody MapUbicacion newMapUbicacion){
        MapUbicacion mapUbicacion = mapUbicacionService.save(newMapUbicacion);

        return new ResponseEntity(mapUbicacion, HttpStatus.CREATED);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/search")
    public List<MapUbicacion> findAll(@RequestBody MapUbicacionRequest mapUbicacionRequest){
        return mapUbicacionService.findAll(mapUbicacionRequest);
    }
}