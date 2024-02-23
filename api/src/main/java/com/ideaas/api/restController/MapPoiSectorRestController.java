package com.ideaas.api.restController;

import com.ideaas.services.domain.MapPoiSector;
import com.ideaas.services.service.interfaces.MapPoiSectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/mapPoiSector")
public class MapPoiSectorRestController {

    private MapPoiSectorService mapPoiSectorService;

    @Autowired
    public MapPoiSectorRestController(MapPoiSectorService mapPoiSectorService) {
        this.mapPoiSectorService = mapPoiSectorService;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public MapPoiSector get(@PathVariable Long id){
        return mapPoiSectorService.get(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/list/{pageSize}/{pageNo}/{sortBy}")
    public List<MapPoiSector> findAll(@PathVariable Integer pageSize, @PathVariable Integer pageNo, @PathVariable String sortBy){
        return mapPoiSectorService.findAll(pageSize, pageNo, sortBy);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/list/{pageSize}/{pageNo}")
    public List<MapPoiSector> findAll(@PathVariable Integer pageSize, @PathVariable Integer pageNo){
        return mapPoiSectorService.findAll(pageSize, pageNo, "id");
    }


    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/list/{pageSize}")
    public List<MapPoiSector> findAll(@PathVariable Integer pageSize){
        return mapPoiSectorService.findAll(pageSize, 0, "id");
    }

    @PostMapping
    public ResponseEntity<MapPoiSector> save(@RequestBody MapPoiSector newMapPoiSector){
        MapPoiSector mapPoiSector = mapPoiSectorService.save(newMapPoiSector);

        return new ResponseEntity(mapPoiSector, HttpStatus.CREATED);
    }
}
