package com.ideaas.api.restController;

import com.ideaas.services.domain.MapBus;
import com.ideaas.services.service.interfaces.MapBusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/mapBus")
public class MapBusRestController {

    private MapBusService mapBusService;

    @Autowired
    public MapBusRestController(MapBusService mapBusService) {
        this.mapBusService = mapBusService;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public MapBus get(@PathVariable Long id){
        return mapBusService.get(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/list/{pageSize}/{pageNo}/{sortBy}")
    public List<MapBus> findAll(@PathVariable Integer pageSize, @PathVariable Integer pageNo, @PathVariable String sortBy){
        return mapBusService.findAll(pageSize, pageNo, sortBy);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/list/{pageSize}/{pageNo}")
    public List<MapBus> findAll(@PathVariable Integer pageSize, @PathVariable Integer pageNo){
        return mapBusService.findAll(pageSize, pageNo, "id");
    }


    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/list/{pageSize}")
    public List<MapBus> findAll(@PathVariable Integer pageSize){
        return mapBusService.findAll(pageSize, 0, "id");
    }

    @PostMapping
    public ResponseEntity<MapBus> save(@RequestBody MapBus newMapBus){
        MapBus mapBus = mapBusService.save(newMapBus);

        return new ResponseEntity(mapBus, HttpStatus.CREATED);
    }
}
