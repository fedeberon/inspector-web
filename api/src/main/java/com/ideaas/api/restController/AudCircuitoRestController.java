package com.ideaas.api.restController;

import com.ideaas.services.domain.AudCircuito;
import com.ideaas.services.service.interfaces.AudCircuitoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/audCircuito")
//@Api(tags = {"Circuitos Management"})
//@Api(value="onlinestore", description="Operations pertaining to products in Online Store")
public class AudCircuitoRestController {

    private AudCircuitoService audCircuitoService;

    @Autowired
    public AudCircuitoRestController(AudCircuitoService audCircuitoService) {
        this.audCircuitoService = audCircuitoService;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
//    @ApiOperation(value = "Long id", response = AudCircuito.class)
//    @ApiResponses(value = {@ApiResponse(code = 201, message = "Successfully get"),})
    public AudCircuito get(@PathVariable Long id){
        return audCircuitoService.get(id);
    }

//    @ApiOperation(value = "List<AudCircuito>", response = List.class)
//    @ApiResponses(value = {@ApiResponse(code = 201, message = "Successfully get"),})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/list/{pageSize}/{pageNo}/{sortBy}")
    public List<AudCircuito> findAll(@PathVariable Integer pageSize, @PathVariable Integer pageNo, @PathVariable String sortBy){
        return audCircuitoService.findAll(pageSize, pageNo, sortBy);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/list/{pageSize}/{pageNo}")
    public List<AudCircuito> findAll(@PathVariable Integer pageSize, @PathVariable Integer pageNo){
        return audCircuitoService.findAll(pageSize, pageNo, "id");
    }


    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/list/{pageSize}")
    public List<AudCircuito> findAll(@PathVariable Integer pageSize){
        return audCircuitoService.findAll(pageSize, 0, "id");
    }
    @PostMapping("/")
    public ResponseEntity<AudCircuito> save(@RequestBody AudCircuito newAudCircuito){
        AudCircuito audCircuito = audCircuitoService.save(newAudCircuito);

        return new ResponseEntity(audCircuito, HttpStatus.CREATED);
    }



}
