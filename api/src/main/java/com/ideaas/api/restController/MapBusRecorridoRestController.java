package com.ideaas.api.restController;

import com.ideaas.services.domain.MapBusRecorrido;
import com.ideaas.services.service.interfaces.MapBusRecorridoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/mapBusRecorrido")
public class MapBusRecorridoRestController {

    private MapBusRecorridoService mapBusRecorridoService;

    @Autowired
    public MapBusRecorridoRestController(MapBusRecorridoService mapBusRecorridoService) {
        this.mapBusRecorridoService = mapBusRecorridoService;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public MapBusRecorrido get(@PathVariable Long id){ return mapBusRecorridoService.get(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/list/{pageSize}/{pageNo}/{sortBy}")
    public List<MapBusRecorrido> findAll(@PathVariable Integer pageSize, @PathVariable Integer pageNo, @PathVariable String sortBy){
        return mapBusRecorridoService.findAll(pageSize, pageNo, sortBy);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/list/{pageSize}/{pageNo}")
    public List<MapBusRecorrido> findAll(@PathVariable Integer pageSize, @PathVariable Integer pageNo){
        return mapBusRecorridoService.findAll(pageSize, pageNo, "id");
    }


    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/list/{pageSize}")
    public List<MapBusRecorrido> findAll(@PathVariable Integer pageSize){
        return mapBusRecorridoService.findAll(pageSize, 0, "id");
    }

    @PostMapping
    public ResponseEntity<MapBusRecorrido> save(@RequestBody MapBusRecorrido newMapBusRecorrido){
        MapBusRecorrido mapBusRecorrido = mapBusRecorridoService.save(newMapBusRecorrido);

        return new ResponseEntity(mapBusRecorrido, HttpStatus.CREATED);
    }

}
