package com.ideaas.api.restController;

import com.ideaas.services.domain.MapFormato;
import com.ideaas.services.service.interfaces.MapFormatoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("api/mapFormato")
public class MapFormatoRestController {

    private MapFormatoService mapFormatoService;

    @Autowired
    public MapFormatoRestController(MapFormatoService mapFormatoService) {
        this.mapFormatoService = mapFormatoService;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public MapFormato get(@PathVariable Long id){
        return mapFormatoService.get(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/list/{pageSize}/{pageNo}/{sortBy}")
    public List<MapFormato> findAll(@PathVariable Integer pageSize, @PathVariable Integer pageNo, @PathVariable String sortBy){
        return mapFormatoService.findAll(pageSize, pageNo, sortBy);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/list/{pageSize}/{pageNo}")
    public List<MapFormato> findAll(@PathVariable Integer pageSize, @PathVariable Integer pageNo){
        return mapFormatoService.findAll(pageSize, pageNo, "id");
    }


    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/list/{pageSize}")
    public List<MapFormato> findAll(@PathVariable Integer pageSize){
        return mapFormatoService.findAll(pageSize, 0, "id");
    }

    @PostMapping
    public ResponseEntity<MapFormato> save(@RequestBody MapFormato newMapFormato){
        MapFormato mapFormato = mapFormatoService.save(newMapFormato);

        return new ResponseEntity(mapFormato, HttpStatus.CREATED);
    }
}
