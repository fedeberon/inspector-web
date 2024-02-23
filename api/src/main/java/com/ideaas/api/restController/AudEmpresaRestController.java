package com.ideaas.api.restController;

import com.ideaas.services.domain.AudEmpresa;
import com.ideaas.services.service.interfaces.AudEmpresaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/audEmpresa")
public class AudEmpresaRestController {

    private AudEmpresaService audEmpresaService;

    @Autowired
    public AudEmpresaRestController(AudEmpresaService audEmpresaService) {
        this.audEmpresaService = audEmpresaService;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public AudEmpresa get(@PathVariable Long id){
        return audEmpresaService.get(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/list/{pageSize}/{pageNo}/{sortBy}")
    public List<AudEmpresa> findAll(@PathVariable Integer pageSize, @PathVariable Integer pageNo, @PathVariable String sortBy){
        return audEmpresaService.findAll(pageSize, pageNo, sortBy);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/list/{pageSize}/{pageNo}")
    public List<AudEmpresa> findAll(@PathVariable Integer pageSize, @PathVariable Integer pageNo){
        return audEmpresaService.findAll(pageSize, pageNo, "id");
    }


    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/list/{pageSize}")
    public List<AudEmpresa> findAll(@PathVariable Integer pageSize){
        return audEmpresaService.findAll(pageSize, 0, "id");
    }

    @PostMapping
    public ResponseEntity<AudEmpresa> save(@RequestBody AudEmpresa newAudEmpresa){
        AudEmpresa audEmpresa = audEmpresaService.save(newAudEmpresa);

        return new ResponseEntity(audEmpresa, HttpStatus.CREATED);
    }
}
