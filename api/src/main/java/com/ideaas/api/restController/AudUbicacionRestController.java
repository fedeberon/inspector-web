package com.ideaas.api.restController;

import com.ideaas.services.domain.AudUbicacion;
import com.ideaas.services.service.interfaces.AudUbicacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/audUbicacion")
public class AudUbicacionRestController {

    private AudUbicacionService audUbicacionService;

    @Autowired
    public AudUbicacionRestController(AudUbicacionService audUbicacionService) {
        this.audUbicacionService = audUbicacionService;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public AudUbicacion get(@PathVariable Long id){
        return audUbicacionService.get(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/list/{pageSize}/{pageNo}/{sortBy}")
    public List<AudUbicacion> findAll(@PathVariable Integer pageSize, @PathVariable Integer pageNo, @PathVariable String sortBy){
        return audUbicacionService.findAll(pageSize, pageNo, sortBy);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/list/{pageSize}/{pageNo}")
    public List<AudUbicacion> findAll(@PathVariable Integer pageSize, @PathVariable Integer pageNo){
        return audUbicacionService.findAll(pageSize, pageNo, "id");
    }


    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/list/{pageSize}")
    public List<AudUbicacion> findAll(@PathVariable Integer pageSize){
        return audUbicacionService.findAll(pageSize, 0, "id");
    }

    @PostMapping
    public ResponseEntity<AudUbicacion> save(@RequestBody AudUbicacion newAudUbicacion){
        AudUbicacion audUbicacion = audUbicacionService.save(newAudUbicacion);

        return new ResponseEntity(audUbicacion, HttpStatus.CREATED);
    }
}
