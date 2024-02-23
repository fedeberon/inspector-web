package com.ideaas.api.restController;

import com.ideaas.services.domain.Menu;
import com.ideaas.services.service.interfaces.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/menu")
public class MenuRestController {

    private MenuService menuService;

    @Autowired
    public MenuRestController(MenuService menuService) {
        this.menuService = menuService;
    }


    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public Menu get(@PathVariable Long id){
        return menuService.get(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/list/{pageSize}/{pageNo}/{sortBy}")
    public List<Menu> findAll(@PathVariable Integer pageSize, @PathVariable Integer pageNo, @PathVariable String sortBy){
        return menuService.findAll(pageSize, pageNo, sortBy);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/list/{pageSize}/{pageNo}")
    public List<Menu> findAll(@PathVariable Integer pageSize, @PathVariable Integer pageNo){
        return menuService.findAll(pageSize, pageNo, "id");
    }


    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/list/{pageSize}")
    public List<Menu> findAll(@PathVariable Integer pageSize){
        return menuService.findAll(pageSize, 0, "id");
    }

    @PostMapping
    public ResponseEntity<Menu> save(@RequestBody Menu newMenu){
        Menu menu = menuService.save(newMenu);

        return new ResponseEntity(menu, HttpStatus.CREATED);
    }
}
