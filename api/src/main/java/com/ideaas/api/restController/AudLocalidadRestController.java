package com.ideaas.api.restController;

import com.ideaas.services.domain.AudLocalidad;
import com.ideaas.services.service.interfaces.AudLocalidadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/audLocalidad")
public class AudLocalidadRestController {

    private AudLocalidadService audLocalidadService;

    @Autowired
    public AudLocalidadRestController(AudLocalidadService audLocalidadService) {
        this.audLocalidadService = audLocalidadService;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public AudLocalidad get(@PathVariable Long id){
        return audLocalidadService.get(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/list/{pageSize}/{pageNo}/{sortBy}")
    public List<AudLocalidad> findAll(@PathVariable Integer pageSize, @PathVariable Integer pageNo, @PathVariable String sortBy){
        return audLocalidadService.findAll(pageSize, pageNo, sortBy);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/list/{pageSize}/{pageNo}")
    public List<AudLocalidad> findAll(@PathVariable Integer pageSize, @PathVariable Integer pageNo){
        return audLocalidadService.findAll(pageSize, pageNo, "id");
    }


    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/list/{pageSize}")
    public List<AudLocalidad> findAll(@PathVariable Integer pageSize){
        return audLocalidadService.findAll(pageSize, 0, "id");
    }

    @PostMapping
    public ResponseEntity<AudLocalidad> save(@RequestBody AudLocalidad newAudLocalidad){
        AudLocalidad audLocalidad = audLocalidadService.save(newAudLocalidad);

        return new ResponseEntity(audLocalidad, HttpStatus.CREATED);
    }
}
