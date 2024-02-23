package com.ideaas.api.restController;

import com.ideaas.services.domain.UsuarioMenu;
import com.ideaas.services.service.interfaces.UsuarioMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/usuarioMenu")
public class UsuarioMenuRestController {

    private UsuarioMenuService usuarioMenuService;

    @Autowired
    public UsuarioMenuRestController(UsuarioMenuService usuarioMenuService) {
        this.usuarioMenuService = usuarioMenuService;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/list/{pageSize}/{pageNo}/{sortBy}")
    public List<UsuarioMenu> findAll(@PathVariable Integer pageSize, @PathVariable Integer pageNo, @PathVariable String sortBy){
        return usuarioMenuService.findAll(pageSize, pageNo, sortBy);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/list/{pageSize}/{pageNo}")
    public List<UsuarioMenu> findAll(@PathVariable Integer pageSize, @PathVariable Integer pageNo){
        return usuarioMenuService.findAll(pageSize, pageNo, "usuarioMenuId");
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/list/{pageSize}")
    public List<UsuarioMenu> findAll(@PathVariable Integer pageSize){
        return usuarioMenuService.findAll(pageSize, 0, "usuarioMenuId");
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/list")
    public List<UsuarioMenu> findAll(){
        return usuarioMenuService.findAll();
    }

    @PostMapping
    public ResponseEntity<UsuarioMenu> save(@RequestBody UsuarioMenu newUsuarioMenu){
        UsuarioMenu usuarioMenu = usuarioMenuService.save(newUsuarioMenu);

        return new ResponseEntity(usuarioMenu, HttpStatus.CREATED);
    }
}
