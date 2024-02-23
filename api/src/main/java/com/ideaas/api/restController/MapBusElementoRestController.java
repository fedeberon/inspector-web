package com.ideaas.api.restController;

import com.ideaas.services.domain.MapBusElemento;
import com.ideaas.services.service.interfaces.MapBusElementoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/mapBusElemento")
public class MapBusElementoRestController {

    private MapBusElementoService mapBusElementoService;

    @Autowired
    public MapBusElementoRestController(MapBusElementoService mapBusElementoService) {
        this.mapBusElementoService = mapBusElementoService;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public MapBusElemento get(@PathVariable Long id){
        return mapBusElementoService.get(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/list/{pageSize}/{pageNo}/{sortBy}")
    public List<MapBusElemento> findAll(@PathVariable Integer pageSize, @PathVariable Integer pageNo, @PathVariable String sortBy){
        return mapBusElementoService.findAll(pageSize, pageNo, sortBy);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/list/{pageSize}/{pageNo}")
    public List<MapBusElemento> findAll(@PathVariable Integer pageSize, @PathVariable Integer pageNo){
        return mapBusElementoService.findAll(pageSize, pageNo, "id");
    }


    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/list/{pageSize}")
    public List<MapBusElemento> findAll(@PathVariable Integer pageSize){
        return mapBusElementoService.findAll(pageSize, 0, "id");
    }

    @PostMapping
    public ResponseEntity<MapBusElemento> save(@RequestBody MapBusElemento newMapBusElemento){
        MapBusElemento mapBusElemento = mapBusElementoService.save(newMapBusElemento);

        return new ResponseEntity(mapBusElemento, HttpStatus.CREATED);
    }
}
