package com.ideaas.api.restController;

import com.ideaas.services.domain.MapEmpresa;
import com.ideaas.services.service.interfaces.MapEmpresaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/mapEmpresa")
public class MapEmpresaRestController {

    private MapEmpresaService mapEmpresaService;

    @Autowired
    public MapEmpresaRestController(MapEmpresaService mapEmpresaService) {
        this.mapEmpresaService = mapEmpresaService;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public MapEmpresa get(@PathVariable Long id){
        return mapEmpresaService.get(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/list/{pageSize}/{pageNo}/{sortBy}")
    public List<MapEmpresa> findAll(@PathVariable Integer pageSize, @PathVariable Integer pageNo, @PathVariable String sortBy){
        return mapEmpresaService.findAll(pageSize, pageNo, sortBy);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/list/{pageSize}/{pageNo}")
    public List<MapEmpresa> findAll(@PathVariable Integer pageSize, @PathVariable Integer pageNo){
        return mapEmpresaService.findAll(pageSize, pageNo, "id");
    }


    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/list/{pageSize}")
    public List<MapEmpresa> findAll(@PathVariable Integer pageSize){
        return mapEmpresaService.findAll(pageSize, 0, "id");
    }

    @PostMapping
    public ResponseEntity<MapEmpresa> save(@RequestBody MapEmpresa newMapEmpresa){
        MapEmpresa mapEmpresa = mapEmpresaService.save(newMapEmpresa);

        return new ResponseEntity(mapEmpresa, HttpStatus.CREATED);
    }
}
