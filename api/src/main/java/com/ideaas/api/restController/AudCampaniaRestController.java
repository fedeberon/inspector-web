package com.ideaas.api.restController;

import com.ideaas.services.domain.AudCampania;
import com.ideaas.services.service.interfaces.AudCampaniaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/audCampania")
public class AudCampaniaRestController {

    private AudCampaniaService audCampaniaService;

    @Autowired
    public AudCampaniaRestController(AudCampaniaService audCampaniaService) {
        this.audCampaniaService = audCampaniaService;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public AudCampania get(@PathVariable Long id){
        return audCampaniaService.get(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/list/{pageSize}/{pageNo}/{sortBy}")
    public List<AudCampania> findAll(@PathVariable Integer pageSize, @PathVariable Integer pageNo, @PathVariable String sortBy){
        return audCampaniaService.findAll(pageSize, pageNo, sortBy);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/list/{pageSize}/{pageNo}")
    public List<AudCampania> findAll(@PathVariable Integer pageSize, @PathVariable Integer pageNo){
        return audCampaniaService.findAll(pageSize, pageNo, "id");
    }


    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/list/{pageSize}")
    public List<AudCampania> findAll(@PathVariable Integer pageSize){
        return audCampaniaService.findAll(pageSize, 0, "id");
    }

    @PostMapping
    public ResponseEntity<AudCampania> save(@RequestBody AudCampania newAudCampania){
        AudCampania audCampania = audCampaniaService.save(newAudCampania);

        return new ResponseEntity(audCampania, HttpStatus.CREATED);
    }
}
