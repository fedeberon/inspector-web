package com.ideaas.api.restController;

import com.ideaas.services.domain.MapHeatmap;
import com.ideaas.services.request.HeatMapRequest;
import com.ideaas.services.service.interfaces.MapHeatmapService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/mapHeatmap")
public class MapHeatmapRestController {

    private MapHeatmapService mapHeatmapService;

    public MapHeatmapRestController(MapHeatmapService mapHeatmapService) {
        this.mapHeatmapService = mapHeatmapService;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public MapHeatmap get(@PathVariable Long id){
        return mapHeatmapService.get(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/list/{pageSize}/{pageNo}/{sortBy}")
    public List<MapHeatmap> findAll(@PathVariable Integer pageSize, @PathVariable Integer pageNo, @PathVariable String sortBy){
        return mapHeatmapService.findAll(pageSize, pageNo, sortBy);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/list/{pageSize}/{pageNo}")
    public List<MapHeatmap> findAll(@PathVariable Integer pageSize, @PathVariable Integer pageNo){
        return mapHeatmapService.findAll(pageSize, pageNo, "id");
    }


    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/list/{pageSize}")
    public List<MapHeatmap> findAll(@PathVariable Integer pageSize){
        return mapHeatmapService.findAll(pageSize, 0, "id");
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/list")
    public List<MapHeatmap> findAll(@RequestParam HeatMapRequest request){
        return mapHeatmapService.findAll();
    }

    @PostMapping
    public ResponseEntity<MapHeatmap> save(@RequestBody MapHeatmap newMapHeatmap){
        MapHeatmap mapHeatmap = mapHeatmapService.save(newMapHeatmap);

        return new ResponseEntity(mapHeatmap, HttpStatus.CREATED);
    }
}
