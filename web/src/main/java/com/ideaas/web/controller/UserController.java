package com.ideaas.web.controller;

import com.ideaas.services.bean.AESPasswordEncoder;
import com.ideaas.services.bean.UsuarioWrapper;
import com.ideaas.services.dao.UsuarioMenuDao;
import com.ideaas.services.domain.*;
import com.ideaas.services.enums.TipoUsuarioEnum;
import com.ideaas.services.service.interfaces.*;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by federicoberon on 08/10/2019.
 */
//TODO Revisar
@SuppressWarnings("Duplicates")
@Controller
@RequestMapping("usuario")
@Secured({"ROLE_ADMINISTRADOR"})
public class UserController {

    private UsuarioService usuarioService;
    private TipoUsuarioService tipoUsuarioService;
    private ContratoService contratoService;
    private AESPasswordEncoder encoder;
    private MenuService menuService;
    private UsuarioMenuService usuarioMenuService;
    private UsuarioMenuDao usuarioMenuDao;
    private MapEmpresaService mapEmpresaService;
    private MapElementoService mapElementoService;
    private StockService stockService;


    @Autowired
    public UserController(UsuarioService usuarioService , TipoUsuarioService tipoUsuarioService, ContratoService contratoService, MenuService menuService ,UsuarioMenuService usuarioMenuService , UsuarioMenuDao usuarioMenuDao, MapEmpresaService mapEmpresaService, MapElementoService mapElementoService, StockService stockService) {
        this.usuarioService = usuarioService;
        this.tipoUsuarioService = tipoUsuarioService;
        this.contratoService = contratoService;
        this.encoder = new AESPasswordEncoder();
        this.menuService = menuService;
        this.usuarioMenuService = usuarioMenuService;
        this.usuarioMenuDao = usuarioMenuDao;
        this.mapEmpresaService = mapEmpresaService;
        this.mapElementoService = mapElementoService;
        this.stockService = stockService;
    }

    @RequestMapping("listComplete")
    public String findAll(Model model){
        List<Usuario> users = usuarioService.findAll();
        users.forEach(user->{
            String passwordDecrypt = null;
            try {
                passwordDecrypt = encoder.decrypt(user.getPassword());
            } catch (InvalidCipherTextException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            user.setPassword(passwordDecrypt);
        });
        model.addAttribute("usuarios", users );

        return "usuario/list";
    }

    @RequestMapping("list")
    public String findByEnabledUsers(Model model){
        List<Usuario> users = usuarioService.findByEstadoNot("B");
        users.forEach(user->{
            String passwordDecrypt = null;
            try {
                passwordDecrypt = encoder.decrypt(user.getPassword());
            } catch (InvalidCipherTextException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            user.setPassword(passwordDecrypt);
        });
        model.addAttribute("usuarios", users );

        return "usuario/list";
    }

    @GetMapping("{id}")
    public String show(@PathVariable Long id, Model model) throws UnsupportedEncodingException, InvalidCipherTextException {
        Usuario usuario = usuarioService.get(id);

        String passwordDecrypt = null;

        passwordDecrypt = encoder.decrypt(usuario.getPassword());

        usuario.setPassword(passwordDecrypt);

        model.addAttribute("usuario", usuario);

        return "usuario/show";
    }

    FieldError usernameExist = new FieldError(
"usuario" , "username" , "El usuario ya existe"
    );
    FieldError emptyUsername = new FieldError(
"usuario" , "username" , "Debes completar este campo"
    );
    FieldError emptyPassword = new FieldError(
"usuario" , "password" , "Debes completar este campo"
    );
    FieldError emptyModule = new FieldError(
            "usuario" , "UsuarioMenuRequest.modulos" , "Debes seleccionar al menos un modulo"
    );
    FieldError emptyEmpresas = new FieldError(
            "mapEmpresa" , "UsuarioMenuRequest.mapEmpresa" , "Debes seleccionar al menos una empresa"
    );

    @RequestMapping(value = "save" , method = RequestMethod.POST)
    public String save(@ModelAttribute UsuarioWrapper usuarioWrapper, BindingResult bindingResult, RedirectAttributes redirectAttributes) throws InvalidCipherTextException {
        Usuario usuario =  usuarioWrapper.getUsuario();

        if( usuarioService.getByUsername(usuario.getUsername()) != null){
            bindingResult.addError(usernameExist);

        }
        if(usuario.getUsername().isEmpty()){
            bindingResult.addError(emptyUsername);

        }
        if(usuario.getPassword().isEmpty()){
            bindingResult.addError(emptyPassword);

        }

        // if(usuario.getUsuarioMenuRequest().getModulos().length == 0){
        //     bindingResult.addError(emptyModule);
        // }

        if(!usuario.isTipo(TipoUsuarioEnum.ROLE_ADMINISTRADOR_OOH) && usuario.getEmpresas() != null){
            usuario.getEmpresas().clear();
        } else if(usuario.isTipo(TipoUsuarioEnum.ROLE_ADMINISTRADOR_OOH)) {
            if(usuario.getEmpresas() != null && usuario.getEmpresas().isEmpty()) {
                bindingResult.addError(emptyEmpresas);
            } else {
                usuario.setEmpresas(Arrays.stream(usuarioWrapper.getEmpresasIDs().split(",")).map(id -> this.mapEmpresaService.get(Long.valueOf(id))).collect(Collectors.toList()));
            }
        }

        if(bindingResult.hasErrors()) {
            return "usuario/create";
        }

        usuario.setPassword(encoder.encrypt(usuario.getPassword()));

        String expirationDate = LocalDate.now().plusMonths(3).toString();
        usuario.setFechaVencimiento(expirationDate);

        usuarioService.save(usuario);
        redirectAttributes.addAttribute("id", usuario.getId());

        List<UsuarioMenu> usuarioMenus = new ArrayList<>();

        // for (Long modulo : usuario.getUsuarioMenuRequest().getModulos()) {
        //     usuarioMenus.add(new UsuarioMenu(new UsuarioMenuId(usuario.getId(), modulo)));
        // }

        usuarioMenuService.saveAll(usuarioMenus);

        return "redirect:/usuario/{id}";

    }

    @RequestMapping(value = "update" , method = RequestMethod.POST)
    public String update(@ModelAttribute UsuarioWrapper usuarioWrapper, RedirectAttributes redirectAttributes) throws InvalidCipherTextException {
        Usuario usuario =  usuarioWrapper.getUsuario();

        usuario.setPassword(encoder.encrypt(usuario.getPassword()));

        if(!usuario.isTipo(TipoUsuarioEnum.ROLE_ADMINISTRADOR_OOH) && usuario.getEmpresas() != null){
            usuario.getEmpresas().clear();
        } else if(usuario.isTipo(TipoUsuarioEnum.ROLE_ADMINISTRADOR_OOH)&&usuarioWrapper.getEmpresasIDs()!=null) {
            usuario.setEmpresas(Arrays.stream(usuarioWrapper.getEmpresasIDs().split(",")).map(id -> this.mapEmpresaService.get(Long.valueOf(id))).collect(Collectors.toList()));
        }

        usuarioService.save(usuario);
        redirectAttributes.addAttribute("id", usuario.getId());

        List<UsuarioMenu> recordsToDelete  = usuarioMenuService.findByUsuarioMenuId_IdUsuario(usuario.getId());
        usuarioMenuDao.deleteAll(recordsToDelete);

        List<UsuarioMenu> usuarioMenus = new ArrayList<>();

        for (Long modulo : usuario.getUsuarioMenuRequest().getModulos()) {
            usuarioMenus.add(new UsuarioMenu(new UsuarioMenuId(usuario.getId(), modulo)));
        }
        usuarioMenuService.saveAll(usuarioMenus);

        return "redirect:/usuario/{id}";
    }

    @GetMapping("create")
    public String create() {
        return "usuario/create";
    }

    @RequestMapping("update")
    public String update(@RequestParam Long id, Model model) {
        Usuario usuario = usuarioService.get(id);
        String passwordDecrypt = null;
        try {
            passwordDecrypt = encoder.decrypt(usuario.getPassword());
        } catch (InvalidCipherTextException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        usuario.setPassword(passwordDecrypt);

        List<UsuarioMenu> usuarioMenuList = usuarioMenuService.findByUsuarioMenuId_IdUsuario(id);

        UsuarioWrapper uw = new UsuarioWrapper();

        uw.setUsuario(usuario);
        uw.setEmpresasIDs(String.valueOf(usuario.getEmpresas().stream().map(empresa -> empresa.getId().toString()).reduce( (prev, next) -> prev +","+ next )));

        model.addAttribute("usuarioWrapper", uw);
        model.addAttribute("modulosUsuario" , usuarioMenuList);

        return "usuario/update";
    }

    @RequestMapping("dropState")
    public String dropState(@RequestParam Long id, Model model, RedirectAttributes redirectAttributes){
        Usuario usuario = usuarioService.get(id);
        usuario.setEstado("B");
        usuarioService.save(usuario);
        redirectAttributes.addAttribute("id", usuario.getId());

        return "redirect:/usuario/{id}";
    }

    @RequestMapping("upState")
    public String upState(@RequestParam Long id, Model model, RedirectAttributes redirectAttributes){
        Usuario usuario = usuarioService.get(id);
        usuario.setEstado("A");
        usuarioService.save(usuario);
        redirectAttributes.addAttribute("id", usuario.getId());

        return "redirect:/usuario/{id}";
    }

    @RequestMapping(value ="authenticate" , method = RequestMethod.POST)
    public String auth(@RequestParam  String authorization , @RequestParam String token , HttpServletRequest request){

        String username = authorization.substring(6);
        UserDetails userDetails = usuarioService.loadUserByUsername(username);

        if (Objects.nonNull(userDetails) && userDetails.getPassword().equals(token)) {
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            return "redirect:/dashboard/panel";
        }

        else return "redirect:/login";
    }

    @ModelAttribute("usuarioWrapper")
    public UsuarioWrapper get(){
        return new UsuarioWrapper();
    }

    // @ModelAttribute("usuario")
    // public Usuario get(){
    //    return new Usuario();
    // }

    @ModelAttribute("tiposUsuarios")
    public List<TipoUsuario> tiposUsuario(){
        return tipoUsuarioService.findAll();
    }

    @ModelAttribute("contratos")
    public List<Contrato> contratos(){
        return contratoService.findAll();
    }

    @ModelAttribute("contratosInversion")
    public List<Contrato> contratosInversion(){
        return contratoService.findByEstadoNotAndTipoContratoSorted("B", "I");
    }

    @ModelAttribute("contratosAuditoria")
    public List<Contrato> contratosAuditoria(){
        return contratoService.findByEstadoNotAndTipoContratoSorted("B", "A");
    }

    @ModelAttribute("contratosMapping")
    public List<Contrato> contratosMapping(){
        return contratoService.findByEstadoNotAndTipoContratoSorted("B", "M");
    }

    @ModelAttribute("empresas")
    public List<MapEmpresa> contratosEmpresas(){
        return mapEmpresaService.findAll();
    }

    @ModelAttribute("modulos")
    public List<Menu> modulos(){
        return menuService.findAll();
    }

}
