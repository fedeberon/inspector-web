package com.ideaas.api.restController;

import com.ideaas.services.domain.MapMedio;
import com.ideaas.services.service.interfaces.MapMedioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/mapMedio")
public class MapMedioRestController {

    private MapMedioService mapMedioService;

    @Autowired
    public MapMedioRestController(MapMedioService mapMedioService) {
        this.mapMedioService = mapMedioService;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public MapMedio get(@PathVariable Long id){
        return mapMedioService.get(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/list/{pageSize}/{pageNo}/{sortBy}")
    public List<MapMedio> findAll(@PathVariable Integer pageSize, @PathVariable Integer pageNo, @PathVariable String sortBy){
        return mapMedioService.findAll(pageSize, pageNo, sortBy);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/list/{pageSize}/{pageNo}")
    public List<MapMedio> findAll(@PathVariable Integer pageSize, @PathVariable Integer pageNo){
        return mapMedioService.findAll(pageSize, pageNo, "id");
    }


    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/list/{pageSize}")
    public List<MapMedio> findAll(@PathVariable Integer pageSize){
        return mapMedioService.findAll(pageSize, 0, "id");
    }

    @PostMapping
    public ResponseEntity<MapMedio> save(@RequestBody MapMedio newMapMedio){
        MapMedio mapMedio = mapMedioService.save(newMapMedio);

        return new ResponseEntity(mapMedio, HttpStatus.CREATED);
    }
}
