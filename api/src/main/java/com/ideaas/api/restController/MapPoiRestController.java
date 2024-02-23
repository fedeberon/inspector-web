package com.ideaas.api.restController;

import com.ideaas.services.domain.MapPoi;
import com.ideaas.services.service.interfaces.MapPoiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/mapPoi")
public class MapPoiRestController {

    private MapPoiService mapPoiService;

    @Autowired
    public MapPoiRestController(MapPoiService mapPoiService) {
        this.mapPoiService = mapPoiService;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public MapPoi get(@PathVariable Long id){
        return mapPoiService.get(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/list/{pageSize}/{pageNo}/{sortBy}")
    public List<MapPoi> findAll(@PathVariable Integer pageSize, @PathVariable Integer pageNo, @PathVariable String sortBy){
        return mapPoiService.findAll(pageSize, pageNo, sortBy);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/list/{pageSize}/{pageNo}")
    public List<MapPoi> findAll(@PathVariable Integer pageSize, @PathVariable Integer pageNo){
        return mapPoiService.findAll(pageSize, pageNo, "id");
    }


    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/list/{pageSize}")
    public List<MapPoi> findAll(@PathVariable Integer pageSize){
        return mapPoiService.findAll(pageSize, 0, "id");
    }

    @PostMapping
    public ResponseEntity<MapPoi> save(@RequestBody MapPoi newMapPoi){
        MapPoi mapPoi = mapPoiService.save(newMapPoi);

        return new ResponseEntity(mapPoi, HttpStatus.CREATED);
    }
}
