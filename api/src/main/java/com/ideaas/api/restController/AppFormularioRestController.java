package com.ideaas.api.restController;

import com.ideaas.services.bean.AESPasswordEncoder;
import com.ideaas.services.bean.FormRequest;
import com.ideaas.services.domain.AppFormulario;
import com.ideaas.services.domain.AppRespuesta;
import com.ideaas.services.service.interfaces.AppFormularioService;
import com.ideaas.services.service.interfaces.AppRespuestaService;
import com.ideaas.services.service.interfaces.AppUbicacionRelService;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("api/appFormulario")
public class AppFormularioRestController {

    private AppFormularioService appFormularioService;
    private AppRespuestaService appRespuestaService;
    private VelocityEngine velocityEngine;
    private AESPasswordEncoder encoder;

    @Value("${spring.server.url}")
    private String serverUrl;

    public AppFormularioRestController(AppFormularioService appFormularioService, AppRespuestaService appRespuestaService, VelocityEngine velocityEngine) {
        this.appFormularioService = appFormularioService;
        this.appRespuestaService = appRespuestaService;
        this.velocityEngine = velocityEngine;
        this.encoder = new AESPasswordEncoder();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public AppFormulario get(@PathVariable Long id){
        return appFormularioService.get(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/list/{pageSize}/{pageNo}/{sortBy}")
    public List<AppFormulario> findAll(@PathVariable Integer pageSize, @PathVariable Integer pageNo, @PathVariable String sortBy){
        return appFormularioService.findAll(pageSize, pageNo, sortBy);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/list/{pageSize}/{pageNo}")
    public List<AppFormulario> findAll(@PathVariable Integer pageSize, @PathVariable Integer pageNo){
        return appFormularioService.findAll(pageSize, pageNo, "id");
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/list/{pageSize}")
    public List<AppFormulario> findAll(@PathVariable Integer pageSize){
        return appFormularioService.findAll(pageSize, 0, "id");
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<AppFormulario> save(@RequestBody AppFormulario newAppFormulario){
        AppFormulario appFormulario = appFormularioService.save(newAppFormulario);

        return new ResponseEntity(appFormulario, HttpStatus.CREATED);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/getFormTemplate", method = RequestMethod.POST , produces = MediaType.TEXT_HTML_VALUE)
    public String getFormTemplate(@RequestBody FormRequest formRequest) throws InvalidCipherTextException {

        Long idFormulario   = formRequest.getIdFormulario();
        Long idUbicacion    = formRequest.getIdUbicacion();
        Long idRespuesta    = formRequest.getIdRespuesta();
        Long idRelevamiento = formRequest.getIdRelevamiento();

        AppRespuesta respuesta = appRespuestaService.get(idRespuesta);
        String timeStamp   = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
        String tokenOfForm = idFormulario.toString()+ "_" + idUbicacion.toString() + "_" + timeStamp;
        String tokenFormEncrypted = encoder.encrypt(tokenOfForm);

        if(Objects.isNull(respuesta)) respuesta = new AppRespuesta();

        respuesta.setToken(tokenFormEncrypted);
        appRespuestaService.save(respuesta);

        VelocityContext velocityContext = new VelocityContext();

        if(respuesta.getRespuesta() == null || respuesta.getRespuesta() == null || respuesta.getRespuesta().trim().isEmpty() ){
            String formulario = appFormularioService.get(idFormulario).getFormulario();
            velocityContext.put("form", formulario);
        }else{
            velocityContext.put("form", respuesta.getRespuesta());
        }
        velocityContext.put("idRespuesta", idRespuesta);
        velocityContext.put("idUbicacion", idUbicacion);
        velocityContext.put("idRelevamiento", idRelevamiento);
        velocityContext.put("token", tokenFormEncrypted);
        velocityContext.put("serverUrl", serverUrl);
        velocityEngine.init();
        StringWriter stringWriter = new StringWriter();
        velocityEngine.mergeTemplate("templates/formTemplate.vm", "UTF-8", velocityContext, stringWriter);

        return stringWriter.toString();
    }
}
