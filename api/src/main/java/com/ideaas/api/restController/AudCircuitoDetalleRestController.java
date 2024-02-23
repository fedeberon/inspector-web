package com.ideaas.api.restController;

import com.ideaas.services.domain.AudCircuitoDetalle;
import com.ideaas.services.service.interfaces.AudCircuitoDetalleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/audCircuitoDetalle")
public class AudCircuitoDetalleRestController {

    private AudCircuitoDetalleService audCircuitoDetalleService;

    @Autowired
    public AudCircuitoDetalleRestController(AudCircuitoDetalleService audCircuitoDetalleService) {
        this.audCircuitoDetalleService = audCircuitoDetalleService;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public AudCircuitoDetalle get(@PathVariable Long id){
        return audCircuitoDetalleService.get(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/list/{pageSize}/{pageNo}/{sortBy}")
    public List<AudCircuitoDetalle> findAll(@PathVariable Integer pageSize, @PathVariable Integer pageNo, @PathVariable String sortBy){
        return audCircuitoDetalleService.findAll(pageSize, pageNo, sortBy);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/list/{pageSize}/{pageNo}")
    public List<AudCircuitoDetalle> findAll(@PathVariable Integer pageSize, @PathVariable Integer pageNo){
        return audCircuitoDetalleService.findAll(pageSize, pageNo, "id");
    }


    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/list/{pageSize}")
    public List<AudCircuitoDetalle> findAll(@PathVariable Integer pageSize){
        return audCircuitoDetalleService.findAll(pageSize, 0, "id");
    }

    @PostMapping
    public ResponseEntity<AudCircuitoDetalle> save(@RequestBody AudCircuitoDetalle newAudCircuitoDetalle){
        AudCircuitoDetalle audCircuitoDetalle = audCircuitoDetalleService.save(newAudCircuitoDetalle);

        return new ResponseEntity(audCircuitoDetalle, HttpStatus.CREATED);
    }
}
