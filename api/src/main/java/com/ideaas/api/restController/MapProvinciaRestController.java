package com.ideaas.api.restController;

import com.ideaas.services.domain.MapProvincia;
import com.ideaas.services.service.interfaces.MapProvinciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/mapProvincia")
public class MapProvinciaRestController {

    private MapProvinciaService mapProvinciaService;

    @Autowired
    public MapProvinciaRestController(MapProvinciaService mapProvinciaService) {
        this.mapProvinciaService = mapProvinciaService;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public MapProvincia get(@PathVariable Long id){
        return mapProvinciaService.get(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/list/{pageSize}/{pageNo}/{sortBy}")
    public List<MapProvincia> findAll(@PathVariable Integer pageSize, @PathVariable Integer pageNo, @PathVariable String sortBy){
        return mapProvinciaService.findAll(pageSize, pageNo, sortBy);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/list/{pageSize}/{pageNo}")
    public List<MapProvincia> findAll(@PathVariable Integer pageSize, @PathVariable Integer pageNo){
        return mapProvinciaService.findAll(pageSize, pageNo, "id");
    }


    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/list/{pageSize}")
    public List<MapProvincia> findAll(@PathVariable Integer pageSize){
        return mapProvinciaService.findAll(pageSize, 0, "id");
    }

    @PostMapping
    public ResponseEntity<MapProvincia> save(@RequestBody MapProvincia newMapProvincia){
        MapProvincia mapProvincia = mapProvinciaService.save(newMapProvincia);

        return new ResponseEntity(mapProvincia, HttpStatus.CREATED);
    }
}
